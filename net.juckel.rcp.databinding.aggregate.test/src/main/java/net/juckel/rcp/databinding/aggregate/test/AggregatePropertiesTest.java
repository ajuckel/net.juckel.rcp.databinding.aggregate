/*******************************************************************************
 * Copyright (c) 2010 Anthony W. Juckel
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Anthony W. Juckel - initial API and implementation
 ******************************************************************************/

package net.juckel.rcp.databinding.aggregate.test;

import static org.junit.Assert.assertEquals;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.juckel.rcp.databinding.aggregate.AggregateProperties;

import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.core.databinding.observable.set.ListToSetAdapter;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.property.Properties;
import org.junit.Before;
import org.junit.Test;

public class AggregatePropertiesTest {

	private Realm realm = new PassthroughRealm();
	private List<Bean> beanList;

	@Before
	public void setUp() {
		this.beanList = new ArrayList<Bean>(5);
		for (int i = 1; i <= 5; i++) {
			this.beanList.add(new Bean(i));
		}
	}

	@Test
	public void testSum() {
		Realm.runWithDefault(realm, new Runnable() {
			public void run() {
				WritableList obList = new WritableList(beanList, Bean.class);
				IObservableValue value = AggregateProperties.sum(
						Properties.selfList(Bean.class).values(
								BeanProperties.value("value", Bean.class)))
						.observe(beanList);
				assertEquals(15.0, value.getValue());
				Bean b = beanList.get(0);
				double val = b.getValue();
				b.setValue(5);
				assertEquals(15.0 - val + 5, value.getValue());
				obList.add(new Bean(6));
				// Capturing some misleading behavior. Our observable value
				// isn't listening to this observable list, and ArrayList
				// doesn't support notifications, so we don't change the
				// aggregation if a value is added.
				assertEquals(15.0 - val + 5, value.getValue());
				obList.remove(0);
				assertEquals(15.0 - val + 5, value.getValue());
				// same if an element is removed.
			}
		});
	}

	@Test
	public void testAverage() {
		Realm.runWithDefault(realm, new Runnable() {
			public void run() {
				IObservableValue value = AggregateProperties.average(
						Properties.selfList(Bean.class).values(
								BeanProperties.value("value", Bean.class)))
						.observe(beanList);
				assertEquals(3.0d, ((Double) value.getValue()).doubleValue(),
						0.0d);
				Bean b = beanList.get(0);
				b.setValue(5.0);
				assertEquals(3.8, ((Double) value.getValue()).doubleValue(),
						0.0d);
			}
		});
	}

	@Test
	public void testNth() {
		Realm.runWithDefault(realm, new Runnable() {
			public void run() {
				IObservableValue zero = AggregateProperties.nth(0,
						Properties.selfList(Bean.class)).observe(beanList);

				IObservableValue four = AggregateProperties.nth(4,
						Properties.selfList(Bean.class)).observe(beanList);

				assertEquals(beanList.get(0), zero.getValue());
				assertEquals(beanList.get(4), four.getValue());
			}
		});
	}

	@Test
	public void testListening() {
		Realm.runWithDefault(realm, new Runnable() {
			public void run() {
				// If we instead listen to something that provides
				// notifications, we should get better results. Note that the
				// javabeans notification crap for "indexed properties" is
				// complete crap.
				Bean b = new Bean(1.0);
				IObservableValue value = AggregateProperties.sum(
						BeanProperties.list("values", Bean.class)).observe(b);
				final int[] updateCalls = new int[1];
				value.addChangeListener(new IChangeListener() {
					public void handleChange(ChangeEvent event) {
						updateCalls[0]++;
					}
				});
				assertEquals(0, updateCalls[0]);
				assertEquals(10.0, (Double) value.getValue(), 0.0);
				b.setValues(0, 1);
				assertEquals(1, updateCalls[0]);
				assertEquals(11.0, (Double) value.getValue(), 0.0);
				b.setValues(5, 10);
				assertEquals(2, updateCalls[0]);
				assertEquals(21.0, (Double) value.getValue(), 0.0);
			}
		});
	}

	@Test
	public void testWeightedAverageCalc() {
		Realm.runWithDefault(realm, new Runnable() {
			public void run() {
				IObservableValue observableWeightedAverage = AggregateProperties
						.weightedAverage(
								Properties.selfList(Bean.class).values(
										BeanProperties.value("value")),
								Properties.selfList(Bean.class).values(
										BeanProperties.value("weight")))
						.observe(beanList);

				// Beans default to a weight of 1, so we should have a simple
				// average.
				assertEquals(3.0d, ((Double) observableWeightedAverage
						.getValue()).doubleValue(), 0.0d);

				// Now toy with weights and make sure the weighted average
				// updates appropriately.

				// Set weight of 5 to 5 times the weight of the other values.
				beanList.get(4).setWeight(5.0);
				assertEquals(3.888889, ((Double) observableWeightedAverage
						.getValue()).doubleValue(), 0.000001d);
				beanList.get(3).setWeight(4.0);
				beanList.get(2).setWeight(3.0);
				beanList.get(1).setWeight(2.0);

				assertEquals(3.666667, ((Double) observableWeightedAverage
						.getValue()).doubleValue(), 0.000001d);
			}
		});
	}

	@Test
	public void testMinAndMax() {
		Realm.runWithDefault(realm, new Runnable() {
			public void run() {
				IObservableValue minValue = AggregateProperties.min(
						Properties.selfList(Bean.class)).observe(beanList);
				IObservableValue maxValue = AggregateProperties.max(
						Properties.selfList(Bean.class)).observe(beanList);
				assertEquals(beanList.get(0), minValue.getValue());
				assertEquals(beanList.get(4), maxValue.getValue());

				// Now make the second value have a weight of 0 (it should be
				// the new min) and the third value have a weight of 100 (it
				// should be the new max). Make sure we get both updates, and
				// that getValue returns the correct results now.
				final int[] changeCount = new int[2];
				minValue.addChangeListener(new IChangeListener() {
					public void handleChange(ChangeEvent event) {
						changeCount[0]++;
					}
				});
				maxValue.addChangeListener(new IChangeListener() {
					public void handleChange(ChangeEvent event) {
						changeCount[1]++;
					}
				});

				// Counter intuitive that this test should pass, but I'm
				// capturing the broken current behavior as documented in the
				// java docs. Updating properties that affect the natural
				// ordering doesn't update the aggregation.
				beanList.get(1).setWeight(0);
				assertEquals(0, changeCount[0]);
				assertEquals(0, changeCount[1]);
				assertEquals(beanList.get(0), minValue.getValue());
				assertEquals(beanList.get(4), maxValue.getValue());
			}
		});
	}

	@Test
	public void testObservableList() {
		Realm.runWithDefault(realm, new Runnable() {
			public void run() {
				IObservableList observableBeanList = new WritableList(beanList,
						Bean.class);
				IObservableList list = AggregateProperties.sum(
						BeanProperties.list("values", Integer.class))
						.observeDetail(observableBeanList);
				// We should now have an observable list where each element is
				// an observable value
				list.addChangeListener(new IChangeListener() {
					public void handleChange(ChangeEvent event) {
					}
				});
				assertEquals(10.0, (Double) list.get(0), 0.0d);
				assertEquals(20.0, (Double) list.get(1), 0.0d);
				assertEquals(30.0, (Double) list.get(2), 0.0d);
				assertEquals(40.0, (Double) list.get(3), 0.0d);
				assertEquals(50.0, (Double) list.get(4), 0.0d);
				beanList.get(0).setValues(new Integer[] { 1, 1 });
				assertEquals(2.0, (Double) list.get(0), 0.0d);
			}
		});
	}

	@Test
	public void testObservableMap() {
		Realm.runWithDefault(realm, new Runnable() {
			public void run() {
				IObservableList observableBeanList = new WritableList(beanList,
						Bean.class);
				IObservableSet observableBeanSet = new ListToSetAdapter(
						observableBeanList);
				IObservableMap map = AggregateProperties.sum(
						BeanProperties.list("values", Integer.class))
						.observeDetail(observableBeanSet);
				map.addChangeListener(new IChangeListener() {
					public void handleChange(ChangeEvent event) {
					}
				});
				assertEquals(10.0, (Double) map.get(beanList.get(0)), 0.0d);
				assertEquals(20.0, (Double) map.get(beanList.get(1)), 0.0d);
				assertEquals(30.0, (Double) map.get(beanList.get(2)), 0.0d);
				assertEquals(40.0, (Double) map.get(beanList.get(3)), 0.0d);
				assertEquals(50.0, (Double) map.get(beanList.get(4)), 0.0d);
				beanList.get(0).setValues(new Integer[] { 1, 1 });
				assertEquals(2.0, (Double) map.get(beanList.get(0)), 0.0d);
			}
		});
	}

	public class Bean implements Comparable<Bean> {
		private double value;
		private double weight;
		private List<Integer> values;
		private PropertyChangeSupport pcs;

		public void addPropertyChangeListener(PropertyChangeListener listener) {
			pcs.addPropertyChangeListener(listener);
		}

		public void removePropertyChangeListener(PropertyChangeListener listener) {
			pcs.removePropertyChangeListener(listener);
		}

		public PropertyChangeListener[] getPropertyChangeListeners() {
			return pcs.getPropertyChangeListeners();
		}

		public void addPropertyChangeListener(String propertyName,
				PropertyChangeListener listener) {
			pcs.addPropertyChangeListener(propertyName, listener);
		}

		public void removePropertyChangeListener(String propertyName,
				PropertyChangeListener listener) {
			pcs.removePropertyChangeListener(propertyName, listener);
		}

		public PropertyChangeListener[] getPropertyChangeListeners(
				String propertyName) {
			return pcs.getPropertyChangeListeners(propertyName);
		}

		public boolean hasListeners(String propertyName) {
			return pcs.hasListeners(propertyName);
		}

		public Bean(double value) {
			this.pcs = new PropertyChangeSupport(this);
			this.value = value;
			this.weight = 1.0d;
			this.values = new ArrayList<Integer>(5);
			for (int i = 0; i < 5; i++) {
				this.values.add(Integer.valueOf(i * (int) value));
			}
		}

		public double getValue() {
			return value;
		}

		public void setValue(double value) {
			double oldValue = this.value;
			this.value = value;
			this.pcs.firePropertyChange("value", oldValue, value);
		}

		public double getWeight() {
			return weight;
		}

		public void setWeight(double weight) {
			double oldWeight = this.weight;
			this.weight = weight;
			this.pcs.firePropertyChange("weight", oldWeight, weight);
		}

		public Integer[] getValues() {
			return this.values.toArray(new Integer[this.values.size()]);
		}

		public void setValues(Integer[] values) {
			Integer[] oldValue = new Integer[this.values.size()];
			for (int i = 0; i < this.values.size(); i++) {
				oldValue[i] = this.values.get(i);
			}
			this.values = new ArrayList<Integer>(Arrays.asList(values));
			this.pcs.fireIndexedPropertyChange("values", 0, oldValue,
					this.getValues());
		}

		public Integer getValues(int index) {
			return this.values.get(index);
		}

		public void setValues(int index, Integer value) {
			Integer[] oldValue = this.getValues();
			if (index >= this.values.size()) {
				List<Integer> oldList = this.values;
				this.values = new ArrayList<Integer>(index + 1);
				this.values.addAll(oldList);
				while (this.values.size() <= index) {
					this.values.add(Integer.valueOf(0));
				}
			}
			this.values.set(index, value);
			this.pcs.fireIndexedPropertyChange("values", index, oldValue,
					this.getValues());
		}

		/**
		 * Compares beans by their value * weight.
		 */
		public int compareTo(Bean o) {
			if (o == null) {
				return -1;
			} else {
				return Double.compare(this.value * this.weight, o.getValue()
						* o.getWeight());
			}
		}
	}

	private class PassthroughRealm extends Realm {
		@Override
		public boolean isCurrent() {
			return true;
		}
	}
}

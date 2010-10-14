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

package net.juckel.rcp.databinding.aggregate.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.List;

import net.juckel.rcp.databinding.aggregate.AggregateProperties;
import net.juckel.rcp.databinding.aggregate.tests.model.ClassroomFactory;
import net.juckel.rcp.databinding.aggregate.tests.model.ClassroomPackage;
import net.juckel.rcp.databinding.aggregate.tests.model.Student;
import net.juckel.rcp.databinding.aggregate.tests.model.ExamResult;

import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.map.IMapChangeListener;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.observable.map.MapChangeEvent;
import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.core.databinding.observable.set.ListToSetAdapter;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.property.list.IListProperty;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.databinding.EMFProperties;
import org.junit.Before;
import org.junit.Test;

public class AggregatePropertiesTest {

	private Realm realm = new PassthroughRealm();
	private EList<Student> studentList;

	@Before
	public void setUp() {
		this.studentList = new BasicEList<Student>(5);
		for (int i = 0; i < 5; i++) {
			this.studentList.add(createStudent(i+1));
		}
	}

	@Test
	public void testSum() {
		Realm.runWithDefault(realm, new Runnable() {
			public void run() {
				// Try observing a single instances set of scores.
				Student studentToObserve = studentList.get(0);
				IObservableValue sumValue = AggregateProperties.sum(EMFProperties.list(ClassroomPackage.Literals.STUDENT__TEST_RESULTS)
						.values(ClassroomPackage.Literals.EXAM_RESULT__SCORE))
					.observe(studentToObserve);

				double manualSum = 0;
				for(ExamResult tr : studentToObserve.getTestResults()) {
					manualSum += tr.getScore();
				}
				assertEquals(manualSum, sumValue.getValue());

				// Capture original value of the first test result.	
				ExamResult r = studentToObserve.getTestResults().get(0);
				double val = r.getScore();
				r.setScore(5);
				assertEquals(manualSum - val + 5, sumValue.getValue());
				
				// Now test adding a new score to the list.
				r = ClassroomFactory.eINSTANCE.createExamResult();
				r.setScore(6.0);
				
				studentToObserve.getTestResults().add(r);
				assertEquals(manualSum - val + 5 + 6, sumValue.getValue());
			}
		});
	}

	@Test
	public void testAverage() {
		Realm.runWithDefault(realm, new Runnable() {
			public void run() {
				Student studentToObserve = studentList.get(0);
				IObservableValue avgValue = AggregateProperties.average(EMFProperties.list(ClassroomPackage.Literals.STUDENT__TEST_RESULTS)
						.values(ClassroomPackage.Literals.EXAM_RESULT__SCORE))
					.observe(studentToObserve);

				double manualAvg = 0;
				for(ExamResult tr : studentToObserve.getTestResults()) {
					manualAvg += tr.getScore();
				}
				manualAvg /= studentToObserve.getTestResults().size();
				assertEquals(manualAvg, avgValue.getValue());

				ExamResult r = studentToObserve.getTestResults().get(0);
				r.setScore(5.0);
				
				double newManualAvg = 0;
				for(ExamResult tr : studentToObserve.getTestResults()) {
					newManualAvg += tr.getScore();
				}
				newManualAvg /= studentToObserve.getTestResults().size();
				
				assertEquals(newManualAvg, avgValue.getValue());
			}
		});
	}

	@Test
	public void testNth() {
		Realm.runWithDefault(realm, new Runnable() {
			public void run() {
				Student studentToObserve = studentList.get(0);
				IObservableValue zero = AggregateProperties.nth(0,
						EMFProperties.list(ClassroomPackage.Literals.STUDENT__TEST_RESULTS))
						.observe(studentToObserve);

				IObservableValue four = AggregateProperties.nth(4,
						EMFProperties.list(ClassroomPackage.Literals.STUDENT__TEST_RESULTS))
						.observe(studentToObserve);

				assertSame(studentToObserve.getTestResults().get(0), zero.getValue());
				assertSame(studentToObserve.getTestResults().get(4), four.getValue());
			}
		});
	}

	@Test
	public void testWeightedAverageCalc() {
		Realm.runWithDefault(realm, new Runnable() {
			public void run() {
				Student studentToObserve = studentList.get(0);
				IObservableValue observableWeightedAverage = AggregateProperties
						.weightedAverage(
								EMFProperties.list(ClassroomPackage.Literals.STUDENT__TEST_RESULTS)
									.values(ClassroomPackage.Literals.EXAM_RESULT__SCORE),
								EMFProperties.list(ClassroomPackage.Literals.STUDENT__TEST_RESULTS)
									.values(ClassroomPackage.Literals.EXAM_RESULT__WEIGHT))
						.observe(studentToObserve);

				// Beans default to a weight of 1, so we should have a simple
				// average.
				double manualAvg = calcWeightedAvg(studentToObserve.getTestResults());
				assertEquals(manualAvg, observableWeightedAverage.getValue());

				// Now toy with weights and make sure the weighted average
				// updates appropriately.

				// Set weight of 5 to 5 times the weight of the other values.
				studentToObserve.getTestResults().get(4).setWeight(5.0);
				manualAvg = calcWeightedAvg(studentToObserve.getTestResults());
				assertEquals(manualAvg, ((Double) observableWeightedAverage
						.getValue()).doubleValue(), 0.000001d);

				// Muck with many weights and retest.
				studentToObserve.getTestResults().get(3).setWeight(4.0);
				studentToObserve.getTestResults().get(2).setWeight(3.0);
				studentToObserve.getTestResults().get(1).setWeight(2.0);
				manualAvg = calcWeightedAvg(studentToObserve.getTestResults());
				assertEquals(3.666667, ((Double) observableWeightedAverage
						.getValue()).doubleValue(), 0.000001d);
			}
		});
	}

	@Test
	public void testMinAndMax() {
		Realm.runWithDefault(realm, new Runnable() {
			public void run() {
				Student studentToObserve = studentList.get(0);
				IListProperty allResults = EMFProperties.list(ClassroomPackage.Literals.STUDENT__TEST_RESULTS);
				// We're going to be using the fact that TestResults are Comparable to get a min and max ExamResult.
				IObservableValue minValue = AggregateProperties.min(allResults)
					.observe(studentToObserve);
				IObservableValue maxValue = AggregateProperties.max(allResults)
					.observe(studentToObserve);
						
				assertSame(studentToObserve.getTestResults().get(0), minValue.getValue());
				assertSame(studentToObserve.getTestResults().get(4), maxValue.getValue());

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
				studentToObserve.getTestResults().get(1).setWeight(0);
				assertEquals(0, changeCount[0]);
				assertEquals(0, changeCount[1]);
				assertEquals(studentToObserve.getTestResults().get(0), minValue.getValue());
				assertEquals(studentToObserve.getTestResults().get(4), maxValue.getValue());
			}
		});
	}

	@Test
	public void testObservableList() {
		Realm.runWithDefault(realm, new Runnable() {
			public void run() {
				// Don't have an EMF container for the list of students, so just
				// use a WritableList.  It won't have any notification support
				// for elements added/removed from studentList.
				IObservableList observableBeanList = new WritableList(studentList,
						Student.class);
				// This next list should be the sum of each individual student's scores.
				IObservableList list = AggregateProperties.sum(
						EMFProperties.list(ClassroomPackage.Literals.STUDENT__TEST_RESULTS).values(ClassroomPackage.Literals.EXAM_RESULT__SCORE))
					.observeDetail(observableBeanList);

				assertEquals(calcSum(studentList.get(0).getTestResults()), (Double) list.get(0), 0.0d);
				assertEquals(calcSum(studentList.get(1).getTestResults()), (Double) list.get(1), 0.0d);
				assertEquals(calcSum(studentList.get(2).getTestResults()), (Double) list.get(2), 0.0d);
				assertEquals(calcSum(studentList.get(3).getTestResults()), (Double) list.get(3), 0.0d);
				assertEquals(calcSum(studentList.get(4).getTestResults()), (Double) list.get(4), 0.0d);
				studentList.get(0).getTestResults().clear();
 				studentList.get(0).getTestResults().add(ClassroomFactory.eINSTANCE.createExamResult(1,1));
 				studentList.get(0).getTestResults().add(ClassroomFactory.eINSTANCE.createExamResult(1,1));
				assertEquals(2.0, (Double) list.get(0), 0.0d);
			}
		});
	}

	@Test
	public void testObservableMap() {
		Realm.runWithDefault(realm, new Runnable() {
			public void run() {
				IObservableList observableBeanList = new WritableList(studentList,
						Student.class);
				IObservableSet observableBeanSet = new ListToSetAdapter(
						observableBeanList);
				IObservableMap map = AggregateProperties.sum(
						EMFProperties.list(ClassroomPackage.Literals.STUDENT__TEST_RESULTS).values(ClassroomPackage.Literals.EXAM_RESULT__SCORE))
					.observeDetail(observableBeanSet);

				assertEquals(calcSum(studentList.get(0).getTestResults()), (Double) map.get(studentList.get(0)), 0.0d);
				assertEquals(calcSum(studentList.get(1).getTestResults()), (Double) map.get(studentList.get(1)), 0.0d);
				assertEquals(calcSum(studentList.get(2).getTestResults()), (Double) map.get(studentList.get(2)), 0.0d);
				assertEquals(calcSum(studentList.get(3).getTestResults()), (Double) map.get(studentList.get(3)), 0.0d);
				assertEquals(calcSum(studentList.get(4).getTestResults()), (Double) map.get(studentList.get(4)), 0.0d);
				studentList.get(0).getTestResults().clear();
 				studentList.get(0).getTestResults().add(ClassroomFactory.eINSTANCE.createExamResult(1,1));
 				studentList.get(0).getTestResults().add(ClassroomFactory.eINSTANCE.createExamResult(1,1));
				assertEquals(2.0, (Double) map.get(studentList.get(0)), 0.0d);
				final int[] updateCount = new int[1];
				map.addMapChangeListener(new IMapChangeListener() {
					public void handleMapChange(MapChangeEvent event) {
						updateCount[0]++;
					}
				});
				assertEquals(0, updateCount[0]);
				studentList.get(0).getTestResults().get(0).setScore(5);
				assertTrue(updateCount[0] > 0);
				assertEquals(6.0, (Double) map.get(studentList.get(0)), 0.0d);
			}
		});
	}

	/**
	 * Creates a student instance with ExamResult values based on the seed.
	 * @param seed
	 * @return
	 */
	private Student createStudent(int seed) {
		Student s = ClassroomFactory.eINSTANCE.createStudent();
		s.setName(String.format("Student %d", seed));
		for(int i = 0; i < 5; i++) {
			ExamResult r = ClassroomFactory.eINSTANCE.createExamResult();
			r.setScore(seed * (i+1));
			s.getTestResults().add(r);
		}
		return s;
	}

	private double calcWeightedAvg(List<ExamResult> examResults) {
		double totalWeight = 0.0;
		double sumProduct = 0.0;
		for(ExamResult r : examResults) {
			sumProduct += r.getScore() * r.getWeight();
			totalWeight += r.getWeight();
		}
		return sumProduct / totalWeight;
	}

	private double calcSum(List<ExamResult> examResults) {
		double sum = 0;
		for(ExamResult r : examResults) {
			sum += r.getScore();
		}
		return sum;
	}

	private class PassthroughRealm extends Realm {
		@Override
		public boolean isCurrent() {
			return true;
		}
	}
}

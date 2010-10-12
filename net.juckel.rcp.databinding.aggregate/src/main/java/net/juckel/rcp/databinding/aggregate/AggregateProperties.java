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

package net.juckel.rcp.databinding.aggregate;

import java.util.List;

import net.juckel.rcp.databinding.aggregate.property.WeightedAverageProperty;

import org.eclipse.core.databinding.property.list.IListProperty;
import org.eclipse.core.databinding.property.list.ListReducer;
import org.eclipse.core.databinding.property.value.IValueProperty;

/**
 * Utility class to provide static factory methods for aggregation-based
 * IProperties.
 */
public class AggregateProperties {

	public static IValueProperty sum(IListProperty listProperty) {
		return listProperty.reduce(new ListReducer(Double.class) {
			public Object reduce(@SuppressWarnings("rawtypes") List list) {
				if (list.isEmpty()) {
					return 0.0d;
				} else {
					double sum = 0.0;
					for (Object d : list) {
						sum += ((Number) d).doubleValue();
					}
					return sum;
				}
			}
		});
	}

	public static IValueProperty average(IListProperty listProperty) {
		return listProperty.reduce(new ListReducer(Double.class) {
			public Object reduce(@SuppressWarnings("rawtypes") List list) {
				if (list.isEmpty()) {
					return 0.0d;
				} else {
					double sum = 0.0;
					for (Object d : list) {
						sum += ((Number) d).doubleValue();
					}
					return sum / list.size();
				}
			}
		});
	}

	public static WeightedAverageProperty weightedAverage(
			IListProperty valueProperty, IListProperty weightProperty) {
		return new WeightedAverageProperty(Double.class, valueProperty,
				weightProperty);
	}

	/**
	 * <p>
	 * Return the minimum value in the observed list.
	 * </p>
	 * 
	 * @return the minimum value
	 */
	public static IValueProperty min(IListProperty listProperty) {
		return listProperty.reduce(new ListReducer(listProperty.getElementType()) {
			public Object reduce(@SuppressWarnings("rawtypes") List list) {
				if( list.size() == 0 ) {
					return null;
				} else {
					@SuppressWarnings("unchecked")
					Comparable<? super Object> min = (Comparable<? super Object>) list.get(0);
					for( int i = 1; i < list.size(); i++ ) {
						@SuppressWarnings("unchecked")
						Comparable<? super Object> candidate = (Comparable<? super Object>) list.get(i);
						if( candidate != null && candidate.compareTo(min) < 0 ) {
							min = candidate;
						}
					}
					return min;
				}
			}
		});
	}

	/**
	 * <p>
	 * Return the maximum value in the observed list.
	 * </p>
	 * 
	 * @param listProperty
	 *            the list from which to determine the maximum value. The value
	 *            type must implement Comparable<? super Object>.
	 * @return the maximum value
	 */
	public static IValueProperty max(IListProperty listProperty) {
		return listProperty.reduce(new ListReducer(listProperty.getElementType()) {
			public Object reduce(@SuppressWarnings("rawtypes") List list) {
				if( list.size() == 0 ) {
					return null;
				} else {
					@SuppressWarnings("unchecked")
					Comparable<? super Object> max = (Comparable<? super Object>) list.get(0);
					for( int i = 1; i < list.size(); i++ ) {
						@SuppressWarnings("unchecked")
						Comparable<? super Object> candidate = (Comparable<? super Object>) list.get(i);
						if( candidate != null && candidate.compareTo(max) > 0 ) {
							max = candidate;
						}
					}
					return max;
				}
			}
		});
	}

	/**
	 * @param index
	 *            0-based index into the listProperty's observed list.
	 * @param listProperty
	 *            the list of objects from which we want the nth value.
	 * @return the nth value in an observed list.
	 */
	public static IValueProperty nth(final int index,
			final IListProperty listProperty) {
		return listProperty.reduce(new ListReducer(listProperty.getElementType()) {
			public Object reduce(@SuppressWarnings("rawtypes") List list) {
				if (0 <= index && index < list.size()) {
					return list.get(index);
				} else {
					return null;
				}
			}
		});
	}
}

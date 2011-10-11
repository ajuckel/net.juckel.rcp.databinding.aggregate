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

import java.util.Collections;
import java.util.List;

import net.juckel.rcp.databinding.aggregate.property.AggregateProperty;
import net.juckel.rcp.databinding.aggregate.property.AverageProperty;
import net.juckel.rcp.databinding.aggregate.property.IAggregateProperty;
import net.juckel.rcp.databinding.aggregate.property.IAggregation;
import net.juckel.rcp.databinding.aggregate.property.SumProperty;
import net.juckel.rcp.databinding.aggregate.property.WeightedAverageProperty;

import org.eclipse.core.databinding.property.list.IListProperty;

/**
 * Utility class to provide static factory methods for aggregation-based
 * IProperties.
 */
public class AggregateProperties {

    public static IAggregateProperty sum(IListProperty listProperty) {
        return new SumProperty(listProperty);
    }

    public static IAggregateProperty average(IListProperty listProperty) {
        return new AverageProperty(listProperty);
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
    public static IAggregateProperty min(IListProperty listProperty) {
        return new AggregateProperty(new IAggregation() {
            @SuppressWarnings("unchecked")
            public Object calculate(List<Object>... values) {
                Collections.sort((List<? extends Comparable<? super Object>>) values[0]);
                return values[0].get(0);
            }
        }, listProperty);
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
    public static IAggregateProperty max(IListProperty listProperty) {
        return new AggregateProperty(new IAggregation() {
            @SuppressWarnings("unchecked")
            public Object calculate(List<Object>... values) {
                Collections.sort((List<? extends Comparable<? super Object>>) values[0]);
                return values[0].get(values[0].size() - 1);
            }
        }, listProperty);
    }

    /**
     * @param index
     *            0-based index into the listProperty's observed list.
     * @param listProperty
     *            the list of objects from which we want the nth value.
     * @return the nth value in an observed list.
     */
    public static IAggregateProperty nth(final int index,
            IListProperty listProperty) {
        return new AggregateProperty(new IAggregation() {
            public Object calculate(List<Object>... values) {
                if (0 <= index && index < values[0].size()) {
                    return values[0].get(index);
                } else {
                    return null;
                }
            }
        }, listProperty);

    }

    /**
     * <p>
     * Here for unforeseen cases. Allows the user to implement their own
     * aggregation function, and tie into the infrastructure used by the
     * pre-existing aggregations.
     * </p>
     * 
     * @param aggregation
     *            the aggregation to perform
     * @param listProperty
     *            the list upon which the aggregation should be performed.
     * @return an IAggregateProperty that will use the given IAggregation to
     *         determine the observed value.
     */
    public static IAggregateProperty customAggregation(
            IAggregation aggregation, IListProperty listProperty) {
        return new AggregateProperty(aggregation, listProperty);
    }
}

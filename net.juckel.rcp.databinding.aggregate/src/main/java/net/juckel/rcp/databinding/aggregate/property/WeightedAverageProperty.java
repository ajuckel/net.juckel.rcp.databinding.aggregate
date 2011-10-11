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

package net.juckel.rcp.databinding.aggregate.property;

import java.util.List;

import org.eclipse.core.databinding.property.list.IListProperty;

public class WeightedAverageProperty extends AggregateProperty {

    private final static IAggregation WEIGHTED_AVG_AGGREGATION = new WeightedAverageAggregation();

    public WeightedAverageProperty(IListProperty... listProperties) {
        super(WEIGHTED_AVG_AGGREGATION, listProperties);
    }

    public WeightedAverageProperty(Object valueType,
            IListProperty... listProperties) {
        super(valueType, WEIGHTED_AVG_AGGREGATION, listProperties);
    }

    private static class WeightedAverageAggregation implements IAggregation {
        public Object calculate(List<Object>... values) {
            // We'll constrain the weighted average to the shortest of
            // the two lists we received, relying upon an assumption
            // that if one list is longer than the other, they will
            // "soon" be brought back in line with one another.
            double sumProd = 0;
            double totalWeight = 0;
            int shortestList = Math.min(values[0].size(), values[1].size());
            for (int i = 0; i < shortestList; i++) {
                sumProd += (Double) values[0].get(i)
                        * (Double) values[1].get(i);
                totalWeight += (Double) values[1].get(i);
            }
            if (totalWeight == 0) {
                return Double.NaN;
            } else {
                return sumProd / totalWeight;
            }
        }
    }
}

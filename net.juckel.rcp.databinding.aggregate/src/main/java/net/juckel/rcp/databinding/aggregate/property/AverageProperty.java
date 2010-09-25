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

/**
 * Assumes that the observed list is a list of Doubles.
 * 
 * @author tjuckel
 */
public class AverageProperty extends AggregateProperty {
	private static final IAggregation AVG_AGGREGATION = new AverageAggregation();

	public AverageProperty(IListProperty listProperty) {
		super(AVG_AGGREGATION, listProperty);
	}

	private static class AverageAggregation implements IAggregation {
		public Object calculate(List<Object>... values) {
			if (values[0].isEmpty()) {
				return 0.0d;
			} else {
				double sum = 0.0;
				for (Object d : values[0]) {
					sum += ((Number) d).doubleValue();
				}
				return sum / values[0].size();
			}
		}
	}
}

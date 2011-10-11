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

public interface IAggregation {
    /**
     * Condenses a list of values into a single value. Examples include min, max
     * and average.
     * 
     * @param values
     *            the current list of observed values upon which to perform the
     *            aggregation/reduction.
     * @return
     */
    Object calculate(List<Object>... values);
}

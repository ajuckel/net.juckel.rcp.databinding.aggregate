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

package net.juckel.rcp.databinding.aggregate.observables;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.value.ComputedValue;

import net.juckel.rcp.databinding.aggregate.property.AggregateProperty;

/**
 * <p>
 * Not currently thread-safe.
 * </p>
 * 
 * @author tjuckel
 */
public class AggregateObservableValue extends ComputedValue {
	private AggregateProperty aggregateProperty;
	private IObservableList[] observableLists;

	/**
	 * Assumes the aggregate value is the same as the element values of the
	 * given observable list.
	 * 
	 * @param aggregateProperty
	 * @param observableList
	 */
	public AggregateObservableValue(AggregateProperty aggregateProperty,
			IObservableList... observableLists) {
		super(observableLists[0].getRealm(), aggregateProperty.getValueType());
		assert observableLists != null && observableLists.length > 0;
		this.aggregateProperty = aggregateProperty;
		this.observableLists = new IObservableList[observableLists.length];
		System.arraycopy(observableLists, 0, this.observableLists, 0,
				observableLists.length);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected Object calculate() {
		try {
			List<Object>[] capturedLists = new ArrayList[this.observableLists.length];
			for (int i = 0; i < capturedLists.length; i++) {
				capturedLists[i] = new ArrayList<Object>(
						this.observableLists[i]);
			}
			return aggregateProperty.getAggregation().calculate(capturedLists);
		} catch (Throwable t) {
			return null;
		}
	}
}

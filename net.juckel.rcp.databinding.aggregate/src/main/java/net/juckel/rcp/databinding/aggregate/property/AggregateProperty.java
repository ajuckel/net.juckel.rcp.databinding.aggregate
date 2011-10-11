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

import org.eclipse.core.databinding.observable.ObservableTracker;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.property.list.IListProperty;
import org.eclipse.core.databinding.property.value.ValueProperty;

import net.juckel.rcp.databinding.aggregate.observables.AggregateObservableList;
import net.juckel.rcp.databinding.aggregate.observables.AggregateObservableValue;
import net.juckel.rcp.databinding.aggregate.observables.MapBackedAggregateObservableMap;
import net.juckel.rcp.databinding.aggregate.observables.SetBackedAggregateObservableMap;

public class AggregateProperty extends ValueProperty implements
        IAggregateProperty {
    final IAggregation aggregation;
    final IListProperty[] listProperties;
    final Object valueType;

    public AggregateProperty(IAggregation aggregation,
            IListProperty... listProperties) {
        this(null, aggregation, listProperties);
    }

    public AggregateProperty(Object valueType, IAggregation aggregation,
            IListProperty... listProperties) {
        assert aggregation != null && listProperties != null
                && listProperties.length > 0;
        this.aggregation = aggregation;
        this.listProperties = new IListProperty[listProperties.length];
        System.arraycopy(listProperties, 0, this.listProperties, 0,
                listProperties.length);
        this.valueType = valueType;
    }

    public IAggregation getAggregation() {
        return aggregation;
    }

    public IListProperty[] getListProperties() {
        return this.listProperties;
    }

    public Object getValueType() {
        return this.valueType;
    }

    public IObservableValue observe(final Realm realm, final Object source) {
        final IObservableList[] listsToAggregate = new IObservableList[this.listProperties.length];

        ObservableTracker.runAndIgnore(new Runnable() {
            public void run() {
                for (int i = 0; i < listProperties.length; i++) {
                    listsToAggregate[i] = listProperties[i].observe(realm,
                            source);
                }
            }
        });

        return new AggregateObservableValue(this, listsToAggregate);
    }

    public IObservableList observeDetail(final IObservableList master) {
        return new AggregateObservableList(this, master);
    }

    public IObservableMap observeDetail(IObservableSet master) {
        return new SetBackedAggregateObservableMap(this, master);
    }

    public IObservableMap observeDetail(IObservableMap master) {
        return new MapBackedAggregateObservableMap(this, master);
    }
}

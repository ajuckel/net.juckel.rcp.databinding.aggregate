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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.databinding.observable.IStaleListener;
import org.eclipse.core.databinding.observable.StaleEvent;
import org.eclipse.core.databinding.observable.map.AbstractObservableMap;
import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.core.databinding.observable.set.ISetChangeListener;
import org.eclipse.core.databinding.observable.set.SetChangeEvent;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.IValueChangeListener;
import org.eclipse.core.databinding.observable.value.ValueChangeEvent;

import net.juckel.rcp.databinding.aggregate.property.AggregateProperty;

public class SetBackedAggregateObservableMap extends AbstractObservableMap {
	private AggregateProperty aggregateProperty;
	private IObservableSet masterKeySet;
	private Map<Object, MapEntry> observableValueMap;
	private Listener listener;

	/**
	 * Create an IObservableList will contain an IObservableValue created by the
	 * valueFactory for each element in the masterList.
	 * 
	 * @param valueFactory
	 * @param master
	 */
	public SetBackedAggregateObservableMap(AggregateProperty aggregateProperty,
			IObservableSet masterKeySet) {
		this.aggregateProperty = aggregateProperty;
		this.masterKeySet = masterKeySet;
		this.observableValueMap = new HashMap<Object, MapEntry>();
		// Eagerly creating Map Entries, which will lazily create their
		// observable values.
		for (Object key : masterKeySet) {
			this.observableValueMap.put(key, new MapEntry(key));
		}
	}

	@Override
	public Object getKeyType() {
		return masterKeySet.getElementType();
	}

	public Object getElementType() {
		return aggregateProperty.getValueType();
	}

	@Override
	public Set<Map.Entry<Object, Object>> entrySet() {
		return new HashSet<Map.Entry<Object, Object>>(
				observableValueMap.values());
	}

	private class MapEntry implements Map.Entry<Object, Object> {
		private Object key;
		private IObservableValue observableValue;

		public MapEntry(Object key) {
			this.key = key;
		}

		public Object getKey() {
			return key;
		}

		public Object getValue() {
			if (this.observableValue == null) {
				this.observableValue = aggregateProperty.observe(this.getKey());
				if (listener != null) {
					this.observableValue.addValueChangeListener(listener);
				}
			}
			return this.observableValue.getValue();
		}

		public Object setValue(Object value) {
			throw new UnsupportedOperationException(
					"AggregateObservableMaps are readonly");
		}

		public IObservableValue getObservableValue() {
			return observableValue;
		}
	}

	@Override
	public Object get(Object key) {
		if (!masterKeySet.contains(key)) {
			return null;
		}

		return observableValueMap.get(key).getValue();
	}

	@Override
	protected void firstListenerAdded() {
		// First time we add a listener to this observableList, we need to hook
		// up a listener to the masterList, as well as all observable values
		// we've already created.
		listener = new Listener();
		this.masterKeySet.addSetChangeListener(listener);
		this.masterKeySet.addStaleListener(listener);
		for (Map.Entry<Object, MapEntry> entry : this.observableValueMap
				.entrySet()) {
			if (entry.getValue().getObservableValue() != null) {
				entry.getValue().getObservableValue()
						.addValueChangeListener(listener);
			}
		}
	}

	protected void lastListenerRemoved() {
		for (Map.Entry<Object, MapEntry> entry : this.observableValueMap
				.entrySet()) {
			entry.getValue().getObservableValue()
					.removeValueChangeListener(listener);
		}
		this.masterKeySet.removeStaleListener(listener);
		this.masterKeySet.removeSetChangeListener(listener);
		listener = null;
	}

	private class Listener implements IStaleListener, IValueChangeListener,
			ISetChangeListener {
		public void handleStale(StaleEvent staleEvent) {
			fireStale();
		}

		public void handleValueChange(ValueChangeEvent event) {
			fireChange();
		}

		public void handleSetChange(SetChangeEvent event) {
			for (Object newKey : event.diff.getAdditions()) {
				observableValueMap.put(newKey, new MapEntry(newKey));
			}
			for (Object oldKey : event.diff.getRemovals()) {
				MapEntry entry = observableValueMap.remove(oldKey);
				if (entry != null && entry.getObservableValue() != null) {
					entry.getObservableValue().removeValueChangeListener(this);
				}
			}
		}
	}

	@Override
	public synchronized void dispose() {
		if (listener != null) {
			lastListenerRemoved();
		}
		for (Map.Entry<Object, MapEntry> entry : this.observableValueMap
				.entrySet()) {
			entry.getValue().getObservableValue().dispose();
		}
		super.dispose();
	}
}

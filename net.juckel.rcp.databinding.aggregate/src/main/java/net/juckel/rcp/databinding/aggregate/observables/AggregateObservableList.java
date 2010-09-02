package net.juckel.rcp.databinding.aggregate.observables;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.databinding.observable.IStaleListener;
import org.eclipse.core.databinding.observable.StaleEvent;
import org.eclipse.core.databinding.observable.list.AbstractObservableList;
import org.eclipse.core.databinding.observable.list.IListChangeListener;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.ListChangeEvent;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.IValueChangeListener;
import org.eclipse.core.databinding.observable.value.ValueChangeEvent;

import net.juckel.rcp.databinding.aggregate.property.AggregateProperty;

public class AggregateObservableList extends AbstractObservableList {
	private AggregateProperty aggregateProperty;
	private IObservableList masterList;
	private List<IObservableValue> observableValueList;
	private Listener listener;

	/**
	 * Create an IObservableList will contain an IObservableValue created by the
	 * valueFactory for each element in the masterList.
	 * 
	 * @param valueFactory
	 * @param master
	 */
	public AggregateObservableList(AggregateProperty aggregateProperty,
			IObservableList masterList) {
		this.aggregateProperty = aggregateProperty;
		this.masterList = masterList;
		this.observableValueList = new ArrayList<IObservableValue>();
	}

	public Object getElementType() {
		return aggregateProperty.getValueType();
	}

	@Override
	protected int doGetSize() {
		return masterList.size();
	}

	@Override
	public Object get(int index) {
		if (index < 0 || index >= masterList.size()) {
			throw new IndexOutOfBoundsException();
		}
		// We should have an element, now check to see if we've already
		// mapped it to an observable value.
		if (index >= observableValueList.size()) {
			// Just null-padding entries that we should have, but haven't
			// been explicitly requested.
			while (this.observableValueList.size() <= index) {
				this.observableValueList.add(null);
			}
		}
		// I'm assuming get is Realm-bound, so there are no concurrency issues
		// to worry about.
		if (observableValueList.get(index) == null) {
			IObservableValue value = aggregateProperty.observe(masterList
					.get(index));
			if (listener != null) {
				value.addValueChangeListener(listener);
			}
			observableValueList.set(index, value);
		}
		return observableValueList.get(index).getValue();
	}

	@Override
	protected void firstListenerAdded() {
		// First time we add a listener to this observableList, we need to hook
		// up a listener to the masterList, as well as all observable values
		// we've already created.
		listener = new Listener();
		this.masterList.addListChangeListener(listener);
		this.masterList.addStaleListener(listener);
		for (IObservableValue value : this.observableValueList) {
			value.addValueChangeListener(listener);
		}
	}

	protected void lastListenerRemoved() {
		for (IObservableValue value : this.observableValueList) {
			value.removeValueChangeListener(listener);
		}
		this.masterList.removeStaleListener(listener);
		this.masterList.removeListChangeListener(listener);
		this.listener = null;
	}

	private class Listener implements IListChangeListener, IStaleListener,
			IValueChangeListener {
		public void handleListChange(ListChangeEvent event) {
			fireChange();
		}

		public void handleStale(StaleEvent staleEvent) {
			fireStale();
		}

		public void handleValueChange(ValueChangeEvent event) {
			fireChange();
		}
	}

	@Override
	public synchronized void dispose() {
		if (listener != null) {
			lastListenerRemoved();
		}
		for (IObservableValue value : this.observableValueList) {
			value.dispose();
		}
		super.dispose();
	}
}

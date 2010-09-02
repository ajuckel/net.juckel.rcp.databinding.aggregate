package net.juckel.rcp.databinding.aggregate.property;

import org.eclipse.core.databinding.property.value.IValueProperty;

public interface IAggregateProperty extends IValueProperty {
	public IAggregation getAggregation();
}

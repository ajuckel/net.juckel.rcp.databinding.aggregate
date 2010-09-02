package net.juckel.rcp.databinding.aggregate.property;

import java.util.List;

import org.eclipse.core.databinding.property.list.IListProperty;

/**
 * Assumes that the observed list is a list of Doubles.
 * 
 * @author tjuckel
 */
public class SumProperty extends AggregateProperty {
	private final static IAggregation SUM_AGGREGATION = new SumAggregation();

	public SumProperty(IListProperty listProperty) {
		super(SUM_AGGREGATION, listProperty);
	}

	private static class SumAggregation implements IAggregation {
		public Object calculate(List<Object>... values) {
			double sum = 0.0;
			for (Object d : values[0]) {
				sum += ((Number) d).doubleValue();
			}
			return sum;
		}
	}
}

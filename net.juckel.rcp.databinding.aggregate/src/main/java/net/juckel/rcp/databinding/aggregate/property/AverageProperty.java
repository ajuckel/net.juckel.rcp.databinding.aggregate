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

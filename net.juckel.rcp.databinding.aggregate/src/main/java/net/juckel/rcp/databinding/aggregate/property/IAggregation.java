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

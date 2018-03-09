package practice.jcip.chp15;

import java.util.concurrent.atomic.AtomicReference;

public class CasNumberRange {
	private static class IntPair {
		public IntPair(int upper, int lower) {
			this.upper = upper;
			this.lower = lower;
		}
		final int lower;
		final int upper;
	}
	
	private final AtomicReference<IntPair> values = new AtomicReference<IntPair>(new IntPair(0, 0));
	
	public int getLower() {
		return values.get().lower;
	}
	
	public int getUpper() {
		return values.get().upper;
	}
	
	public void setLower(int i) {
		while (true) {
			IntPair oldPair = values.get();
			if (oldPair.upper < i) {
				throw new IllegalArgumentException("Can't set lower to " + i + " > upper");
			} else {
				IntPair newPair = new IntPair(oldPair.upper, i);
				if (values.compareAndSet(oldPair, newPair)) {
					return;
				}
			}
		}
	}

}

package engine.utilities.collisions;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class Range {

	private double min;
	private double max;

	public Range(double min, double max) {
		this.min = min;
		this.max = max;
	}

	public double getMin() {
		return min;
	}

	public double getMax() {
		return max;
	}

	public double getDifference() {
		return max - min;
	}

	public boolean contains(double value) {
		return value >= min && value <= max;
	}

	public double getOverlap(Range other) {
		if (!intersects(other))
			return 0;
		return (getDifference() + other.getDifference()
				- (Math.abs(min - other.getMin()) + Math.abs(max - other.getMax()))) / 2;
	}

	public boolean intersects(Range other) {
		return contains(other.getMin()) || other.contains(min);
	}

	public String toString() {
		return "[" + min + ", " + max + "]";
	}
}

package engine.utilities.collisions;

import java.util.Collection;
import java.util.HashSet;

import javafx.geometry.Point2D;

public abstract class BoundingSet {
	
	protected abstract Collection<BoundingGeometry> getGeometry();
	
	public Point2D checkCollision(BoundingSet other) {
		Point2D result = null;
		double maxMagnitude = 0;
		for (BoundingGeometry thisGeometry : getGeometry()) {
			for (BoundingGeometry otherGeometry : other.getGeometry()) {
				Point2D collision = thisGeometry.checkCollision(otherGeometry);
				if (collision == null)
					continue;
				double magnitude = collision.magnitude();
				if (magnitude > maxMagnitude) {
					maxMagnitude = magnitude;
					result = collision;
				}
			}
		}
		return result;
	}
	
	public Point2D checkCollision(BoundingGeometry other) {
		return checkCollision(new SingleSet(other));
	}

	private class SingleSet extends BoundingSet {
		private HashSet<BoundingGeometry> set;

		public SingleSet(BoundingGeometry element) {
			set = new HashSet<BoundingGeometry>();
			set.add(element);
		}
		
		@Override
		protected Collection<BoundingGeometry> getGeometry() {
			return set;
		}
	}
}

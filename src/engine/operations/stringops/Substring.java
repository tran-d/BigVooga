package engine.operations.stringops;

import engine.GameObject;
import engine.Layer;
import engine.operations.doubleops.DoubleOperation;

public class Substring implements StringOperation {

	private StringOperation string;
	private DoubleOperation start;
	private DoubleOperation end;

	public Substring(StringOperation string, DoubleOperation start, DoubleOperation end) {
		this.string = string;
		this.start = start;
		this.end = end;
	}

	@Override
	public String evaluate(GameObject asking, Layer world) {
		String s = string.evaluate(asking, world);
		int first = (int) Math.round(start.evaluate(asking, world));
		int last  = (int) Math.round(end.evaluate(asking, world));
		first = Math.max(0, first);
		last = Math.min(last, s.length());
		first = Math.min(first, last);
		return s.substring(first, last);
	}
	
}

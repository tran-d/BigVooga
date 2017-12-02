package engine.operations.stringops;

public class Concatenate implements StringOperation{

	private StringOperation first;
	private StringOperation second;

	public Concatenate(StringOperation first, StringOperation second) {
		this.first = first;
		this.second = second;
	}
	
	@Override
	public String evaluate() {
		return first.evaluate() + second.evaluate();
	}

}

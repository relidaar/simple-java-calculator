package interpreter;

public class Number implements Expression {
	private double mValue;
	
	public Number(double value) {
		this.mValue = value;
	}

	@Override
	public Double evaluate() {
		return mValue;
	}
}
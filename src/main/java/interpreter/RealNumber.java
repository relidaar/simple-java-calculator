package interpreter;

public class RealNumber implements Expression {
	private double mValue;
	
	public RealNumber(double value) {
		this.mValue = value;
	}

	@Override
	public Double evaluate() {
		return mValue;
	}
}
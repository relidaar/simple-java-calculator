package interpreter;

public class IntegerExpression implements Expression {
	private int mValue;
	
	public IntegerExpression(int value) {
		this.mValue = value;
	}

	@Override
	public Integer evaluate() {
		return mValue;
	}
}
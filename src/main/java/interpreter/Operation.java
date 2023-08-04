package interpreter;

public class Operation implements Expression {
	private Expression mLeft;
	private Expression mRight;
	private OperationType mType;
	
	public Operation(Expression left, Expression right, OperationType type) {
		mLeft = left;
		mRight = right;
		mType = type;
	}

	@Override
	public Double evaluate() {
		var leftResult = mLeft.evaluate();
		var rightResult = mRight.evaluate();
		return switch (mType) {
		case Addition -> leftResult + rightResult;
		case Subtraction -> leftResult - rightResult;
		};
	}
}
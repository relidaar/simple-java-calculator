package interpreter;

public class OperationExpression implements Expression {
	private Expression mLeft;
	private Expression mRight;
	private OperationType mType;
	
	public OperationExpression(Expression left, Expression right, OperationType type) {
		mLeft = left;
		mRight = right;
		mType = type;
	}

	@Override
	public Integer evaluate() {
		var leftResult = mLeft.evaluate();
		var rightResult = mRight.evaluate();
		return switch (mType) {
		case Addition -> leftResult + rightResult;
		case Subtraction -> leftResult - rightResult;
		};
	}
}
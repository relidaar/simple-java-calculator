package interpreter;

import java.util.List;

import parser.Token;
import parser.TokenType;

public final class Interpreter {
	private Interpreter() {}
	
	public static Expression parse(List<Token> tokens) throws Exception {
		Expression lastExpression = null;
		TokenIterator iterator = new TokenIterator(tokens);
		
		while (iterator.hasNext()) {
			Token token = iterator.next();
			switch (token.getType()) {
			case NUMBER:
				lastExpression = parseExpression(iterator, token);
				break;
			case OPERATOR:
				lastExpression = extendExpression(lastExpression, iterator, token);
				break;
			}
		}
		
		return lastExpression;
	}

	private static Expression parseExpression(TokenIterator it, Token token) throws NumberFormatException, Exception {
		if (!it.hasNext()) {
			return new RealNumber(Double.parseDouble(token.getValue()));
		}

		var operator = it.next();
		var right = it.next();

		if (operator.getType() == TokenType.OPERATOR && right != null && right.getType() == TokenType.NUMBER) {
			return new Operation(new RealNumber(Double.parseDouble(token.getValue())),
					new RealNumber(Double.parseDouble(right.getValue())), OperationType.valueOf(operator));
		}
		
		throw new Exception("Invalid expression");
	}

	private static Expression extendExpression(Expression lastExpression, TokenIterator it, Token token)
			throws NumberFormatException, Exception {
		if (!it.hasNext() || lastExpression == null) {
			throw new Exception("Invalid expression");
		}

		var right = it.next();

		if (right.getType() == TokenType.NUMBER) {
			return new Operation(lastExpression, new RealNumber(Double.parseDouble(right.getValue())),
					OperationType.valueOf(token));
		}

		throw new Exception("Invalid expression");
	}
}
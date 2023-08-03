package interpreter;

import java.util.List;

import parser.Token;
import parser.TokenType;

public class Interpreter {
	public Expression parse(List<Token> tokens) throws Exception {
		Expression lastExpression = null;
		TokenIterator iterator = new TokenIterator(tokens);
		while (iterator.hasNext()) {
			Token token = iterator.next();			
			switch(token.getType()) {
				case NUMBER: lastExpression = parseExpression(iterator, token); break;
				case OPERATOR: lastExpression = extendExpression(lastExpression, iterator, token); break;
			}
		}
		return lastExpression;
	}

	private Expression parseExpression(TokenIterator it, Token token) throws NumberFormatException, Exception {
		if (!it.hasNext()) {
			return new IntegerExpression(Integer.parseInt(token.getValue()));
		} else {			
			var operator = it.next();
			var right = it.next();
			if (operator.getType() == TokenType.OPERATOR && right != null && right.getType() == TokenType.NUMBER) {
				return new OperationExpression(new IntegerExpression(Integer.parseInt(token.getValue())),
						new IntegerExpression(Integer.parseInt(right.getValue())),
						OperationType.getOperation(operator));
			}
			else {
				throw new Exception("Invalid expression");
			}
		}
	}

	private Expression extendExpression(Expression lastExpression, TokenIterator it, Token token)
			throws NumberFormatException, Exception {
		if (!it.hasNext() || lastExpression == null) {
			throw new Exception("Invalid expression");
		} else {			
			var right = it.next();
			if (right.getType() == TokenType.NUMBER) {
				return new OperationExpression(lastExpression,
						new IntegerExpression(Integer.parseInt(right.getValue())),
						OperationType.getOperation(token));
			} else {
				throw new Exception("Invalid expression");
			}
		}
	}
}
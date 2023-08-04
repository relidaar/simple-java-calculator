package interpreter;

import java.util.List;

import parser.Token;
import parser.TokenType;

public final class Interpreter {
	private Interpreter() {
	}

	public static Expression buildAST(List<Token> tokens) throws Exception {
		if (tokens == null || tokens.isEmpty()) return null;
		
		Expression lastExpression = null;
		TokenIterator iterator = new TokenIterator(tokens);

		while (iterator.hasNext()) {
			Token token = iterator.next();
			lastExpression = switch (token.getType()) {
			case NUMBER -> buildExpression(iterator);
			default -> throw new Exception("Invalid expression");
			};
		}

		return lastExpression;
	}

	private static Expression buildExpression(TokenIterator iterator) throws NumberFormatException, Exception {
		Expression lastExpression = new RealNumber(Double.parseDouble(iterator.getCurrentToken().getValue()));
		
		if (!iterator.hasNext()) {
			return lastExpression;
		}
		
		while (iterator.hasNext()) {
			Token operator = iterator.next();
			Token right = iterator.next();
	
			if (operator.getType() != TokenType.OPERATOR || right == null || right.getType() != TokenType.NUMBER) {
				throw new Exception("Invalid expression");
			}
			
			lastExpression = new Operation(lastExpression,
					new RealNumber(Double.parseDouble(right.getValue())), OperationType.valueOf(operator));
		}

		return lastExpression;
	}
}
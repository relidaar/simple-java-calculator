package interpreter;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import parser.Token;
import parser.TokenType;

public class Interpreter {
	public String evaluate(List<Token> tokens) throws Exception {
		if (tokens == null || tokens.isEmpty())
			return "";
		
		var operators = new LinkedList<OperationType>();
		var numbers = new LinkedList<Integer>();
		var iterator = new TokenIterator(tokens);
		
		while (iterator.hasNext()) {
			var currentToken = iterator.next();
			var tokenType = currentToken.getType();
			var tokenValue = currentToken.getValue();

			if (tokenType == TokenType.NUMBER) {
				numbers.add(Integer.parseInt(tokenValue));

				if (iterator.hasNext() && iterator.peakNext().getType() != TokenType.OPERATOR) {
					throw new InvalidExpressionException(iterator.getCurrentPosition(), currentToken, iterator.peakNext());
				}
			} else if (tokenType == TokenType.OPERATOR) {
				operators.add(OperationType.getOperation(currentToken));
				
				if (!iterator.hasNext()) {
					throw new InvalidExpressionException(iterator.getCurrentPosition(), currentToken);
				}
				
				if (iterator.hasNext() && iterator.peakNext().getType() != TokenType.NUMBER) {
					throw new InvalidExpressionException(iterator.getCurrentPosition(), currentToken, iterator.peakNext());
				}
			}
		}
		
		Integer result = calculateExpression(operators, numbers);
		return result.toString();
	}

	private int calculateExpression(LinkedList<OperationType> operators, LinkedList<Integer> numbers) {
		while (!operators.isEmpty()) {
			OperationType currentOperation = operators.pop();
			int left = numbers.pop();
			int right = numbers.pop();
			
			int result = switch (currentOperation) {
			case Addition -> left + right;
			case Subtraction -> left - right;
			};
			
			numbers.addFirst(result);
		}
		
		return numbers.pop();
	}
}

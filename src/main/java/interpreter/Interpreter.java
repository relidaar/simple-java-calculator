package interpreter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import parser.Token;
import parser.TokenType;

public final class Interpreter {
	private Interpreter() {
	}
	
	private static Set<OperationType> mFactorOperators;
	private static Set<OperationType> mTermOperators;
	
	static {
		mFactorOperators = new HashSet<>();
		mFactorOperators.add(OperationType.Multiplication);
		mFactorOperators.add(OperationType.Division);

		mTermOperators = new HashSet<>();
		mTermOperators.add(OperationType.Addition);
		mTermOperators.add(OperationType.Subtraction);
	}

	public static Expression buildAST(List<Token> tokens) throws Exception {
		if (tokens == null || tokens.isEmpty()) return null;
		var iterator = new TokenIterator(tokens);
		return buildTerm(iterator);
	}
	
	private static Expression buildTerm(TokenIterator it) throws Exception {
		Expression expression = buildFactor(it);
		Token current = it.current();
		while (current.getType() == TokenType.OPERATOR && mTermOperators.contains(OperationType.valueOf(current))) {
			it.next();
			Expression next = buildFactor(it);
			expression = new BinaryOperation(expression, next, OperationType.valueOf(current));
			current = it.current();
		}
			
		return expression;
	}
	
	private static Expression buildFactor(TokenIterator it) throws Exception {
		Expression expression = buildPrimary(it);
		Token current = it.current();
		while (it.current().getType() == TokenType.OPERATOR && mFactorOperators.contains(OperationType.valueOf(current))) {
			it.next();
			Expression next = buildPrimary(it);
			expression = new BinaryOperation(expression, next, OperationType.valueOf(current));
			current = it.current();
		}
			
		return expression;
	}
	
	private static Expression buildPrimary(TokenIterator it) throws Exception {
		Token current = it.current();
		if (current.getType() == TokenType.NUMBER) {
			it.next();
			return new Number(Double.parseDouble(current.getValue()));
		}

		throw new Exception("Invalid expression");
	}
}
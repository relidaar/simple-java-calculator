package interpreter;

import java.util.List;

import parser.Token;
import parser.TokenType;

public final class Interpreter {
	private Interpreter() {
	}

	public static Expression buildExpression(List<Token> tokens) throws Exception {
		if (tokens == null || tokens.isEmpty())
			return null;
		var iterator = new TokenIterator(tokens);
		return buildTerm(iterator);
	}

	private static Expression buildTerm(TokenIterator it) throws Exception {
		Expression expression = buildFactor(it);
		Token current = it.current();
		while (Term.TermType.contains(current)) {
			it.next();
			Expression next = buildFactor(it);
			expression = new Term(expression, next, Term.TermType.valueOf(current));
			current = it.current();
		}

		return expression;
	}

	private static Expression buildFactor(TokenIterator it) throws Exception {
		Expression expression = buildPrimary(it);
		Token current = it.current();
		while (Factor.FactorType.contains(current)) {
			it.next();
			Expression next = buildPrimary(it);
			expression = new Factor(expression, next, Factor.FactorType.valueOf(current));
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
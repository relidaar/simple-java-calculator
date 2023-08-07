package parser;

import java.util.List;

import parser.Unary.UnaryType;
import tokenizer.Token;
import tokenizer.TokenType;

public final class Parser {
	private Parser() {
	}

	public static Expression buildExpression(List<Token> tokens) throws Exception {
		if (tokens == null || tokens.isEmpty())
			return null;

		var iterator = new TokenIterator(tokens);
		Expression expression = buildTerm(iterator);

		if (iterator.current() != null || iterator.hasNext())
			throw new InvalidExpressionException(iterator.current());

		return expression;
	}

	private static Expression buildTerm(TokenIterator it) throws InvalidExpressionException {
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

	private static Expression buildFactor(TokenIterator it) throws InvalidExpressionException {
		Expression expression = buildUnary(it);
		Token current = it.current();
		while (Factor.FactorType.contains(current)) {
			it.next();
			Expression next = buildUnary(it);
			expression = new Factor(expression, next, Factor.FactorType.valueOf(current));
			current = it.current();
		}

		return expression;
	}

	private static Expression buildUnary(TokenIterator it) throws InvalidExpressionException {
		Token current = it.current();
		if (isOf(current, TokenType.MINUS, TokenType.PLUS)) {
			it.next();
			Expression expression = buildPrimary(it);
			return new Unary(expression, UnaryType.valueOf(current));
		}
		return buildPrimary(it);
	}
	
	private static Expression buildPrimary(TokenIterator it) throws InvalidExpressionException {
		Token current = it.current();
		if (isOf(current, TokenType.NUMBER)) {
			it.next();
			return new Number(Double.parseDouble(current.getValue()));
		}

		if (isOf(current, TokenType.LEFT_PARENTHESIS)) {
			it.next();
			Expression expression = buildTerm(it);
			if (expression == null)
				throw new InvalidExpressionException(current);

			current = it.current();
			if (isOf(current, TokenType.RIGHT_PARENTHESIS)) {
				it.next();
				return expression;
			}

			throw new InvalidExpressionException(current != null ? current : it.previous());
		}

		throw new InvalidExpressionException(current != null ? current : it.previous());
	}
	
	private static boolean isOf(Token token, TokenType... types) {
		if (token == null) return false;
		for (TokenType type : types) {
			if (token.getType() == type) return true;
		}
		return false;
	}
}
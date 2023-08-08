package parser;

import java.util.ArrayList;
import java.util.List;

import parser.models.Expression;
import parser.models.Factor;
import parser.models.Function;
import parser.models.Function.FunctionType;
import parser.models.Number;
import parser.models.Term;
import parser.models.Unary;
import parser.models.Unary.UnaryType;
import parser.models.Variable;
import tokenizer.Token;
import tokenizer.TokenType;

public final class Parser {
	private Parser() {}
	
	public static Expression buildExpression(List<Token> tokens) throws Exception {
		if (tokens == null || tokens.isEmpty())
			return null;

		var iterator = new TokenIterator(tokens);
		Expression expression = buildTerm(iterator);

		if (iterator.current() != null || iterator.hasNext())
			throw new InvalidExpressionException(iterator.current());

		return expression;
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

	private static Expression buildUnary(TokenIterator it) throws Exception {
		Token current = it.current();
		if (isOf(current, TokenType.MINUS, TokenType.PLUS)) {
			it.next();
			Expression expression = buildPrimary(it);
			return new Unary(expression, UnaryType.valueOf(current));
		}
		return buildPrimary(it);
	}

	private static Expression buildPrimary(TokenIterator it) throws Exception {
		Token current = it.current();
		if (isOf(current, TokenType.NUMBER)) {
			it.next();
			return new Number(Double.parseDouble(current.getValue()));
		}

		if (isOf(current, TokenType.LEFT_PARENTHESIS)) {
			return buildParenthesizedExpression(it, current);
		}

		if (Function.FunctionType.contains(current)) {
			return buildFunction(it, current);
		}
		
		if (isOf(current, TokenType.IDENTIFIER)) {
			it.next();
			return new Variable(current.getValue());
		}

		throw new InvalidExpressionException(current != null ? current : it.previous());
	}

	// Helper Functions Section
	private static Expression buildFunction(TokenIterator it, Token current) throws Exception {
		FunctionType functionType = FunctionType.valueOf(current);
		if (functionType == null) {
			throw new UndefinedIdentifier(current);
		}
				
		current = it.next();
		if (isOf(current, TokenType.LEFT_PARENTHESIS) == false) {
			throw new InvalidExpressionException(current != null ? current : it.previous());
		}
		it.next();
		
		List<Expression> arguments = new ArrayList<>();
		for	(int i = 0; i < functionType.getParametersCount() - 1; i++) {
			Expression expression = buildTerm(it);
			if (expression == null)
				throw new InvalidExpressionException(current);

			current = it.current();
			if (isOf(current, TokenType.COMMA) == false) {
				throw new InvalidExpressionException(current != null ? current : it.previous());
			}
			it.next();
			
			arguments.add(expression);
		}
		
		Expression expression = buildTerm(it);
		if (expression == null)
			throw new InvalidExpressionException(current);
		arguments.add(expression);

		current = it.current();
		if (isOf(current, TokenType.RIGHT_PARENTHESIS) == false) {
			throw new InvalidExpressionException(current != null ? current : it.previous());
		}
		
		it.next();
		return new Function(functionType, arguments);
	}

	private static Expression buildParenthesizedExpression(TokenIterator it, Token current)
			throws Exception {
		it.next();
		Expression expression = buildTerm(it);
		if (expression == null)
			throw new InvalidExpressionException(current);

		current = it.current();
		if (isOf(current, TokenType.RIGHT_PARENTHESIS) == false) {
			throw new InvalidExpressionException(current != null ? current : it.previous());
		}
		
		it.next();
		return expression;
	}

	private static boolean isOf(Token token, TokenType... types) {
		if (token == null)
			return false;
		for (TokenType type : types) {
			if (token.getType() == type)
				return true;
		}
		return false;
	}
}
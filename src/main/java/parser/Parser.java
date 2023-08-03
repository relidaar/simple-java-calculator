package parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Parser {

	private Set<Character> supportedOperators;
	private Set<Character> blankCharacters;

	public Parser() {
		supportedOperators = new HashSet<>();
		supportedOperators.add('+');
		supportedOperators.add('-');

		blankCharacters = new HashSet<>();
		blankCharacters.add(' ');
		blankCharacters.add('\t');
		blankCharacters.add('\n');
	}

	public List<Token> parse(String input) throws InvalidInputException {
		if (input == null || input.isBlank()) {
			return Collections.emptyList();
		}

		List<Token> tokens = new ArrayList<>();
		var scanner = new Scanner(input);
		while (scanner.hasNext()) {
			char currentSymbol = scanner.next();

			if (blankCharacters.contains(currentSymbol)) {
			} else if (Character.isDigit(currentSymbol)) {
				parseNumber(currentSymbol, scanner, tokens);
			} else if (supportedOperators.contains(currentSymbol)) {
				tokens.add(new Token(TokenType.OPERATOR, String.valueOf(currentSymbol)));
			} else {
				throw new InvalidInputException(scanner.getCurrentPosition(), currentSymbol);
			}

		}
		return tokens;
	}

	private void parseNumber(char currentSymbol, Scanner scanner, List<Token> tokens) {
		var buffer = new StringBuffer();
		buffer.append(currentSymbol);

		while (scanner.hasNext()) {
			currentSymbol = scanner.peakNext();
			if (!Character.isDigit(currentSymbol))
				break;
			scanner.next();
			buffer.append(currentSymbol);
		}

		tokens.add(new Token(TokenType.NUMBER, buffer.toString()));
	}
}

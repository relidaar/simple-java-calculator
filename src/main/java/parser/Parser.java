package parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class Parser {

	private static Set<Character> supportedOperators;
	private static Set<Character> blankCharacters;
	
	private Parser() {}

	static {
		supportedOperators = new HashSet<>();
		supportedOperators.add('+');
		supportedOperators.add('-');

		blankCharacters = new HashSet<>();
		blankCharacters.add(' ');
		blankCharacters.add('\t');
		blankCharacters.add('\n');	
	}

	public static List<Token> parse(String input) throws Exception {
		if (input == null || input.isBlank()) {
			return Collections.emptyList();
		}

		List<Token> tokens = new ArrayList<>();
		var scanner = new Scanner(input);
		while (scanner.hasNext()) {
			char currentSymbol = scanner.next();

			if (blankCharacters.contains(currentSymbol)) {
			} else if (Character.isDigit(currentSymbol)) {
				tokens.add(NumberParser.parse(scanner));
			} else if (supportedOperators.contains(currentSymbol)) {
				tokens.add(OperatorParser.parse(scanner));
			} else {
				throw new InvalidInputException(scanner.getCurrentPosition(), currentSymbol);
			}
		}
		
		return tokens;
	}
}

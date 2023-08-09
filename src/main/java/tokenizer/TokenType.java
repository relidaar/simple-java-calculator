package tokenizer;

public enum TokenType {
	NUMBER, PLUS, MINUS, STAR, SLASH, PERCENT, LEFT_PARENTHESIS, RIGHT_PARENTHESIS, COMMA, IDENTIFIER, CARET;
	
	public static TokenType valueOf(char symbol) {
		return switch(symbol) {
		case '+' -> PLUS;
		case '-' -> MINUS;
		case '*' -> STAR;
		case '/' -> SLASH;
		case '%' -> PERCENT;
		case '(' -> LEFT_PARENTHESIS;
		case ')' -> RIGHT_PARENTHESIS;
		case ',' -> COMMA;
		case '^' -> CARET;
		default -> null;
		};
	}
	
	public static boolean contains(char symbol) {
		return valueOf(symbol) != null;
	}
}

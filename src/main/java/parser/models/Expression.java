package parser.models;

import parser.Context;

public interface Expression {
	public Double evaluate(Context context) throws Exception;
}
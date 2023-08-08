package parser.models;

import parser.Context;
import parser.UndefinedIdentifier;

public class Variable implements Expression {
	private String mName;
	
	public Variable(String name) {
		mName = name;
	}

	@Override
	public Double evaluate(Context context) throws Exception {
		if (!context.containsKey(mName)) {
			throw new UndefinedIdentifier(mName);
		}
		
		return context.get(mName);
	}
}

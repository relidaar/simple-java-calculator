package parser;

import java.util.*;

import parser.models.Function.FunctionType;

public class Context {
	private Map<String, Double> mCache = new HashMap<>();
	
	public void add(String key, Double value) throws Exception {
		if (FunctionType.toFunctionType(key) != null) 
			throw new Exception("Identifier \"%s\" is a reserved keyword".formatted(key));
		
		if (mCache.containsKey(key)) 
			throw new Exception("Identifier \"%s\" has already been defined".formatted(key));
		mCache.put(key, value);
	}
	
	public boolean containsKey(String key) {
		return mCache.containsKey(key);
	}
	
	public Double get(String key) {
		return mCache.get(key);
	}
	
	public void delete(String key) {
		if (!containsKey(key)) return;
		mCache.remove(key);
	}
	
	public void clear() {
		mCache.clear();
	}
	
	@Override
	public String toString() {
		return mCache.toString();
	}
}

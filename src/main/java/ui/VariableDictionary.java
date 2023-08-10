package ui;

import java.util.*;

import javafx.collections.*;
import javafx.scene.control.*;

public class VariableDictionary {
	private ObservableSet<String> mCache;
	private ObservableList<String> mKeys;
	private ObservableList<Variable> mItems;
	
	public VariableDictionary() {
		mItems = FXCollections.observableArrayList();
		mKeys = FXCollections.observableArrayList();
		mCache = FXCollections.observableSet();
		mItems.addListener((ListChangeListener<Variable>) change -> {
			List<String> newNames = change.getList().stream().map(Variable::getName).toList();
			mKeys.setAll(newNames);
			mCache.clear();
			mCache.addAll(newNames);
		});
	}
	
	public ObservableList<String> getKeys() {
		return mKeys;
	}
	
	public ObservableList<Variable> getItems() {
		return mItems;
	}
	
	public void clear() {
		mItems.clear();
	}
	
	public boolean add(String name, Double value) {
		if (mCache.contains(name)) return false;
		mItems.add(new Variable(name, value));
		return true;
	}
	
	public void remove(Variable variable) {
		mItems.remove(variable);
	}
}

package ui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;

import java.net.URL;
import java.util.*;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.input.KeyCode;

public class VariablesDictionaryController implements Initializable {
	private final VariableDictionary mVariables;

	public VariablesDictionaryController(VariableDictionary variables) {
		mVariables = variables;
	}

    @FXML
    private Label messageLabel;

    @FXML
    private Button addItemButton;

    @FXML
    private TextField variableNameField;

    @FXML
    private TextField variableValueField;

	@FXML
	private Button clearDictionaryButton;

	@FXML
	private Button saveDictionaryButton;

	@FXML
	private TableColumn<Variable, String> variableNameColumn;

	@FXML
	private TableColumn<Variable, Double> variableValueColumn;

	@FXML
	private TableView<Variable> variablesDictionary;

    @FXML
    void addItem(ActionEvent event) {
    	String name = variableNameField.getText();
    	String value = variableValueField.getText();
    	if (name.isBlank()) {
    		messageLabel.setText("The name field must not be empty");
    		return;
    	}
    	if (value.isBlank()) {
    		messageLabel.setText("The value field must not be empty");
    		return;
    	}
    	
    	try {
        	boolean successfullyAdded = mVariables.add(name, Double.parseDouble(value));
        	if (successfullyAdded) {
        		variableNameField.clear();
        		variableValueField.clear();
        		messageLabel.setText("");
        	} else {
        		messageLabel.setText("Variable \"%s\" is already defined".formatted(name));
        	}
		} catch (Exception e) {
    		messageLabel.setText("\"%s\" must be a number".formatted(value));
		}
    }

	@FXML
	void clearDictionary(ActionEvent event) {
		mVariables.clear();
		variableNameField.clear();
		variableValueField.clear();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		variablesDictionary.setItems(mVariables.getItems());
		variablesDictionary.setOnKeyPressed(keyEvent -> {
			Variable selectedItem = variablesDictionary.getSelectionModel().getSelectedItem();
			if (selectedItem == null) return;
			
			if (keyEvent.getCode().equals(KeyCode.DELETE)) {
				mVariables.remove(selectedItem);
			}
		});
		
		variableNameColumn.setCellValueFactory(new PropertyValueFactory<Variable, String>("name"));
		variableValueColumn.setCellValueFactory(new PropertyValueFactory<Variable, Double>("value"));

		variableNameField.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (!newValue.matches("[A-Za-z]*")) {
		        	variableNameField.setText(newValue.replaceAll("[^[A-Za-z]]", ""));
		        }
		    }
		});
	}
}

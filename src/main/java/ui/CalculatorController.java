package ui;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.*;
import parser.*;
import tokenizer.*;

public class CalculatorController implements Initializable {
	private static final VariableDictionary mVariables = new VariableDictionary();

    @FXML
    private GridPane mainGrid;
	
	@FXML
	private TextArea inputField;

	@FXML
	private TextField resultField;

	@FXML
	private ListView<String> variablesList;

	@FXML
	void clear(ActionEvent event) {
		inputField.clear();
		resultField.clear();
	}

	@FXML
	void removeLast(ActionEvent event) {
		String input = inputField.getText().trim();
		if (!input.isEmpty()) {
			input = input.substring(0, input.length() - 1);
		}
		inputField.setText(input.trim());
	}

	@FXML
	void evaluate(ActionEvent event) {
		try {
			String input = inputField.getText().trim();
			inputField.setText(input);
			var tokens = Tokenizer.parse(input);
			var expression = Parser.buildExpression(tokens);

			if (expression != null) {
				var result = expression.evaluate(createContext());
				resultField.setText("= %s".formatted(result));
				resultField.setStyle("-fx-text-fill: black;");
			}

		} catch (Exception e) {
			resultField.setText(e.getMessage());
			resultField.setStyle("-fx-text-fill: red;");
		}
	}

	@FXML
	void insertFunction(ActionEvent event) {
		Button btn = (Button) event.getSource();
		String input = "%s%s(".formatted(inputField.getText(), btn.getText());
		inputField.setText(input.trim());
	}

	@FXML
	void insertSymbol(ActionEvent event) {
		Button btn = (Button) event.getSource();
		String input = inputField.getText() + btn.getText();
		inputField.setText(input.trim());
	}

	@FXML
	void showVariables(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("VariablesWindow.fxml"));
        loader.setController(new VariablesDictionaryController(mVariables));
		Stage variablesWindow = new Stage();
		Parent root = loader.load();
		variablesWindow.setTitle("Variables Dictionary");
		variablesWindow.initModality(Modality.WINDOW_MODAL);
		variablesWindow.initOwner(((Node) event.getSource()).getScene().getWindow());
		variablesWindow.setScene(new Scene(root));
		variablesWindow.show();
		
		variablesWindow.setMinHeight(variablesWindow.getHeight());
		variablesWindow.setMinWidth(variablesWindow.getWidth());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		variablesList.setItems(mVariables.getKeys());
		variablesList.setOnMouseClicked(event -> {
			String variable = variablesList.getSelectionModel().getSelectedItem();
			if (variable == null) return;
			String input = inputField.getText() + variable;
			inputField.setText(input.trim());
		});
		
		variablesList.setOnKeyPressed(keyEvent -> {
			String variable = variablesList.getSelectionModel().getSelectedItem();
			if (variable == null) return;
			if (keyEvent.getCode().equals(KeyCode.SPACE)) {
				String input = inputField.getText() + variable;
				inputField.setText(input.trim());
			}
		});

		final KeyCombination kc = new KeyCodeCombination(KeyCode.TAB);
		inputField.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
	        if (kc.match(keyEvent)) {
	            int ind = mainGrid.getChildren().indexOf(inputField);
	            Node next = mainGrid.getChildren().get(ind + 2);
	            next.requestFocus();
	            keyEvent.consume();
	        }
		});
	}
	
	private Context createContext() throws Exception {
		Context context = new Context();
		for (Variable v : mVariables.getItems()) {
			context.add(v.getName(), v.getValue());
		}
		return context;
	}
}

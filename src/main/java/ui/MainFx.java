package ui;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainFx extends Application {
	public void hello(String[] args) {
		launch(args);
	}

    @Override
    public void start(Stage mainWindow) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
		loader.setController(new CalculatorController());
        Parent root = loader.load();
        mainWindow.setTitle("Calculator--");
        mainWindow.setScene(new Scene(root));
        mainWindow.setResizable(false);
        mainWindow.show();
    }
}
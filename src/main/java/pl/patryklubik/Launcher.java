package pl.patryklubik;

import javafx.application.Application;
import javafx.stage.Stage;
import pl.patryklubik.view.ViewFactory;

/**
 * JavaFX App
 */
public class Launcher extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        ViewFactory viewFactory = new ViewFactory(new EmailManager());
        viewFactory.showLoginWindow();
    }

    public static void main(String[] args) {
        launch();
    }
}
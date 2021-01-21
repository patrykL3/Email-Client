package pl.patryklubik;

import javafx.application.Application;
import javafx.stage.Stage;

public class Launcher extends Application {

    ProgramState programState = new ProgramState();

    @Override
    public void start(Stage stage) throws Exception {
        programState.init();
    }

        @Override
    public void stop() throws Exception {
        programState.saveAccountsToPersistence();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
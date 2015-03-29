package GMsis.parts;

/**
 * Temporary Main for parts module
 * to be used for testing before integration
 * with other modules
 **/

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lib.DBConn;


public class Main extends Application {

    private static DBConn db;

    public static void main(String[] args) {
        db = DBConn.getInstance();
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        db.getConn();

        Parent root = (new PartsLauncher()).launch();

        Scene scene = new Scene(root, 800, 500);
        stage.setScene(scene);
        stage.setTitle("GMSIS - Parts");
        stage.show();
    }

    @Override
    public void stop() {
        db.close();
    }


}

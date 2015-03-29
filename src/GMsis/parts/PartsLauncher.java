package GMsis.parts;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

/**
 * Created by filipt on 25/03/15.
 */
public class PartsLauncher {

    public Parent launch() {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("frontend/PartsFrontEnd.fxml"));
        } catch (IOException e) {
            System.out.println("Can't load FXML file for main app.");
            e.printStackTrace();
            System.exit(1);
        }
        return root;
    }
}

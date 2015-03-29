package GMsis.parts.frontend.forms;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import org.controlsfx.dialog.Dialogs;
import org.controlsfx.dialog.*;
import GMsis.parts.frontend.MainPartsController;


/**
 * Created by filipt on 3/20/15.
 */
public abstract class Form {

    protected FXMLLoader loader;
    protected MainPartsController mainController;
    protected Stage window;
    protected Parent root;

    protected Form(MainPartsController mainController) {
        this.mainController = mainController;
    }

    abstract public void show();
    abstract public void show(Integer id);

    protected void alert(String msg) {
        Dialogs.create()
                .title("Error Message")
                .message(msg)
                .showInformation();

    }

}

package GMsis.parts.frontend.forms;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import GMsis.parts.DateUtils;
import GMsis.parts.backend.Part;
import GMsis.parts.backend.PartOrder;
import GMsis.parts.frontend.MainPartsController;

import javax.xml.soap.Text;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by filipt on 06/03/15.
 */
public class OrderFormController extends Form{

    @FXML
    private TextField partIDField;
    @FXML
    private TextField dateField;
    @FXML
    private TextField idField;
    @FXML
    private TextField quantityField;

    @FXML
    private Button submitBtn;
    @FXML
    private Button closeBtn;

    public OrderFormController(MainPartsController mainController) {
        super(mainController);
    }

    @Override
    public void show() {
        loader = new FXMLLoader(getClass().getResource("Order-Form.fxml"));

        try {
            loader.setController(this);
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading Order form form");
        }

        window = new Stage();
        Scene scene = new Scene(root, 375, 424);
        window.setScene(scene);
        window.setTitle("Order new Part");
        setListeners();
        window.show();
    }

    @Override
    public void show(Integer id) throws NullPointerException{
        show();
        PartOrder order = mainController.getPartsGarage().supplierManager.getOrder(id);
        idField.setText(order.getID().toString());
        partIDField.setText(order.getPartID().toString());
        dateField.setText(order.deliveryToStringProperty().get());
        quantityField.setText(order.getQuantity().toString());
    }

    private void setListeners() {
        closeBtn.setOnAction((event) -> {
            window.close();
            root = null;
        });
        submitBtn.setOnAction((event) -> {
            addPartOrder();
            window.close();
        });
    }

    public void addPartOrder() {
        Integer ID = Integer.valueOf(idField.getText());
        Integer partID = Integer.valueOf(partIDField.getText());
        Integer quantity = Integer.valueOf(quantityField.getText());
        System.out.println(quantity);

        Date date;

        try {
            date = DateUtils.str2date(dateField.getText());
        } catch (ParseException e) {
            alert("The date must be in the format: \"" + DateUtils.FMT + "\"");
            return;
        }

        if (partExists(partID))
            mainController.getPartsGarage().supplierManager.orderPart(ID, partID, date, quantity);
        else
            alert(String.format("Part with ID %d does not exist", partID));
    }

    private boolean partExists(Integer id) {
        for (Part p : mainController.getPartsGarage().getPartsInStock()) {
            if (p.getID().equals(id)) {
               return true;
            }
        }
        return false;
    }

}

package GMsis.parts.frontend.forms;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import GMsis.parts.backend.Supplier;
import GMsis.parts.frontend.MainPartsController;

import java.io.IOException;

/**
 * Created by filipt on 06/03/15.
 */
public class SuppliersFormController extends Form{

    @FXML
    private Button closeBtn;
    @FXML
    private Button submitBtn;

    @FXML
    private TextField supplierIDField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField fstAddrField;
    @FXML
    private TextField sndAddrField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField postField;
    @FXML
    private TextField phoneNoField;

    public SuppliersFormController(MainPartsController mainController) {
        super(mainController);
    }

    @Override
    public void show() {
        loader = new FXMLLoader(getClass().getResource("Suppliers-Form.fxml"));

        try {
            loader.setController(this);
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading parts form");
        }

        window = new Stage();
        Scene scene = new Scene(root, 375, 424);
        window.setScene(scene);
        window.setTitle("Add a new supplier");
        setListeners();
        window.show();
    }

    @Override
    public void show(Integer id) throws NullPointerException{
        show();
        Supplier supplier = mainController.getPartsGarage().supplierManager.getSupplier(id);
        supplierIDField.setText(id.toString());
        nameField.setText(supplier.getName());
        fstAddrField.setText(supplier.getFirstLineAddress());
        sndAddrField.setText(supplier.getSecondLineAddress());
        cityField.setText(supplier.getCity());
        postField.setText(supplier.getPostCode());
        phoneNoField.setText(supplier.getPhoneNumber().toString());
    }



    private void setListeners() {
        closeBtn.setOnAction((event) -> {
            window.close();
            root = null;
        });
        submitBtn.setOnAction((event) -> {
            if (addSupplierRecord())
                window.close();
        });
    }

    private boolean addSupplierRecord() {
        try {

            String name = nameField.getText();
            String fstAddr = fstAddrField.getText();
            String secondAddr = sndAddrField.getText();
            String city = cityField.getText();
            String postCode = postField.getText();
            Integer phone = Integer.valueOf(phoneNoField.getText());
            System.out.println(supplierIDField.getText());
            Integer id = Integer.valueOf(supplierIDField.getText());

            if (!mainController.getPartsGarage().supplierManager.supplierExists(id)) {
                mainController.getPartsGarage().supplierManager.addSupplier(id, name, fstAddr,
                        secondAddr, city, postCode, phone);
            }
            else {
                Supplier current = mainController.getPartsGarage().supplierManager.getSupplier(id);
                if (!current.getName().equals(name)) {
                    current.setName(name);
                }
                if (!current.getFirstLineAddress().equals(fstAddr)) {
                    current.setFirstLineAddress(fstAddr);
                }
                if (!current.getSecondLineAddress().equals(secondAddr)) {
                    current.setSecondLineAddress(secondAddr);
                }
                if (!current.getCity().equals(city)) {
                    current.setCity(city);
                }
                if (!current.getPostCode().equals(postCode)) {
                    current.setPostCode(postCode);
                }
                if (!current.getPhoneNumber().equals(phone)) {
                    current.setPhoneNumber(phone);
                }

            }

        } catch (NullPointerException e) {
            alert("Must fill out fields");
            return false;
        } catch (NumberFormatException e) {
            alert("You entered a non-numeric character where numbers where required");
            e.printStackTrace();
            return false;
        } catch (IllegalArgumentException e) {
            alert("You must fill out all fields in this form.");
            return false;
        }

        return true;
    }
}

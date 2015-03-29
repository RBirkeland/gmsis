package GMsis.parts.frontend.forms;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import GMsis.parts.backend.Part;
import GMsis.parts.frontend.MainPartsController;

/**
 * Created by filipt on 06/03/15.
 */
public class PartsFormController extends Form {

    @FXML
    private Button closeBtn;
    @FXML
    private Button submitBtn;

    @FXML
    private TextField partIDField;
    @FXML
    private TextField supplierIDField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField descField;
    @FXML
    private TextField stockField;
    @FXML
    private TextField costField;

    public PartsFormController(MainPartsController mainController) {
        super(mainController);
    }

    @Override
    public void show() {
        loader = new FXMLLoader(getClass().getResource("Parts-Form.fxml"));

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
        window.setTitle("Add a new part");
        setListeners();
        window.show();
    }

    @Override
    public void show(Integer id) throws NullPointerException{
        show();
        Part part = mainController.getPartsGarage().getPart(id);
        partIDField.setText(part.getID().toString());
        supplierIDField.setText(part.getSupplierID().toString());
        nameField.setText(part.getName());
        descField.setText(part.getDescription());
        stockField.setText(part.getCurrentStock().toString());
        costField.setText(part.getCost().toString());
    }

    private void setListeners() {
        closeBtn.setOnAction((event) -> {
            root = null;
            window.close();
        });
        submitBtn.setOnAction((event) -> {
            addPartRecord();
            mainController.updateTableLists();
            window.close();

        });
    }

    private void addPartRecord() {

        try {
            Integer id = Integer.valueOf(partIDField.getText());
            Integer sid = Integer.valueOf(supplierIDField.getText());
            String name = nameField.getText();
            String description = descField.getText();
            Integer stock = Integer.valueOf(stockField.getText());
            Double cost = Double.valueOf(costField.getText());

            if (!mainController.getPartsGarage().partExists(id)) {
                mainController.getPartsGarage().addPart(id, sid, name, description, stock, cost);
            }
            else {
                Part current = mainController.getPartsGarage().getPart(id);
                if (!current.getSupplierID().equals(sid)) {
                    current.setSupplierID(sid);
                }
                if (!current.getName().equals(name)) {
                    current.setName(name);
                }
                if (!current.getDescription().equals(description)) {
                    current.setDescription(description);
                }
                if (!current.getCurrentStock().equals(stock)) {
                    current.setCurrentStock(stock);
                }
                if (!current.getCost().equals(cost)) {
                    current.setCost(cost);
                }
            }

            if (name.equals("") || description.equals("")) {
                throw new IllegalArgumentException();
            }

        } catch (NullPointerException e) {
            alert(e.getMessage());
        } catch (NumberFormatException e) {
            alert("You entered a non-numeric character where numbers where required");
        } catch (IllegalArgumentException e) {
            alert("You must fill out all fields in this form.");
        }
    }
}

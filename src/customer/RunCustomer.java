package customer;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lib.db.DBConn;
import javafx.scene.control.TableView;
import org.controlsfx.dialog.Dialogs;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Created by Rene on 13/02/2015.
 *
 * GUI controller class.
 * Initialiate and runs the
 */
public class RunCustomer extends Application implements Initializable {
    static CustomerManager c;
    private static DBConn connection;

    public static void main(String[] args) {
        //Gets the singelton instance to connect to the database
        connection = DBConn.getInstance();
        connection.getConn();

        //Creates a manager, and reads from the Customer table and stores it in customer objects.
        c = new CustomerManager();
        c.read();

        //Lunches GUI
        launch(args);

        try {
            c.doSomething();

        } catch (ClassNotFoundException cnfe) {
            System.out.println("class not found");
        }
        connection.close();
    }

    private Stage primaryStage;
    private TabPane rootLayout;

    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Customer");
        System.out.println("******");
        initRootLayout();
    }

    //Loads the FXML file and shows the stage
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(CustomerManager.class.getResource("gui.fxml"));
            rootLayout = (TabPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

            //Check if needed to call customer
            ArrayList<Integer> customersToCall = c.alert();
            if(customersToCall.size() > 0) {
                String s = "";
                for(Integer i : customersToCall) {
                    s += "ID: "+i+"\n";
                }
                popup("Customers to call about booking", s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Popup window for errors and information
    public void popup(String s1, String s2) throws IOException{
        Dialogs.create()
                .owner(primaryStage)
                .title("Information Dialog")
                .masthead(s1)
                .message(s2)
                .showInformation();
    }

    //NEW CUSTOMER TAB
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField adr1;
    @FXML
    private TextField adr2;
    @FXML
    private TextField town;
    @FXML
    private TextField postCode;
    @FXML
    private TextField phone;
    @FXML
    final ToggleGroup group = new ToggleGroup();
    @FXML
    private RadioButton businessType;
    @FXML
    private RadioButton privateType;


    //Event listener for Submit buttom
    public void submit() throws IOException {
        //Checks if required fields are empty
        if(firstName.getText().equals("") || lastName.getText().equals("") || adr1.getText().equals("") || town.getText().equals("") || postCode.getText().equals("")) {
            popup("Error", "Please fill out all fields that are marked with *");
        } else if(!isNumeric(phone.getText())) {
            popup("Error", "Phone number has to be a number");
        } else {
            //Crreate new customer
            String type = null;
            if(privateType.isSelected()) type = "private";
            else type = "business";
            if(!c.newCustomer(firstName.getText(), lastName.getText(), adr1.getText(), adr2.getText(), town.getText(), postCode.getText(), phone.getText(), type)) popup(null, "Customer already exists");
            System.out.println("Added " + firstName.getText() + " " + lastName.getText());

        }
    }

    public static boolean isNumeric(String str) {
        try {
            int d = Integer.parseInt(str);
        }
        catch(NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    //FIND CUSTOMER TAB
    @FXML
    private TextField findFirstName;
    @FXML
    private TextField findLastName;
    @FXML
    private Label findError;
    @FXML
    private TextArea findOutput;

    //Event listener for submit button
    public void findSubmit() throws IOException {
        //Checks if required fields are empty
        if(findFirstName.getText().equals("") || findLastName.getText().equals("")) {
            popup("Error", "Please fill out all the fields");
        } else {
            //Finds customer object based on name
            Customer tmp = c.findCustomerName(findFirstName.getText(), findLastName.getText());
            if(tmp == null) {
                popup("Error", "Customer does not exist");
            } else {
                findOutput.setText("ID: " + tmp.getId() + "\nFirst name: " + tmp.getFirst() + "\nLast name: " + tmp.getLast()+ "\nAddress 1: " + tmp.getAdr1()+ "\nAddress 2: " + tmp.getAdr2() + "\nTown: " + tmp.getTown()+ "\nPost code: " + tmp.getPostCode()+ "\nPhone: " + tmp.getPhone() + "\nType: "+ tmp.getType());
            }
        }
    }

    //CUSTOMER ACCOUNT TAB
    @FXML private TextField accountFirstName;
    @FXML private TextField accountLastName;

    @FXML TableView<Booking> tableView;
    @FXML TableColumn<Booking, String> date;
    @FXML TableColumn<Booking, String> customer;
    @FXML TableColumn<Booking, String> vehicle;
    @FXML TableColumn<Booking, String> booking;
    @FXML TableColumn<Booking, String> parts;

    ObservableList<Booking> data = FXCollections.observableArrayList();
    public void table(int id) throws IOException {
        data.clear();
        ArrayList<Booking> blist = c.readBooking();
        if(blist.size() < 1) popup("Info", "The customer does not have any registered vehicles");
        c.readCustomer(blist);
        c.readParts(blist);
        c.readDate(blist);


        for(Booking b : blist) {
            System.out.println("Booking: "+b.getBookings());
            System.out.println("Customer: "+b.getCustomer());
            System.out.println("Vehicle: "+b.getVehicles());
            System.out.println("Parts: "+b.getParts());
            System.out.println();
            if(b.getCustomer().equals(id+""))
            data.add(b);
        }
        /*if(v.size() < 1) popup(null, "The customer does not have any registered vehicles");
        for(myVehicle mv : v) {
            c.readParts(mv);
            c.readBooking(mv);
        }

        Account a;
        for(myVehicle mv : v) {
           a = new Account();
            a.setCustomer(mv.getCustomer()+"");
            a.setVehicles(mv.getVehicle()+"");
            System.out.println("Customer: " + mv.getCustomer());

            String s = "";
            String s2 = "";

            for(Integer i : mv.getParts()) {
                s2 += i+", ";
            }
            System.out.println("Parts: "+s2);
            a.setParts(s2);

            for(Integer i : mv.getBookings()) {
                s += i+", ";
            }
            System.out.println("Booking: " +s);
            a.setBookings(s);

            data.add(a);
        }*/

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        date.setCellValueFactory(new PropertyValueFactory<Booking, String>("date"));
        customer.setCellValueFactory(new PropertyValueFactory<Booking, String>("customer"));
        booking.setCellValueFactory(new PropertyValueFactory<Booking, String>("bookings"));
        vehicle.setCellValueFactory(new PropertyValueFactory<Booking, String>("vehicles"));
        parts.setCellValueFactory(new PropertyValueFactory<Booking, String>("parts"));
        tableView.setItems(data);
    }

    //Submit button event listener
    public void accountButton() throws IOException {
        String first = accountFirstName.getText();
        String last = accountLastName.getText();
        if(first.equals("") || last.equals("")) {
            popup("Error", "Enter a name");
        } else {
            if(!c.existsName(first, last)) popup("Error", "Customer does not exist");
            else {
                table(c.findCustomerName(first, last).getId());
            }

        }
    }

    //Settle account button event listener
    public void settleAccount() throws IOException {
        if(accountFirstName.getText().equals("") || accountLastName.getText().equals("")) {
            popup("Error", "Enter a name");
        } else {
            Customer cus = c.findCustomerName(accountFirstName.getText(), accountLastName.getText());
            System.out.println(c.isSetteled(cus.getId()));
            int isSetteled = c.isSetteled(cus.getId());
            if(isSetteled == 1) {
                popup("Info", "The account is already settled");

            } else if(isSetteled == 0) {
                popup("Confirmation", "Account is now settled");
                c.settle(cus.getId());
            } else {
                popup("Error", "Customer does not have an account");
            }
        }
    }
}

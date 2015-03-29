package GMsis.booking;

import java.net.URL;
import java.sql.*;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import lib.DBConn;
import org.joda.time.DateTimeZone;

public class FXMLDocumentController implements Initializable {

    @FXML
    private ToggleGroup Stype;
    @FXML
    private Button add_btn;
    @FXML
    private Button remove_btn;
    @FXML
    private Button upf_btn;
    @FXML
    private DatePicker mydatepicker;
    @FXML
    private ComboBox cid_input;
    @FXML
    private TextField mile_input;
    @FXML
    private TextField bid_input;
    @FXML
    private ComboBox vid_input;
    @FXML
    private ComboBox bay_input;
    @FXML
    private ComboBox time_input;
    @FXML
    private RadioButton mot_btn;
    @FXML
    private TextArea f_area;
    @FXML
    private TableView<SMB> bookingtable;
    @FXML
    private TableColumn<SMB, Integer> bid_col;
    @FXML
    private TableColumn<SMB, Integer> cid_col;
    @FXML
    private TableColumn<SMB, Integer> vid_col;
    @FXML
    private TableColumn<SMB, LocalDate> date_col;
    @FXML
    private TableColumn<SMB, LocalTime> time_col;
    @FXML
    private TableColumn<SMB, Integer> m_col;
    @FXML
    private TableColumn<SMB, Integer> bay_col;
    @FXML
    private TableColumn<SMB, Boolean> type_col;
    @FXML
    private TableColumn<SMB, String> f_col;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        final DateTimeZone jodaTzUTC = DateTimeZone.forID("UTC");
        //load database
        final String path = "jdbc:sqlite:Resources/gmsis.db";//path of database

        //create connection and load some data to initialize form
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(path);
            c.setAutoCommit(false);

            stmt = c.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT \"Customer ID\" FROM Customer;");
            while (rs.next()) {
                cid_input.getItems().addAll(rs.getInt("Customer ID"));
            }
            
            cid_input.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    updateVehicleID((int) newValue);
         });

            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        remove_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override

            public void handle(ActionEvent e) {
                String[] options = null;
                Connection c = null;
                Statement stmt = null;
                try {
                    Class.forName("org.sqlite.JDBC");
                    c = DriverManager.getConnection(path);
                    c.setAutoCommit(false);
                    stmt = c.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS total FROM Sbooking;");
                    while (rs.next()) {
                        options = new String[rs.getInt("total")];
                    }

                    rs = stmt.executeQuery("SELECT \"Sbooking ID\" FROM Sbooking;");
                    int count = 0;
                    while (rs.next()) {
                        options[count] = rs.getInt("Sbooking ID") + "";
                        count++;
                    }
                    rs.close();
                    stmt.close();
                    c.close();
                } catch (ClassNotFoundException | SQLException ex) {
                    System.err.println(e.getClass().getName() + ": " + ex.getMessage());
                    System.exit(0);
                }

                String removeid = (String) JOptionPane.showInputDialog(null, "Sbooking ID",
                        "which booking id you want to remove?", JOptionPane.QUESTION_MESSAGE, null,
                        options, // Array of choices
                        options[0]); // Initial choice

                if (removeid != null) {
                    int removeidint = Integer.parseInt(removeid);
                    c = null;
                    stmt = null;
                    try {
                        Class.forName("org.sqlite.JDBC");
                        c = DriverManager.getConnection(path);
                        c.setAutoCommit(false);
                        stmt = c.createStatement();

                        PreparedStatement statement = c.prepareStatement("DELETE FROM Sbooking WHERE \"Sbooking ID\" =?;");
                        statement.setInt(1, removeidint);

                        statement.execute();
                        c.commit();
                        stmt.close();
                        c.close();

                    } catch (ClassNotFoundException | SQLException ex) {
                        System.err.println(e.getClass().getName() + ": " + ex.getMessage());
                        System.exit(0);
                    }
                }
                bookingtable.setItems(load(path));
            }
        });

        upf_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String[] options = null;
                Connection c = null;
                Statement stmt = null;
                try {
                    Class.forName("org.sqlite.JDBC");
                    c = DriverManager.getConnection(path);
                    c.setAutoCommit(false);
                    stmt = c.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS total FROM Sbooking;");
                    while (rs.next()) {
                        options = new String[rs.getInt("total")];
                    }

                    rs = stmt.executeQuery("SELECT \"Sbooking ID\" FROM Sbooking;");
                    int count = 0;
                    while (rs.next()) {
                        options[count] = rs.getInt("Sbooking ID") + "";
                        count++;
                    }
                    rs.close();
                    stmt.close();
                    c.close();
                } catch (ClassNotFoundException | SQLException ex) {
                    System.err.println(e.getClass().getName() + ": " + ex.getMessage());
                    System.exit(0);
                }

                //JPanel myPanel = new JPanel();
                JComboBox idbox = new JComboBox(options);
                JTextArea ta = new JTextArea();
                //myPanel.add(idbox);
                //myPanel.add(ta);
                Object[] myobj = {
                    "Sbooking ID:", idbox,
                    "failure:", ta
                };

                int result = JOptionPane.showConfirmDialog(null, myobj, "please enter Sbooking ID and failur", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    int b = Integer.parseInt((String) idbox.getSelectedItem());
                    String f = ta.getText();

                    c = null;
                    stmt = null;
                    try {
                        Class.forName("org.sqlite.JDBC");
                        c = DriverManager.getConnection(path);
                        c.setAutoCommit(false);
                        stmt = c.createStatement();
                        PreparedStatement statement = c.prepareStatement("UPDATE Sbooking SET failure=? WHERE \"Sbooking ID\"=?;");
                        statement.setString(1, f);
                        statement.setInt(2, b);

                        statement.execute();
                        c.commit();
                        stmt.close();
                        c.close();

                    } catch (ClassNotFoundException | SQLException ex) {
                        System.err.println(e.getClass().getName() + ": " + ex.getMessage());
                        System.exit(0);
                    }
                    bookingtable.setItems(load(path));

                }

            }
        });

        //method on add button
        add_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override

            public void handle(ActionEvent e) {
                // check input valid 
                if ((mydatepicker.getValue().compareTo(LocalDate.now()) <= 0)) {
                    JOptionPane.showMessageDialog(null, "Booking can only be made in future");
                } else if ((time_input.getValue() == null) || ((String) time_input.getValue()).equals("NOT open on Sunday")) {
                    JOptionPane.showMessageDialog(null, "need a time");
                } else if (vid_input.getValue() == null) {
                    JOptionPane.showMessageDialog(null, "Vehicle ID incorrect");
                } else if (cid_input.getValue() == null) {
                    JOptionPane.showMessageDialog(null, "Customer ID incorrect");
                } else {

                    //get data from input element in form
                    int n = Integer.parseInt((String) bid_input.getText());
                    int c = (Integer) cid_input.getValue();
                    int v = (Integer) vid_input.getValue();
                    LocalDate ld = mydatepicker.getValue();
                    Date d = ldtod.ldtodate(ld.getYear(), ld.getMonthValue(), ld.getDayOfMonth());
                    Time t = Time.valueOf(time_input.getValue() + ":00");
                    int m = Integer.parseInt(mile_input.getText());
                    int b = Integer.parseInt((String) bay_input.getValue());
                    String type;
                    if (mot_btn.isSelected()) {
                        type = "MOT";
                    } else {
                        type = "Service";
                    }
                    String f = f_area.getText();
                    //make a new SMB 
                    SMB smb = new SMB(n, c, v, type, d, t, m, b, f);
                    if (checkok(d, t, b, path)) {
                        //insert it into database
                        Connection con = null;
                        Statement stmt = null;
                        try {
                            Class.forName("org.sqlite.JDBC");
                            con = DriverManager.getConnection(path);
                            con.setAutoCommit(false);

                            stmt = con.createStatement();

                            PreparedStatement p = con.prepareStatement("INSERT INTO Sbooking ('Sbooking ID','Customer ID','Vehicle ID',type,date,time,mileage,bayNo,failure) VALUES(?,?,?,?,?,?,?,?,?)");
                            p.setInt(1, n);
                            p.setInt(2, c);
                            p.setInt(3, v);
                            p.setString(4, type);
                            p.setDate(5, d);
                            p.setTime(6, t);
                            p.setInt(7, m);
                            p.setInt(8, b);
                            p.setString(9, f);
                            p.execute();
                            p = con.prepareStatement("UPDATE Vehicle SET Mileage=? WHERE \"Vehicle ID\"=?;");
                            p.setInt(1, m);
                            p.setInt(2,v);
                            p.execute();
                            //stmt.executeUpdate(sql);
                            stmt.close();
                            con.commit();
                            con.close();
                        } catch (Exception ex) {
                            System.err.println(e.getClass().getName() + ": " + ex.getMessage());
                            System.exit(0);
                        }

                        bookingtable.setItems(load(path));
                        n++;
                        bid_input.setText(n + "");//set booking textfield to next id
                    } else {
                        JOptionPane.showMessageDialog(null, "this bay is taken at this time, plz try other bay or time");
                    }
                }

            }

        }
        );

        cid_input.setEditable(false);
        vid_input.setEditable(false);

        bay_input.setValue("1");
        bid_input.setText(gethighestID(path) + "");
        bid_input.setEditable(false);
        bookingtable.setEditable(true);
        time_input.setEditable(false);

        ObservableList<SMB> data = load(path);

        bid_col.setCellValueFactory(
                new PropertyValueFactory<>("bookingNo"));
        cid_col.setCellValueFactory(
                new PropertyValueFactory<>("customerID"));
        vid_col.setCellValueFactory(
                new PropertyValueFactory<>("vehicleID"));
        date_col.setCellValueFactory(
                new PropertyValueFactory<>("date"));
        time_col.setCellValueFactory(
                new PropertyValueFactory<>("time"));
        m_col.setCellValueFactory(
                new PropertyValueFactory<>("currentmileage"));
        bay_col.setCellValueFactory(
                new PropertyValueFactory<>("bayNo"));
        type_col.setCellValueFactory(
                new PropertyValueFactory<>("type"));
        f_col.setCellValueFactory(
                new PropertyValueFactory<>("failure"));

        bookingtable.setItems(data);

    }
//load data from database for making table
    public void updateVehicleID(int value) {
        ObservableList<Integer> vehIDs = FXCollections.observableArrayList();
        
        DBConn db = DBConn.getInstance();
        db.getConn();
        
        try {
            ResultSet rs = db.queryDB("SELECT \"Vehicle ID\" FROM Vehicle WHERE \"Customer ID\"=" + value + ";");
            while (rs.next()) {
                vehIDs.add(rs.getInt("Vehicle ID"));
                //vid_input.getItems().addAll(rs.getInt("Vehicle ID"));
            }
            vid_input.setItems(vehIDs);
        } catch (Exception e) {
            System.out.println(e);
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
    
    public ObservableList<SMB> load(String path) {
        ArrayList<SMB> al = new ArrayList<SMB>();

        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(path);
            c.setAutoCommit(false);

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Sbooking;");
            while (rs.next()) {
                al.add(new SMB(
                        rs.getInt("Sbooking ID"),
                        rs.getInt("Customer ID"),
                        rs.getInt("Vehicle ID"),
                        rs.getString("type"),
                        rs.getDate("date"),
                        rs.getTime("time"),
                        rs.getInt("mileage"),
                        rs.getInt("bayNo"),
                        rs.getString("failure")
                ));
            }

            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        bid_input.setText(gethighestID(path) + "");
        ObservableList<SMB> data = FXCollections.observableArrayList(al);
        return data;
    }

    //give differe time set to time combo box, depends on day of the week
    public void getoptions() {
        if (mydatepicker.getValue() != null) {
            ObservableList<String> options1 = FXCollections.observableArrayList(
                    "09:00",
                    "09:30",
                    "10:00",
                    "10:30",
                    "11:00",
                    "11:30",
                    "12:00",
                    "12:30",
                    "13:00",
                    "13:30",
                    "14:00",
                    "14:30"
            );
            ObservableList<String> options2 = FXCollections.observableArrayList(
                    "09:00",
                    "09:30",
                    "10:00",
                    "10:30",
                    "11:00"
            );
            ObservableList<String> options3 = FXCollections.observableArrayList("NOT open on Sunday");

            int a = mydatepicker.getValue().getDayOfWeek().getValue();
            if (a == 7) {
                time_input.setItems(options3);
            } else if (a == 6) {
                time_input.setItems(options2);
            } else {
                time_input.setItems(options1);
            }
        }
    }

    public boolean checkok(java.sql.Date d, java.sql.Time t, int b, String path) {
        int flag = -1;
        Time d1 = new Time(t.getTime() + 1800000);
        Time d2 = new Time(t.getTime() - 1800000);
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(path);
            c.setAutoCommit(false);

            stmt = c.createStatement();
            String sql = "SELECT \"Sbooking ID\" FROM Sbooking"
                    + " WHERE date='" + d.getTime() + "' AND bayNo=" + b
                    + " AND(time='" + t.getTime() + "' OR time='" + d1.getTime() + "' OR time='" + d2.getTime() + "');";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                flag = rs.getInt("Sbooking ID");
            }
            
            stmt = c.createStatement();
            sql = "SELECT \"Booking ID\" FROM Bookings"
                    + " WHERE \"Date of Booking\"='" + d.getTime() + "' AND \"Bay Number\"=" + b
                    + " AND(\"Start Time\"='" + t.getTime() + "' OR \"Start Time\"='" + d1.getTime() + "' OR \"Start Time\"='" + d2.getTime() + "');";
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                flag = rs.getInt("Booking ID");
            }
            

            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        if (flag < 0) {
            return true;
        } else {
            System.out.println(flag);
            return false;
        }
    }

    public int gethighestID(String path) {

        int highestID = 0;//int of highest booking ID +1 

        //create connection and load some data to initialize form
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(path);
            c.setAutoCommit(false);
            stmt = c.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT \"Sbooking ID\" FROM Sbooking;");
            while (rs.next()) {
                highestID = rs.getInt("Sbooking ID") + 1;
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return highestID;
    }
    
    private Stage primaryStage;
    
    public void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }
    
    public Stage getPrimaryStage() {
        return primaryStage;
    }

}

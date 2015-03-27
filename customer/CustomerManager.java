package GMsis.customer;

import lib.DBConn;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CustomerManager {

    ArrayList<Customer> customers = new ArrayList<Customer>();
    private static DBConn connection = DBConn.getInstance();

    public void read() {
        try {
            String query = "select * from Customer";
            ResultSet rs = connection.queryDB(query);

            while(rs.next()) {
                int cid = rs.getInt("Customer ID");
                String first = rs.getString("First Name");
                String last = rs.getString("Last Name");
                String adr1 = rs.getString("1st Line Address");
                String adr2 = rs.getString("2nd Line Address");
                String town = rs.getString("Town");
                String postCode = rs.getString("Postal Code");
                String phone = rs.getString("Phone Number");
                String type = rs.getString("Type");

                Customer c = new Customer(cid, first, last, adr1, adr2, town, postCode, phone, type);
                customers.add(c);
            }

        } catch(SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void printC() {
        for(Customer c : customers) {
            System.out.println(c.getId());
        }
    }

    public void printTable(String table) {
        try {
            String query = "SELECT * FROM " + table + ";";
            ResultSet rs = connection.queryDB(query);
            System.out.println("TABLE PRINT");

            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            while (rs.next()) {
                for(int i = 1; i<=columnCount; i++) {
                    System.out.print(rs.getString(rsmd.getColumnName(i)) + " ");
                }
                System.out.println();
            }

        } catch(SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public Customer findCustomerName(String first, String last) {
        for(Customer c : customers) {
            if(c.getFirst().equals(first) && c.getLast().equals(last)) return c;
        }
        //Does not exists
        return null;
    }

    public Customer findCustomer(int id) {
        try {
            String query = "select * from Customer";
            ResultSet rs = connection.queryDB(query);

            while(rs.next()) {
                if(rs.getInt("Customer ID") == id) {
                    int cid = rs.getInt("Customer ID");
                    String first = rs.getString("First Name");
                    String last = rs.getString("Last Name");;
                    String adr1 = rs.getString("1st Line Address");;
                    String adr2 = rs.getString("2nd Line Address");;
                    String town = rs.getString("Town");;
                    String postCode = rs.getString("Postal Code");;
                    String phone = rs.getString("Phone Number");;
                    String type = rs.getString("Type");

                    Customer c = new Customer(cid, first, last, adr1, adr2, town, postCode, phone, type);
                    return c;
                }
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public boolean newCustomer(String first, String last, String adr1, String adr2, String town, String postCode, String phone, String type) {
        int id = 1;
        if(existsName(first, last)) {
            return false;
        }
        while(existsID(id)) id++;

        Customer tmp = new Customer(id, first, last, adr1, adr2, town, postCode, phone, type);
        customers.add(tmp);
        String sql = "INSERT INTO Customer (\"Customer ID\", \"First Name\", \"Last Name\", \"1st Line Address\", \"2nd Line Address\", Town, \"Postal Code\", \"Phone Number\", \"Type\") " +
               // "VALUES (" + id + "','" + first + "','" + last + "','" + adr1 + "','" + adr2 + "','" + town + "','" + postCode + "','" + phone + "','" + type + ");";
        String.format("VALUES (%d, '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s');", id, first, last, adr1, adr2, town, postCode, phone, type);
        connection.updateDB(sql);

        return true;
    }


    public void deleteCustomer(int id) {
        if(!existsID(id)) {
            System.out.println("Customer does not exists");
            return;
        } else {
            String sql = "DELETE FROM Customer WHERE " + "\"Customer ID\"" +"=" + id + ";";
            connection.updateDB(sql);
            for(Customer c : customers) {
                if(c.getId() == id) customers.remove(c);
            }

            System.out.println("Deleted");
        }
    }

    public boolean existsID(int id) {
        try {
            String query = "select * from Customer";
            ResultSet rs = connection.queryDB(query);

            while(rs.next()) {
                if(rs.getInt("Customer ID") == id) return true;
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    public boolean existsName(String first, String last) {
        try{
            String query = "select * from Customer";
            ResultSet rs = connection.queryDB(query);

            while(rs.next()) {
                if(rs.getString("Last Name").equals(last)) {
                    if(rs.getString("First Name").equals(first)) return true;
                }
            }

        } catch(SQLException e) {
            System.err.println(e.getMessage());
        }

        return false;
    }

    //NEW SHIT
    public ArrayList<Booking> readBooking() {
        ArrayList<Booking> b = new ArrayList<Booking>();
        try {
            String query = "SELECT * FROM Bookings";
            ResultSet rs = connection.queryDB(query);

            while(rs.next()){
                b.add(new Booking("", rs.getString("Booking ID"), rs.getString("Vehicle ID"), "", ""));
            }
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        }
        return b;
    }

    public ArrayList<Booking> readCustomer(ArrayList<Booking> b) {
        try {
            String query = "SELECT * FROM Account";

            for(int i = 0; i<b.size();i++) {
                ResultSet rs = connection.queryDB(query);
                while (rs.next()) {
                    if(b.get(i).getBookings().equals(rs.getString("Booking ID"))) {
                        b.get(i).setCustomer(rs.getString("Customer ID"));
                    }
                }
            }
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        }
        return b;
    }

    public ArrayList<Booking> readParts(ArrayList<Booking> b) {
        try {
            String query = "SELECT * FROM \"Vehicle Parts\"";

            for(int i = 0; i<b.size();i++) {
                ResultSet rs = connection.queryDB(query);
                while (rs.next()) {
                    if(b.get(i).getVehicles().equals(rs.getString("Vehicle ID"))) {
                        b.get(i).setParts(rs.getString("Part ID"));
                    }
                }
            }
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        }
        return b;
    }

    public ArrayList<Booking> readDate(ArrayList<Booking> b) {
        try {
            String query = "SELECT * FROM Bookings";

            for(int i = 0; i<b.size();i++) {
                ResultSet rs = connection.queryDB(query);
                while (rs.next()) {
                    if(b.get(i).getBookings().equals(rs.getString("Booking ID"))) {
                        b.get(i).setDate(rs.getString("Date of Booking"));
                    }
                }
            }
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        }
        return b;
    }

    public int isSetteled(int id) {
        try {
            String query = "SELECT * FROM Account";
            ResultSet rs = connection.queryDB(query);

            while(rs.next()) {
                if(rs.getInt("Customer ID") == id) return rs.getInt("Payment Status");
            }

        } catch(SQLException e) {
            System.err.println(e.getMessage());
        }
        return 2;
    }

    public void settle(int id) {
        String query = "UPDATE Account SET \"Payment Status\"=1, \"Booking Cost\"=0 WHERE \"Customer ID\"=" +id+";";
        //String query2 = "UPDATE Account SET \"Booking Cost\"=0 WHERE \"Customer ID\"=" +id+";";
        connection.updateDB(query);
        //connection.updateDB(query2);
        System.out.println("Setteled");

    }

    public ArrayList<Integer> alert() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date1 = new Date();
        System.out.println(date1);
        ArrayList<Integer> customersToCall = new ArrayList<Integer>();
        int i = 0;

        try {
            String query = "SELECT * FROM Bookings";
            ResultSet rs = connection.queryDB(query);

            while(rs.next()) {
                try {
                    Date date2 = dateFormat.parse(rs.getString("Date of Booking"));
                    long diff = date2.getTime() - date1.getTime();
                    long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                    //System.out.println ("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));

                    if(days == 30) {
                        customersToCall.add(rs.getInt("Booking ID"));
                    }

                } catch (ParseException e) {

                }
                i++;
            }
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        }
        return customersToCall;
    }
}
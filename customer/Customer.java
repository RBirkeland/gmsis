package GMsis.customer; /**
 * Created by Rene Birkeland on 25.02.2015.
 */

public class Customer {
    private int id;
    private String first;
    private String last;
    private String adr1;
    private String adr2;
    private String town;
    private String postCode;
    private String phone;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public void setAdr1(String adr1) {
        this.adr1 = adr1;
    }

    public void setAdr2(String adr2) {
        this.adr2 = adr2;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public String getFirst() {
        return first;
    }

    public String getLast() {
        return last;
    }

    public String getAdr1() {
        return adr1;
    }

    public String getAdr2() {
        return adr2;
    }

    public String getTown() {
        return town;
    }

    public String getPostCode() {
        return postCode;
    }

    public String getPhone() {
        return phone;
    }

    public Customer(int id, String first, String last, String adr1, String adr2, String town, String postCode, String phone, String type) {
        this.id = id;
        this.first = first;
        this.last = last;
        this.adr1 = adr1;
        this.adr2 = adr2;
        this.town = town;
        this.postCode = postCode;
        this.phone = phone;
        this.type = type;
    }
}

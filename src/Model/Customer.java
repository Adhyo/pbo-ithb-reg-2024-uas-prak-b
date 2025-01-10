package Model;

public class Customer {
    private int cust_id;
    private String password;
    private String name;
    private String address;
    private String phone;
    public Customer(int cust_id, String password, String name, String address, String phone){
        this.cust_id = cust_id;
        this.password = password;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }
    public int getCustId() {
        return cust_id;
    }
    public void setCustId(int cust_id) {
        this.cust_id = cust_id;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
}

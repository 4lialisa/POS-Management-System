public class Cashier {
    private int cashierID;
    private String name;
    private String role;
    private String password;

    public Cashier(int cashierID, String name, String role, String password) {
        this.cashierID = cashierID;
        this.name = name;
        this.role = role;
        this.password = password;
    }

    public int getCashierID() {
        return cashierID;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }
}

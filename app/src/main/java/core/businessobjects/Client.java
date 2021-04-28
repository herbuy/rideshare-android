package core.businessobjects;

public class Client {
    private String photo;
    private String name;
    private String amount;
    private String telephone;

    public Client(String photo, String name, String amount, String telephone) {
        this.photo = photo;
        this.name = name;
        this.amount = amount;
        this.telephone = telephone;
    }

    public String getPhoto() {
        return photo;
    }

    public String getName() {
        return name;
    }

    public String getAmount() {
        return amount;
    }

    public String getTelephone() {
        return telephone;
    }
}

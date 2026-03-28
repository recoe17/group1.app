package sms;

public abstract class Person {
    private final String id;
    private String name;
    private String gender;
    private String phoneNumber;
    private String address;

    protected Person(String id, String name, String gender, String phoneNumber, String address) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("ID cannot be blank.");
        }
        this.id = id.trim();
        setName(name);
        setGender(gender);
        setPhoneNumber(phoneNumber);
        setAddress(address);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be blank.");
        }
        this.name = name.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        if (gender == null || gender.isBlank()) {
            throw new IllegalArgumentException("Gender cannot be blank.");
        }
        this.gender = gender.trim();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalArgumentException("Phone number cannot be blank.");
        }
        this.phoneNumber = phoneNumber.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if (address == null || address.isBlank()) {
            throw new IllegalArgumentException("Address cannot be blank.");
        }
        this.address = address.trim();
    }

    public abstract String displayDetails();
}

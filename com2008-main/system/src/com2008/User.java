package com2008;

public class User {
    private String email;
    private String passwordHash;
    private String userId;
    private String firstName;
    private String lastName;
    private String houseNumber;
    private String roadName;
    private String cityName;
    private String postCode;



    // Constructor to initialize a Book object with its attributes
    public User(String email, String passwordHash, String firstName, String lastName) {
        this.setEmail(email);
        this.setPasswordHash(passwordHash);
    }

    // Getter and setter methods for email with validation
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (isValidEmail(email)) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Email is not valid.");
        }
    }

    // Getter and setter methods for password hash with validation
    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        if (isValidPasswordHash(passwordHash)) {
            this.passwordHash = passwordHash;
        } else {
            throw new IllegalArgumentException("Password Hash is not valid.");
        }
    }



    // Private validation methods for each attribute
    private boolean isValidEmail(String email) {
        return email != null && email.length() <= 100;
    }

    private boolean isValidPasswordHash(String passwordHash) {
        return passwordHash != null && passwordHash.length() <= 255;
    }
    // Getter methods
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public String getRoadName() {
        return roadName;
    }

    public String getCityName() {
        return cityName;
    }

    public String getPostCode() {
        return postCode;
    }
    public User(String userId, String firstName, String lastName,
                String houseNumber, String roadName, String cityName, String postCode) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.houseNumber = houseNumber;
        this.roadName = roadName;
        this.cityName = cityName;
        this.postCode = postCode;
    }


    @Override
    public String toString() {
        return "{ " +
                " email='" + getEmail() + "'" +
                ", passwordHash='" + getPasswordHash() + "'" +
                " }";
    }
}

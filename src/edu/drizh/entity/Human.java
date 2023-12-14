package edu.drizh.entity;

public abstract class Human {
    private String name;
    private String surname;
    private String middlename;
    private String company;
    private String adress;
    private String phoneNumber;

    public Human(String name, String surname, String middlename, String company, String adress, String phoneNumber) {
        this.name = name;
        this.surname = surname;
        this.middlename = middlename;
        this.company = company;
        this.adress = adress;
        this.phoneNumber = phoneNumber;
    }

    public void print() {
        System.out.println("Name: " + name);
        System.out.println("Surname: " + surname);
        System.out.println("Middlename: " + middlename);
        System.out.println("Company: " + company);
        System.out.println("Adress: " + adress);
        System.out.println("PhoneNumber: " + phoneNumber);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}

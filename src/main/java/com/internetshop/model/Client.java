package com.internetshop.model;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Client {
    private int id;

    @Size(max = 50,message = "Username must be less then 50 character long")
    @NotEmpty
    @Pattern(regexp ="^[a-zA-Z0-9_]*$",
    message= "Username must be alphanumeric with no spaces")
    private String name;
    private String surname;
    private Date birthdate;

    @NotEmpty
    @Size(max = 50)
    @Pattern(regexp="^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]+$",
    message = "Invalid email address")
    private String email;

    @Size(min = 6, max = 50)
    @Pattern(regexp="^[a-zA-Z0-9]+$",
    message = "The password must be at least 6 characters long.")
    private String password;

    @NotEmpty
    @Size(max = 20)
    @Pattern(regexp="^\\+[1-9][0-9]?[\\s]*\\(?" +
            "\\d{3}\\)?[-\\s]?\\d{3}[-\\s]?\\d{2}[-\\s]?\\d{2}$")
    private String phone;
    private int orderCounter;
    private Role role;
    private ClientAddress clientAddress;
    private Set<Order> order = new HashSet<>();



    public Client() {
    }

    public Client(int id, String name, String surname, Date birthdate, String email, String password, String phone, int orderCounter, Role role, ClientAddress clientAddress) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.birthdate = birthdate;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.orderCounter = orderCounter;
        this.role = role;
        this.clientAddress = clientAddress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getOrderCounter() {
        return orderCounter;
    }

    public void setOrderCounter(int orderCounter) {
        this.orderCounter = orderCounter;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public ClientAddress getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(ClientAddress clientAddress) {
        this.clientAddress = clientAddress;
    }

    public Set<Order> getOrder() {
        return order;
    }

    public void setOrder(Set<Order> order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "Client{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birthdate='" + birthdate + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

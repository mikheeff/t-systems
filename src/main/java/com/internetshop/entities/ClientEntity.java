package com.internetshop.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "client")
public class ClientEntity {
    @Id
    @Column(name = "idClient")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column (name = "birthdate")
    private Date birthdate;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column (name = "phone")
    private String phone;
    @Column (name = "order_counter")
    private int order_counter;
    @Column (name = "id_role")
    private int id_role;

    public ClientEntity() {
    }

    public ClientEntity(String name, String surname, Date birthdate, String email, String password, String phone, int order_counter, int id_role) {
        this.name = name;
        this.surname = surname;
        this.birthdate = birthdate;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.order_counter = order_counter;
        this.id_role = id_role;
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

    public int getOrder_counter() {
        return order_counter;
    }

    public void setOrder_counter(int order_counter) {
        this.order_counter = order_counter;
    }

    public int getId_role() {
        return id_role;
    }

    public void setId_role(int id_role) {
        this.id_role = id_role;
    }
}

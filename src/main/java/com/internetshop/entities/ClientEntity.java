package com.internetshop.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
    private int orderCounter;

    @OneToMany(mappedBy = "clientEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<OrderEntity> orderEntities = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL,fetch=FetchType.LAZY)   //
    @JoinColumn(name = "home_address_id")
    private ClientAddressEntity clientAddressEntity;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleEntity roleEntity;

    public ClientEntity() {
    }

    public ClientEntity(String name, String surname, Date birthdate, String email, String password, String phone, int orderCounter, ClientAddressEntity clientAddressEntity, RoleEntity roleEntity) {
        this.name = name;
        this.surname = surname;
        this.birthdate = birthdate;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.orderCounter = orderCounter;
        this.clientAddressEntity = clientAddressEntity;
        this.roleEntity = roleEntity;
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


    public ClientAddressEntity getClientAddressEntity() {
        return clientAddressEntity;
    }

    public void setClientAddressEntity(ClientAddressEntity clientAddressEntity) {
        this.clientAddressEntity = clientAddressEntity;
    }

    public RoleEntity getRoleEntity() {
        return roleEntity;
    }

    public void setRoleEntity(RoleEntity roleEntity) {
        this.roleEntity = roleEntity;
    }

    public Set<OrderEntity> getOrderEntities() {
        return orderEntities;
    }

    public void setOrderEntities(Set<OrderEntity> orderEntities) {
        this.orderEntities = orderEntities;
    }
}

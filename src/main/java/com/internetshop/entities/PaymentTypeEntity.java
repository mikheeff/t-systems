package com.internetshop.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "paymentType")
public class PaymentTypeEntity {
    @Id
    @Column(name = "type_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "paymentType", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<OrderEntity> orderEntities = new HashSet<>();

    PaymentTypeEntity(){

    }

    public PaymentTypeEntity(String name, Set<OrderEntity> orderEntities) {
        this.name = name;
        this.orderEntities = orderEntities;
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

    public Set<OrderEntity> getOrderEntities() {
        return orderEntities;
    }

    public void setOrderEntities(Set<OrderEntity> orderEntities) {
        this.orderEntities = orderEntities;
    }
}

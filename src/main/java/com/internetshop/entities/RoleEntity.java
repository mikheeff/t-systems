package com.internetshop.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "role")
public class RoleEntity {
    @Id
    @Column(name = "id_role")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "roleEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<ClientEntity> clientSet = new HashSet<>();

    public RoleEntity() {

    }

    public RoleEntity(String name, Set<ClientEntity> clientSet) {
        this.name = name;
        this.clientSet = clientSet;
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

    public Set<ClientEntity> getClientSet() {
        return clientSet;
    }

    public void setClientSet(Set<ClientEntity> clientSet) {
        this.clientSet = clientSet;
    }
}

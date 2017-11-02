package com.internetshop.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "category")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcategory")
    private int id;
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<GoodsEntity> GoodsSet = new HashSet<>();


    public CategoryEntity() {
    }

    public CategoryEntity(String name, Set<GoodsEntity> goodsSet) {
        this.name = name;
        GoodsSet = goodsSet;
    }

    public Set<GoodsEntity> getGoodsSet() {
        return GoodsSet;
    }

    public void setGoodsSet(Set<GoodsEntity> goodsSet) {
        GoodsSet = goodsSet;
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
}

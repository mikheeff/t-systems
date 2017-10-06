package com.internetshop.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "rule")
public class RuleEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "rule_id")
        private int id;
        @Column(name = "name")
        private String name;
        @OneToMany(mappedBy = "rule", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
        private Set<GoodsEntity> GoodsSet = new HashSet<>();

    public RuleEntity(){

    }
    public RuleEntity(String name, Set<GoodsEntity> goodsSet) {
        this.name = name;
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

    public Set<GoodsEntity> getGoodsSet() {
        return GoodsSet;
    }

    public void setGoodsSet(Set<GoodsEntity> goodsSet) {
        GoodsSet = goodsSet;
    }
}

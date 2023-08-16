package com.springhow.examples.springboot.cache.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
public class Item implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer count;
    private String hashKey;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Item() {
    }

    public Item(String hashKey, Integer count) {
        this.hashKey = hashKey;
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer id) {
        this.count = count;
    }

    public String getHashKey() {
        return hashKey;
    }

    public void setHashKey(String hashKey) {
        this.hashKey = hashKey;
    }


    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", hashKey=" + hashKey +
                ", count=" + count +
                '}';
    }


}

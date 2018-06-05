package com.cch.cz.entity;


import com.cch.cz.base.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name="t_group")
public class UrgeGroup extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    private  String name;

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id=id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

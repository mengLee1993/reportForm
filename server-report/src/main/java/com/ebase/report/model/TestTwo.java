package com.ebase.report.model;


import com.ebase.report.core.db.DataBaseType;

public class TestTwo {
    private Long id;

    private String name;

    private Integer age;
    // state 1.  2.  3.
    private DataBaseType baseType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
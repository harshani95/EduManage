package com.developersstack.edumanage.model;

import java.util.Arrays;

public class Program {
    private String code;
    private String name;
    private String[] technology;
    private double cost;
    private String teacherId;

    public Program() {
    }

    public Program(String code, String name, String[] technology, double cost, String teacherId) {
        this.code = code;
        this.name = name;
        this.technology = technology;
        this.cost = cost;
        this.teacherId = teacherId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getTechnology() {
        return technology;
    }

    public void setTechnology(String[] technologies) {
        this.technology = technology;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    @Override
    public String toString() {
        return "Program{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", technology=" + Arrays.toString(technology) +
                ", cost=" + cost +
                ", teacherId='" + teacherId + '\'' +
                '}';
    }


}

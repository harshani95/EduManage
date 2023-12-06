package com.developersstack.edumanage.view.tm;

import javafx.scene.control.Button;

public class StudentTM {
    private String id;
    private String fullName;
    private String dob;
    private String address;

    public StudentTM() {
    }

    public StudentTM(String id, String fullName, String dob, String address, Button btn) {
        this.id = id;
        this.fullName = fullName;
        this.dob = dob;
        this.address = address;
        this.btn = btn;
    }

    private Button btn;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    @Override
    public String toString() {
        return "StudentTM{" +
                "id='" + id + '\'' +
                ", fullName='" + fullName + '\'' +
                ", dob='" + dob + '\'' +
                ", address='" + address + '\'' +
                ", btn=" + btn +
                '}';
    }
}

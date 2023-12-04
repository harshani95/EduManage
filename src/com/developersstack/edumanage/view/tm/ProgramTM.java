package com.developersstack.edumanage.view.tm;

import javafx.scene.control.Button;

public class ProgramTM {
      private String code;
      private String name;
      private Button btnTech;
      private double cost;
      private String teacher;
      private Button btn;

      public ProgramTM() {
      }

      public ProgramTM(String code, String name, Button btnTech, double cost, String teacher, Button btn) {
            this.code = code;
            this.name = name;
            this.btnTech = btnTech;
            this.cost = cost;
            this.teacher = teacher;
            this.btn = btn;
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

      public Button getBtnTech() {
            return btnTech;
      }

      public void setBtnTech(Button btnTech) {
            this.btnTech = btnTech;
      }

      public String getCost() {
            return cost;
      }

      public void setCost(double cost) {
            this.cost = cost;
      }

      public String getTeacher() {
            return teacher;
      }

      public void setTeacher(String teacher) {
            this.teacher = teacher;
      }

      public Button getBtn() {
            return btn;
      }

      public void setBtn(Button btn) {
            this.btn = btn;
      }

      @Override
      public String toString() {
            return "ProgramTM{" +
                    "code='" + code + '\'' +
                    ", name='" + name + '\'' +
                    ", btnTech=" + btnTech +
                    ", cost=" + cost +
                    ", teacher='" + teacher + '\'' +
                    ", btn=" + btn +
                    '}';
      }
}

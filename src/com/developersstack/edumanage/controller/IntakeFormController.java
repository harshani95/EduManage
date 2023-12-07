package com.developersstack.edumanage.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class IntakeFormController {


    public AnchorPane context;
    public TextField txtId;
    public TextField txtName;
    public TextField txtSearch;
    public Button btn;
    public DatePicker txtDate;
    public ComboBox cmbProgram;
    public TableView tblProgram;
    public TableColumn colId;
    public TableColumn colIntake;
    public TableColumn colStartDate;
    public TableColumn colProgram;
    public TableColumn colStat;
    public TableColumn colOption;

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" +location+".fxml"))));
        stage.centerOnScreen();
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashboardForm");
    }

    public void saveOnAction(ActionEvent actionEvent) {
    }

    public void newIntakeOnAction(ActionEvent actionEvent) {
    }
}

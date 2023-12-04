package com.developersstack.edumanage.controller;

import com.developersstack.edumanage.db.Database;
import com.developersstack.edumanage.model.Student;
import com.developersstack.edumanage.model.Teacher;
import com.developersstack.edumanage.view.tm.StudentTM;
import com.developersstack.edumanage.view.tm.TeacherTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class TeacherFormController {
    public TextField txtContact;
    public Button btn;
    public TextField txtSearch;
    public TextField txtAddress;
    public TextField txtName;
    public TextField txtId;
    public TableView<TeacherTM> tblTeacher;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colContact;
    public TableColumn colAddress;
    public TableColumn colOption;
    public AnchorPane teacherContext;
    String searchText = "";

    public void initialize(){

        colId.setCellValueFactory(new PropertyValueFactory<>("code"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));
        setTeacherId();
        setTableData(searchText );

        txtSearch.textProperty().addListener((observable, oldValue, newValue) ->{
            searchText = newValue;
            setTableData(searchText);
        });
        tblTeacher.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (null!=newValue){
                        setData(newValue);
                    }
                }
        );
    }

    private void setData(TeacherTM tm) {
        txtId.setText(tm.getCode());
        txtName.setText(tm.getName());
        txtAddress.setText(tm.getAddress());
        txtContact.setText(tm.getContact());
        btn.setText("Update Student");
    }

    private void setTableData(String searchText) {
        ObservableList<TeacherTM> obList = FXCollections.observableArrayList();
        for (Teacher t:Database.teacherTable) {
            if (t.getName().contains(searchText)){
                Button btn = new Button("Delete");
                TeacherTM tm = new TeacherTM(
                        t.getCode(),
                        t.getName(),
                        t.getAddress(),
                       t.getContact(),
                        btn
                );

                btn.setOnAction(e->{
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are You sure!",ButtonType.YES,ButtonType.NO);
                    Optional<ButtonType> buttonType  = alert.showAndWait();
                    if (buttonType.get().equals(ButtonType.YES)){
                        Database.teacherTable.remove(t);
                        new Alert(Alert.AlertType.INFORMATION,"Deleted").show();
                        setTableData(searchText);
                    }
                });
                obList.add(tm);
            }

        }
        tblTeacher.setItems(obList);
    }

    public void saveOnAction(ActionEvent actionEvent) {
        if(btn.getText().equalsIgnoreCase("Save Teacher")){
            Teacher teacher =new Teacher(
                    txtId.getText(),
                    txtName.getText(),
                    txtContact.getText(),
                    txtAddress.getText()
            );
            Database.teacherTable.add(teacher);
            setTeacherId();
            clear();
            setTableData(searchText);
            new Alert(Alert.AlertType.INFORMATION,"Teacher Saved!").show();
        }
        else{
            for (Teacher t:Database.teacherTable
            ) {
                if(t.getCode().equals(txtId.getText())){
                    t.setAddress(txtAddress.getText());
                    t.setName(txtName.getText());
                    t.setContact(txtContact.getText());
                    //setTableData(searchText);
                    clear();
                    setTeacherId();
                    return;
                }
            }
            new Alert(Alert.AlertType.WARNING,"Not Fond").show();

        }
    }

    private void clear(){
        txtAddress.clear();
        txtName.clear();
        txtContact.clear();
    }

    private void setTeacherId() {
        if(!Database.teacherTable.isEmpty()){
            Teacher lastTeacher = Database.teacherTable.get(
                    Database.teacherTable.size()-1
            );
            String lastId = lastTeacher.getCode();
            String splitData[] = lastId.split("-");
            String lastIntegerNumberAsString = splitData[1];
            int lastIntegerIdAsInt = Integer.parseInt(lastIntegerNumberAsString);
            lastIntegerIdAsInt++;
            String generatedStudentId = "T-"+ lastIntegerIdAsInt;
            txtId.setText(generatedStudentId);

        }else{
            txtId.setText("T-1");
        }
    }

    public void newTeacherOnAction(ActionEvent actionEvent) {
        clear();
        setTeacherId();
        btn.setText("Save Student");
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) teacherContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" +location+".fxml"))));
        stage.centerOnScreen();
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashboardForm");
    }
}

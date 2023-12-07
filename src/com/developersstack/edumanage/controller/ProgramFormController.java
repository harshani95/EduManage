package com.developersstack.edumanage.controller;

import com.developersstack.edumanage.db.Database;
import com.developersstack.edumanage.model.Program;
import com.developersstack.edumanage.model.Student;
import com.developersstack.edumanage.model.Teacher;
import com.developersstack.edumanage.view.tm.ProgramTM;
import com.developersstack.edumanage.view.tm.TechAddTM;
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
import java.util.ArrayList;
import java.util.Optional;

public class ProgramFormController {


    public AnchorPane programContext;
    public TextField txtCode;
    public TextField txtName;
    public TextField txtTechnology;
    public TextField txtSearch;
    public Button btn;
    public TextField txtCost;
    public TableView<TechAddTM> tblTechnologies;
    public TableColumn colTCode;
    public TableColumn colTName;
    public TableColumn colTRemove;
    public ComboBox<String> cmbTeacher;
    public TableView<ProgramTM> tblPrograms;
    public TableColumn colCode;
    public TableColumn colName;
    public TableColumn colTechnology;
    public TableColumn colTeacherId;
    public TableColumn colCost;
    public TableColumn colOption;
    String searchText = "";

    public void initialize(){

        setProgramCode();
        setTeachers();
        loadPrograms();

        colTCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colTName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colTRemove.setCellValueFactory(new PropertyValueFactory<>("btn"));

        colTCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colTName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colTeacherId.setCellValueFactory(new PropertyValueFactory<>("teacher"));
        colTechnology.setCellValueFactory(new PropertyValueFactory<>("btnTech"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));

    }

    ArrayList<String> teachersArray = new ArrayList<>();

    private void setTeachers() {
        for (Teacher t:Database.teacherTable
             ) {
            teachersArray.add(t.getCode()+ "."+t.getName());
        }
        ObservableList<String> obList = FXCollections.observableArrayList(teachersArray);
        cmbTeacher.setItems(obList  );
    }


    private void setProgramCode() {
        if (!Database.programTable.isEmpty()) {
            Program lastProgram = Database.programTable.get(
                    Database.programTable.size() - 1
            );
            String lastId = lastProgram.getCode();
            String splitData[] = lastId.split("-");
            String lastIdIntegerNumberAsAString = splitData[1];
            int lastIntegerIdAsInt = Integer.parseInt(lastIdIntegerNumberAsAString);
            lastIntegerIdAsInt++;
            String generatedStudentId = "P-" + lastIntegerIdAsInt;
            txtCode.setText(generatedStudentId);
        } else {
            txtCode.setText("P-1");
        }
    }


    public void newProgramOnAction(ActionEvent actionEvent) {
        clear();
        setProgramCode();
        btn.setText("Save Program");
    }

    public void saveOnAction(ActionEvent actionEvent) {

        String[] selectedTechs = new String[tmList.size()];
        int pointer = 0;
        for (TechAddTM t: tmList
             ) {
            selectedTechs[pointer] =t.getName();
            pointer++;
        }
        if(btn.getText().equalsIgnoreCase("Save Program")){
            Program program =new Program(
                    txtCode.getText(),
                    txtName.getText(),
                    selectedTechs,
                    Double.parseDouble(txtCost.getText()),
                    cmbTeacher.getValue().split("\\.")[0]

            );

            Database.programTable.add(program);
            new Alert(Alert.AlertType.INFORMATION,"Program Saved!").show();
            loadPrograms();
        }
    }

    private void loadPrograms() {
        ObservableList<ProgramTM> programsTmList = FXCollections.observableArrayList();
        for (Program p : Database.programTable
        ) {
            Button techButton = new Button("show Tech");
            Button removeButton = new Button("Delete");
            ProgramTM tm = new ProgramTM(
                    p.getCode(),
                    p.getName(),
                    p.getTeacherId(),
                    techButton,
                    p.getCost(),
                    removeButton
            );
            programsTmList.add(tm);
        }
        tblPrograms.setItems(programsTmList);
    }

    private void clear(){
        txtCost.clear();
        txtName.clear();
        txtTechnology.clear();
    }

   private void setUi(String location) throws IOException {
        Stage stage = (Stage) programContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" +location+".fxml"))));
        stage.centerOnScreen();
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashboardForm");
    }

    ObservableList<TechAddTM> tmList = FXCollections.observableArrayList();

    public void addTechOnAction(ActionEvent actionEvent) {
        if (isExists(txtTechnology.getText().trim())){
            Button btn = new Button("Remove");
            TechAddTM tm = new TechAddTM(
                tmList.size()+1, txtTechnology.getText().trim(),btn
            );
            tmList.add(tm);
            tblTechnologies.setItems(tmList);
            txtTechnology.clear();
        }else {
            txtTechnology.selectAll();
            new Alert(Alert.AlertType.WARNING,"Already Exists").show();
        }
    }

    private boolean isExists(String tech){
        for (TechAddTM tm: tmList
             ) {
            if (tm.getName().equals(tech)){
                return true;
            }
        }
        return false;
    }
}

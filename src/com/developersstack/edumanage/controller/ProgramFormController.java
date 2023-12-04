package com.developersstack.edumanage.controller;

import com.developersstack.edumanage.db.Database;
import com.developersstack.edumanage.model.Program;
import com.developersstack.edumanage.model.Student;
import com.developersstack.edumanage.model.Teacher;
import com.developersstack.edumanage.view.tm.ProgramTM;
import com.developersstack.edumanage.view.tm.StudentTM;
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
import org.w3c.dom.html.HTMLDListElement;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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
    public ComboBox cmbTeacher;
    public TableView<ProgramTM> tblProgram;
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
        setTableData();

        colTCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colTName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colTRemove.setCellValueFactory(new PropertyValueFactory<>("btn"));

        colTCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colTName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colTeacherId.setCellValueFactory(new PropertyValueFactory<>("teacher"));
        colTechnology.setCellValueFactory(new PropertyValueFactory<>("btnTech"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));


        tblProgram.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (null!=newValue){
                        setData(newValue);
                    }
                }
        );
    }

    ArrayList<String> teacherArray = new ArrayList<>();
    private void setTeachers() {
        for (Teacher t:Database.teacherTable
             ) {
            teacherArray.add(t.getCode()+ "."+t.getName());
        }
        ObservableList<String> obList = FXCollections.observableArrayList(teacherArray);
        cmbTeacher.setItems(obList  );
    }

    private void setData(ProgramTM tm) {
        txtCode.setText(tm.getCode());
        txtName.setText(tm.getName());

       // Double.parseDouble(txtCost.setText(tm.getCost()));
        String costText = tm.getCost();
        txtCost.setText(costText);

        btn.setText("Update Program");
    }

    private void setProgramCode() {
        if(!Database.programTable.isEmpty()){
            Program lastProgram = Database.programTable.get(
                    Database.programTable.size()-1
            );
            String lastId = lastProgram.getCode();
            String splitData[] = lastId.split("-");
            String lastIntegerNumberAsString = splitData[1];
            int lastIntegerIdAsInt = Integer.parseInt(lastIntegerNumberAsString);
            lastIntegerIdAsInt++;
            String generatedProgramCode = "P-"+ lastIntegerIdAsInt;
            txtCode.setText(generatedProgramCode);

        }else{
            txtCode.setText("P-1");
        }
    }

    private void setTableData() {
            ObservableList<ProgramTM> obList = FXCollections.observableArrayList();
            for (Program p: Database.programTable) {
                    Button techButton = new Button("Show Technology");
                    Button removeButton = new Button("Delete");
                    ProgramTM tm = new ProgramTM(
                            p.getCode(),
                            p.getName(),
                            techButton,
                            p.getCost(),
                            p.getTeacherId(),
                            removeButton
                    );

                    btn.setOnAction(e->{
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are You sure!",ButtonType.YES,ButtonType.NO);
                        Optional<ButtonType> buttonType  = alert.showAndWait();
                        if (buttonType.get().equals(ButtonType.YES)){
                            Database.programTable.remove(p);
                            new Alert(Alert.AlertType.INFORMATION,"Deleted").show();
                            setTableData();
                        }
                    });
                    obList.add(tm);
                }

            tblProgram.setItems(obList);
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
                    cmbTeacher.getValue().split("\\.")[0],
                    Double.parseDouble(txtCost.getText());
            );

            Database.programTable.add(program);
            setProgramCode();
            clear();
            setTableData();
            new Alert(Alert.AlertType.INFORMATION,"Program Saved!").show();
        }
        else{
            for (Program p:Database.programTable
            ) {
               if(p.set().equals(txtCode.getText())){
                    p.setName(txtName.getText());
                    p.setCost(Double.parseDouble(txtCost.getText()));
                    clear();
                    setProgramCode();
                    return;
                }
            }
            new Alert(Alert.AlertType.WARNING,"Not Fond").show();

        }
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

package com.developersstack.edumanage.controller;

import com.developersstack.edumanage.db.Database;
import com.developersstack.edumanage.db.DbConnection;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TeacherFormController {
    public TextField txtContact;
    public Button btn;
    public TextField txtSearch;
    public TextField txtAddress;
    public TextField txtName;
    public TextField txtId;
    public TableView<TeacherTM> tblTeachers;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colContact;
    public TableColumn colAddress;
    public TableColumn colOption;
    public AnchorPane teacherContext;
    String searchText = "";

    public void initialize() throws SQLException, ClassNotFoundException {

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


        tblTeachers.getSelectionModel().selectedItemProperty().addListener(
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
        btn.setText("Update Teacher");
    }

    private void setTableData(String searchText) {
        ObservableList<TeacherTM> obList = FXCollections.observableArrayList();
        try {
            for (Teacher t : searchTeachers(searchText)) {
                Button btn = new Button("Delete");
                TeacherTM tm = new TeacherTM(
                        t.getCode(),
                        t.getName(),
                        t.getAddress(),
                        t.getContact(),
                        btn
                );
                btn.setOnAction(e -> {
                    Alert alert = new Alert(
                            Alert.AlertType.CONFIRMATION, "Are you sure?",
                            ButtonType.YES, ButtonType.NO
                    );
                    Optional<ButtonType> buttonType = alert.showAndWait();
                    if (buttonType.get().equals(ButtonType.YES)) {

                        try {
                            if (deleteTeacher(t.getCode())) {
                                new Alert(Alert.AlertType.INFORMATION, "Deleted!").show();
                                setTableData(searchText);
                                setTeacherId();
                            } else {
                                new Alert(Alert.AlertType.WARNING, "Try Again!").show();
                            }
                        } catch (ClassNotFoundException | SQLException ex) {
                            new Alert(Alert.AlertType.ERROR, e.toString()).show();
                        }

                    }
                });
                obList.add(tm);
            }
            tblTeachers.setItems(obList);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void saveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
            Teacher teacher =new Teacher(
                    txtId.getText(),
                    txtName.getText(),
                    txtContact.getText(),
                    txtAddress.getText()
            );

        if (btn.getText().equalsIgnoreCase("Save Teacher")) {
            try {
                if (saveTeacher(teacher)) {
                    setTeacherId();
                    clear();
                    setTableData(searchText);
                    new Alert(Alert.AlertType.INFORMATION, "Teacher saved!").show();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Try Again!").show();
                }
            } catch (SQLException | ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR, e.toString()).show();
            }

        } else {

            if (updateTeacher(teacher)) {
                clear();
                setTableData(searchText);
                new Alert(Alert.AlertType.INFORMATION, "Teacher Updated!").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again!").show();
            }
        }
    }

    private void clear(){
        txtAddress.clear();
        txtName.clear();
        txtContact.clear();
    }

    private void setTeacherId() throws SQLException, ClassNotFoundException {
        String lastId = getLastId();
        if (null != lastId) {
            String splitData[] = lastId.split("-");
            String lastIdIntegerNumberAsAString = splitData[1];
            int lastIntegerIdAsInt = Integer.parseInt(lastIdIntegerNumberAsAString);
            lastIntegerIdAsInt++;
            String generatedStudentId = "T-" + lastIntegerIdAsInt;
            txtId.setText(generatedStudentId);
        } else {
            txtId.setText("T-1");
        }
    }


    public void newTeacherOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        clear();
        setTeacherId();
        btn.setText("Save Teacher");
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) teacherContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" +location+".fxml"))));
        stage.centerOnScreen();
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashboardForm");
    }

    //=====================================

    private boolean updateTeacher(Teacher teacher) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement preparedStatement =
                connection.prepareStatement("UPDATE teacher SET name=?, address=?, contact=? WHERE teacher_id=?");
        preparedStatement.setString(1, teacher.getName());
        preparedStatement.setObject(2, teacher.getAddress());
        preparedStatement.setString(3, teacher.getContact());
        preparedStatement.setString(4, teacher.getCode());
        return preparedStatement.executeUpdate() > 0;
    }

    private boolean saveTeacher(Teacher teacher) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement preparedStatement =
                connection.prepareStatement("INSERT INTO teacher VALUES(?,?,?,?)");
        preparedStatement.setString(1, teacher.getCode());
        preparedStatement.setString(2, teacher.getName());
        preparedStatement.setObject(3, teacher.getAddress());
        preparedStatement.setString(4, teacher.getContact());
        return preparedStatement.executeUpdate() > 0;
    }

    private boolean deleteTeacher(String code) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement preparedStatement =
                connection.prepareStatement("DELETE FROM teacher WHERE teacher_id=?");
        preparedStatement.setString(1,code);
        return preparedStatement.executeUpdate()>0;
    }

    private List<Teacher> searchTeachers(String text) throws ClassNotFoundException, SQLException {
        text = "%" + text + "%";// %text%
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement preparedStatement =
                connection.prepareStatement("SELECT * FROM teacher WHERE name LIKE ? OR address LIKE ?");
        preparedStatement.setString(1,text);
        preparedStatement.setString(2,text);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Teacher> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(
                    new Teacher(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4)
                    )
            );
        }
        return list;
    }

    private String getLastId() throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement preparedStatement =
                connection.prepareStatement("SELECT teacher_id FROM teacher ORDER BY CAST(SUBSTRING(teacher_id,3) AS UNSIGNED ) DESC LIMIT 1");
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }
}

package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Student;
import view.tm.StudentTm;

import java.sql.SQLException;
import java.util.ArrayList;

public class StudentFormController {
    public TableView tblStudent;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colEmail;
    public TableColumn colContact;
    public TableColumn colAddress;
    public TableColumn colNic;
    public TextField txtId;
    public TextField txtName;
    public TextField txtEmail;
    public TextField txtContact;
    public TextField txtNic;
    public TextField txtAddress;


    public void initialize(){
        colId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));

        try {
            setStudentToTable(new StudentController().getAllStudent());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        tblStudent.refresh();
    }


    private void setStudentToTable(ArrayList<Student> students){
        ObservableList<Student> obList = FXCollections.observableArrayList();
        students.forEach(e->{
            obList.add(
                    new StudentTm(e.getStudentId(), e.getStudentName(), e.getEmail(), e.getContact(),e.getAddress(), e.getNic()));
        });
//        System.out.println(obList);
        tblStudent.setItems(obList);
        tblStudent.refresh();
    }

    public void saveOnAction(ActionEvent actionEvent) {
        Student s1 = new Student(
                txtId.getText(),txtName.getText(),txtEmail.getText(),txtContact.getText(),txtAddress.getText(),txtNic.getId()
        );
        try {
            if (addStudent(s1)){
                new Alert(Alert.AlertType.CONFIRMATION, "Student Saved...").show();
            }else {
                new Alert(Alert.AlertType.WARNING, "Try Again...").show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private boolean addStudent(Student s1) throws SQLException, ClassNotFoundException {
        return new StudentController().saveStudent(s1);
    }

    public void UpdateOnAction(ActionEvent actionEvent) {
        Student s1 = new Student(
                txtId.getText(),txtName.getText(),txtEmail.getText(),txtContact.getText(),
                txtAddress.getText(),txtNic.getText()
        );
        try {
            if (new StudentController().updateStudent(s1))
                new Alert(Alert.AlertType.CONFIRMATION,"Student Updated...").show();
            else
                new Alert(Alert.AlertType.WARNING,"Try again..").show();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void DeleteOnAction(ActionEvent actionEvent) {
    }
}

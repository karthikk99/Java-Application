package javafxapplication.fp.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafxapplication.fp.dao.UserDAO;
import java.util.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import static javafxapplication.fp.JavaFXApplication.stage;
import javafxapplication.fp.model.User;

/**
 *
 * @author karthik
 */
public class FXMLDocumentController1_1 implements Initializable {

    @FXML
    private TableView example_tableview;

    @FXML
    private TableColumn name_tablecolumn;

    @FXML
    private TableColumn email_tablecolumn;

    @FXML
    private TableColumn address_tablecolumn;

    @FXML
    private TableColumn department_tablecolumn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        UserDAO ud = new UserDAO();
        List<User> a = ud.DisplayUser();
        ObservableList<UserData> data = FXCollections.observableArrayList();
        for (int i = 0; i < a.size(); i++) {
            data.add(new UserData(a.get(i).getId(), a.get(i).getFirstName(), 
                    a.get(i).getLastName(), a.get(i).getDepartment()));
        }
        example_tableview.getItems().clear();
        example_tableview.setItems(data);
        name_tablecolumn.setCellValueFactory(new PropertyValueFactory<UserData, Integer>("ID"));
        email_tablecolumn.setCellValueFactory(new PropertyValueFactory<UserData, String>("FName"));
        address_tablecolumn.setCellValueFactory(new PropertyValueFactory<UserData, String>("LName"));
        department_tablecolumn.setCellValueFactory(new PropertyValueFactory<UserData, String>("Department"));
    }

    public void okProcess() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/javafxapplication/fp/view/AdminMenu.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println("Exception :" + ex);
        }
    }

    public static class UserData {
        private IntegerProperty ID;
        private StringProperty FName;
        private StringProperty LName;
        private StringProperty Department;

        private UserData(Integer ID, String FName, String LName, String Dept) {
            this.ID = new SimpleIntegerProperty(ID);
            this.FName = new SimpleStringProperty(FName);
            this.LName = new SimpleStringProperty(LName);
            this.Department = new SimpleStringProperty(Dept);
        }

        public IntegerProperty IDProperty() {
            return ID;
        }

        public StringProperty FNameProperty() {
            return FName;
        }

        public StringProperty LNameProperty() {
            return LName;
        }

        public StringProperty DepartmentProperty() {
            return Department;
        }
    }
}

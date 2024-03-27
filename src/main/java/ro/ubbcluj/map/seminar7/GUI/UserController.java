package ro.ubbcluj.map.seminar7.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ro.ubbcluj.map.seminar7.domain.Utilizator;
import ro.ubbcluj.map.seminar7.service.ServiceInvitation;
import ro.ubbcluj.map.seminar7.service.ServiceMessage;
import ro.ubbcluj.map.seminar7.service.ServicePrieten;
import ro.ubbcluj.map.seminar7.service.ServiceUtilizator;
import ro.ubbcluj.map.seminar7.validators.ValidationException;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class UserController {
    ServiceUtilizator serviceUtilizator;
    ServicePrieten servicePrieten;
    ServiceInvitation serviceInvitation;

    ServiceMessage serviceMessage;
    ObservableList<Utilizator> model = FXCollections.observableArrayList();

    @FXML
    TableView<Utilizator> tableView;
    @FXML
    TableColumn<Utilizator,Long> tableColumnUserID;
    @FXML
    TableColumn<Utilizator,String> tableColumnFirstname;
    @FXML
    TableColumn<Utilizator,String> tableColumnLastname;
    @FXML
    TextField textNrPag;
    @FXML
    private TextField textFirstname;
    @FXML
    private TextField textLastname;
    @FXML
    private TextField textID;
    public void setService(ServiceUtilizator serviceUtilizator, ServicePrieten servicePrieten, ServiceInvitation serviceInvitation, ServiceMessage serviceMessage) {
        this.serviceUtilizator = serviceUtilizator;
        this.servicePrieten = servicePrieten;
        this.serviceInvitation = serviceInvitation;
        this.serviceMessage = serviceMessage;
        initModel();
    }

    @FXML
    public void initialize() {
        tableColumnUserID.setCellValueFactory(new PropertyValueFactory<Utilizator, Long>("id"));
        tableColumnFirstname.setCellValueFactory(new PropertyValueFactory<Utilizator, String>("firstName"));
        tableColumnLastname.setCellValueFactory(new PropertyValueFactory<Utilizator, String>("lastName"));
        tableView.setItems(model);

        this.textNrPag.textProperty().addListener((observable, oldvalue, newValue)-> {
            this.setPage();
        });
    }
    @FXML
    public void setTextFields(){
        Utilizator selected = tableView.getSelectionModel().getSelectedItem();
        this.textFirstname.setText(selected.getFirstName());
        this.textLastname.setText(selected.getLastName());
        this.textID.setText(selected.getId().toString());
    }
    @FXML
    public void addUser(){
        String firstname = this.textFirstname.getText();
        String lastname = this.textLastname.getText();
        try {
            this.serviceUtilizator.addUser(firstname, lastname);
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Add User", "Am adaugat cu succes utilizatorul " +
                    firstname + ' ' + lastname);
            this.initModel();
        }
        catch(ValidationException ex){
            MessageAlert.showErrorMessage(null, ex.toString());
        }
    }
    @FXML
    public void removeUser(){
        Long id = Long.parseLong(this.textID.getText());
        try {
            Utilizator selected = this.serviceUtilizator.removeUser(id);
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Add User", "Am sters cu succes utilizatorul " +
                    selected.getFirstName() + ' ' + selected.getLastName());
            this.initModel();
        }
        catch(ValidationException ex){
            MessageAlert.showErrorMessage(null, ex.toString());
        }
    }
    @FXML
    public void updateUser(){
        Long id = Long.parseLong(this.textID.getText());
        String firstname = this.textFirstname.getText();
        String lastname = this.textLastname.getText();
        try{
            Utilizator updated = this.serviceUtilizator.updateUser(id, firstname, lastname);
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Add User", "Am facut update cu succes noului utilizatorului " +
                    firstname + ' ' + lastname);
            this.initModel();
        }
        catch (ValidationException ex){
            MessageAlert.showErrorMessage(null, ex.toString());
        }
    }
    @FXML
    private void showUserProfile(){

        try{
            Long id = Long.parseLong(this.textID.getText());
            String firstname = this.textFirstname.getText();
            String lastname = this.textLastname.getText();
            Utilizator user = new Utilizator(firstname, lastname);
            user.setId(id);

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("profile.fxml"));
            AnchorPane root = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("User");
            //dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            ProfileController profileController = loader.getController();
            profileController.setService(serviceUtilizator, servicePrieten, serviceInvitation, serviceMessage, dialogStage, user);

            dialogStage.show();
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        catch(ValidationException ex){
            MessageAlert.showErrorMessage(null, ex.toString());
        }
    }
    @FXML
    private void setPage(){
        int nr = Integer.parseInt(this.textNrPag.getText());
        this.serviceUtilizator.setPageSize(nr);
        initModel();
    }
    @FXML
    private void previousPage(){
        try {
            this.serviceUtilizator.getPreviousUsers();
            initModel();
        }
        catch (ValidationException ex){
            MessageAlert.showErrorMessage(null, ex.toString());
        }
    }
    @FXML
    private void nextPage(){
        try {
            this.serviceUtilizator.getNextUsers();
            initModel();
        }
        catch (ValidationException ex){
            MessageAlert.showErrorMessage(null, ex.toString());
        }
    }
    private void initModel() {
        Iterable<Utilizator> users = serviceUtilizator.findAll();
        List<Utilizator> usersList = StreamSupport.stream(users.spliterator(), false)
                .collect(Collectors.toList());
        model.setAll(usersList);
    }

}
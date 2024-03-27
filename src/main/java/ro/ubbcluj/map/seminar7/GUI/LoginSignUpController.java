package ro.ubbcluj.map.seminar7.GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ro.ubbcluj.map.seminar7.domain.Account;
import ro.ubbcluj.map.seminar7.domain.Utilizator;
import ro.ubbcluj.map.seminar7.service.*;
import ro.ubbcluj.map.seminar7.validators.ValidationException;

import java.io.IOException;

public class LoginSignUpController {

    ServiceUtilizator serviceUtilizator;
    ServicePrieten servicePrieten;
    ServiceInvitation serviceInvitation;
    ServiceMessage serviceMessage;
    ServiceAccount serviceAccount;



    @FXML
    Label labelLoginError;
    @FXML
    Label labelSignUpError;
    @FXML
    TextField textUsernameLogin;
    @FXML
    TextField textPasswordLogin;
    @FXML
    TextField textFirstnameSignUp;
    @FXML
    TextField textLastnameSignUp;
    @FXML
    TextField textUsernameSignUp;
    @FXML
    TextField textPasswordSignUp;

    public void setService(ServiceUtilizator serviceUtilizator, ServicePrieten servicePrieten, ServiceInvitation serviceInvitation, ServiceMessage serviceMessage, ServiceAccount serviceAccount) {
        this.serviceUtilizator = serviceUtilizator;
        this.servicePrieten = servicePrieten;
        this.serviceInvitation = serviceInvitation;
        this.serviceMessage = serviceMessage;
        this.serviceAccount = serviceAccount;
        //this.criptare = new CriptareDecriptare();
    }
    @FXML
    private void showUserProfileLogin(){
        try{
            String username = this.textUsernameLogin.getText();
            String password = this.textPasswordLogin.getText();
            if(!this.serviceAccount.exists(username))
                throw new ValidationException("Username sau parola invalid/a!");
            Account account = this.serviceAccount.findAccount(username);
            if(!account.getPassword().equals(password))
                throw new ValidationException("Username sau parola invalid/a!");
            Utilizator user = this.serviceUtilizator.findUser(account.getIdUser());

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("profile.fxml"));
            AnchorPane root = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle(username);
            //dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            ProfileController profileController = loader.getController();
            profileController.setService(serviceUtilizator, servicePrieten, serviceInvitation, serviceMessage, dialogStage, user);

            dialogStage.show();
            this.textUsernameLogin.setText("");
            this.textPasswordLogin.setText("");
            this.labelLoginError.setText("");
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        catch(ValidationException ex){
            this.labelLoginError.setText(ex.toString());
            this.textUsernameLogin.setText("");
            this.textPasswordLogin.setText("");
        }
    }
    @FXML
    private void showUserProfileSignUp(){
        try{
            String firstname = this.textFirstnameSignUp.getText();
            String lastname = this.textLastnameSignUp.getText();
            String username = this.textUsernameSignUp.getText();
            String password = this.textPasswordSignUp.getText();
            if(this.serviceAccount.exists(username) || password == null)
                throw new ValidationException("Username deja existent sau parola invalida!");
            Utilizator user = addUser(firstname, lastname);
            Account account = this.serviceAccount.addAccount(username, password, user.getId());

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("profile.fxml"));
            AnchorPane root = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle(username);
            //dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            ProfileController profileController = loader.getController();
            profileController.setService(serviceUtilizator, servicePrieten, serviceInvitation, serviceMessage, dialogStage, user);

            dialogStage.show();
            this.textLastnameSignUp.setText("");
            this.textFirstnameSignUp.setText("");
            this.textUsernameSignUp.setText("");
            this.textPasswordSignUp.setText("");
            this.labelSignUpError.setText("");
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        catch(ValidationException ex){
            this.labelSignUpError.setText(ex.toString());
            this.textLastnameSignUp.setText("");
            this.textFirstnameSignUp.setText("");
            this.textUsernameSignUp.setText("");
            this.textPasswordSignUp.setText("");
        }
    }

    public Utilizator addUser(String firstname, String lastname) throws ValidationException{
            return this.serviceUtilizator.addUser(firstname, lastname);
    }
}

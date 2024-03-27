package ro.ubbcluj.map.seminar7.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ro.ubbcluj.map.seminar7.domain.Utilizator;
import ro.ubbcluj.map.seminar7.repository.*;
import ro.ubbcluj.map.seminar7.service.*;
import ro.ubbcluj.map.seminar7.validators.PrietenieValidator;
import ro.ubbcluj.map.seminar7.validators.UtilizatorValidator;

import java.io.IOException;

public class StartApplication extends Application {
    private ServiceUtilizator serviceUtilizator;
    private ServicePrieten servicePrieten;
    private ServiceInvitation serviceInvitation;
    private ServiceAccount serviceAccount;

    private ServiceMessage serviceMessage;
    @Override
    public void start(Stage stage) throws IOException {

        String url="jdbc:postgresql://localhost:5432/socialnetwork";
        String username = "postgres";
        String password = "alisapaula23";

        UserDBPagingRepository userDBRepository = new UserDBPagingRepository(url, username, password, new UtilizatorValidator());
        FriendshipDBRepository friendshipDBRepository = new FriendshipDBRepository(url, username, password, new PrietenieValidator());
        InvitationDBRepository invitationDBRepository = new InvitationDBRepository(url, username, password);
        MessageDBRepository messageDBRepository = new MessageDBRepository(url,username,password);
        AccountDBRepository accountDBRepository = new AccountDBRepository(url,username, password);
        //userDBRepository.loadFromDB();
        //friendshipDBRepository.loadFromDB();

        serviceUtilizator = new ServiceUtilizator(userDBRepository);
        servicePrieten = new ServicePrieten(friendshipDBRepository);
        serviceInvitation = new ServiceInvitation(invitationDBRepository);
        serviceMessage = new ServiceMessage(messageDBRepository);
        serviceAccount = new ServiceAccount(accountDBRepository);

        //initViewUserTable(stage);
        initViewLoginPage(stage);
        stage.setTitle("SocialNetwork");
//        stage.getIcons().add(new Image("C:\\Users\\vanes\\Documents\\facultate\\MAP\\LABORATOR\\lab11\\src\\main\\resources\\ro\\ubbcluj\\map\\seminar7\\GUI\\profile.png"));
        stage.setWidth(610);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
    private void initViewUserTable(Stage primaryStage) throws IOException {

        try {
            FXMLLoader userLoader = new FXMLLoader();
            userLoader.setLocation(getClass().getResource("user-view.fxml"));
            AnchorPane userLayout = userLoader.load();
            primaryStage.setScene(new Scene(userLayout));

            UserController userController = userLoader.getController();
            userController.setService(serviceUtilizator, servicePrieten, serviceInvitation, serviceMessage);
        }
        catch(RuntimeException ex){
            ex.getMessage();
        }
    }

    private void initViewLoginPage(Stage primaryStage) throws  IOException{
        try {
            FXMLLoader userLoader = new FXMLLoader();
            userLoader.setLocation(getClass().getResource("LoginSignUp_Page.fxml"));
            AnchorPane userLayout = userLoader.load();
            primaryStage.setScene(new Scene(userLayout));

            LoginSignUpController loginSignUpController = userLoader.getController();
            loginSignUpController.setService(serviceUtilizator, servicePrieten, serviceInvitation, serviceMessage, serviceAccount);
        }
        catch(RuntimeException ex){
            ex.getMessage();
        }
    }
}
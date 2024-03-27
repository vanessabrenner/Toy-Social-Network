package ro.ubbcluj.map.seminar7;

import ro.ubbcluj.map.seminar7.UI.Consola;
import ro.ubbcluj.map.seminar7.repository.FriendshipDBRepository;
import ro.ubbcluj.map.seminar7.repository.UserDBRepository;
import ro.ubbcluj.map.seminar7.service.ServicePrieten;
import ro.ubbcluj.map.seminar7.service.ServiceUtilizator;
import ro.ubbcluj.map.seminar7.validators.PrietenieValidator;
import ro.ubbcluj.map.seminar7.validators.UtilizatorValidator;

import java.io.IOException;

//        Utilizator u1=new Utilizator("u1FirstName", "u1LastName");
//        u1.setId(1l);
//        Utilizator u2=new Utilizator("u2FirstName", "u2LastName"); u2.setId(2l);
//        Utilizator u3=new Utilizator("u3FirstName", "u3LastName"); u3.setId(3l);
//        Utilizator u4=new Utilizator("u4FirstName", "u4LastName"); u4.setId(4l);
//        Utilizator u5=new Utilizator("u5FirstName", "u5LastName"); u5.setId(5l);
//        Utilizator u6=new Utilizator("u6FirstName", "u6LastName"); u6.setId(6l);
//        Utilizator u7=new Utilizator("u7FirstName", "u7LastName"); u7.setId(7l);
//
//        InMemoryRepository<Long, Utilizator> repo=new InMemoryRepository<>(new UtilizatorValidator());
//        repo.save(u1);
//        repo.save(u2);
//        repo.save(u3);
//        repo.save(u4);
//        repo.save(u5);
//        repo.save(u6);
//        repo.save(u7);
//
//        System.out.println("ok");
//        for(var e: repo.findAll())
//            System.out.println(e);


public class Main {

    public static void main(String[] args) throws IOException {
//        String url="jdbc:postgresql://localhost:5432/socialnetwork";
//        String username = "postgres";
//        String password = "alisapaula23";
//
//        UtilizatorValidator val_users = new UtilizatorValidator();
//        PrietenieValidator val_friends = new PrietenieValidator();
//        UserDBRepository repo_users = new UserDBRepository(url, username, password, val_users);
//        FriendshipDBRepository repo_friends = new FriendshipDBRepository(url, username, password, val_friends);
//        ServiceUtilizator srv_users = new ServiceUtilizator(repo_users);
//        ServicePrieten srv_friends = new ServicePrieten(repo_friends);
//
//        Consola ui = new Consola(srv_users, srv_friends);
//        ui.run();
    }
}

package ro.ubbcluj.map.seminar7.service;

import ro.ubbcluj.map.seminar7.domain.Invitation;
import ro.ubbcluj.map.seminar7.domain.Message;
import ro.ubbcluj.map.seminar7.domain.Utilizator;
import ro.ubbcluj.map.seminar7.observers.Observable;
import ro.ubbcluj.map.seminar7.observers.Observer;
import ro.ubbcluj.map.seminar7.repository.OptionalRepository;
import ro.ubbcluj.map.seminar7.validators.ValidationException;

import java.util.ArrayList;
import java.util.List;

public class ServiceMessage implements Observable {
    OptionalRepository repo_messages;

    public ServiceMessage(OptionalRepository repo_messages) {
        this.repo_messages = repo_messages;
    }
    public Message addMessage(Utilizator user1, Utilizator user2, String message, Message reply) throws ValidationException {
        Message msg;
        if(reply == null)
            msg = new Message(user1.getId(), user2.getId(), message, null);
        else
            msg = new Message(user1.getId(), user2.getId(), message, reply.getId());
        if(repo_messages.save(msg).isEmpty()){
            return msg;
        }
        else {
            this.notifyObservers();
            return null;
        }
    }
    public Message findMessage(Long id){
        return repo_messages.findOne(id).isEmpty()? null : (Message) repo_messages.findOne(id).get();
    }

    public Iterable<Message> findAll(){
        return repo_messages.findAll();
    }

    private List<Observer> observers = new ArrayList<>();
    @Override
    public void addObserver(Observer e) {
        this.observers.add(e);
    }

    @Override
    public void removeObserver(Observer e) {
        this.observers.remove(e);
    }

    @Override
    public void notifyObservers() {
        this.observers.forEach(o->o.update());
    }
}

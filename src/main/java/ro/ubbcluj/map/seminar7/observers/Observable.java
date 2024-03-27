package ro.ubbcluj.map.seminar7.observers;


public interface Observable {
    void addObserver(Observer e);
    void removeObserver(Observer e);
    void notifyObservers();
}

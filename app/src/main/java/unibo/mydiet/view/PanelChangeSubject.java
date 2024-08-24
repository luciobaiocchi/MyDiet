package unibo.mydiet.view;

public interface PanelChangeSubject {
    void addObserver(PanelChangeObserver observer);
    void removeObserver(PanelChangeObserver observer);
    void notifyObservers(String panelName);
}
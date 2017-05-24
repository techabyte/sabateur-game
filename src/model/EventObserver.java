package model;

/**
 * Timer Status. Need to change name of file
 */
import controller.PlayGameListener;
import view.GameView;

import java.util.Observable;

public class EventObserver extends Observable {
    private Boolean status;

    public EventObserver(Boolean status) {
        this.status = status;
    }

    public Boolean getTimerStatus() {
        return status;
    }

    public void setTimerStatus(Boolean status) {
        this.status = status;
        System.out.println("OBSERVER: " + status);
        if(this.status){
            setChanged();
            notifyObservers();
        }
    }
}

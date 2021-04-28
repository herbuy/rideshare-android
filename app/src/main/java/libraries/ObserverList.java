package libraries;

import android.content.Context;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ObserverList<TakingInEventArgsType> {


    public interface Observer<TakingInType>{
        void notifyEvent(TakingInType eventArgs);
    }

    public interface ObserverWithContext<TakingInType> extends Observer<TakingInType>{
        //used for deleting observers when they get out of context e.g when activity is closed
        Context getContext();
    }

    private List<Observer<TakingInEventArgsType>> observers = new ArrayList<>();

    public void add(Observer<TakingInEventArgsType> observer){
        observers.add(observer);
    }

    public void remove(Observer<TakingInEventArgsType> observer){
        observers.remove(observer);
    }

    synchronized public void clear(){
        observers.clear();
    }

    int totalCalls = 0;

    public int getTotalCalls() {
        return totalCalls;
    }

    public void notifyObservers(TakingInEventArgsType eventArgs){
        totalCalls += 1;
        for(Observer<TakingInEventArgsType> observer : observers){
            observer.notifyEvent(eventArgs);
        }
    }


    public void notifyObserversExcept(Observer<TakingInEventArgsType> excludedObserver,TakingInEventArgsType eventArgs){
        totalCalls += 1;
        for(Observer<TakingInEventArgsType> observer : observers){
            if(observer == excludedObserver) continue;
            observer.notifyEvent(eventArgs);
        }
    }

    public int getCount() {
        return observers.size();
    }

}

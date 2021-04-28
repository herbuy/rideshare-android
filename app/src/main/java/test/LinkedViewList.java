package test;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class LinkedViewList {

    List<View> list = new ArrayList<>();
    int currentIndex = 0;

    public LinkedViewList() {
    }

    public void addView(View... viewList) {
        for(View child: viewList){
            list.add(child);
        }
    }

    public boolean hasNextVisibleIndex() {
        return getNextVisibleIndex() >= 0;
    }
    public boolean hasPreviousVisibleIndex() {
        return getPreviousVisibleIndex() >= 0;
    }


    public View getNextVisibleView() {
        int nextVisibleIndex = getNextVisibleIndex();
        if(nextVisibleIndex < 0){
            return null;
        }
        return list.get(nextVisibleIndex);
    }

    public View getPreviousVisibleView() {
        int previousVisibleIndex = getPreviousVisibleIndex();
        if(previousVisibleIndex < 0){
            return null;
        }
        return list.get(previousVisibleIndex);
    }

    public boolean hasCurrentVisibleView(){
        return getFirstVisibleIndex(currentIndex) >= 0;
    }

    private int getFirstVisibleIndex(int startIndex) {
        int firstVisibleIndex = getFirstVisibleIndex(startIndex,1);
        if(firstVisibleIndex >= 0){
            return firstVisibleIndex;
        }
        return getFirstVisibleIndex(startIndex,-1);
    }

    private int getFirstVisibleIndex(int startIndex, int direction) {
        if(startIndex >= list.size()){
            return -1;
        }
        if (list.get(startIndex).getVisibility() == View.VISIBLE){
            return startIndex;
        }
        else{
            startIndex += direction;
            return getFirstVisibleIndex(startIndex);
        }
    }

    public View getCurrentVisibleView(){
        int indexOfCurrentVisibleView = getFirstVisibleIndex(currentIndex);

        if(indexOfCurrentVisibleView >= 0){
            return list.get(indexOfCurrentVisibleView);
        }
        else{
            return null;
        }
    }

    public void moveToNextVisibleIndex(){
        currentIndex = getNextVisibleIndex();

    }

    public void moveToPreviousVisibleIndex(){
        currentIndex = getPreviousVisibleIndex();

    }

    public int getNextVisibleIndex() {
        int firstVisibleIndex = getFirstVisibleIndex(currentIndex);
        if(firstVisibleIndex < 0){
            return -1;
        }
        return getFirstVisibleIndex(firstVisibleIndex + 1,1);
    }

    public int getPreviousVisibleIndex() {
        int currentVisibleIndex = getFirstVisibleIndex(currentIndex);
        if(currentVisibleIndex <= 0){
            return -1;
        }
        return getFirstVisibleIndex(currentVisibleIndex - 1,-1);
    }

    public void hideViewIndex(int... indexes) {
        for(int index : indexes){
            if(hasIndex(index)){
                list.get(index).setVisibility(View.GONE);
            }
        }

    }
    public void showViewIndex(int... indexes) {
        for(int index: indexes){
            if(hasIndex(index)){
                list.get(index).setVisibility(View.VISIBLE);
            }
        }

    }

    public boolean hasIndex(int index) {
        return index >= 0 && index < list.size();
    }
}

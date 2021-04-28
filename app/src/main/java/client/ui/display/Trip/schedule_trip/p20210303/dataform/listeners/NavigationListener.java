package client.ui.display.Trip.schedule_trip.p20210303.dataform.listeners;

public interface NavigationListener {
    void displayNextPage();
    void displayPreviousPage();
    boolean isOnLastPage();
    boolean pageApplicableToCurrentUser(int page);
}

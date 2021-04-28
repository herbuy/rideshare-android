package client.ui.display.Trip.schedule_trip.steps;

public interface TripUploadPilot {
    void indicateUploading();
    void indicateSuccess();
    void indicateError(String message);
}

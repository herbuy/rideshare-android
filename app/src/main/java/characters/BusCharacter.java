package characters;

import android.widget.ImageView;
import android.widget.TextView;

public class BusCharacter {
    ItemContainer<BusData> busContainer;
    public void display(){
        displayBusImage();
        displayBusName();
        displayBusRegistrationNumber();
    }

    private void displayBusRegistrationNumber() {
        TextView busRegNumber = new TextView(busContainer.getContext());
        busRegNumber.setText(busContainer.getData().getRegistrationNumber());
        busContainer.add(busRegNumber);
    }

    private void displayBusName() {
        TextView busName = new TextView(busContainer.getContext());
        busName.setText(busContainer.getData().getName());
        busContainer.add(busName);
    }

    private void displayBusImage() {
        busContainer.add(new ImageView(busContainer.getContext()));
    }
}

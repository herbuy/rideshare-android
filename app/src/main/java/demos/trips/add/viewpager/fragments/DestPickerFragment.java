package demos.trips.add.viewpager.fragments;

import core.businessobjects.Location;

public class DestPickerFragment extends SingleLocationPickerFragment {
    public DestPickerFragment(Parameters<Location> params) {
        super(params);
    }

    @Override
    protected String question() {
        return "Where are you going?";
    }
}

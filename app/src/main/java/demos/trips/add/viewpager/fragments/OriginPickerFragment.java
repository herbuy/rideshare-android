package demos.trips.add.viewpager.fragments;

import core.businessobjects.Location;

public class OriginPickerFragment extends SingleLocationPickerFragment {
    public OriginPickerFragment(Parameters<Location> params) {
        super(params);
    }

    @Override
    protected String question() {
        return "Where will you be setting off from?";
    }
}

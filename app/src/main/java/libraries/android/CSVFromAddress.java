package libraries.android;

import android.content.Context;

import java.util.HashSet;
import java.util.Set;

import libraries.android.AddressListFromString;

public class CSVFromAddress {

    private String text = "";

    public CSVFromAddress(AddressListFromString.Address address) {
        text = getTextFromAddress(address);

    }

    private String getTextFromAddress(AddressListFromString.Address address) {
        Set<String> addedText = new HashSet<>();
        String text = "";
        String separator = "";
        String commandSeparator = ", ";


        String newText = address.getPremises();
        if(address.hasPremises() && !addedText.contains(newText)){

            text += String.format("%s%s", separator,newText);
            addedText.add(newText);
            separator = commandSeparator;
        }

        newText = address.getFeatureName();
        if(address.hasFeatureName() && !addedText.contains(newText)){

            text += String.format("%s%s", separator,newText);
            addedText.add(newText);
            separator = commandSeparator;
        }

        newText = address.getSubThoroughfare();
        if(address.hasSubThoroughFare() && !addedText.contains(newText)){

            text += String.format("%s%s", separator,newText);
            addedText.add(newText);
            separator = commandSeparator;
        }

        newText = address.getThoroughfare();
        if(address.hasThoroughFare() && !addedText.contains(newText)){

            text += String.format("%s%s", separator,newText);
            addedText.add(newText);
            separator = commandSeparator;
        }


        newText = address.getSubLocality();
        if(address.hasSubLocality() && !addedText.contains(newText)){

            text += String.format("%s%s", separator,newText);
            addedText.add(newText);
            separator = commandSeparator;
        }

        newText = address.getLocality();
        if(address.hasLocality() && !addedText.contains(newText)){

            text += String.format("%s%s", separator,newText);
            addedText.add(newText);
            separator = commandSeparator;
        }

        newText = address.getSubAdminArea();
        if(address.hasSubAdminArea() && !addedText.contains(newText)){

            text += String.format("%s%s", separator,newText);
            addedText.add(newText);
            separator = commandSeparator;
        }

        newText = address.getAdminArea();
        if(address.hasAdminArea() && !addedText.contains(newText)){

            text += String.format("%s%s", separator,newText);
            addedText.add(newText);
            separator = commandSeparator;
        }

        newText = address.getCountryName();
        if(address.hasCountryName() && !addedText.contains(newText)){

            text += String.format("%s%s", separator,newText);
            addedText.add(newText);
            separator = commandSeparator;
        }
        return text;
    }


    public boolean notEmpty() {
        return !(text == null || text.trim().equalsIgnoreCase(""));
    }

    public String getText() {
        return text;
    }
}

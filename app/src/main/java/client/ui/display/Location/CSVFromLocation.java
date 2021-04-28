package client.ui.display.Location;

import java.util.HashSet;
import java.util.Set;

import core.businessobjects.Location;

public class CSVFromLocation {

    private String text = "";

    public CSVFromLocation(Location address) {
        text = getTextFromAddress(address);

    }

    private String getTextFromAddress(Location address) {
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
        text = makeFirstTextBold(text);
        return text;
    }

    private String makeFirstTextBold(String text) {
        if(text == null || text.length() < 1){
            return text;
        }
        String[] tokens = text.split(",");
        if(tokens == null || tokens.length < 2){
            return text;
        }

        tokens[0] = String.format("<b><font color='#444444'>%s</font></b>",tokens[0]);

        StringBuilder builder = new StringBuilder();
        String prefix = "";
        for(String token : tokens){
            builder.append(prefix);
            builder.append(token);
            prefix = ", ";
        }
        return builder.toString();
    }


    public boolean notEmpty() {
        return !(text == null || text.trim().equalsIgnoreCase(""));
    }

    public String getText() {
        return text;
    }



}

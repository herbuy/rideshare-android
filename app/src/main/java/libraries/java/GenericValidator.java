
package libraries.java;

import java.util.ArrayList;
import java.util.List;

public class GenericValidator<InputType> {
    List<GenericRule<InputType>> rules = new ArrayList<>();


    public static abstract class GenericRule<InputType> {
        protected static boolean isNumeric(String value) {
            return value != null && value.matches("^-?\\d+(.\\d+)?$");
        }

        protected static boolean isWholeNumber(String value) {
            return value != null && value.matches("^-?\\d+$");
        }

        protected static boolean isWholeNumberAbove(String value, final Long threshold){
            try{
                if(value.length() > Long.valueOf(Long.MAX_VALUE).toString().length()){
                    return false;
                }
                Long result = Long.valueOf(value);
                return result > threshold ;
            }
            catch (Exception ex){
                return false;
            }
        }

        protected static boolean isWholeNumberBelow(String value, final Long threshold){
            try{
                if(value.length() > Long.valueOf(Long.MAX_VALUE).toString().length()){
                    return false;
                }

                Long result = Long.valueOf(value);
                return result < threshold ;
            }
            catch (Exception ex){
                return false;
            }
        }

        public abstract String getError(InputType value);

        protected static final boolean isNullOrEmpty(String value) {
            return value == null || value.trim().equalsIgnoreCase("");
        }

        protected static final boolean isAlphaNumericOrSpace(String value) {
            if(value == null){
                return false;
            }
            return value.matches("[\\w ]+");
        }

        protected static final boolean isLongerThan(String value, int threshold) {
            if(value == null){
                return false;
            }
            return value.length() > threshold;
        }

        protected static final boolean isNotLongerThan(String value, int threshold) {
            if(value == null){
                return false;
            }
            return value.length() <= threshold;
        }

        protected static final boolean isShorterThan(String value, int threshold) {
            if(value == null){
                return false;
            }
            return value.length() < threshold;
        }

        protected static final boolean isNotShorterThan(String value, int threshold) {
            if(value == null){
                return false;
            }
            return value.length() >= threshold;
        }

        /** returns true if all the conditions are false, else returns false if any condition is true */
        protected static final boolean noneIsTrue(boolean... conditionList){
           return !anyIsTrue(conditionList);
        }

        protected static final boolean anyIsTrue(boolean... conditionList){
            if(conditionList == null){
                return false;
            }
            for(boolean condition: conditionList){
                if(condition){
                    return true;
                }
            }
            return false;
        }

        protected static final boolean allIsTrue(boolean... conditionList){
            if(conditionList == null){
                return false;
            }
            for(boolean condition: conditionList){
                if(!condition){
                    return false;
                }
            }
            return true;
        }

        protected static final boolean notAllTrue(boolean... conditionList){
            return !allIsTrue(conditionList);
        }

        protected static final boolean anyIsNullOrEmpty(String[] valueList){
            if(valueList == null){
                return false;
            }
            for(String value: valueList){
                if(isNullOrEmpty(value)){
                    return true;
                }
            }
            return false;
        }
    }

    public interface Parameters<InputType> {
        InputType getValue();

        void onSuccess(InputType inputValue);

        void onError(String error, InputType inputValue);
    }

    public String validate(InputType value) {
        String error = "";

        for (GenericRule<InputType> rule : rules) {

            error = rule.getError(value);
            if (GenericRule.isNullOrEmpty(error)) continue;
            break;
        }
        return error;

    }

    /**
     * useful on the server, where u might just want to throw an exception if error detected
     **/
    public void throwExceptionIfNotValid(InputType value) {
        String error = validate(value);
        if (!GenericRule.isNullOrEmpty(error)) {
            throw new RuntimeException(error);
        }
    }

    /**
     * most useful on the client for showing against the field that has the error
     */
    public void validate(Parameters<InputType> parameters) {

        InputType value = parameters.getValue();
        String error = validate(value);
        if (GenericRule.isNullOrEmpty(error)) {
            parameters.onSuccess(value);
        } else {
            parameters.onError(error, value);
        }
    }

    public GenericValidator<InputType> addRule(GenericRule<InputType> rule) {
        if (rule != null) {
            rules.add(rule);
        }
        return this;
    }

    //returns the passed error message if the value validated is null or empty
    public GenericValidator<InputType> addRuleNotNull(final String errorMessage) {
        rules.add(
                new GenericRule<InputType>() {
                    @Override
                    public String getError(InputType value) {
                        return value == null ? errorMessage : "";
                    }
                }
        );
        return this;
    }

}


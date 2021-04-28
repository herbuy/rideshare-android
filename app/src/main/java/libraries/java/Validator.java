package libraries.java;


public class Validator extends GenericValidator<String>{


    public static abstract class Rule extends GenericRule<String>{

    }

    public static interface Parameters extends GenericValidator.Parameters<String>{

    }

    public void throwExceptionIfNotValid(int value){
        throwExceptionIfNotValid(String.valueOf(value));
    }
    public void throwExceptionIfNotValid(long value){
        throwExceptionIfNotValid(String.valueOf(value));
    }
    public void throwExceptionIfNotValid(float value){
        throwExceptionIfNotValid(String.valueOf(value));
    }
    public void throwExceptionIfNotValid(double value){
        throwExceptionIfNotValid(String.valueOf(value));
    }

    public Validator addRuleIsDateFormat(final String errorMessage){
        rules.add(
                new Rule() {
                    @Override
                    public String getError(String value) {
                        return value != null || value.matches("\\d{4}-\\d{1,2}-\\d{1,2}") ? "" : errorMessage;
                    }
                }
        );
        return this;
    }

    public Validator addRuleIsTimeFormat(final String errorMessage){
        rules.add(
                new Rule() {
                    @Override
                    public String getError(String value) {
                        return value != null || value.matches("\\d{1,2}:\\d{1,2}:\\d{1,2}") ? "" : errorMessage;
                    }
                }
        );
        return this;
    }

    //returns the passed error message if the value validated is null or empty
    public Validator addRuleNotNullOrEmpty(final String errorMessage){
        rules.add(
                new Rule() {
                    @Override
                    public String getError(String value) {
                        return isNullOrEmpty(value) ? errorMessage : "";
                    }
                }
        );
        return this;
    }

    public Validator addRuleStartsWith(final String prefix, final String errorMessage) {
        rules.add(
                new Rule() {
                    @Override
                    public String getError(String value) {
                        return value != null && value.startsWith(prefix) ? "" : errorMessage;
                    }
                }
        );
        return this;
    }

    public Validator addRuleNotStartsWith(final String prefix, final String errorMessage) {
        rules.add(
                new Rule() {
                    @Override
                    public String getError(String value) {
                        return value == null || !value.startsWith(prefix) ? "" : errorMessage;
                    }
                }
        );
        return this;
    }

    public Validator addRuleEndsWith(final String suffix, final String errorMessage) {
        rules.add(
                new Rule() {
                    @Override
                    public String getError(String value) {
                        return value != null && value.endsWith(suffix) ? "" : errorMessage;
                    }
                }
        );
        return this;
    }

    public Validator addRuleNotEndsWith(final String suffix, final String errorMessage) {
        rules.add(
                new Rule() {
                    @Override
                    public String getError(String value) {
                        return value == null || !value.endsWith(suffix) ? "" : errorMessage;
                    }
                }
        );
        return this;
    }

    public Validator addRuleContains(final String needle, final String errorMessage) {
        rules.add(
                new Rule() {
                    @Override
                    public String getError(String value) {
                        return value != null && value.contains(needle) ? "" : errorMessage;
                    }
                }
        );
        return this;
    }

    public Validator addRuleNotContains(final String needle, final String errorMessage) {
        rules.add(
                new Rule() {
                    @Override
                    public String getError(String value) {
                        return value == null || !value.contains(needle) ? "" : errorMessage;
                    }
                }
        );
        return this;
    }

    public Validator addRuleIsNumeric(final String errorMessage) {
        rules.add(
                new Rule() {
                    @Override
                    public String getError(String value) {
                        return GenericRule.isNumeric(value) ? "" : errorMessage;
                    }
                }
        );
        return this;
    }

    public Validator addRuleIsWholeNumber(final String errorMessage) {
        rules.add(
                new Rule() {
                    @Override
                    public String getError(String value) {
                        return GenericRule.isWholeNumber(value) ? "" : errorMessage;
                    }
                }
        );
        return this;
    }

    public Validator addRuleIsWholeNumberNonNegative(final String errorMessage) {
        addRuleIsWholeNumberAbove(-1L,errorMessage);
        return this;
    }

    public Validator addRuleIsWholeNumberAboveZero(final String errorMessage) {
        addRuleIsWholeNumberAbove(0L,errorMessage);
        return this;
    }

    public Validator addRuleIsWholeNumberAbove(final Long threshold, final String errorMessage) {
        rules.add(
                new Rule() {
                    @Override
                    public String getError(String value) {
                        if(value == null){
                            return "";
                        }
                        return GenericRule.isWholeNumberAbove(value,threshold) ? "" : errorMessage;

                    }
                }
        );
        return this;
    }


    public Validator addRuleNthCharacterIs(final char expectedValue, final int index, final String onError) {
        rules.add(new Rule() {
            @Override
            public String getError(String value) {
                if(value == null){
                    return onError;
                }

                char[] characters = value.toCharArray();

                //he could be still inputing
                if(characters.length <= index){
                    return "";
                }

                if(characters[index] != expectedValue){
                    return onError;
                }
                return "";
            }
        });
        return this;
    }


    public Validator addRuleNthCharacterIsNot(final char expectedValue, final int index, final String onError) {
        rules.add(new Rule() {
            @Override
            public String getError(String value) {
                if(value == null){
                    return "";
                }

                char[] characters = value.toCharArray();

                //he could be still inputing
                if(characters.length <= index){
                    return "";
                }

                if(characters[index] != expectedValue){
                    return "";
                }
                return onError;
            }
        });
        return this;
    }


    public Validator addRuleNotLongerThan(final int length, final String errorMessage) {
        rules.add(
                new Rule() {
                    @Override
                    public String getError(String value) {
                        return value != null && value.length() > length ? errorMessage : "";
                    }
                }
        );
        return this;
    }

    public Validator addRuleNotShorterThan(final int length, final String errorMessage) {
        rules.add(
                new Rule() {
                    @Override
                    public String getError(String value) {
                        return value != null && value.length() < length ? errorMessage : "";
                    }
                }
        );
        return this;
    }

    public Validator addRuleStartsWithALetter(final String errorMessage) {
        rules.add(
                new Rule() {
                    @Override
                    public String getError(String value) {
                        return value == null || value.matches("^[a-zA-Z](.)*") ? "" : errorMessage;
                    }
                }
        );
        return this;
    }

    public Validator addRuleAlphaNumeric(final String errorMessage) {
        rules.add(
                new Rule() {
                    @Override
                    public String getError(String value) {
                        return value == null || value.matches("\\w+") ? "" : errorMessage;
                    }
                }
        );
        return this;
    }

    public Validator addRuleAlphaNumericAndSpace(final String errorMessage) {
        rules.add(
                new Rule() {
                    @Override
                    public String getError(String value) {
                        return value == null || value.matches("[ \\w]+") ? "" : errorMessage;
                    }
                }
        );
        return this;
    }

}

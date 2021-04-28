package core.validators;

import java.util.List;
import java.util.Locale;

import core.businessobjects.Location;
import libraries.java.GenericValidator;
import libraries.java.Validator;

public class ValidatorFor {

    private static Validator newValidator() {
        return new Validator();
    }

    private static String messageBuilder(String fieldName, String message){
        return String.format("%s %s",fieldName,message);
    }

    public static String cannotBeEmpty(String fieldName) {
        return messageBuilder(fieldName,"can not be empty");
    }

    public static String invalid(String fieldName) {
        return messageBuilder(fieldName, "is invalid");
    }

    public static Validator mobileNumberDuringSignup(){
        String fieldName = "Mobile number";
        return newValidator()
                .addRuleNotNullOrEmpty(cannotBeEmpty(fieldName))
                .addRuleIsWholeNumberNonNegative(messageBuilder(fieldName,"should be a number such as 0772123456"));
    }

    public static Validator verificationCodeDuringSignup(){
        String fieldName = "Verification code";
        return newValidator()
                .addRuleNotNullOrEmpty(cannotBeEmpty(fieldName))
                .addRuleIsWholeNumberNonNegative(messageBuilder(fieldName,"should be a 4 digit number"))
                .addRuleNotLongerThan(4,shouldBeNCharacters(fieldName,4))
                ;
    }



    public static Validator usernameDuringSignup(){
        String fieldName = "Name";
        return newValidator()
                .addRuleNotNullOrEmpty(cannotBeEmpty(fieldName))
                .addRuleStartsWithALetter(shouldStartWithALetter(fieldName))
                .addRuleAlphaNumericAndSpace(canOnlyContainLettersNumbersAndSpace(fieldName))
                .addRuleNotShorterThan(4,shouldBeAtleastNChars(fieldName,4))
                .addRuleNotLongerThan(16,shouldNotBeLongerThan(fieldName,16))
                ;
    }

    public static Validator password(){
        String fieldName = "Password";
        return newValidator()
                .addRuleNotNullOrEmpty(cannotBeEmpty(fieldName))
                .addRuleNotShorterThan(6,shouldBeAtleastNChars(fieldName,6))
                .addRuleNotLongerThan(32,shouldNotBeLongerThan(fieldName,32))
                ;
    }

    public static Validator dateDuringTripUpload(){
        String fieldName = "Date";
        return newValidator()
                .addRuleNotNullOrEmpty(cannotBeEmpty(fieldName))
                .addRuleIsDateFormat(invalid(fieldName))
                ;
    }

    public static Validator timeDuringTripUpload(){
        String fieldName = "Time";
        return newValidator()
                .addRuleNotNullOrEmpty(cannotBeEmpty(fieldName))
                .addRuleIsTimeFormat(invalid(fieldName))
                ;
    }

    public static Validator carModelDuringTripUpload(){
        final String fieldName = "Car Model";
        Validator validator = newValidator();

                validator.addRule(new Validator.Rule() {
                    @Override
                    public String getError(String value) {
                        if(isNullOrEmpty(value)){
                            return "";
                        }
                        if(!isAlphaNumericOrSpace(value)){
                            return fieldName + " can only contain letters, numbers, and space";
                        }
                        return "";
                    }
                })
                ;
                return validator;
    }

    public static Validator carRegNumberDuringTripUpload(){
        final String fieldName = "Car Registration Number";
        Validator validator = newValidator();

        validator.addRule(new Validator.Rule() {
            @Override
            public String getError(String value) {
                if(isNullOrEmpty(value)){
                    return "";
                }
                if(!isAlphaNumericOrSpace(value)){
                    return fieldName + " can only contain letters, numbers, and space";
                }
                return "";
            }
        })
        ;
        return validator;
    }

    public static Validator seatsAvailableDuringTripUpload(){
        final String fieldName = "Seats Available";
        Validator validator = newValidator();

        validator.addRule(new Validator.Rule() {
            @Override
            public String getError(String value) {
                if(isNullOrEmpty(value)){
                    return "";
                }
                if(!isNumeric(value)){
                    return fieldName+" should be a number";
                }
                if(!isWholeNumber(value)){
                    return fieldName+" should be a whole number such as 1";
                }
                if(!isWholeNumberAbove(value,0L)){
                    return fieldName + " should be at least 1";
                }
                if(!isWholeNumberBelow(value,6L)){
                    return fieldName + " should be at most 5";
                }

                return "";
            }
        })
        ;
        return validator;
    }

    public static Validator fuelContributionDuringTripUpload(){
        final String fieldName = "Fuel contribution";
        Validator validator = newValidator();

        validator.addRule(new Validator.Rule() {
            @Override
            public String getError(String value) {
                if(isNullOrEmpty(value)){
                    return "";
                }
                if(!isNumeric(value)){
                    return fieldName+" should be a number";
                }
                if(!isWholeNumber(value)){
                    return fieldName+" should be a whole number such as 5000";
                }
                if(!isWholeNumberAbove(value,-1L)){
                    return fieldName + " should be ZERO or a positive number";
                }
                if(!isWholeNumberBelow(value,60001L)){
                    return fieldName + " should be at most 60000";
                }

                return "";
            }
        })
        ;
        return validator;
    }


    private abstract static class LocationRule extends GenericValidator.GenericRule<Location> {
        public static boolean isValidLocationName(String value){
            return isAlphaNumericOrSpace(value) && isLongerThan(value,2) && isShorterThan(value,16);
        }

        public static boolean isInvalidLocationName(String value){
            return !isValidLocationName(value);
        }

        public static boolean isValidLocation(Location value){
            return allIsTrue(
                    isValidLocationName(value.getCountryName()),
                    isValidLocationName(value.getAdminArea()),
                    isValidLocationName(value.getSubAdminArea()),
                    anyIsTrue(
                            isNullOrEmpty(value.getLocality()),
                            isValidLocationName(value.getLocality())
                    )

            );
        }

        public static boolean isInvalidLocation(Location value){
            return !isValidLocation(value);
        }
    }

    public static GenericValidator<Location> destinationDuringTripUpload(){
        final String fieldName = "Destination";
        final GenericValidator<Location> validator = new GenericValidator<>();
        validator.addRuleNotNull(cannotBeEmpty(fieldName));

        validator.addRule(new LocationRule() {
            @Override
            public String getError(Location value) {
                if(isValidLocation(value)){
                    return "";
                }
                return invalid(fieldName);
            }
        });

        return validator;
    }

    public static GenericValidator<List<Location>> routeDuringTripUpload(){

        final GenericValidator<List<Location>> validator = new GenericValidator<>();
        validator.addRule(new GenericValidator.GenericRule<List<Location>>() {
            @Override
            public String getError(List<Location> locationList) {
                if(locationList == null || locationList.size() < 1){
                    return "";
                }
                for(Location location : locationList){
                    if(LocationRule.isInvalidLocation(location)){
                        return "Invalid route information";
                    }
                }
                return "";
            }
        });

        return validator;
    }

    public static GenericValidator<Location> originDuringTripUpload(){
        final String fieldName = "Set off location";
        GenericValidator<Location> validator = new GenericValidator<>();
        validator.addRuleNotNull(cannotBeEmpty(fieldName));

        validator.addRule(new LocationRule() {
            @Override
            public String getError(Location value) {
                if(isValidLocation(value)){
                    return "";
                }
                return invalid(fieldName);
            }
        });

        return validator;
    }



    private static String canOnlyContainLettersNumbersAndSpace(String fieldName) {
        return messageBuilder(fieldName,"can only contain letters, numbers and space");
    }

    private static String shouldStartWithALetter(String fieldName) {
        return messageBuilder(fieldName,"should start with a letter");
    }

    private static String shouldNotBeLongerThan(String fieldName, int length) {
        return messageBuilder(fieldName, String.format(Locale.ENGLISH,"should not be longer than %d characters",length));
    }

    private static String shouldBeNCharacters(String fieldName, int length) {
        return messageBuilder(fieldName, String.format(Locale.ENGLISH,"should be %d characters",length));
    }

    private static String shouldBeAtleastNChars(String fieldName, int length) {
        return messageBuilder(fieldName, String.format(Locale.ENGLISH,"should be at least %d characters",length));
    }



}

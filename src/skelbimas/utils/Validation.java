package skelbimas.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    public static final String USER_NAME_REGEX_PATTERN = "^[a-zA-Z0-9]{1,300}$";
    public static final String USER_PASSWORD_REGEX_PATTERN = "^[a-zA-Z0-9!@#$%^&*()_=*/.,?|]{6,16}$";
    public static final String USER_EMAIL_REGEX_PATTERN = "^[a-zA-Z0-9!@#$%^&*()_=*/.,?|]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,50}$";
    public static final String USER_ID_REGEX_PATTERN = "^[0-9]$";
    public static final String KAINA_REGEX_PATTERN = "^[0-9]{1,20}$";
    public static final String KONTAKTAI_REGEX_PATTERN = "^[a-zA=Z0-9, ]{1,40}$";


    public static boolean isValidUsername(String username){
        Pattern pattern = Pattern.compile(USER_NAME_REGEX_PATTERN);
        Matcher matcher = pattern.matcher(username);
        return matcher.find();
    }

    public static boolean isValidPassword(String password){
        Pattern pattern = Pattern.compile(USER_PASSWORD_REGEX_PATTERN);
        Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }

    public static boolean isValidEmail(String email){
        Pattern pattern = Pattern.compile(USER_EMAIL_REGEX_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }

    public static boolean isValidID(String id){
        Pattern pattern = Pattern.compile(USER_ID_REGEX_PATTERN);
        Matcher matcher = pattern.matcher(id);
        return matcher.find();
    }

    public static boolean isValidKaina(String kaina){
        Pattern pattern = Pattern.compile(KAINA_REGEX_PATTERN);
        Matcher matcher = pattern.matcher(kaina);
        return matcher.find();
    }

    public static boolean isValidKontaktai(String kontaktai) {
        Pattern pattern = Pattern.compile(KONTAKTAI_REGEX_PATTERN);
        Matcher matcher = pattern.matcher(kontaktai);
        return matcher.find();
    }
}

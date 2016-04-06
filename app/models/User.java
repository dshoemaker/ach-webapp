package models;


import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Dan on 3/20/2016.
 */
// This class is purely for storing user information for mock login.
// It does not currently persist to a db or perform any validation.
public class User {

    private static String full_name;

    private static String email;

    private enum Access {
        ADMIN("Admin"), EBS("EBS"), CALLCENTER("Call Center"), MANAGEMENT("Management");
        private String value;
        Access(String value){ this.value = value; }
        public String getValue() { return value; }
    }

    private static Access access;

    public static void setAccess(String acc) {
        acc  = acc.replaceAll("\\s","");
        acc = acc.toUpperCase();
        access = Access.valueOf(acc);
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String e) {
        email = e;
    }

    public static String getFull_name() {
        return full_name == null ? "" : full_name;
    }

    public static void setFull_name(String name) {
        full_name = name;
    }

    public static Access getAccess() {
        return access;
    }

    public static Map<String, String> accessOptions() {
        Map<String, String> access_map = new LinkedHashMap<>();
        for (Access acc : Access.values()) {
            access_map.put(acc.getValue(), acc.getValue());
        }
        return access_map;
    }

    public static void logout() {
        email = null;
        full_name = null;
        access = null;
    }
}

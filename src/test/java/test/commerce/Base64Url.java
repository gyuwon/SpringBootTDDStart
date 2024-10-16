package test.commerce;

import java.util.Base64;

public class Base64Url {

    public static boolean isBase64Url(String part) {
        try {
            Base64.getUrlDecoder().decode(part);
            return true;
        } catch (IllegalArgumentException exception) {
            return false;
        }
    }
}

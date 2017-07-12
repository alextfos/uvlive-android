package es.uv.uvlive.utils;

/**
 * Created by atraverf on 29/12/15.
 */
public class StringUtils {
    public static boolean isBlank(String str){
        return str==null || str.equals("");
    }

    public static boolean equals(String str1, String str2) {
        return str1 != null && str1.equals(str2);
    }
}

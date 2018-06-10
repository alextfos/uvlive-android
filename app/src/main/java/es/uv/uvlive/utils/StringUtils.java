package es.uv.uvlive.utils;

public class StringUtils {

    private StringUtils() {
        throw new RuntimeException("Class can't be instatiated");
    }

    public static boolean isBlank(String str){
        return str==null || str.equals("");
    }

    public static boolean equals(String str1, String str2) {
        return str1 != null && str1.equals(str2);
    }
}

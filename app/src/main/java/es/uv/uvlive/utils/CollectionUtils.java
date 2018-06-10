package es.uv.uvlive.utils;


import android.support.annotation.Nullable;

import java.util.Collection;

public class CollectionUtils {

    private CollectionUtils() {
        throw new RuntimeException("Class can't be instatiated");
    }

    public static boolean isEmpty(@Nullable Collection<?> collection) {
        return collection != null && collection.isEmpty();
    }
}

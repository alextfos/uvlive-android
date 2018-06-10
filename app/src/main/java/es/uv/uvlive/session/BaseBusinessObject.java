package es.uv.uvlive.session;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class BaseBusinessObject {
    private static final Gson GSON_CREATOR = new GsonBuilder().create();

    @Override
    public String toString() {
        return GSON_CREATOR.toJson(this);
    }
}

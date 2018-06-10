package es.uv.uvlive.ui.models;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class BaseModel {
    private static final Gson GSON_CREATOR = new GsonBuilder().create();

    @Override
    public String toString() {
        return GSON_CREATOR.toJson(this);
    }
}

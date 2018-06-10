package es.uv.uvlive.session;


import android.support.annotation.NonNull;

public interface BusinessCallback<T> {
    void onDataReceived(@NonNull T result);
    void onError(BusinessError businessError);
}

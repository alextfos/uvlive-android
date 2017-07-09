package es.uv.uvlive.data;

import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import es.uv.uvlive.data.gateway.response.BaseResponse;

public abstract class UVCallback<T> {

    private static final String TAG = UVCallback.class.getSimpleName();

    public final Listener Listener;
    public final ErrorListener ErrorListener;

    public UVCallback () {
        Listener = new Listener();
        ErrorListener = new ErrorListener();
    }

    public class Listener implements Response.Listener<T>  {
        @Override
        public void onResponse(T t) {
            if ( t != null && t instanceof BaseResponse) {
                Log.d(TAG,t.toString());
                if (((BaseResponse) t).getErrorCode() == 0) {
                    onSuccess(t);
                } else {
                    onError(((BaseResponse) t).getErrorCode());
                }
            } else {
                onError(ErrorManager.ERROR_CODE_UNKNOWN);
            }
        }
    }

    public abstract void onSuccess(@NonNull T t);

    public abstract void onError(int errorCode);

    public class ErrorListener implements Response.ErrorListener {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            volleyError.printStackTrace();
            onError(ErrorManager.ERROR_CODE_UNKNOWN);
        }
    }
}

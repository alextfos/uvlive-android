package es.uv.uvlive.data.gateway;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;

import es.uv.uvlive.UVLiveApplication;
import es.uv.uvlive.utils.StringUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class GsonRequest<T> extends Request<T> {
    private static final int TIME_OUT = 40000;
    private static final String TAG = Request.class.getName();
    private static final String RESPONSE_ENCODING = "utf-8";

    private final Gson gson = new Gson();
    private final Class<T> clazz;
    private static HashMap<String, String> headers= new HashMap<>();
    private static String token;
    private final Response.Listener<T> listener;
    private String mRequestBody;
    static{
        headers.put("Accept","application/json");
        headers.put("Content-Type","application/json");
    }
    /**
     * Make a GET request and return a parsed object from JSON.
     *
     * @param url URL of the request to make
     * @param clazz Relevant class object, for Gson's reflection
     */
    public GsonRequest(String url, Class<T> clazz,String requestBody,
                       Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(Method.POST, url, errorListener);
        this.clazz = clazz;
        this.listener = listener;
        mRequestBody=requestBody;

        this.setRetryPolicy(new DefaultRetryPolicy(
                TIME_OUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    public static void setToken(String token) {
        GsonRequest.token = token;
    }

    public static void removeToken() {
        token = null;
    }

    public static boolean hasToken() {
        return token != null;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        if (token != null) {
            headers.put("Authorization", "Bearer " + token);
        }
        return headers != null ? headers : super.getHeaders();
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    @Override
    public byte[] getBody() {
        try {
            Log.d(TAG,mRequestBody);
            return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
        } catch (UnsupportedEncodingException uee) {
            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                    mRequestBody, "utf-8");
            return null;
        }
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            Map<String, String> headers = response.headers;
//            if (!StringUtils.isBlank(headers.get(HEADER_COOKIE))) {
//                UVLiveGateway.setCookie(headers.get(HEADER_COOKIE));
//            }
            //String str=  new String(response.data);
            String json = new String(response.data, RESPONSE_ENCODING);
            Log.d(TAG,"Headers: " + response.headers);
            Log.d(TAG,"Status code: " + response.statusCode);
            Log.d(TAG,"Body: " + json);
            return Response.success(
                    gson.fromJson(json, clazz),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }
}
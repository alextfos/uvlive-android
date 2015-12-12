package com.example.atraverf.uvlive.gateway;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import com.google.gson.Gson;

import java.util.*;

/**
 * Volley request parsing Json strings using a Gson deserializer
 */
public class GsonRequest<T> extends Request<T> {

    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_TYPE_TEXT_HTML = "text/html";
    public static final int HTTP_STATUS_OK = 200;

    protected final Class<T> mTypeOfT;
    private final Response.Listener<T> mListener;
    protected final Gson mGson;
    protected String mUrl = "";

    protected boolean mIsCached = false;

    /**
     * Create a new Gson Json-parser request.
     *
     * @param method the Http method see {@link com.android.volley.Request.Method}
     * @param typeOfT type of generic.
     * @param url request url.
     * @param listener response listener.
     * @param errorListener error listener.
     */
    public GsonRequest(final int method, final Class<T> typeOfT, final String url,
                       final Response.Listener<T> listener,
                       final Response.ErrorListener errorListener) {
        this(method, typeOfT, url, listener, errorListener, new Gson());
    }

    /**
     * Create a new Gson Json-parser request with a custom gson instance (useful for specifying
     * custom date formats, etc.)
     *
     * @param method the Http method see {@link com.android.volley.Request.Method}
     * @param typeOfT type of generic.
     * @param url request url.
     * @param listener response listener.
     * @param errorListener error listener.
     * @param gson custom Gson instance.
     */
    public GsonRequest(final int method, final Class<T> typeOfT, final String url,
                       final Response.Listener<T> listener,
                       final Response.ErrorListener errorListener, final Gson gson) {
        super(method, url, errorListener);

        mListener = listener;
        mTypeOfT = typeOfT;
        mGson = gson;
        mUrl = url;
    }

    @Override
    protected Response<T> parseNetworkResponse(final NetworkResponse response) {
        try {
            String charset = HttpHeaderParser.parseCharset(response.headers);
            final String responseData = new String(response.data, charset);

            if (isHtmlFacade(response)) {
                //TODO mirar a ver que va aquí
                //return Response.error(new HtmlFacadeError(responseData));
            }

            T responseObject = mGson.fromJson(responseData, mTypeOfT);

            return Response.success(responseObject, HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception e) {
            return Response.error(new ParseError(e));
        }
    }

    /**
     * @param response the response to check
     * @return true if the response contains html according to its Content-Type and the status is
     * 200 OK.
     */
    private boolean isHtmlFacade(NetworkResponse response) {
        Map<String, String> headers = response.headers;
        String contentType = headers.get(HEADER_CONTENT_TYPE);

        return response.statusCode == HTTP_STATUS_OK && contentType != null
                && contentType.contains(CONTENT_TYPE_TEXT_HTML);
    }

    @Override
    protected void deliverResponse(final T response) {
        mListener.onResponse(response);
    }

}

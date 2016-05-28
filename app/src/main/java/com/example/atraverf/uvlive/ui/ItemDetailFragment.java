package com.example.atraverf.uvlive.ui;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.atraverf.uvlive.R;
import com.example.atraverf.uvlive.UVLiveApplication;
import com.example.atraverf.uvlive.gateway.form.MessagesForm;
import com.example.atraverf.uvlive.gateway.response.LoginResponse;
import com.example.atraverf.uvlive.gateway.response.MessagesResponse;
import com.example.atraverf.uvlive.ui.adapter.ListContentManager;

import butterknife.InjectView;


public class ItemDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private ListContentManager.ListItem mItem;
    //@InjectView(R.id.recycler_view)
    //private RecyclerView mRecyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_item_detail, container, false);
        // Show the dummy content as text in a TextView.
        /*if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.item_detail)).setText(mItem.toString());
        }*/

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = ListContentManager.getItemMap().get(getArguments().getString(ARG_ITEM_ID));
            /*
            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.content);
            }*/
        }

        //mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //Se consultan los últimos mensajes almacenados y se solicita el resto con
        //una llamada al servicio
        /*MessagesForm messagesForm = new MessagesForm();
        messagesForm.setIdConversation(mItem.getIdConversation());

        Response.Listener<MessagesResponse> responseListener = new Response.Listener<MessagesResponse>(){
            @Override
            public void onResponse(MessagesResponse messagesResponse) {
                Log.d("proves", "vuelta del servidor");
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("proves","Error de respuesta en el login");
                Toast.makeText(UVLiveApplication.getInstance(), "Ha habido un problema con el login", Toast.LENGTH_LONG).show();
            }
        };

        UVLiveApplication.getUVLiveGateway().messages(messagesForm,responseListener,errorListener);*/

    }
}

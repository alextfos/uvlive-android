package es.uv.uvlive.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.atraverf.uvlive.R;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.uv.uvlive.ui.adapter.AdminOptionsAdapter;

/**
 * Created by alextfos on 21/01/2017.
 */

public class AdminOptionsListFragment extends BaseFragment implements AdminOptionsAdapter.AdminOptionClick {

    private static final ArrayList<String> optionsArrayList = new ArrayList<>(Arrays.asList(
            "Dar de Alta Comerciante",
            "Modificar Datos Comerciante",
            "Registrar Difusión",
            "Caducar sesiones actuales",
            "Ver logs"));

    @BindView(R.id.fragment_admin_options_list)
    protected RecyclerView recyclerView;

    private AdminOptionsAdapter adminOptionsAdapter;

    public static AdminOptionsListFragment newInstance() {
        AdminOptionsListFragment fragment = new AdminOptionsListFragment();
        return fragment;
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_admin_options_list, container, false);
        ButterKnife.bind(this,rootView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adminOptionsAdapter = new AdminOptionsAdapter(optionsArrayList,this);
        recyclerView.setAdapter(adminOptionsAdapter);
        return rootView;
    }


    @Override
    public void onOptionClicked(int position) {
        switch (position) {
            case 0:
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameLayout, MerchantRegisterFragment.newInstance())
                        .addToBackStack(MerchantRegisterFragment.class.getName()).commit();
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameLayout, LogListFragment.newInstance())
                        .addToBackStack(LogListFragment.class.getName()).commit();
                break;
        }
    }
}

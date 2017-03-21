package es.uv.uvlive.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;

import es.uv.uvlive.ui.adapter.viewholder.AdminOptionViewHolder;

/**
 * Created by alextfos on 21/01/2017.
 */

public class AdminOptionsAdapter extends RecyclerView.Adapter<AdminOptionViewHolder> {

    private ArrayList<String> arrayOptions;
    private AdminOptionClick adminOptionClick;

    public AdminOptionsAdapter(ArrayList<String> arrayOptions, AdminOptionClick adminOptionClick) {
        this.arrayOptions = arrayOptions;
        this.adminOptionClick = adminOptionClick;
    }

    @Override
    public AdminOptionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return AdminOptionViewHolder.newInstance(parent,adminOptionClick);
    }

    @Override
    public void onBindViewHolder(AdminOptionViewHolder holder, int position) {
        holder.setText(arrayOptions.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayOptions.size();
    }

    public interface AdminOptionClick {
        void onOptionClicked(int position);
    }
}

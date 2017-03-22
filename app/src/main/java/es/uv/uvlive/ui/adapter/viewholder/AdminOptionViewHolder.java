package es.uv.uvlive.ui.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.atraverf.uvlive.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.uv.uvlive.ui.adapter.AdminOptionsAdapter;

/**
 * Created by alextfos on 21/01/2017.
 */

public class AdminOptionViewHolder extends RecyclerView.ViewHolder {

    private AdminOptionsAdapter.AdminOptionClick adminOptionClick;

    @BindView(R.id.item_admin_option_container)
    protected ViewGroup container;
    @BindView(R.id.item_admin_option)
    protected TextView textView;

    public static AdminOptionViewHolder
    newInstance(ViewGroup parent, AdminOptionsAdapter.AdminOptionClick adminOptionClick) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_option, parent, false);
        return new AdminOptionViewHolder(view,adminOptionClick);
    }

    public AdminOptionViewHolder(View itemView,
                                 final AdminOptionsAdapter.AdminOptionClick adminOptionClick) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        this.adminOptionClick = adminOptionClick;
        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                    adminOptionClick.onOptionClicked(getAdapterPosition());
                }
            }
        });
    }

    public void setText(String text) {
        textView.setText(text);
    }
}

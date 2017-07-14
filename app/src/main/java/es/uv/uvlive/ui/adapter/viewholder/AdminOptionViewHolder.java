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

public class AdminOptionViewHolder extends BaseViewHolder {

    @BindView(R.id.item_admin_option_container)
    protected ViewGroup container;
    @BindView(R.id.item_admin_option)
    protected TextView textView;

    public static AdminOptionViewHolder
    newInstance(ViewGroup parent, AdminOptionsAdapter.AdminOptionClick adminOptionClick) {
        return new AdminOptionViewHolder(inflateView(parent,R.layout.item_admin_option),adminOptionClick);
    }

    public AdminOptionViewHolder(View itemView,
                                 final AdminOptionsAdapter.AdminOptionClick adminOptionClick) {
        super(itemView);
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

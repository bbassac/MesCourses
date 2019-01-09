package lioncorps.org.mescourses.adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import lioncorps.org.mescourses.MainActivity;
import lioncorps.org.mescourses.R;
import lioncorps.org.mescourses.TextViewUtils;
import lioncorps.org.mescourses.bean.Item;
import lioncorps.org.mescourses.bean.Liste;
import lioncorps.org.mescourses.listeners.ClickCheckboxItemListener;
import lioncorps.org.mescourses.listeners.ClickListListener;

public class ListItemsAdapter extends RecyclerView.Adapter<ItemViewHolder> {
    private Liste liste ;
    private MainActivity displayActivity;

    public ListItemsAdapter(Liste liste, MainActivity displayActivity) {
        super();
        this.liste = liste;
        this.displayActivity = displayActivity;

    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View view = inflater.inflate(R.layout.display_items, parent, false);
        view.setOnClickListener(new ClickListListener(displayActivity, liste));
        // Return a new holder instance
        ItemViewHolder viewHolder = new ItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = liste.getItems().get(position);
        final TextView idItemtextView = holder.idItemtextView;
        final TextView nameItemtextView = holder.nameItemtextView;
        final TextView quantiteTextView = holder.quantiteTextView;
        final CheckBox checkBox = holder.checkBox;

        nameItemtextView.setText(item.getNom());
        quantiteTextView.setText(item.getQuantite());

        checkBox.setChecked(item.getDone());
        if (checkBox.isChecked()){
            TextViewUtils.strike(nameItemtextView);
            TextViewUtils.strike(quantiteTextView);
        }else{
            TextViewUtils.unStrike(nameItemtextView);
            TextViewUtils.unStrike(quantiteTextView);
        }

        checkBox.setOnClickListener(new ClickCheckboxItemListener(item,idItemtextView,nameItemtextView,quantiteTextView));
    }

    @Override
    public int getItemCount() {
        return liste.getItems().size();
    }
}




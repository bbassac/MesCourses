package lioncorps.org.mescourses.adapters;


import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import lioncorps.org.mescourses.MainActivity;
import lioncorps.org.mescourses.R;
import lioncorps.org.mescourses.TextViewUtils;
import lioncorps.org.mescourses.bean.Item;
import lioncorps.org.mescourses.bean.Liste;
import lioncorps.org.mescourses.listeners.ClickCheckboxItemListener;
import lioncorps.org.mescourses.listeners.ClickListListener;
import lioncorps.org.mescourses.services.WebServiceProvider;
import lioncorps.org.mescourses.viewholders.ItemViewHolder;

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
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = liste.getItems().get(position);
        final TextView idItemtextView = holder.getIdItemtextView();
        final TextView nameItemtextView = holder.getNameItemtextView();
        final TextView quantiteTextView = holder.getQuantiteTextView();
        final CheckBox checkBox = holder.getCheckBox();

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

    public void removeItem(SwipeRefreshLayout coordinatorLayout, int adapterPosition, final Item deletedItem, final int deletedIndex) {

            final Item item = liste.getItems().get(adapterPosition);

            liste.getItems().remove(adapterPosition);
            notifyItemRemoved(adapterPosition);
            // showing snack bar with Undo option
            Snackbar snackbar = Snackbar.make(coordinatorLayout, item.getNom() + " removed from cart!", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // undo is selected, restore the deleted item
                    restoreItem(deletedItem, deletedIndex);
                }
            });
            snackbar.addCallback(new Snackbar.Callback() {
                @Override
                public void onDismissed(Snackbar snackbar, int event) {
                    WebServiceProvider.getInstance().deleteItem(liste.getId(),item.getId());

                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();


            displayActivity.loadItems(liste.getId());

    }

    private void restoreItem(Item item, int position) {
        liste.getItems().add(position,item);
        notifyItemInserted(position);
        displayActivity.loadItems(liste.getId());
    }
}




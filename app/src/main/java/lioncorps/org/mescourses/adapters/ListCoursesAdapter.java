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
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import lioncorps.org.mescourses.MainActivity;
import lioncorps.org.mescourses.R;
import lioncorps.org.mescourses.bean.Collection;
import lioncorps.org.mescourses.bean.Liste;
import lioncorps.org.mescourses.listeners.ClickListListener;
import lioncorps.org.mescourses.listeners.OnLongClickImageListener;
import lioncorps.org.mescourses.services.WebServiceProvider;
import lioncorps.org.mescourses.viewholders.ListeViewHolder;

public class ListCoursesAdapter extends RecyclerView.Adapter<ListeViewHolder> {

    private Collection collection = null; //GET FROM JSON
    private MainActivity displayActivity;

    public ListCoursesAdapter(Collection collection, MainActivity displayActivity) {
        super();
        this.collection = collection;
        this.displayActivity = displayActivity;

    }
    @NonNull
    @Override
    public ListeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View view = inflater.inflate(R.layout.display_lists, parent, false);
        // Return a new holder instance
        return new ListeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListeViewHolder holder, int position) {
        Liste liste = collection.getListes().get(position);

        TextView nomtextView = holder.getNomtextView();
        nomtextView.setText(liste.getNom());
        nomtextView.setClickable(true);
        nomtextView.setOnClickListener(new ClickListListener(displayActivity, liste));

        ImageView imgView = holder.getImgView();
        if (liste.getTemplate()) {

            Picasso.get().load(R.mipmap.locked).into(imgView);
        }
        else {
            Picasso.get().load(R.mipmap.unlocked).into(imgView);
        }
        imgView.setOnLongClickListener(new OnLongClickImageListener(displayActivity, liste.getId(),liste.getNom(), liste.getTemplate()));
    }

    @Override
    public int getItemCount() {
        return collection.getListes().size();
    }


    public void removeList(final SwipeRefreshLayout coordinatorLayout, int adapterPosition, final Liste deletedItem, final int deletedIndex) {
        if (!collection.getListes().get(adapterPosition).getTemplate()) {
            Liste item = collection.getListes().get(adapterPosition);
            WebServiceProvider.getInstance().deleteListe(item.getId());
            collection.getListes().remove(adapterPosition);
            notifyItemRemoved(adapterPosition);
            // showing snack bar with Undo option
            Snackbar snackbar = Snackbar.make(coordinatorLayout, item.getNom() + " removed from cart!", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // undo is selected, restore the deleted item
                    restoreList(deletedItem, deletedIndex);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
        displayActivity.loadListes();
    }

    private void restoreList(Liste item, int position) {
        collection.getListes().add(position,item);
        notifyItemInserted(position);
        displayActivity.loadListes();
    }
}

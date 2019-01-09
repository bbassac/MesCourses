package lioncorps.org.mescourses.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import lioncorps.org.mescourses.R;

// Provide a direct reference to each of the views within a data item
// Used to cache the views within the item layout for fast access
public class ListeViewHolder extends RecyclerView.ViewHolder {
    // Your holder should contain a member variable
    // for any view that will be set as you render a row
    TextView nomtextView;
    TextView idtextView;
    ImageView imgView;

    // We also create a constructor that accepts the entire item row
    // and does the view lookups to find each subview
    public ListeViewHolder(View itemView) {
        // Stores the itemView in a public final member variable that can be used
        // to access the context from any ViewHolder instance.
        super(itemView);

        nomtextView = itemView.findViewById(R.id.nom);
        idtextView = itemView.findViewById(R.id.listId);
        imgView = itemView.findViewById(R.id.lockedImg);
    }
}
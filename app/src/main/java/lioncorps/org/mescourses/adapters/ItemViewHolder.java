package lioncorps.org.mescourses.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import lioncorps.org.mescourses.R;

// Provide a direct reference to each of the views within a data item
// Used to cache the views within the item layout for fast access
public class ItemViewHolder extends RecyclerView.ViewHolder {
    // Your holder should contain a member variable
    // for any view that will be set as you render a row
    TextView idItemtextView;
    TextView nameItemtextView;
    TextView quantiteTextView;
    CheckBox checkBox;
    // We also create a constructor that accepts the entire item row
    // and does the view lookups to find each subview
    public ItemViewHolder(View itemView) {
        // Stores the itemView in a public final member variable that can be used
        // to access the context from any ViewHolder instance.
        super(itemView);
        idItemtextView = itemView.findViewById(R.id.itemId);
         nameItemtextView = itemView.findViewById(R.id.nomItem);
         quantiteTextView = itemView.findViewById(R.id.quantiteItem);
         checkBox = itemView.findViewById(R.id.doneItem);
    }
}
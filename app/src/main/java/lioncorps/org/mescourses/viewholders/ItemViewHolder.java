package lioncorps.org.mescourses.viewholders;

import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import lioncorps.org.mescourses.MainActivity;
import lioncorps.org.mescourses.R;

// Provide a direct reference to each of the views within a data item
// Used to cache the views within the item layout for fast access
public class ItemViewHolder extends BaseViewHolder {
    // Your holder should contain a member variable
    // for any view that will be set as you render a row
    private TextView idItemtextView;
    private TextView nameItemtextView;
    private TextView quantiteTextView;
    private CheckBox checkBox;
    private RelativeLayout viewForegroundItem;


    public TextView getNameItemtextView() {
        return nameItemtextView;
    }

    public TextView getQuantiteTextView() {
        return quantiteTextView;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public RelativeLayout getViewForegroundItem() {
        return viewForegroundItem;
    }
    public TextView getIdItemtextView() {
        return idItemtextView;
    }
    // We also create a constructor that accepts the entire item row

    // and does the view lookups to find each subview
    public ItemViewHolder(View itemView) {
        // Stores the itemView in a public final member variable that can be used
        // to access the context from any ViewHolder instance.
        super(itemView,MainActivity.DisplayMode.DISPLAY_MODE_ITEM);
        idItemtextView = itemView.findViewById(R.id.itemId);
         nameItemtextView = itemView.findViewById(R.id.nomItem);
         quantiteTextView = itemView.findViewById(R.id.quantiteItem);
         checkBox = itemView.findViewById(R.id.doneItem);
        viewForegroundItem = itemView.findViewById(R.id.view_foregroundItem);
    }
}
package lioncorps.org.mescourses.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import lioncorps.org.mescourses.MainActivity;
import lioncorps.org.mescourses.R;

// Provide a direct reference to each of the views within a data item
// Used to cache the views within the item layout for fast access
public class ListeViewHolder extends BaseViewHolder{
    // Your holder should contain a member variable
    // for any view that will be set as you render a row
    private TextView nomtextView;
    private TextView idtextView;
    private ImageView imgView;

    public TextView getNomtextView() {
        return nomtextView;
    }

    public ImageView getImgView() {
        return imgView;
    }

    public RelativeLayout getViewForegroundList() {
        return viewForegroundList;
    }

    private RelativeLayout viewForegroundList;

    // We also create a constructor that accepts the entire item row
    // and does the view lookups to find each subview
    public ListeViewHolder(View itemView) {
        // Stores the itemView in a public final member variable that can be used
        // to access the context from any ViewHolder instance.
        super(itemView,MainActivity.DisplayMode.DISPLAY_MODE_LIST);

        nomtextView = itemView.findViewById(R.id.nom);
        idtextView = itemView.findViewById(R.id.listId);
        imgView = itemView.findViewById(R.id.lockedImg);
        viewForegroundList = itemView.findViewById(R.id.view_foregroundList);
    }
}
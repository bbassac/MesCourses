package lioncorps.org.mescourses.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import lioncorps.org.mescourses.MainActivity;

abstract public class BaseViewHolder extends RecyclerView.ViewHolder {

    private MainActivity.DisplayMode displayListsMode;

    public BaseViewHolder(View itemView, MainActivity.DisplayMode displayListsMode) {

        super(itemView);
        this.displayListsMode = displayListsMode;
    }


    public MainActivity.DisplayMode getDisplayListsMode() {
        return displayListsMode;
    }

    public void setDisplayListsMode(MainActivity.DisplayMode displayListsMode) {
        this.displayListsMode = displayListsMode;
    }
}

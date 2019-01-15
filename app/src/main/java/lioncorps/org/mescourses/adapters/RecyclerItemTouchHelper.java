package lioncorps.org.mescourses.adapters;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import lioncorps.org.mescourses.MainActivity;
import lioncorps.org.mescourses.viewholders.BaseViewHolder;
import lioncorps.org.mescourses.viewholders.ItemViewHolder;
import lioncorps.org.mescourses.viewholders.ListeViewHolder;

/**
 * Created by ravi on 29/09/17.
 */

public class RecyclerItemTouchHelper extends ItemTouchHelper.SimpleCallback {
    private RecyclerItemTouchHelperListener listener;

    public RecyclerItemTouchHelper(int dragDirs, int swipeDirs, RecyclerItemTouchHelperListener listener) {
        super(dragDirs, swipeDirs);
        this.listener = listener;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return true;
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (viewHolder != null) {
            final BaseViewHolder baseHolder = (BaseViewHolder)viewHolder;
            View foregroundView = getForegroundView(viewHolder,baseHolder);
            getDefaultUIUtil().onSelected(foregroundView);
        }
    }

    private View getForegroundView(RecyclerView.ViewHolder viewHolder,BaseViewHolder baseHolder ){
        View foregroundView = null;
        if(baseHolder.getDisplayListsMode().equals(MainActivity.DisplayMode.DISPLAY_MODE_LIST)) {
            foregroundView = ((ListeViewHolder) viewHolder).getViewForegroundList();
        }else if (baseHolder.getDisplayListsMode().equals(MainActivity.DisplayMode.DISPLAY_MODE_ITEM)){
            foregroundView = ((ItemViewHolder) viewHolder).getViewForegroundItem();
        }
        return foregroundView;
    }

    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView,
                                RecyclerView.ViewHolder viewHolder, float dX, float dY,
                                int actionState, boolean isCurrentlyActive) {
        final BaseViewHolder baseHolder = (BaseViewHolder)viewHolder;
        View foregroundView = getForegroundView(viewHolder,baseHolder);
        getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        final BaseViewHolder baseHolder = (BaseViewHolder)viewHolder;
        View foregroundView = getForegroundView(viewHolder,baseHolder);
        getDefaultUIUtil().clearView(foregroundView);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        final BaseViewHolder baseHolder = (BaseViewHolder)viewHolder;
        View foregroundView = getForegroundView(viewHolder,baseHolder);
        getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        listener.onSwiped(viewHolder, direction, viewHolder.getAdapterPosition());
    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    public interface RecyclerItemTouchHelperListener {
        void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);
    }
}
package lioncorps.org.mescourses.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
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
        ListeViewHolder viewHolder = new ListeViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListeViewHolder holder, int position) {
        Liste liste = collection.getListes().get(position);

        TextView nomtextView = holder.nomtextView;
        nomtextView.setText(liste.getNom());
        nomtextView.setClickable(true);
        nomtextView.setOnClickListener(new ClickListListener(displayActivity, liste));

        ImageView imgView = holder.imgView;
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


}

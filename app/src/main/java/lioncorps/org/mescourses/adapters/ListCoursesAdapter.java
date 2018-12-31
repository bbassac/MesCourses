package lioncorps.org.mescourses.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import lioncorps.org.mescourses.MainActivity;
import lioncorps.org.mescourses.R;
import lioncorps.org.mescourses.bean.Collection;
import lioncorps.org.mescourses.bean.Liste;

public class ListCoursesAdapter extends BaseAdapter  {
    Collection collection = null; //GET FROM JSON
    MainActivity displayActivity;
    private Context context;

    public ListCoursesAdapter(Context exContext, Collection collection, MainActivity displayActivity) {
        super();
        context = exContext;
        this.collection = collection;
        this.displayActivity = displayActivity;

    }

    @Override
    public int getCount() {
        return collection.getListes().size();
    }

    @Override
    public Liste getItem(int arg0) {
        return collection.getListes().get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return collection.getListes().get(arg0).getId();
    }


    @Override
    public View getView(int index, View view, ViewGroup viewGroup) {

        Liste liste = collection.getListes().get(index);
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.display_lists, null);
        }

        TextView nomtextView = view.findViewById(R.id.nom);
        nomtextView.setText(liste.getNom());
        nomtextView.setClickable(true);
        nomtextView.setOnClickListener(new ClickListListener(context, displayActivity, liste));

        ImageView imgView = view.findViewById(R.id.lockedImg);
        if (liste.getTemplate()) {

            Picasso.get().load(R.mipmap.locked).into(imgView);
        }
        else {
            Picasso.get().load(R.mipmap.unlocked).into(imgView);
        }

        return view;
    }


}




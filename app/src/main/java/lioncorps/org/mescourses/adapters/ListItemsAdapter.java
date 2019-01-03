package lioncorps.org.mescourses.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import lioncorps.org.mescourses.MainActivity;
import lioncorps.org.mescourses.R;
import lioncorps.org.mescourses.TextViewUtils;
import lioncorps.org.mescourses.bean.Item;
import lioncorps.org.mescourses.bean.Liste;

public class ListItemsAdapter extends BaseAdapter  {
    Liste liste = null; //GET FROM JSON
    MainActivity displayActivity;
    private Context context;

    public ListItemsAdapter(Context exContext, Liste liste, MainActivity displayActivity) {
        super();
        context = exContext;
        this.liste = liste;
        this.displayActivity = displayActivity;

    }

    @Override
    public int getCount() {
        return liste.getItems().size();
    }

    @Override
    public Item getItem(int arg0) {
        return liste.getItems().get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return liste.getItems().get(arg0).getId();
    }


    @Override
    public View getView(int index, View view, ViewGroup viewGroup) {

        Item item = this.liste.getItems().get(index);
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.display_items, null);
            view.setClickable(true);
            view.setOnClickListener(new ClickListListener(context, displayActivity, liste));
        }
        final TextView idItemtextView = view.findViewById(R.id.itemId);
        final TextView nameItemtextView = view.findViewById(R.id.nomItem);
        final TextView quantiteTextView = view.findViewById(R.id.quantiteItem);
        final CheckBox checkBox = view.findViewById(R.id.doneItem);

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

        checkBox.setOnClickListener(new ClickCheckboxItemListener(context,displayActivity,item,idItemtextView,nameItemtextView,quantiteTextView));
        return view;
    }


}




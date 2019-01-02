package lioncorps.org.mescourses.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import lioncorps.org.mescourses.MainActivity;
import lioncorps.org.mescourses.TextViewUtils;
import lioncorps.org.mescourses.bean.Item;
import lioncorps.org.mescourses.bean.Liste;

class ClickListItemListener implements View.OnClickListener {
    private Item currentItem;
    private MainActivity displayActivity;
    private Context context;
    TextView idItemtextView;
    TextView nameItemtextView;
    TextView quantiteTextView;


    public ClickListItemListener(Context context, MainActivity displayActivity, Item item, TextView idItemtextView, TextView nameItemtextView, TextView quantiteTextView) {
        this.displayActivity = displayActivity;
        this.context = context;
        this.currentItem = item;
        this.idItemtextView = idItemtextView;
        this.nameItemtextView=nameItemtextView;
        this.quantiteTextView = quantiteTextView;
    }

    @Override
    public void onClick(View view) {
        CheckBox checkBox = (CheckBox) view;
        if (checkBox.isChecked()){
            TextViewUtils.strike(nameItemtextView);
            TextViewUtils.strike(quantiteTextView);
        }else{
            TextViewUtils.unStrike(nameItemtextView);
            TextViewUtils.unStrike(quantiteTextView);
        }

    }
}
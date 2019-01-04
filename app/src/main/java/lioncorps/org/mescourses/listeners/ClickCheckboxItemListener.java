package lioncorps.org.mescourses.listeners;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import lioncorps.org.mescourses.TextViewUtils;
import lioncorps.org.mescourses.bean.Item;
import lioncorps.org.mescourses.services.WebServiceProvider;
public class ClickCheckboxItemListener implements View.OnClickListener {
    private Item currentItem;

    TextView idItemtextView;
    TextView nameItemtextView;
    TextView quantiteTextView;

    public ClickCheckboxItemListener(Item item, TextView idItemtextView, TextView nameItemtextView, TextView quantiteTextView) {
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
         WebServiceProvider.getInstance().updateItem(currentItem.getId(),nameItemtextView.getText().toString(),quantiteTextView.getText().toString(),checkBox.isChecked());
    }
}

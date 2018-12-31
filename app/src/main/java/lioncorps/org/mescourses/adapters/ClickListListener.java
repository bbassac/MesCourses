package lioncorps.org.mescourses.adapters;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import lioncorps.org.mescourses.MainActivity;
import lioncorps.org.mescourses.bean.Liste;

class ClickListListener implements View.OnClickListener {
    private Liste currentListe;
    private MainActivity displayActivity;
    private Context context;

    public ClickListListener(Context context, MainActivity displayActivity, Liste liste) {
        this.displayActivity = displayActivity;
        this.context = context;
        this.currentListe = liste;
    }

    @Override
    public void onClick(View view) {
        displayActivity.loadItems(currentListe.getId());
    }
}

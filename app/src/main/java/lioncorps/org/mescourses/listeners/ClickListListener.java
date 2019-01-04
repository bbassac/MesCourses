package lioncorps.org.mescourses.listeners;

import android.view.View;

import lioncorps.org.mescourses.MainActivity;
import lioncorps.org.mescourses.bean.Liste;
public class ClickListListener implements View.OnClickListener {
    private Liste currentListe;
    private MainActivity displayActivity;

    public ClickListListener(MainActivity displayActivity, Liste liste) {
        this.displayActivity = displayActivity;
        this.currentListe = liste;
    }

    @Override
    public void onClick(View view) {
        displayActivity.loadItems(currentListe.getId());
    }
}

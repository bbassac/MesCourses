package lioncorps.org.mescourses.listeners;

import android.view.View;

import lioncorps.org.mescourses.MainActivity;
import lioncorps.org.mescourses.services.WebServiceProvider;

public class OnLongClickImageListener implements View.OnLongClickListener {

    private Boolean isTemplate;
    private Long listId;
    private String nom;
    private MainActivity displayActivity;

    public OnLongClickImageListener(MainActivity displayActivity, Long listId,String nom, Boolean template) {
        this.listId = listId;
        this.isTemplate = template;
        this.nom = nom;
        this.displayActivity = displayActivity;
    }

    @Override
    public boolean onLongClick(View view) {

        WebServiceProvider.getInstance().updateListe(listId,nom,!isTemplate);
        displayActivity.loadListes();
        return true;
    }
}

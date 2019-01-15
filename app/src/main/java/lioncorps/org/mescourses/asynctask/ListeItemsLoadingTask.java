package lioncorps.org.mescourses.asynctask;

import android.os.AsyncTask;

import lioncorps.org.mescourses.MainActivity;
import lioncorps.org.mescourses.bean.Liste;
import lioncorps.org.mescourses.services.WebServiceProvider;

public class ListeItemsLoadingTask extends AsyncTask<String, String, Liste> {

    private final Long currentListId;
    private MainActivity activity;

    public ListeItemsLoadingTask(MainActivity activity, Long currentListId){
        this.activity = activity;
        this.currentListId=currentListId;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Liste doInBackground(String... args) {

        Liste currentList = WebServiceProvider.getInstance().loadListe(currentListId);
        activity.currentList = currentList;
        return currentList;
    }

    @Override
    protected void onPostExecute(Liste json) {
        activity.reloadListItemsView();
    }
}

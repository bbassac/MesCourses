package lioncorps.org.mescourses.asynctask;

import android.os.AsyncTask;

import lioncorps.org.mescourses.MainActivity;
import lioncorps.org.mescourses.bean.Collection;
import lioncorps.org.mescourses.services.WebServiceProvider;

public class ListeCoursesLoadingTask extends AsyncTask<String, String, Collection> {

    MainActivity activity;

    public ListeCoursesLoadingTask(MainActivity activity){
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Collection doInBackground(String... args) {
        Collection collection =  WebServiceProvider.getInstance().loadCollection();
        activity.collection = collection;
        return collection;
    }

    @Override
    protected void onPostExecute(Collection json) {
        activity.reloadListeCoursesView();
    }
}
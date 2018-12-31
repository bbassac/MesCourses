package lioncorps.org.mescourses;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.io.IOException;

import lioncorps.org.mescourses.adapters.ListCoursesAdapter;
import lioncorps.org.mescourses.bean.Collection;

public class MainActivity extends AppCompatActivity {
ServiceProvider serviceProvider = new ServiceProvider();
    Collection collection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new ListeCoursesLoading().execute();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    private class ListeCoursesLoading extends AsyncTask<String, String, Collection> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            }

        @Override
        protected Collection doInBackground(String... args) {

            try {
                collection = serviceProvider.loadCourses();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return collection;
        }

        @Override
        protected void onPostExecute(Collection json) {
            try {
                final ListCoursesAdapter adapter = new ListCoursesAdapter(getApplicationContext(), collection, MainActivity.this);
                ListView list = findViewById(R.id.list);
                list.setAdapter(adapter);
            } catch (Exception e) {

            }
        }
    }


}

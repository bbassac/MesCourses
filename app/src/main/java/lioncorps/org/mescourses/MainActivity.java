package lioncorps.org.mescourses;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;

import lioncorps.org.mescourses.bean.Collection;

public class MainActivity extends AppCompatActivity {
ServiceProvider serviceProvider = new ServiceProvider();
    Collection collection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new JSONParseTask().execute();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    private class JSONParseTask extends AsyncTask<String, String, Collection> {


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

package lioncorps.org.mescourses;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.io.IOException;

import lioncorps.org.mescourses.adapters.ListCoursesAdapter;
import lioncorps.org.mescourses.adapters.ListItemsAdapter;
import lioncorps.org.mescourses.bean.Collection;
import lioncorps.org.mescourses.bean.Liste;

public class MainActivity extends AppCompatActivity {
ServiceProvider serviceProvider = new ServiceProvider();
    Collection collection;
    Liste currentList;
    Long currentListId;
    Menu optionsMenu;
    boolean displayListsMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadListes();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        menu.findItem(R.id.menu_refresh).setVisible(false);
        optionsMenu=menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
               if(! displayListsMode){

                   loadListes();
               }
                return true;
            case R.id.menu_quit:
                MainActivity.this.finish();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public void loadListes(){
        new ListeCoursesLoadingTask().execute();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        displayListsMode =true;
        if (optionsMenu!=null && optionsMenu.findItem(R.id.menu_refresh) !=null) {
            optionsMenu.findItem(R.id.menu_refresh).setVisible(false);
        }
    }

    public void loadItems(Long id){
        currentListId=id;
        new ListeItemsLoadingTask().execute();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        displayListsMode =false;
        if (optionsMenu!=null && optionsMenu.findItem(R.id.menu_refresh) !=null) {
            optionsMenu.findItem(R.id.menu_refresh).setVisible(true);
        }
    }

    private class ListeCoursesLoadingTask extends AsyncTask<String, String, Collection> {


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

    private class ListeItemsLoadingTask extends AsyncTask<String, String, Liste> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Liste doInBackground(String... args) {

            try {
                currentList = serviceProvider.loadItems(currentListId);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return currentList;
        }

        @Override
        protected void onPostExecute(Liste json) {
            try {
                final ListItemsAdapter adapter = new ListItemsAdapter(getApplicationContext(), currentList, MainActivity.this);
                ListView list = findViewById(R.id.list);
                list.setAdapter(adapter);
            } catch (Exception e) {

            }
        }
    }


}

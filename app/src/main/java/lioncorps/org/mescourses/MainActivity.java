package lioncorps.org.mescourses;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import lioncorps.org.mescourses.adapters.ListCoursesAdapter;
import lioncorps.org.mescourses.adapters.ListItemsAdapter;
import lioncorps.org.mescourses.bean.Collection;
import lioncorps.org.mescourses.bean.Liste;
import lioncorps.org.mescourses.services.WebServiceProvider;

public class MainActivity extends AppCompatActivity {
    private static final String TITLE_APP = "B&Y";

    Collection collection;
    Liste currentList;
    Long currentListId;
    Menu optionsMenu;
    DisplayMode displayListsMode;
    private static final int REQ_CODE_SPEECH_INPUT = 100;
    private enum DisplayMode{
        DISPLAY_MODE_LIST,
        DISPLAY_MODE_ITEM
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadListes();
        manageMicButton();
        manageRefreshSweep();
    }

    private void manageRefreshSweep() {
        final SwipeRefreshLayout  swipeRefreshLayout = findViewById(R.id.swipeid);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(displayListsMode.equals(DisplayMode.DISPLAY_MODE_ITEM)){
                    loadItems(currentListId);
                }else{
                    loadListes();
                }
                swipeRefreshLayout.setRefreshing(false);

            }});
    }

    private void manageMicButton() {
        FloatingActionButton fab = findViewById(R.id.micRecord);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hello, How can I help you?");
                    try {
                        startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
                    } catch (ActivityNotFoundException a) {


                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    if (displayListsMode.equals(DisplayMode.DISPLAY_MODE_LIST)){
                        Toast.makeText(MainActivity.this,"saving " + result.get(0),Toast.LENGTH_LONG).show();
                        collection = WebServiceProvider.getInstance().addListe(result.get(0));
                        reloadListeCoursesView();
                    }else if (displayListsMode.equals(DisplayMode.DISPLAY_MODE_ITEM)) {
                        currentList = WebServiceProvider.getInstance().addItemToListe(currentListId, result.get(0), "");
                        reloadListItemsView();
                    }
                }
                break;
            }

        }
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        menu.findItem(R.id.menu_back).setVisible(false);
        optionsMenu=menu;
        displayTitle(TITLE_APP);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_back:
               if(displayListsMode.equals(DisplayMode.DISPLAY_MODE_ITEM)){

                   loadListes();
               }
                return true;

            case R.id.menu_refresh:
                if(displayListsMode.equals(DisplayMode.DISPLAY_MODE_ITEM)){
                    loadItems(currentListId);
                }else{
                    loadListes();
                }

        }
        return super.onOptionsItemSelected(item);
    }

    public void loadListes(){
        new ListeCoursesLoadingTask().execute();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        displayListsMode =DisplayMode.DISPLAY_MODE_LIST;
        if (optionsMenu!=null && optionsMenu.findItem(R.id.menu_back) !=null) {
            optionsMenu.findItem(R.id.menu_back).setVisible(false);
        }
    }

    public void loadItems(Long id){

        currentListId=id;
        new ListeItemsLoadingTask().execute();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        displayListsMode =DisplayMode.DISPLAY_MODE_ITEM;
        if (optionsMenu!=null && optionsMenu.findItem(R.id.menu_back) !=null) {
            optionsMenu.findItem(R.id.menu_back).setVisible(true);
        }
    }

    private class ListeCoursesLoadingTask extends AsyncTask<String, String, Collection> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            }

        @Override
        protected Collection doInBackground(String... args) {
            collection =  WebServiceProvider.getInstance().loadCollection();
            return collection;
        }

        @Override
        protected void onPostExecute(Collection json) {
            reloadListeCoursesView();
        }
    }



    private class ListeItemsLoadingTask extends AsyncTask<String, String, Liste> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Liste doInBackground(String... args) {

            currentList = WebServiceProvider.getInstance().loadListe(currentListId);
            return currentList;
        }

        @Override
        protected void onPostExecute(Liste json) {
                reloadListItemsView();
        }
    }

    private void reloadListItemsView() {
        final ListItemsAdapter adapter = new ListItemsAdapter(getApplicationContext(), currentList, MainActivity.this);
        ListView list = findViewById(R.id.list);
        list.setAdapter(adapter);
        displayTitle(TITLE_APP +" : " + currentList.getNom());
    }
    private void reloadListeCoursesView() {
        final ListCoursesAdapter adapter = new ListCoursesAdapter(getApplicationContext(), collection, MainActivity.this);
        ListView list = findViewById(R.id.list);
        list.setAdapter(adapter);
        displayTitle(TITLE_APP);
    }

    private void displayTitle(String title){
        setTitle(title);
    }

}

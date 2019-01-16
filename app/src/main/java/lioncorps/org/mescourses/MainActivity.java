package lioncorps.org.mescourses;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import lioncorps.org.mescourses.adapters.ListCoursesAdapter;
import lioncorps.org.mescourses.adapters.ListItemsAdapter;
import lioncorps.org.mescourses.asynctask.ListeCoursesLoadingTask;
import lioncorps.org.mescourses.asynctask.ListeItemsLoadingTask;
import lioncorps.org.mescourses.viewholders.ListeViewHolder;
import lioncorps.org.mescourses.adapters.RecyclerItemTouchHelper;
import lioncorps.org.mescourses.bean.Collection;
import lioncorps.org.mescourses.bean.Item;
import lioncorps.org.mescourses.bean.Liste;
import lioncorps.org.mescourses.services.WebServiceProvider;

public class MainActivity extends AppCompatActivity implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener{
    private static final String TITLE_APP = "B&Y";
    private static final int REQ_CODE_SPEECH_INPUT = 100;
    public DisplayMode displayListsMode;
    public Collection collection;
    public Liste currentList;
    private Long currentListId;
    private Menu optionsMenu;
    private ListCoursesAdapter coursesAdapter;
    private ListItemsAdapter itemsAdapter;
    private SwipeRefreshLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadListes();
        manageMicButton();
        manageRefreshSweep();

        coordinatorLayout = findViewById(R.id.swipeid);
    }

    private void manageRefreshSweep() {
        final SwipeRefreshLayout  swipeRefreshLayout = findViewById(R.id.swipeid);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(isDisplayModeItem()){
                    loadItems(currentListId);
                }else{
                    loadListes();
                }
                swipeRefreshLayout.setRefreshing(false);

            }});
    }

    private boolean isDisplayModeItem() {
        return displayListsMode.equals(DisplayMode.DISPLAY_MODE_ITEM);
    }

    private boolean isDisplayModeList() {
        return displayListsMode.equals(DisplayMode.DISPLAY_MODE_LIST);
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
                    if (isDisplayModeList()){
                        Toast.makeText(MainActivity.this,"saving " + result.get(0),Toast.LENGTH_LONG).show();
                        collection = WebServiceProvider.getInstance().addListe(result.get(0));
                        reloadListeCoursesView();

                    }else if (isDisplayModeItem()) {
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
        setTitle(TITLE_APP);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_back:
               if(isDisplayModeItem()){
                   loadListes();
               }
                return true;

            case R.id.menu_refresh:
                if(isDisplayModeItem()){
                    loadItems(currentListId);
                }else{
                    loadListes();
                }

        }
        return super.onOptionsItemSelected(item);
    }

    public void loadListes(){
        new ListeCoursesLoadingTask(MainActivity.this).execute();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        displayListsMode =DisplayMode.DISPLAY_MODE_LIST;
        if (optionsMenu!=null && optionsMenu.findItem(R.id.menu_back) !=null) {
            optionsMenu.findItem(R.id.menu_back).setVisible(false);
        }
    }

    public void loadItems(Long id){

        currentListId=id;
        new ListeItemsLoadingTask(MainActivity.this,currentListId).execute();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        displayListsMode =DisplayMode.DISPLAY_MODE_ITEM;
        if (optionsMenu!=null && optionsMenu.findItem(R.id.menu_back) !=null) {
            optionsMenu.findItem(R.id.menu_back).setVisible(true);
        }
    }

    public void reloadListItemsView() {
        itemsAdapter = new ListItemsAdapter( currentList, MainActivity.this);
        buildView(itemsAdapter);
        setTitle(TITLE_APP +" : " + currentList.getNom());
    }

    public void reloadListeCoursesView() {
        coursesAdapter = new ListCoursesAdapter(collection, MainActivity.this);
        buildView(coursesAdapter);
        setTitle(TITLE_APP);
    }

    private void buildView(RecyclerView.Adapter adapter) {
        RecyclerView list = findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setItemAnimator(new DefaultItemAnimator());
        list.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.RIGHT, this);

        // attaching the touch helper to recycler view
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(list);
    }



    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {

        if (viewHolder instanceof ListeViewHolder) {
            if (isDisplayModeList()) {
                // backup of removed item for undo purpose
                final Liste deletedItem = collection.getListes().get(viewHolder.getAdapterPosition());
                final int deletedIndex = viewHolder.getAdapterPosition();

                // remove the item from recycler view
                coursesAdapter.removeList(coordinatorLayout, viewHolder.getAdapterPosition(), deletedItem, deletedIndex);
            }
        }else if (isDisplayModeItem()){
            final Item deletedItem = currentList.getItems().get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
            itemsAdapter.removeItem(coordinatorLayout, viewHolder.getAdapterPosition(), deletedItem, deletedIndex);
        }

    }

    public enum DisplayMode{
        DISPLAY_MODE_LIST,
        DISPLAY_MODE_ITEM
    }




}

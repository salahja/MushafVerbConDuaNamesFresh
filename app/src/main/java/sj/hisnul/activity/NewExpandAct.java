package sj.hisnul.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.mushafconsolidated.Activity.BaseActivity;
import com.example.mushafconsolidated.R;
import com.example.mushafconsolidated.Utils;
import com.example.utility.QuranGrammarApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import sj.hisnul.entity.hduanames;
import sj.hisnul.fragments.HDuaNamesfrag;

public class NewExpandAct extends BaseActivity implements SearchView.OnQueryTextListener, SearchView.OnCloseListener {
    ArrayList<String> dataheader;
    //  ExpandableListView expandableListView;
    private int previousGroup = -1;
    private HashMap<String, ParentItem> subjects;
    private ArrayList<ParentItem> parentItemsList;

    private ExpandableListView expandableListView;
    private ArrayList<ChildItem> childItemsList;
    private ArrayList<hduanames> hduanamesArrayList = new ArrayList<>();
    private CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expand_act);
        //     setContentView(R.layout.activity_dua_group);
        Toolbar toolbar = findViewById(R.id.my_action_bar);
        // setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(QuranGrammarApplication.getContext());
        //  actionbar.setDisplayHomeAsUpEnabled(true);
        //  actionbar.setDisplayShowHomeEnabled(true);
        //  setSupportActionBar(toolbar);
        assert actionbar != null;
        actionbar.setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        //   String list=   bundle.getString("list");
        int dua_id = bundle.getInt("dua_id");
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        //  expandableListDetail = ExpandableListDataPump.getData();
        expandableListView = findViewById(R.id.expandableListView);
        //  iv_groupIndicator = findViewById(R.id.iv_groupIndicator);
        Utils utils = new Utils(this);
        //  hduanamesArrayList = utils.getDunamesbyCatId(String.valueOf(dua_id));
        hduanamesArrayList = utils.getDuaCATNAMES(String.valueOf(dua_id));
        subjects = new HashMap<>();
        parentItemsList = new ArrayList<>();
        childItemsList = new ArrayList<>();
        loadDatas();
        displayList();
        dataheader = new ArrayList<>();
        for (hduanames duanamesDetail : hduanamesArrayList) {
            dataheader.add(duanamesDetail.getDuaname());

        }

    }

    private void loadDatas() {
        for (hduanames hduanames : hduanamesArrayList) {
            addItem(hduanames.getChapname(), hduanames.getDuaname(), hduanames.getID(), hduanames.getChap_id());

        }
        System.out.println("check");

    }

    @SuppressWarnings("UnusedAssignment")
    private void addItem(String parentItemName, String childItemName, String id, int chapterid) {
        int size = parentItemsList.size();
        int groupPosition;
        if (parentItemName.isEmpty()) {
            parentItemName = parentItemsList.get(size - 1).getName();
        }
        //check the hash map if the group already exists
        ParentItem parentItemObj = subjects.get(parentItemName);
        //add the group if doesn't exists
        if (parentItemObj == null) {
            parentItemObj = new ParentItem();
            parentItemObj.setName(parentItemName);
            parentItemObj.setChapterid(chapterid);
            subjects.put(parentItemName, parentItemObj);
            parentItemsList.add(parentItemObj);

        }
        //get the children for the group
        childItemsList = parentItemObj.getChildList();
        //size of the children list
        int listSize = childItemsList.size();
        //add to the counter
        listSize++;
        //create a new child and add that to the group
        ChildItem childItemObj = new ChildItem();
        childItemObj.setName(childItemName);
        childItemObj.setID(id);
        childItemsList.add(childItemObj);
        parentItemObj.setChildList(childItemsList);
        //find the group position inside the list
        groupPosition = parentItemsList.indexOf(parentItemObj);
    }

    private void displayList() {
        //  loadData();
        customAdapter = new CustomAdapter(this, parentItemsList);
        expandableListView.setAdapter(customAdapter);
        expandableListView.setOnGroupClickListener((parent, v, groupPosition, id) -> {
            if (expandableListView.isGroupExpanded(groupPosition)) {
                expandableListView.collapseGroup(groupPosition);
                previousGroup = -1;
            } else {
                expandableListView.expandGroup(groupPosition);
                if (previousGroup != -1) {
                    expandableListView.collapseGroup(previousGroup);
                }
                previousGroup = groupPosition;
            }
            return true;
        });
        expandableListView.setOnGroupExpandListener(groupPosition -> {
            /* Auto Scrolling */
            expandableListView.setSelectedGroup(groupPosition);
        });
        expandableListView.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
            int chap_id = parentItemsList.get(groupPosition).getChapterid();
            Objects.requireNonNull(getSupportActionBar()).hide();
            Bundle bundle1 = new Bundle();
            bundle1.putInt("chap_id", chap_id);
            bundle1.putBoolean("cattwo", false);
            Fragment fragvsi = HDuaNamesfrag.newInstance();
            fragvsi.setArguments(bundle1);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.replace(R.id.frame_container, fragvsi, "items");
            //     transaction.addToBackStack("setting");
            transaction.addToBackStack("items");
            transaction.commit();
            return false;
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(getSupportActionBar()).show();
    }


  /*
    @Override
        public void onBackPressed() {
            super.onBackPressed();

            getSupportActionBar().show();

        }

   */

    @Override
    public void onBackPressed() {

    }

    @SuppressWarnings("UnusedAssignment")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo
                (searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);
        searchView.requestFocus();
        return true;

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
/*
        if (id == R.id.backup) {
            int count = getSupportFragmentManager().getBackStackEntryCount();

            if (count == 0) {
                super.onBackPressed();
                //additional code
            } else {
                getSupportFragmentManager().popBackStack();
            }

        }

 */
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onClose() {
        customAdapter.filterData("");
        //  expandAll();
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        customAdapter.filterData(query);
        expandAll();
        return false;
    }

    private void expandAll() {
        int count = customAdapter.getGroupCount();
        for (int i = 0; i < count; i++) {
            expandableListView.expandGroup(i);
        }
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        customAdapter.filterData(newText);
        expandAll();
        return false;
    }

}

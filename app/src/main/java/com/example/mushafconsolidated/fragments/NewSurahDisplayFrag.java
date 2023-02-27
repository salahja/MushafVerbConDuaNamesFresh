package com.example.mushafconsolidated.fragments;

import static android.content.Context.MODE_PRIVATE;
import static com.example.Constant.AYAHNUMBER;
import static com.example.Constant.AYAH_ID;
import static com.example.Constant.CHAPTER;
import static com.example.mushafconsolidated.R.drawable.custom_search_box;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mushafconsolidated.Activity.QuranGrammarAct;
import com.example.mushafconsolidated.Activity.ShowMushafActivity;
import com.example.mushafconsolidated.Adapters.NewSurahDisplayAdapter;
import com.example.mushafconsolidated.Entities.ChaptersAnaEntity;
import com.example.mushafconsolidated.R;
import com.example.mushafconsolidated.Utils;
import com.example.mushafconsolidated.intrface.OnItemClickListener;
import com.example.mushafconsolidated.intrface.PassdataInterface;
import com.example.mushafconsolidated.settings.Constants;
import com.example.utility.QuranGrammarApplication;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import org.sj.conjugator.activity.ConjugatorAct;

import java.util.ArrayList;
import java.util.List;

import database.GridImageAct;
import sj.hisnul.activity.HisnulBottomACT;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewSurahDisplayFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewSurahDisplayFrag extends Fragment implements  SearchView.OnQueryTextListener  {
    //   implements FragmentCommunicator {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    //   private static final String ARG_PARAM1 = "param1";
    //   private static final String ARG_PARAM2 = "param2";
    private RecyclerView parentRecyclerView;
    //   private RecyclerView.Adapter ParentAdapter;
    private NewSurahDisplayAdapter ParentAdapter;
    //  SurahDisplayAdapter ParentAdapter;
    private OnItemClickListener mItemClickListener;
    private final boolean isfragmentshowing = true;
    private ImageView drop;
    private TextView devIndicatorView;
    FloatingActionButton btnBottomSheet;
    private PassdataInterface passdataInterface;
    private PassdataInterface datapasser;
    private int lastreadchapterno, lastreadverseno;
    private ArrayList<ChaptersAnaEntity> allAnaChapters;

    private SearchView.OnQueryTextListener queryTextListener;
    private List<ChaptersAnaEntity> chapterfilered;
    private  TextView searchint;
    private NavigationBarView bottomNavigationView;

    public NewSurahDisplayFrag(PassdataInterface passdataInterface) {
        this.setPassdataInterface(passdataInterface);
    }

    public NewSurahDisplayFrag() {
        // Required empty public constructor
    }

    public static NewSurahDisplayFrag newInstance() {
        NewSurahDisplayFrag fragment = new NewSurahDisplayFrag();
        Bundle args = new Bundle();
        //    args.putString(ARG_PARAM1, param1);
        //    args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /*


     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        setDatapasser((PassdataInterface) context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            // TODO: Rename and change types of parameters
            // String mParam1 = getArguments().getString(ARG_PARAM1);
            //     String mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private void setToolbarMenu() {
        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onPrepareMenu(@NonNull Menu menu) {
                SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
                MenuItem searchItem = menu.findItem(R.id.search);
                SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
                if (searchItem != null) {
                    searchView = (SearchView) searchItem.getActionView();
                    Drawable sear = ContextCompat.getDrawable(getActivity(), custom_search_box);
                    searchView.setClipToOutline(true);
                    searchView.setBackgroundDrawable(sear);
                    searchView.setGravity(View.TEXT_ALIGNMENT_CENTER);
                    searchView.setMaxWidth(Integer.MAX_VALUE);




                }
                if (searchView != null) {
                    searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
                    queryTextListener = new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextChange(String newText) {
                            //   Log.i("onQueryTextChange", newText);
                            ParentAdapter.  getFilter().filter(newText);
                            return true;
                        }

                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            //    Log.i("onQueryTextSubmit", query);
                            ParentAdapter.  getFilter().filter(query);
                            return false;
                        }
                    };
                    searchView.setOnQueryTextListener(queryTextListener);
                }

                MenuProvider.super.onPrepareMenu(menu);


            }

            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                     menuInflater.inflate(R.menu.menu_search,menu);
                SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
                MenuItem searchItem = menu.findItem(R.id.search);
                SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
                if (searchItem != null) {
                    searchView = (SearchView) searchItem.getActionView();
                    Drawable sear = ContextCompat.getDrawable(getActivity(), custom_search_box);
                    searchView.setClipToOutline(true);
                    searchView.setBackgroundDrawable(sear);
                    searchView.setGravity(View.TEXT_ALIGNMENT_CENTER);
                    searchView.setMaxWidth(Integer.MAX_VALUE);




                }
                if (searchView != null) {
                    searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
                    queryTextListener = new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextChange(String newText) {
                            //   Log.i("onQueryTextChange", newText);
                            ParentAdapter.getFilter().filter(newText);
                            return true;
                        }

                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            //    Log.i("onQueryTextSubmit", query);
                            ParentAdapter.   getFilter().filter(query);
                            return false;
                        }
                    };
                    searchView.setOnQueryTextListener(queryTextListener);
                }
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.getItemId()==R.id.search){
                    searchint.setVisibility(View.VISIBLE);

                }
                return false;
            }

            @Override
            public void onMenuClosed(@NonNull Menu menu) {
                MenuProvider.super.onMenuClosed(menu);
            }
        },getViewLifecycleOwner(), Lifecycle.State.RESUMED); {

        }
    }

    private void setToolbarFragment() {
        Toolbar materialToolbar = ((QuranGrammarAct) requireActivity()).findViewById(R.id.toolbarmain);
        ((QuranGrammarAct) requireActivity()).   setSupportActionBar(materialToolbar)
     ;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        boolean removing = isRemoving();
        Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag("group");
        if (fragment != null) {
            getActivity().getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        boolean removing = isRemoving();
        Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag("group");
        if (fragment != null) {
            getActivity().getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        container.removeAllViews();

        //     View view = inflater.inflate(R.layout.list_surah_juz, container, false);
        View view = inflater.inflate(R.layout.list_surah_juz, container, false);
        initnavagation(view);
       searchint=  view.findViewById(R.id.searchint);
        setToolbarFragment();
        setToolbarMenu();
        Utils utils = new Utils(getContext());
      allAnaChapters = utils.getAllAnaChapters();
        chapterfilered=allAnaChapters;
        TypedArray imgs = getContext().getResources().obtainTypedArray(R.array.sura_imgs);
        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        // parentRecyclerView = view.findViewById(R.id.juzRecyclerView);
        parentRecyclerView = view.findViewById(R.id.wordByWordRecyclerView);
        MaterialButton lastread = view.findViewById(R.id.lastread);
        TextView kahaf = view.findViewById(R.id.kahaf);
        TextView ayakursi = view.findViewById(R.id.ayatkursi);
        SharedPreferences pref = getContext().getSharedPreferences("lastread", MODE_PRIVATE);
        lastreadchapterno = pref.getInt(CHAPTER, 1);
        lastreadverseno = pref.getInt(AYAH_ID, 1);
        lastread.setText("Last read" + ":" + "Surah:" + lastreadchapterno + " " + "Ayah:" + lastreadverseno);
        kahaf.setText(R.string.linkkahaf);
        lastread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
                Intent intent = new Intent(QuranGrammarApplication.getContext(), QuranGrammarAct.class);
                //  Intent intent = new Intent(DarkThemeApplication.getContext(), ReadingSurahPartActivity.class);
                intent.putExtra("chapter", lastreadchapterno);
                intent.putExtra("chapterorpart", true);
                intent.putExtra("partname", allAnaChapters.get(lastreadchapterno - 1).getAbjadname());
                intent.putExtra(AYAH_ID, lastreadverseno);
                intent.putExtra(AYAHNUMBER, lastreadverseno);
                startActivity(intent);

            }
        });
        kahaf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuranGrammarApplication.getContext(), QuranGrammarAct.class);
                //  Intent intent = new Intent(DarkThemeApplication.getContext(), ReadingSurahPartActivity.class);
                intent.putExtra("chapter", 18);
                intent.putExtra("chapterorpart", true);
                intent.putExtra("partname", allAnaChapters.get(18).getAbjadname());
                intent.putExtra("verseno", 1);
                intent.putExtra(AYAH_ID, 1);
                startActivity(intent);

            }
        });
        ayakursi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuranGrammarApplication.getContext(), QuranGrammarAct.class);
                //  Intent intent = new Intent(DarkThemeApplication.getContext(), ReadingSurahPartActivity.class);
                intent.putExtra("chapter", 2);
                intent.putExtra("chapterorpart", true);
                intent.putExtra("partname", allAnaChapters.get(2).getAbjadname());
                intent.putExtra("verseno", 255);
                intent.putExtra(AYAH_ID, 255);
                startActivity(intent);

            }
        });
        parentRecyclerView.setLayoutManager(mLayoutManager);
        parentRecyclerView.setHasFixedSize(true);
        parentRecyclerView.setLayoutManager(mLayoutManager);
        ParentAdapter = new NewSurahDisplayAdapter(getContext(), allAnaChapters);
        ParentAdapter.setUp(allAnaChapters);
        parentRecyclerView.setAdapter(ParentAdapter);
        return view;
    }

    private void initnavagation(View view) {
        bottomNavigationView =view. findViewById(R.id.bottomNavView);
        btnBottomSheet = view.findViewById(R.id.fab);

        btnBottomSheet.setOnClickListener(v -> {
            toggleBottomSheets();
            //  toggleHideSeek();
        });
        bottomNavigationView.setOnItemReselectedListener(new NavigationBarView.OnItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {



                switch (item.getItemId()) {
                    case R.id.surahnav:

                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.setCustomAnimations(R.anim.slide_down, R.anim.slide_up);
                        NewSurahDisplayFrag newCustomFragment = NewSurahDisplayFrag.newInstance();
                        transaction.replace(R.id.frame_container, newCustomFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();


                        break;
                    case R.id.conjugationnav:



                        Intent conjugatorintent = new Intent(getActivity(), ConjugatorAct.class);
                        startActivity(conjugatorintent);
                        break;
                    case R.id.dua:


                        Intent searchintent = new Intent(getActivity(), HisnulBottomACT.class);
                        startActivity(searchintent);







                        break;
                    case R.id.names:



                        Intent settingint = new Intent(getActivity(), GridImageAct.class);
                      //  settingint.putExtra(Constants.SURAH_INDEX, getChapterno());
                        startActivity(settingint);

                        break;
                    case R.id.mushafview:
                      //  materialToolbar.setTitle("Mushaf");
                        //   Intent searchs = new Intent(QuranGrammarAct.this, MainTwoActivityPrayer.class);

                        //  startActivity(searchs);

                        Intent settingints = new Intent(getActivity(), ShowMushafActivity.class);
                        //      settingints.putExtra(Constants.SURAH_INDEX, getChapterno());
                        startActivity(settingints);






                        break;
                    default:
                        break;
                }

            }
        });
    }

    public void toggleBottomSheets() {
        if (bottomNavigationView.getVisibility() == View.VISIBLE) {
            bottomNavigationView.setVisibility(View.GONE);
            //    btnBottomSheet.setText("Close sheet");
        } else {
            bottomNavigationView.setVisibility(View.VISIBLE);
            //    btnBottomSheet.setText("Expand sheet");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ParentAdapter.SetOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                ChaptersAnaEntity item = (ChaptersAnaEntity) ParentAdapter.getItem(position);
                item.getChapterid();
                //    Intent intent = new Intent(DarkThemeApplication.getContext(), ReadingSurahPartActivity.class);
                //   intent.putExtra("chapter", item.getChapterid());
                //  intent.putExtra("chapterorpart",true);
                //  intent.putExtra(  "partname",item.getAbjadname());
                //  startActivity(intent);
                passData(item.getChapterid(), item.getAbjadname(), item.getVersescount(), item.getRukucount(), item.getIsmakki());

            }

            private void passData(int chapterid, String abjadname, int versescount, int rukucount, int ismakki) {
                getDatapasser().ondatarecevied(chapterid, abjadname, versescount, rukucount, ismakki);

            }
        });

/*

  private void passData(int chapter_no,String partname,int versescount,int rukucount) {
    getDatapasser().ondatarecevied(chapter_no,true,partname);
  }

 */
    }

    public OnItemClickListener getmItemClickListener() {
        return mItemClickListener;
    }

    public void setmItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public PassdataInterface getPassdataInterface() {
        return passdataInterface;
    }

    public void setPassdataInterface(PassdataInterface passdataInterface) {
        this.passdataInterface = passdataInterface;
    }

    public PassdataInterface getDatapasser() {
        return datapasser;
    }

    public void setDatapasser(PassdataInterface datapasser) {
        this.datapasser = datapasser;
    }



    @Override
    public boolean onQueryTextSubmit(String query) {
        ParentAdapter. getFilter().filter(query);
        //  Utils.LogDebug("Submitted: "+query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        //    Utils.LogDebug("Changed: "+newText);
        return false;
    }
}
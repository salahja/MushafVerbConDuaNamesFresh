package com.example.mushafconsolidated.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mushafconsolidated.Entities.BookMarks;
import com.example.mushafconsolidated.Entities.BookMarksPojo;
import com.example.mushafconsolidated.R;
import com.example.mushafconsolidated.Utils;
import com.example.mushafconsolidated.intrface.OnItemClickListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import com.example.mushafconsolidated.Adapters.BookmarkCreateAdapter;

/**
 * <p>A fragment that shows a list of items as a modal bottom sheet.</p>
 * <p>You can show this modal bottom sheet from your activity like this:</p>
 * <pre>
 *     ThemeListPrefrence.newInstance(30).show(getSupportFragmentManager(), "dialog");
 * </pre>
 */
public class BookMarkCreateFrag extends BottomSheetDialogFragment implements OnItemClickListener, View.OnClickListener {
    private RecyclerView recyclerView;
    private String suraname;
    private int chapter, verse;
    private int selectedposition;
    MaterialButton addtocollection, newcollection;

    public BookMarkCreateFrag() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    // TODO: Customize parameter argument names
    private static final String ARG_OPTIONS_DATA = "item_count";
    RadioGroup radioGroup;
    List<BookMarksPojo> collectionC = new ArrayList<>();
    ArrayList<BookMarks> bookMarks = new ArrayList<>();

    // TODO: Customize parameters
    public static BookMarkCreateFrag newInstance(String[] data) {
        final BookMarkCreateFrag fragment = new BookMarkCreateFrag();
        final Bundle args = new Bundle();
        args.putStringArray(ARG_OPTIONS_DATA, data);
        fragment.setArguments(args);
        return fragment;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        Bundle bundle = this.getArguments();
        assert bundle != null;
        String[] stringArray = bundle.getStringArray(ARG_OPTIONS_DATA);
        chapter = Integer.parseInt(stringArray[0]);
        verse = Integer.parseInt(stringArray[1]);
        suraname = (stringArray[2]);

        return inflater.inflate(R.layout.bookmark_create, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ArrayList<String> details = new ArrayList<>();
        Utils utils = new Utils(getActivity());

        collectionC = utils.getCollectionC();
        BookmarkCreateAdapter bookmarkCreateAdapter = new BookmarkCreateAdapter(collectionC);
        newcollection = view.findViewById(R.id.newcollection);
        addtocollection = view.findViewById(R.id.addtocollection);
        newcollection.setOnClickListener(this);
        ;
        addtocollection.setOnClickListener(this);
        newcollection.setOnClickListener(v -> {
            AlertDialog.Builder dialogPicker = new AlertDialog.Builder(getActivity());

            LayoutInflater inflater = getActivity().getLayoutInflater();
            View view1 = inflater.inflate(R.layout.folder_bookmark, null);
            //  View view = inflater.inflate(R.layout.activity_wheel, null);
            dialogPicker.setView(view1);
            EditText mTextView = view1.findViewById(R.id.tvFolderName);
            dialogPicker.setPositiveButton("Done", (dialogInterface, i) -> {
                String str = String.valueOf(mTextView.getText());
                bookMarkSelected(v,selectedposition, str);

            });
            dialogPicker.setNegativeButton("Cancel", (dialogInterface, i) -> {
            });
            dialogPicker.show();
        });

        addtocollection.setOnClickListener(v -> {
            BookMarksPojo bookMarksPojo = collectionC.get(selectedposition);
            bookMarkSelected(v, Integer.parseInt(bookMarksPojo.getChapterno()), bookMarksPojo.getHeader());

        });

        recyclerView.setAdapter(bookmarkCreateAdapter);
        bookmarkCreateAdapter.SetOnItemClickListener(new BookmarkCreateAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

            }
        });




        bookmarkCreateAdapter.SetOnItemClickListener((v, position) -> {
            RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder) recyclerView.findViewHolderForAdapterPosition(position);
            if (holder != null) {

                CheckBox ck = holder.itemView.findViewById(R.id.checkbox);
                if (ck != null) {
                    selectedposition = position;
                }
                System.out.println("check");
            }

            Object tag = v.getTag();
            if (tag.equals("newcollection")) {
                AlertDialog.Builder dialogPicker = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getActivity().getLayoutInflater();
                View view12 = inflater.inflate(R.layout.folder_bookmark, null);
                //  View view = inflater.inflate(R.layout.activity_wheel, null);
                dialogPicker.setView(view12);
                EditText mTextView = view12.findViewById(R.id.tvFolderName);
                dialogPicker.setPositiveButton("Done", (dialogInterface, i) -> {
                    String str = String.valueOf(mTextView.getText());
                    bookMarkSelected(v, position, str);

                });
                dialogPicker.setNegativeButton("Cancel", (dialogInterface, i) -> {
                });
                dialogPicker.show();


            } else if (tag.equals("addcollection")) {
                --selectedposition;
                BookMarksPojo book = collectionC.get(selectedposition);
                RecyclerView.ViewHolder holderS = (RecyclerView.ViewHolder) recyclerView.findViewHolderForAdapterPosition(position - 2);
                if (holder != null) {
                    System.out.println("check");
                }
                bookMarkSelected(v, selectedposition, book.getHeader());
                Toast.makeText(getActivity(), "create collections", Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void bookMarkSelected(View view, int position, String text) {
        //  position = flowAyahWordAdapter.getAdapterposition();

        BookMarks en = new BookMarks();
        en.setHeader(text);
        en.setChapterno(String.valueOf(chapter));
        en.setVerseno(String.valueOf(verse));
        en.setSurahname(suraname);
        //     Utils utils = new Utils(ReadingSurahPartActivity.this);
        Utils utils=new Utils(getContext());
        utils.insertBookMark(en);
        //     View view = findViewById(R.id.bookmark);
        Snackbar snackbar = Snackbar
                .make(view, "BookMark Created", Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.BLUE);
        snackbar.setTextColor(Color.CYAN);
        snackbar.setBackgroundTint(Color.BLACK);
        snackbar.show();
    }

    @Override
    public void onItemClick(View v, int position) {

    }

    @Override
    public void onClick(View v) {

    }
}
package com.example.roots;

import static com.example.Constant.WORDDETAILS;

import android.content.ClipData;
import android.content.ClipDescription;
import android.os.Build;
import android.os.Bundle;

import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mushafconsolidated.R;

 ;
import com.example.mushafconsolidated.databinding.ArabicrootListContentBinding;
import com.example.mushafconsolidated.databinding.FragmentArabicrootListBinding;
import com.example.roots.placeholder.PlaceholderContent;

import java.util.List;

/**
 * A fragment representing a list of arabicrootitems. This fragment
 * has different presentations for handset and larger screen devices. On
 * handsets, the fragment presents a list of items, which when touched,
 * lead to a {@link arabicrootDetailFragment} representing
 * item details. On larger screens, the Navigation controller presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class arabicrootListFragment extends Fragment {

    public String iswordorverb;

    /**
     * Method to intercept global key events in the
     * item list fragment to trigger keyboard shortcuts
     * Currently provides a toast when Ctrl + Z and Ctrl + F
     * are triggered
     */
    ViewCompat.OnUnhandledKeyEventListenerCompat unhandledKeyEventListenerCompat = (v, event) -> {
        if (event.getKeyCode() == KeyEvent.KEYCODE_Z && event.isCtrlPressed()) {
            Toast.makeText(
                    v.getContext(),
                    "Undo (Ctrl + Z) shortcut triggered",
                    Toast.LENGTH_LONG
            ).show();
            return true;
        } else if (event.getKeyCode() == KeyEvent.KEYCODE_F && event.isCtrlPressed()) {
            Toast.makeText(
                    v.getContext(),
                    "Find (Ctrl + F) shortcut triggered",
                    Toast.LENGTH_LONG
            ).show();
            return true;
        }
        return false;
    };
    public static arabicrootListFragment newInstance(String wordorverb) {
        arabicrootListFragment fragment = new arabicrootListFragment();

        final Bundle args = new Bundle();
        args.putString(WORDDETAILS, wordorverb);
        fragment.setArguments(args);

        return fragment;

    }

    private FragmentArabicrootListBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentArabicrootListBinding.inflate(inflater, container, false);
        Bundle bundle = this.getArguments();
      iswordorverb=  bundle.getString(WORDDETAILS);


        return binding.getRoot();

    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewCompat.addOnUnhandledKeyEventListener(view, unhandledKeyEventListenerCompat);

        RecyclerView recyclerView = binding.arabicrootList;

        // Leaving this not using view binding as it relies on if the view is visible the current
        // layout configuration (layout, layout-sw600dp)
        View itemDetailFragmentContainer = view.findViewById(R.id.arabicroot_detail_nav_container);

        getArguments().getString(WORDDETAILS);

        setupRecyclerView(recyclerView, itemDetailFragmentContainer);
    }

    private void setupRecyclerView(
            RecyclerView recyclerView,
            View itemDetailFragmentContainer
    ) {

        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(
                PlaceholderContent.ITEMS,
                itemDetailFragmentContainer,iswordorverb
        ));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<PlaceholderContent.PlaceholderItem> mValues;
        private final View mItemDetailFragmentContainer;
        private final String iswordorverb;

        SimpleItemRecyclerViewAdapter(List<PlaceholderContent.PlaceholderItem> items,
                                      View itemDetailFragmentContainer, String iswordorverb) {
            mValues = items;
            mItemDetailFragmentContainer = itemDetailFragmentContainer;
            this.iswordorverb=iswordorverb;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            ArabicrootListContentBinding binding =
                    ArabicrootListContentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ViewHolder(binding);

        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
          //  holder.mIdView.setText(mValues.get(position).id);
         //   holder.mContentView.setText(mValues.get(position).content);
         //   StringBuilder sb=new StringBuilder();
         //   sb.append(mValues.get(position).id).append("-").append(mValues.get(position).content);
            holder.mIdView.setText(mValues.get(position).id);
            holder.mContentView.setText(mValues.get(position).content);
          //  holder.mIdView.setText(mValues.get(position).content);
            holder.itemView.setTag(mValues.get(position));

            holder.itemView.setOnClickListener(itemView -> {
                PlaceholderContent.PlaceholderItem item =
                        (PlaceholderContent.PlaceholderItem) itemView.getTag();
                Bundle arguments = new Bundle();
                arguments.putString(arabicrootDetailFragment.ARG_ITEM_ID, item.id);
                arguments.putString(WORDDETAILS,iswordorverb);
                if (mItemDetailFragmentContainer != null) {
                    Navigation.findNavController(mItemDetailFragmentContainer)
                            .navigate(R.id.fragment_arabicroot_detail, arguments);
                } else {
                    Navigation.findNavController(itemView).navigate(R.id.show_arabicroot_detail, arguments);
                }
            });
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                /*
                 * Context click listener to handle Right click events
                 * from mice and trackpad input to provide a more native
                 * experience on larger screen devices
                 */
                holder.itemView.setOnContextClickListener(v -> {
                    PlaceholderContent.PlaceholderItem item =
                            (PlaceholderContent.PlaceholderItem) holder.itemView.getTag();
                    Toast.makeText(
                            holder.itemView.getContext(),
                            "Context click of item " + item.id,
                            Toast.LENGTH_LONG
                    ).show();
                    return true;
                });
            }
            holder.itemView.setOnLongClickListener(v -> {
                // Setting the item id as the clip data so that the drop target is able to
                // identify the id of the content
                ClipData.Item clipItem = new ClipData.Item(mValues.get(position).id);
                ClipData dragData = new ClipData(
                        ((PlaceholderContent.PlaceholderItem) v.getTag()).content,
                        new String[]{ClipDescription.MIMETYPE_TEXT_PLAIN},
                        clipItem
                );

                if (Build.VERSION.SDK_INT >= 24) {
                    v.startDragAndDrop(
                            dragData,
                            new View.DragShadowBuilder(v),
                            null,
                            0
                    );
                } else {
                    v.startDrag(
                            dragData,
                            new View.DragShadowBuilder(v),
                            null,
                            0
                    );
                }
                return true;
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView;

            ViewHolder(ArabicrootListContentBinding binding) {
                super(binding.getRoot());

                mIdView = binding.idText;
                mContentView = binding.content;
            }

        }
    }
}
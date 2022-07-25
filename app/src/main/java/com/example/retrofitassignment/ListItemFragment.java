package com.example.retrofitassignment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retrofitassignment.network.MarsProperty;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ListItemFragment extends Fragment {

    private final String TAG = "ListMarsFragment";
    private static final String ARG_PARAM = "isRent";

    private boolean isRent;
    private RecyclerView recyclerView;
    private List<MarsProperty> marsList = new ArrayList<>();
    private Context context;
    private ListItemViewModel listMarsViewModel;
    private ItemsAdapter marsAdapter = new ItemsAdapter();
    private OpenScreen openScreen;
    interface OpenScreen{
        void openMarsDetailScreen(MarsProperty marsProperty);
    }

    public ListItemFragment() {
        // Required empty public constructor
    }

    public static ListItemFragment newInstance(boolean isRent) {
        ListItemFragment fragment = new ListItemFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_PARAM, isRent);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        openScreen = (OpenScreen) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isRent = getArguments().getBoolean(ARG_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_mars, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
        recyclerView.setAdapter(marsAdapter);
        listMarsViewModel = new ViewModelProvider(this).get(ListItemViewModel.class);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadData();
    }

    @Override
    public void onStart() {

        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void loadData() {
        if (isRent) {
            listMarsViewModel.rentMarsList.observe(getViewLifecycleOwner(), mars -> {
                if (mars != null) {
                    marsList = mars;
                    marsAdapter.notifyDataSetChanged();
                }
            });
        } else {
            listMarsViewModel.boughtMarsList.observe(getViewLifecycleOwner(), mars -> {
                if (mars != null) {
                    marsList = mars;
                    marsAdapter.notifyDataSetChanged();
                }
            });
        }
    }


    private class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView imageView;
        private MarsProperty marsProperty;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            imageView.setOnClickListener(this);
        }

        public void bind(MarsProperty marsProperty){
            this.marsProperty = marsProperty;
            String photoURL = marsProperty.getImgSrcUrl();
            Log.i(TAG, photoURL);
            int placeHolder = R.drawable.loading_animation;
            Picasso.get()
                    .load(photoURL)
                    .placeholder(placeHolder)
                    .error(R.drawable.ic_broken_image)
                    .into(imageView);
        }

        @Override
        public void onClick(View v) {
            Log.i(TAG, "item is clicked");
            openScreen.openMarsDetailScreen(marsProperty);
        }
    }

    private class ItemsAdapter extends RecyclerView.Adapter<ItemHolder> {

        @NonNull
        @Override
        public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View view = layoutInflater.inflate(R.layout.mar_item_holder, viewGroup, false);
            return new ItemHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemHolder marsHolder, int i) {
            MarsProperty marsProperty = marsList.get(i);
            marsHolder.bind(marsProperty);
        }

        @Override
        public int getItemCount() {
            return marsList.size();
        }
    }
}
package com.example.retrofitassignment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.retrofitassignment.network.MarsProperty;
import com.squareup.picasso.Picasso;

public class ItemDetailFragment extends Fragment {

    private static final String ARG_PARAM1 = "param01";

    private MarsProperty marsProperty;
    private ImageView imageView;
    private TextView forSaleTV;
    private TextView priceTV;

    public ItemDetailFragment() {
        // Required empty public constructor
    }
    public static ItemDetailFragment newInstance(MarsProperty param1) {
        ItemDetailFragment fragment = new ItemDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            marsProperty = (MarsProperty) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_item_detail, container, false);
        imageView = view.findViewById(R.id.mars_image);
        forSaleTV = view.findViewById(R.id.for_sale_tv);
        priceTV = view.findViewById(R.id.price_tv);
        String photoURL = marsProperty.getImgSrcUrl();
        Picasso.get()
                .load(photoURL)
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image)
                .into(imageView);
        String forSale = "For "+ (("buy".equals(marsProperty.getType())?"Sale":"Rent"));
        forSaleTV.setText(forSale);
        priceTV.setText(marsProperty.getPrice()+"");
        return view;
    }

}
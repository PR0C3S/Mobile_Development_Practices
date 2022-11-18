package com.pucmm.ecommerceapp_proyectofinal.ui.detailOrder;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pucmm.ecommerceapp_proyectofinal.R;

public class DetailOrderFragment extends Fragment {

    private DetailOrderViewModel mViewModel;

    public static DetailOrderFragment newInstance() {
        return new DetailOrderFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_order, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DetailOrderViewModel.class);
        // TODO: Use the ViewModel
    }

    public void Add(View view)
    {

    }

    public void Remove(View view)
    {

    }

    public void BuyNow(View view)
    {

    }

    public void AddToCart(View view)
    {

    }

}
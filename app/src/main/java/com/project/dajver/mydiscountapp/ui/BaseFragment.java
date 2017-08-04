package com.project.dajver.mydiscountapp.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by gleb on 8/4/17.
 */

public abstract class BaseFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(getItemId(), container, false);
        ButterKnife.bind(this, rootView);
        setHasOptionsMenu(true);
        onCreateView();
        return rootView;
    }

    public void setupRecyclerView(RecyclerView recyclerView, int numberOfColumns) {
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
    }

    public abstract int getItemId();
    public abstract void onCreateView();
}

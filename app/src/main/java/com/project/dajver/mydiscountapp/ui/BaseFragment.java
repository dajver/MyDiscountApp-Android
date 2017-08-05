package com.project.dajver.mydiscountapp.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.project.dajver.mydiscountapp.etc.TransitionHelper;

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
        onCreateView(savedInstanceState);
        if(isBackButtonActive()) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }
        return rootView;
    }

    public void setupRecyclerView(RecyclerView recyclerView, int numberOfColumns) {
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                TransitionHelper.finish(getContext());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public abstract int getItemId();
    public abstract void onCreateView(Bundle savedInstanceState);
    public abstract boolean isBackButtonActive();
}

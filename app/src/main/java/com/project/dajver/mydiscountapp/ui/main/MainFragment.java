package com.project.dajver.mydiscountapp.ui.main;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.project.dajver.mydiscountapp.R;
import com.project.dajver.mydiscountapp.db.DiscountController;
import com.project.dajver.mydiscountapp.etc.TransitionHelper;
import com.project.dajver.mydiscountapp.ui.BaseFragment;
import com.project.dajver.mydiscountapp.ui.main.adapter.MyDiscountRecyclerAdapter;

import butterknife.BindView;

import static com.project.dajver.mydiscountapp.etc.Constants.ON_REFRESH_TIME_OUT;

/**
 * Created by gleb on 8/4/17.
 */

public class MainFragment extends BaseFragment implements MyDiscountRecyclerAdapter.ItemClickListener,
        SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    private MyDiscountRecyclerAdapter myDiscountRecyclerAdapter;
    private DiscountController discountController;
    private boolean isFirstStart = true;

    @Override
    public int getItemId() {
        return R.layout.fragment_main;
    }

    @Override
    public void onCreateView(Bundle savedInstanceState) {
        discountController = new DiscountController(getContext());
        setupRecyclerView(recyclerView, 2);
        swipeRefreshLayout.setOnRefreshListener(this);

        setupAdapter();
    }

    private void setupAdapter() {
        myDiscountRecyclerAdapter = new MyDiscountRecyclerAdapter(getContext(), discountController.getDiscounts());
        myDiscountRecyclerAdapter.setClickListener(this);
        recyclerView.setAdapter(myDiscountRecyclerAdapter);
    }

    @Override
    public boolean isBackButtonActive() {
        return false;
    }

    @Override
    public void onItemClick(int id) {
        TransitionHelper.setDetailsIntent(getContext(), id, false);
    }

    @Override
    public void onLongItemClick(final int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getString(R.string.alert_dialog_title))
                .setMessage(getString(R.string.alert_dialog_description))
                .setCancelable(false)
                .setNegativeButton(getString(R.string.alert_dialog_no), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                .setPositiveButton(getString(R.string.alert_dialog_yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        new DiscountController(getContext()).removeItemById(id);
                        onRefresh();
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_add_discount, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            TransitionHelper.setAddIntent(getContext());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onResume() {
        super.onResume();
        if(!isFirstStart)
            onRefresh();
        isFirstStart = false;
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
                setupAdapter();
            }
        }, ON_REFRESH_TIME_OUT);
    }
}

package com.project.dajver.mydiscountapp.ui.main;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.project.dajver.mydiscountapp.R;
import com.project.dajver.mydiscountapp.db.DiscountController;
import com.project.dajver.mydiscountapp.db.model.DiscountModel;
import com.project.dajver.mydiscountapp.etc.TransitionHelper;
import com.project.dajver.mydiscountapp.ui.BaseFragment;
import com.project.dajver.mydiscountapp.ui.main.adapter.MyDiscountRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.project.dajver.mydiscountapp.etc.Constants.ON_REFRESH_TIME_OUT;

/**
 * Created by gleb on 8/4/17.
 */

public class MainFragment extends BaseFragment implements MyDiscountRecyclerAdapter.ItemClickListener,
        SwipeRefreshLayout.OnRefreshListener, SearchView.OnQueryTextListener, MultiplePermissionsListener {

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

        setupAdapter(discountController.getDiscounts());

        List<String> permissionsList = new ArrayList<>();
        permissionsList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissionsList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        permissionsList.add(Manifest.permission.WRITE_SETTINGS);
        permissionsList.add(Manifest.permission.CAMERA);

        Dexter.withActivity(context)
                .withPermissions(permissionsList)
                .withListener(this)
                .check();
    }

    private void setupAdapter(ArrayList<DiscountModel> discountModels) {
        if(isAdded()) {
            myDiscountRecyclerAdapter = new MyDiscountRecyclerAdapter(getContext(), discountModels);
            myDiscountRecyclerAdapter.setClickListener(this);
            recyclerView.setAdapter(myDiscountRecyclerAdapter);
        }
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
                .setNegativeButton(getString(R.string.alert_dialog_no), (dialog, id1) -> dialog.cancel())
                .setPositiveButton(getString(R.string.alert_dialog_yes), (dialogInterface, i) -> {
                    new DiscountController(getContext()).removeItemById(id);
                    onRefresh();
                    dialogInterface.cancel();
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_add_discount, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
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
        swipeRefreshLayout.postDelayed(() -> {
            swipeRefreshLayout.setRefreshing(false);
            setupAdapter(discountController.getDiscounts());
        }, ON_REFRESH_TIME_OUT);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if(newText.length() > 0)
            setupAdapter(discountController.getDiscountByName(newText));
        else
            setupAdapter(discountController.getDiscounts());
        return false;
    }

    @Override
    public void onPermissionsChecked(MultiplePermissionsReport report) { }

    @Override
    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) { }
}

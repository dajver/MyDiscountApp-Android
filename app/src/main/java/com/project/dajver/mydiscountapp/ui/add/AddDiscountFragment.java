package com.project.dajver.mydiscountapp.ui.add;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.project.dajver.mydiscountapp.R;
import com.project.dajver.mydiscountapp.db.DiscountController;
import com.project.dajver.mydiscountapp.etc.TransitionHelper;
import com.project.dajver.mydiscountapp.etc.parser.ParseJSONHelper;
import com.project.dajver.mydiscountapp.etc.parser.model.DataDetailsModel;
import com.project.dajver.mydiscountapp.ui.BaseFragment;
import com.project.dajver.mydiscountapp.ui.add.adapter.DiscountCardsRecyclerAdapter;

import butterknife.BindView;

import static android.app.Activity.RESULT_CANCELED;

/**
 * Created by gleb on 8/4/17.
 */

public class AddDiscountFragment extends BaseFragment implements DiscountCardsRecyclerAdapter.ItemClickListener,
        SearchView.OnQueryTextListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private DiscountCardsRecyclerAdapter discountCardsRecyclerAdapter;
    private DataDetailsModel dataDetailsModel;

    @Override
    public int getItemId() {
        return R.layout.fragment_add_discount;
    }

    @Override
    public void onCreateView(Bundle savedInstanceState) {
        setupRecyclerView(recyclerView, 1);
        discountCardsRecyclerAdapter = new DiscountCardsRecyclerAdapter(getContext(), ParseJSONHelper.getData(getContext()));
        discountCardsRecyclerAdapter.setClickListener(this);
        recyclerView.setAdapter(discountCardsRecyclerAdapter);
    }

    @Override
    public boolean isBackButtonActive() {
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if(resultCode != RESULT_CANCELED) {
            IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
            if (scanningResult != null) {
                DiscountController discountController = new DiscountController(getContext());
                discountController.addDiscount(dataDetailsModel.getName(), scanningResult.getContents(), dataDetailsModel.getImage());
                TransitionHelper.setDetailsIntent(getContext(), discountController.getCurrentId(), true);
            }
        }
    }

    @Override
    public void onItemClick(DataDetailsModel dataDetailsModel) {
        this.dataDetailsModel = dataDetailsModel;
        IntentIntegrator.forSupportFragment(this).initiateScan();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_add_discount_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }
    @Override
    public boolean onQueryTextChange(String s) {
        discountCardsRecyclerAdapter.getFilter().filter(s.toString());
        return false;
    }
}

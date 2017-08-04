package com.project.dajver.mydiscountapp.ui.main;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.project.dajver.mydiscountapp.R;
import com.project.dajver.mydiscountapp.db.DiscountController;
import com.project.dajver.mydiscountapp.db.model.DiscountModel;
import com.project.dajver.mydiscountapp.etc.Constants;
import com.project.dajver.mydiscountapp.ui.BaseFragment;
import com.project.dajver.mydiscountapp.ui.add.AddDiscountActivity;
import com.project.dajver.mydiscountapp.ui.details.DiscountDetailsActivity;
import com.project.dajver.mydiscountapp.ui.main.adapter.MyDiscountRecyclerAdapter;

import butterknife.BindView;

/**
 * Created by gleb on 8/4/17.
 */

public class MainFragment extends BaseFragment implements MyDiscountRecyclerAdapter.ItemClickListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private MyDiscountRecyclerAdapter myDiscountRecyclerAdapter;
    private DiscountController discountController;

    @Override
    public int getItemId() {
        return R.layout.fragment_main;
    }

    @Override
    public void onCreateView() {
        discountController = new DiscountController(getContext());
        setupRecyclerView(recyclerView, 2);
        myDiscountRecyclerAdapter = new MyDiscountRecyclerAdapter(getContext(), discountController.getDiscounts());
        myDiscountRecyclerAdapter.setClickListener(this);
        recyclerView.setAdapter(myDiscountRecyclerAdapter);
    }

    @Override
    public void onItemClick(DiscountModel discountModel) {
        Intent intent = new Intent(getContext(), DiscountDetailsActivity.class);
        intent.putExtra(Constants.INTENT_DISCOUNT_MODEL, discountModel);
        startActivity(intent);
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
            startActivity(new Intent(getContext(), AddDiscountActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

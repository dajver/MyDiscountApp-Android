package com.project.dajver.mydiscountapp.ui.add;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.project.dajver.mydiscountapp.R;
import com.project.dajver.mydiscountapp.etc.parser.ParseJSONHelper;
import com.project.dajver.mydiscountapp.etc.parser.model.DataDetailsModel;
import com.project.dajver.mydiscountapp.ui.BaseFragment;
import com.project.dajver.mydiscountapp.ui.add.adapter.DiscountCardsRecyclerAdapter;

import butterknife.BindView;

import static android.app.Activity.RESULT_CANCELED;

/**
 * Created by gleb on 8/4/17.
 */

public class AddDiscountFragment extends BaseFragment implements DiscountCardsRecyclerAdapter.ItemClickListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private DiscountCardsRecyclerAdapter discountCardsRecyclerAdapter;

    @Override
    public int getItemId() {
        return R.layout.fragment_add_discount;
    }

    @Override
    public void onCreateView() {
        setupRecyclerView(recyclerView, 1);
        discountCardsRecyclerAdapter = new DiscountCardsRecyclerAdapter(getContext(), ParseJSONHelper.getData(getContext()));
        discountCardsRecyclerAdapter.setClickListener(this);
        recyclerView.setAdapter(discountCardsRecyclerAdapter);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if(resultCode != RESULT_CANCELED) {
            IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
            if (scanningResult != null) {
//                CardsListDialog dialog = new CardsListDialog(this, this);
//                dialog.setCode(scanningResult.getContents());
//                dialog.show();
            }
        }
    }

    @Override
    public void onItemClick(DataDetailsModel discountModel) {
        new IntentIntegrator(getActivity()).initiateScan();
    }
}

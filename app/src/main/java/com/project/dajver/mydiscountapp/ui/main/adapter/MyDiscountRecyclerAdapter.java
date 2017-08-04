package com.project.dajver.mydiscountapp.ui.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.dajver.mydiscountapp.R;
import com.project.dajver.mydiscountapp.db.model.DiscountModel;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;

/**
 * Created by gleb on 8/4/17.
 */

public class MyDiscountRecyclerAdapter extends RecyclerView.Adapter<MyDiscountRecyclerAdapter.ViewHolder> {

    private RealmResults<DiscountModel> discountModels = null;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;

    public MyDiscountRecyclerAdapter(Context context, RealmResults<DiscountModel> discountModels) {
        this.mInflater = LayoutInflater.from(context);
        this.discountModels = discountModels;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_discount, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(getItem(position).getName());
        Picasso.with(context).load(getItem(position).getImage()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return discountModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.image)
        ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(discountModels.get(getAdapterPosition()));
        }
    }

    public DiscountModel getItem(int id) {
        return discountModels.get(id);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(DiscountModel discountModel);
    }
}
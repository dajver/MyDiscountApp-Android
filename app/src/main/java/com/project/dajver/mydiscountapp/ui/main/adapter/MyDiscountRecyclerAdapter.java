package com.project.dajver.mydiscountapp.ui.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.project.dajver.mydiscountapp.R;
import com.project.dajver.mydiscountapp.db.model.DiscountModel;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gleb on 8/4/17.
 */

public class MyDiscountRecyclerAdapter extends RecyclerView.Adapter<MyDiscountRecyclerAdapter.ViewHolder> {

    private ArrayList<DiscountModel> discountModels =  new ArrayList<>();
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;

    public MyDiscountRecyclerAdapter(Context context, ArrayList<DiscountModel> discountModels) {
        this.mInflater = LayoutInflater.from(context);
        this.discountModels = discountModels;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_my_discount, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(!TextUtils.isEmpty(getItem(position).getImage())) {
            if (getItem(position).getImage().startsWith("http"))
                Picasso.with(context)
                        .load(getItem(position).getImage())
                        .into(holder.image);
            else
                Picasso.with(context)
                        .load(new File(getItem(position).getImage()))
                        .into(holder.image);
        } else {
            holder.image.setImageResource(R.mipmap.placeholder);
        }
    }

    @Override
    public int getItemCount() {
        return discountModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        @BindView(R.id.image)
        ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(discountModels.get(getAdapterPosition()).getId());
        }

        @Override
        public boolean onLongClick(View view) {
            if (mClickListener != null) mClickListener.onLongItemClick(discountModels.get(getAdapterPosition()).getId());
            return false;
        }
    }

    public DiscountModel getItem(int id) {
        return discountModels.get(id);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(int id);
        void onLongItemClick(int id);
    }
}
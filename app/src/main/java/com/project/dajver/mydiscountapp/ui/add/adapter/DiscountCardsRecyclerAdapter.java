package com.project.dajver.mydiscountapp.ui.add.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.dajver.mydiscountapp.R;
import com.project.dajver.mydiscountapp.etc.parser.model.DataDetailsModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.project.dajver.mydiscountapp.etc.Constants.ITEM_TYPE_HEADER;
import static com.project.dajver.mydiscountapp.etc.Constants.ITEM_TYPE_NORMAL;

/**
 * Created by gleb on 8/4/17.
 */

public class DiscountCardsRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements Filterable {

    private List<DataDetailsModel> dataModels = new ArrayList<>();
    private List<DataDetailsModel> dataSearchModels = new ArrayList<>();
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;
    private Filter filter;

    public DiscountCardsRecyclerAdapter(Context context, List<DataDetailsModel> dataModels) {
        this.mInflater = LayoutInflater.from(context);
        this.dataModels = dataModels;
        this.dataSearchModels = dataModels;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ITEM_TYPE_HEADER;
        } else {
            return ITEM_TYPE_NORMAL;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_NORMAL) {
            View view = mInflater.inflate(R.layout.item_discount, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        } else {
            View view = mInflater.inflate(R.layout.item_discount_add_custom, parent, false);
            ViewHeaderHolder viewHolder = new ViewHeaderHolder(view);
            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final int itemType = getItemViewType(position);
        if (itemType == ITEM_TYPE_NORMAL) {
            ((ViewHolder) viewHolder).name.setText(getItem(position).getName());
            Picasso.with(context).load(getItem(position).getImage()).into(((ViewHolder) viewHolder).image);
        }
    }

    @Override
    public int getItemCount() {
        return dataModels.size();
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
            if (mClickListener != null) mClickListener.onItemClick(dataModels.get(getAdapterPosition()));
        }
    }

    public class ViewHeaderHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ViewHeaderHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mClickListener.onAddCustomClick();
        }
    }

    public DataDetailsModel getItem(int id) {
        return dataModels.get(id);
    }

    @Override
    public Filter getFilter() {
        if(filter == null)
            filter = new DiscountsFilter();
        return filter;
    }

    private class DiscountsFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint.length() == 0) {
                results.values = dataSearchModels;
                results.count = dataSearchModels.size();
            } else {
                ArrayList<DataDetailsModel> dataDetailsModels = new ArrayList<>();
                for (DataDetailsModel s : dataModels) {
                    if (s.getName().toUpperCase().trim().contains(constraint.toString().toUpperCase().trim())) {
                        dataDetailsModels.add(s);
                    }
                }
                results.values = dataDetailsModels;
                results.count = dataDetailsModels.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            dataModels = (ArrayList<DataDetailsModel>) results.values;
            notifyDataSetChanged();
        }
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(DataDetailsModel discountModel);
        void onAddCustomClick();
    }
}
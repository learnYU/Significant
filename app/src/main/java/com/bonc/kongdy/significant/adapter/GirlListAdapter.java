package com.bonc.kongdy.significant.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bonc.kongdy.basketball.R;
import com.bonc.kongdy.significant.interfactory.OnRecyclerViewItemClickListener;
import com.bonc.kongdy.significant.model.GirlBean;
import com.bonc.kongdy.significant.ui.RatioImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SizeReadyCallback;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class GirlListAdapter extends RecyclerView.Adapter<GirlListAdapter.ViewHolder> implements View.OnClickListener {

    private List<GirlBean> mList;
    private Context mContext;
    private OnRecyclerViewItemClickListener itemClickListener;

    public GirlListAdapter(Context context, List<GirlBean> girlBeanList) {
        mList = girlBeanList;
        mContext = context;
    }

    public void setList(List<GirlBean> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_girls, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        GirlBean girl = mList.get(position);
        viewHolder.itemView.setTag(girl);

        int limit = 48;
        String text = girl.desc.length() > limit ? girl.desc.substring(0, limit) + "..." : girl.desc;

        viewHolder.titleView.setText(text);

        Glide.with(mContext)
             .load(girl.url)
             .centerCrop()
             .into(viewHolder.meizhiView)
             .getSize(new SizeReadyCallback() {
                @Override
                public void onSizeReady(int width, int height) {
                    if (!viewHolder.card.isShown()) {
                        viewHolder.card.setVisibility(View.VISIBLE);
                    }
                }
            });
    }

    @Override public int getItemCount() {
        return mList.size();
    }


    public void setItemClickListener(OnRecyclerViewItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        if (itemClickListener != null)
            itemClickListener.onItemClick(view,(GirlBean)view.getTag());
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_meizhi) RatioImageView meizhiView;
        @Bind(R.id.tv_title) TextView titleView;
        View card;
        public ViewHolder(View itemView) {
            super(itemView);
            card = itemView;
            ButterKnife.bind(this, itemView);
            meizhiView.setOriginalSize(50, 50);
        }
    }
}

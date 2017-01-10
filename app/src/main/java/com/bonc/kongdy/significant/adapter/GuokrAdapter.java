package com.bonc.kongdy.significant.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bonc.kongdy.basketball.R;
import com.bonc.kongdy.significant.interfactory.OnRecyclerViewItemClickListener;
import com.bonc.kongdy.significant.model.GuokrBean;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by kongdy on 2016/11/16.
 */
public class GuokrAdapter extends AnimRecyclerViewAdapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private List<GuokrBean.ResultBean> dataList;
    private Context context;
    private OnRecyclerViewItemClickListener itemClickListener;


    public GuokrAdapter(Context context, List<GuokrBean.ResultBean> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    public void setItemClickListener(OnRecyclerViewItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public List<GuokrBean.ResultBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<GuokrBean.ResultBean> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.guokr_item,parent,false);
        view.setOnClickListener(this);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final MyViewHolder myViewHolder = (MyViewHolder) holder;

        final GuokrBean.ResultBean resultBean = dataList.get(position);
        myViewHolder.titleTv.setText(resultBean.getTitle());
        myViewHolder.authorTv.setText(resultBean.getAuthor().getNickname());
        myViewHolder.descTv.setText(resultBean.getSummary());

        Glide.with(context).load(resultBean.getSmall_image()).centerCrop()
                .placeholder(R.mipmap.ic_launcher).into(myViewHolder.imageView);

        myViewHolder.itemView.setTag(resultBean);

        showItemAnim(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onItemClick(view, view.getTag());
    }

    class  MyViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.guokr_iv) ImageView imageView;
        @Bind(R.id.tv_author) TextView authorTv;
        @Bind(R.id.tv_title) TextView titleTv;
        @Bind(R.id.tv_desc) TextView descTv;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}

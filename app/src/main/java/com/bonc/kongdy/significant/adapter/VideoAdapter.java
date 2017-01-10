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
import com.bonc.kongdy.significant.utlis.MainUtils;
import com.bonc.kongdy.significant.model.VideoBean;
import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by kongdy on 2016/11/16.
 */
public class VideoAdapter extends AnimRecyclerViewAdapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private List<VideoBean.IssueListBean.ItemListBean>  dataList;
    private Context context;
    private OnRecyclerViewItemClickListener itemClickListener;

    public VideoAdapter(Context context, List<VideoBean.IssueListBean.ItemListBean> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    public void setItemClickListener(OnRecyclerViewItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public List<VideoBean.IssueListBean.ItemListBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<VideoBean.IssueListBean.ItemListBean> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.video_item,parent,false);
        view.setOnClickListener(this);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final MyViewHolder myViewHolder = (MyViewHolder) holder;

        final VideoBean.IssueListBean.ItemListBean videoBean = dataList.get(position);
        myViewHolder.titleTv.setText(videoBean.getData().getTitle());
        myViewHolder.timeTv.setText(videoBean.getData().getCategory()+ "  /  "+
                MainUtils.second2Time(videoBean.getData().getDuration()));

        try{
            Glide.with(context).load(videoBean.getData().getCover().getFeed()).centerCrop()
                    .placeholder(R.mipmap.ic_launcher).into(myViewHolder.imageView);
        } catch (Exception e){
            Logger.e(e.getMessage());
        }
        myViewHolder.itemView.setTag(videoBean);
//        showItemAnim(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onItemClick(view, (VideoBean.IssueListBean.ItemListBean) view.getTag());
    }

    class  MyViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.iv) ImageView imageView;
        @Bind(R.id.time_tv) TextView timeTv;
        @Bind(R.id.title_tv) TextView titleTv;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}

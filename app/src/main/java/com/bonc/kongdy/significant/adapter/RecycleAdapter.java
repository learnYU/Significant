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
import com.bonc.kongdy.significant.model.StoryBean;
import com.bonc.kongdy.significant.model.StoryExtraBean;
import com.bonc.kongdy.significant.network.NetWorkFactory;
import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by kongdy on 2016/11/16.
 */
public class RecycleAdapter extends AnimRecyclerViewAdapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private List<StoryBean> dataList;
    private Context context;
    private OnRecyclerViewItemClickListener itemClickListener;


    public RecycleAdapter(Context context,List<StoryBean> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    public void setItemClickListener(OnRecyclerViewItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public List<StoryBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<StoryBean> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.main_recycle_item,parent,false);
        view.setOnClickListener(this);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final MyViewHolder myViewHolder = (MyViewHolder) holder;

        final StoryBean newsBean = dataList.get(position);
        myViewHolder.titleTv.setText(newsBean.getTitle());
        try {
            Glide.with(context).load(newsBean.getImages().get(0)).centerCrop()
                    .placeholder(R.mipmap.ic_launcher).into(myViewHolder.imageView);
        }catch (Exception e){
            Logger.e(e.getMessage());
        }


        String id = newsBean.getId();

        NetWorkFactory.getZhihuApi()
                .getZhihuExtraData(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<StoryExtraBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(StoryExtraBean extraBean) {
                        myViewHolder.newsTv.setText(extraBean.getPopularity());
                        myViewHolder.msgnumTv.setText(extraBean.getComments());

                    }
                });

        myViewHolder.itemView.setTag(newsBean);
//        showItemAnim(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onItemClick(view, (StoryBean) view.getTag());
    }

    class  MyViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.news_iv) ImageView imageView;
        @Bind(R.id.news_tv) TextView newsTv;
        @Bind(R.id.title_tv) TextView titleTv;
        @Bind(R.id.msgnum_tv) TextView msgnumTv;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}

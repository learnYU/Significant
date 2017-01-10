package com.bonc.kongdy.significant.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.bonc.kongdy.basketball.R;
import com.bonc.kongdy.significant.model.VideoBean.IssueListBean.ItemListBean;
import com.bonc.kongdy.significant.utlis.MainUtils;
import com.bonc.kongdy.significant.view.base.ToolbarActivity;
import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.BlurTransformation;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class VideoInfoActivity extends ToolbarActivity {

    public static final String TAG = "VideoInfoActivity";

    private ItemListBean listBean;
    private String videoUrl;
    @Bind(R.id.contentlayout) View contentlayout;
    @Bind(R.id.video_iv) ImageView videoIv;
    @Bind(R.id.video_mo_iv) ImageView moIv;
    @Bind(R.id.play_btn) ImageButton playBtn;
    @Bind(R.id.video_title_tv) TextView titleTv;
    @Bind(R.id.video_time_tv) TextView timeTv;
    @Bind(R.id.video_desc_tv) TextView descTv;
    @Bind(R.id.tv_title) TextSwitcher textSwitcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        context = this;
        loadData();
        initView();

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_video_info;
    }

    @Override
    protected void loadData() {
        listBean = (ItemListBean) getIntent().getSerializableExtra("data");
        videoUrl = listBean.getData().getPlayUrl();
    }

    @Override
    protected void initView() {
        setTitle("");

        textSwitcher.setVisibility(View.VISIBLE);
        textSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @SuppressLint("NewApi")
            @Override
            public View makeView() {
                final TextView textView = new TextView(context);
                textView.setScrollBarStyle(View.SCROLLBARS_INSIDE_INSET);
                textView.setTextAppearance(R.style.WebTitle);
                textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                //                textView.setGravity();
                textView.setSingleLine(true);
                textView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        textView.setSelected(true);
                    }
                } ,1800);
                return textView;
            }
        });
        textSwitcher.setInAnimation(this, android.R.anim.fade_in);
        textSwitcher.setOutAnimation(this, android.R.anim.fade_out);
        textSwitcher.setText(listBean.getData().getTitle());


        ViewCompat.setTransitionName(contentlayout,TAG);
        String imageUrl = listBean.getData().getCover().getFeed();
        Glide.with(context).load(imageUrl).centerCrop().into(videoIv);
        Glide.with(context).load(imageUrl)
//                .placeholder(R.drawable.loading)
//                .error(R.drawable.failed)
                .crossFade(1000)
                .bitmapTransform(new BlurTransformation(context,25,6))
                // “23”：设置模糊度(在0.0到25.0之间)，默认”25";"4":图片缩放比例,默认“1”。
                .into(moIv);

        titleTv.setText(listBean.getData().getTitle());
        timeTv.setText(listBean.getData().getCategory()+ " / "
                +MainUtils.second2Time(listBean.getData().getDuration()));
        descTv.setText(listBean.getData().getDescription());

        playBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.play_btn:
                Intent intent = new Intent(context,VideoPlayActivity.class);
                intent.putExtra("videoUrl",videoUrl);
                intent.putExtra("title",listBean.getData().getTitle());
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.follow_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.follow){
            getSubscription().add(Observable.create(new Observable.OnSubscribe<Boolean>() {
                        @Override
                        public void call(Subscriber<? super Boolean> subscriber) {
                            List<ItemListBean> list = liteOrm.query(ItemListBean.class);
                            Logger.d(list.toString());
                            Logger.d(listBean);
                            boolean b = list.contains(listBean);
                            Logger.d(b);
                            subscriber.onNext(b);
                        }
                    }).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<Boolean>() {
                                @Override
                                public void onCompleted() {
                                }
                                @Override
                                public void onError(Throwable e) {
                                    Logger.d("error");
                                }

                                @Override
                                public void onNext(Boolean b) {
                                    if (!b) {
                                        liteOrm.save(listBean);
                                        showToast(getString(R.string.follow));
                                    } else {
                                        liteOrm.delete(listBean);
                                        showToast(getString(R.string.no_follow));
                                    }
                                }
                            })
            );
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}

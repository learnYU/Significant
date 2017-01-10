package com.bonc.kongdy.significant.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bonc.kongdy.basketball.R;
import com.bonc.kongdy.significant.model.GirlBean;
import com.bonc.kongdy.significant.view.base.ToolbarActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * https://github.com/chrisbanes/PhotoView/tree/master/sample
 */
public class PictureActivity extends ToolbarActivity implements View.OnLongClickListener, View.OnClickListener {

    public static final String TRANSIT_PIC = "picture";
    @Bind(R.id.image)
    ImageView imageView;

    private String url, desc;
    private Bitmap bitmap = null;
    private GirlBean girlBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        context = this;
        loadData();
        initView();
    }

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_picture;
    }
    @Override
    protected void loadData() {
        Intent intent = getIntent();
        if (intent == null){
            finish();
        }

        girlBean = (GirlBean) intent.getSerializableExtra("data");
        url = girlBean.url;
        desc = girlBean.desc;
    }

    @Override
    protected void initView() {
        setAppBarAlpha(0.7f);
        ViewCompat.setTransitionName(imageView,TRANSIT_PIC);

        //        Glide.with(context).load(url).centerCrop().into(imageView);
        Glide.with(context).load(url).asBitmap().centerCrop().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                bitmap = resource;
                imageView.setImageBitmap(bitmap);
            }
        });
        setTitle(desc);

        imageView.setOnClickListener(this);
        imageView.setOnLongClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pic_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        item.setVisible(true);

        switch (item.getItemId()){
            case R.id.follow:
                followPic(item);
                item.setIcon(R.mipmap.icon_like);
                item.setTitle(R.string.no_follow);
                break;
            case R.id.save:
                savePicture();
                break;
            case R.id.share:
                sharePicture();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try{
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                    Logger.e(getClass().getSimpleName(), "onMenuOpened...unable to set icons for overflow menu", e);
                }
            }
        }
        return super.onPrepareOptionsPanel(view, menu);
    }

    private void followPic(final MenuItem item) {
        getSubscription().add(Observable.create(new Observable.OnSubscribe<Boolean>() {
                    @Override
                    public void call(Subscriber<? super Boolean> subscriber) {
                        List<GirlBean> list = liteOrm.query(GirlBean.class);
                        boolean b = list.contains(girlBean);
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
                                    liteOrm.save(girlBean);
                                    item.setIcon(R.mipmap.icon_like);
                                    item.setTitle(R.string.no_follow);
                                    showToast(getString(R.string.follow));
                                } else {
                                    liteOrm.delete(girlBean);
                                    item.setIcon(R.drawable.ic_favorite_24dp);
                                    item.setTitle(R.string.follow);
                                    showToast(getString(R.string.no_follow));
                                }
                            }
                        })
        );

    }

    private void sharePicture() {

    }

    private void savePicture() {
        getSubscription().add(

        Observable.create(new Observable.OnSubscribe<Bitmap>() {
            @Override
            public void call(Subscriber<? super Bitmap> subscriber) {
                if (bitmap == null) {
                    subscriber.onError(new Exception("无法下载到图片"));
                }
                subscriber.onNext(bitmap);
                subscriber.onCompleted();
            }
        }).flatMap(new Func1<Bitmap, Observable<Uri>>() {
            @Override
            public Observable<Uri> call(Bitmap bitmap) {
                File dir = new File(Environment.getExternalStorageDirectory(),"Grils");
                if (!dir.exists()){
                    dir.mkdir();
                }

                String fileName = desc.replace('/', '-') + ".jpg";

                File file = new File(dir,fileName);

                try {
                    FileOutputStream outputStream = new FileOutputStream(file);
                    assert bitmap !=null;
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);

                    outputStream.flush();
                    outputStream.close();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Uri uri = Uri.fromFile(file);
                // 发送广播 更新相册
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,uri);
                context.sendBroadcast(intent);

                return Observable.just(uri);
            }
        }).subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(new Observer<Uri>() {
              @Override
              public void onCompleted() {
              }

              @Override
              public void onError(Throwable e) {
                  showToast(e.getMessage());
                  Logger.d(e.getMessage());
              }

              @Override
              public void onNext(Uri uri) {
                  File picFile = new File(Environment.getExternalStorageDirectory(),"Ball_Gril");
                  String msg = String.format(getResources().getString(R.string.picture_had_save_to),picFile.getAbsolutePath());
                  showToast(msg);
              }
          })
        );

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    /**
     * 长按保存图片
     * @param view
     * @return
     */
    @Override
    public boolean onLongClick(View view) {
        new AlertDialog.Builder(PictureActivity.this)
                .setMessage(getString(R.string.action_save)+" ?")
                .setNegativeButton(android.R.string.cancel,null)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        savePicture();
                    }
                }).show();

        return true;
    }

    @Override
    public void onClick(View view) {
        finish();
    }
}

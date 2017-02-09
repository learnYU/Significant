package com.bonc.kongdy.significant.view.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.bonc.kongdy.basketball.R;
import com.bonc.kongdy.significant.utlis.PhoneInfoUtils;


public class AboutFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {

    private SwitchPreference preference;
    private Preference appIntro,version,checkUpdate,star,share,github,email;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.about);

        appIntro = findPreference("app_intro");
        version = findPreference("version");
        checkUpdate = findPreference("check_update");
        star = findPreference("star");
        share = findPreference("share");
        github = findPreference("github");
        email = findPreference("email");

        version.setSummary(PhoneInfoUtils.getVersionCode(getActivity()));

        appIntro.setOnPreferenceClickListener(this);
        checkUpdate.setOnPreferenceClickListener(this);
        star.setOnPreferenceClickListener(this);
        share.setOnPreferenceClickListener(this);
        github.setOnPreferenceClickListener(this);
        email.setOnPreferenceClickListener(this);

    }
    @Override
    public boolean onPreferenceClick(Preference preference) {
        if (preference.equals(appIntro)){
            // 应用详情

        } else if (preference.equals(checkUpdate)) {
            // 检查更新

        } else if (preference.equals(star)) {
            // 去GitHub点赞
            new AlertDialog.Builder(getActivity()).setTitle("点赞")
                    .setMessage("去项目地址给作者个Star，鼓励下作者୧(๑•̀⌄•́๑)૭✧")
                    .setNegativeButton("复制", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            copyToClipboard(getView(), getActivity().getResources().getString(R.string.my_github));
                        }
                    })
                    .setPositiveButton("打开", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Uri uri = Uri.parse(getString(R.string.my_github));   //指定网址
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_VIEW);           //指定Action
                            intent.setData(uri);                            //设置Uri
                            getActivity().startActivity(intent);        //启动Activity
                        }
                    }).show();
        } else if (preference.equals(share)) {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
            sharingIntent.putExtra(Intent.EXTRA_TEXT, "xxx");
            startActivity(Intent.createChooser(sharingIntent, getString(R.string.app_name)));

        } else if (preference.equals(github)) {
            copyToClipboard(getView(), github.getSummary().toString());
        } else if (preference.equals(email)) {
            copyToClipboard(getView(), email.getSummary().toString());
        } else if (preference.equals(appIntro)) {
//            copyToClipboard(getView(), appIntro.getSummary().toString());
        }

        return false;
    }

    //复制黏贴板
    private void copyToClipboard(View view, String info) {
        ClipboardManager clipboardManager = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        clipboardManager.setPrimaryClip(ClipData.newPlainText("",info));
        Snackbar.make(view,"已经复制到剪切板啦( •̀ .̫ •́ )✧",Snackbar.LENGTH_SHORT)
                .setAction("", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }).show();
    }


}

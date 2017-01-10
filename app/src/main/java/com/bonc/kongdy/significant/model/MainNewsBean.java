package com.bonc.kongdy.significant.model;

import java.io.Serializable;

/**
 * Created by kongdy on 2016/11/22.
 */
public class MainNewsBean implements Serializable{

    private String imagerUrl;
    private String newsTitle;
    private String news;
    private String msgNum;

    public MainNewsBean(String imagerUrl, String newsTitle, String news, String msgNum) {
        this.imagerUrl = imagerUrl;
        this.newsTitle = newsTitle;
        this.news = news;
        this.msgNum = msgNum;
    }

    public String getImagerUrl() {
        return imagerUrl;
    }

    public void setImagerUrl(String imagerUrl) {
        this.imagerUrl = imagerUrl;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }

    public String getMsgNum() {
        return msgNum;
    }

    public void setMsgNum(String msgNum) {
        this.msgNum = msgNum;
    }


    @Override
    public String toString() {
        return "MainNewsBean{" +
                "imagerUrl='" + imagerUrl + '\'' +
                ", newsTitle='" + newsTitle + '\'' +
                ", news='" + news + '\'' +
                ", msgNum='" + msgNum + '\'' +
                '}';
    }
}

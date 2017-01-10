package com.bonc.kongdy.significant.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kongdy on 2016/12/1.
 * https://github.com/izzyleung/ZhihuDailyPurify/wiki/%E7%9F%A5%E4%B9%8E%E6%97%A5%E6%8A%A5-API-%E5%88%86%E6%9E%90
 */
public class ZhihuBean implements Serializable{

    private String date;
    private List<StoryBean> stories;
    private List<TopStoryBean> top_stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<StoryBean> getStories() {
        return stories;
    }

    public void setStories(List<StoryBean> stories) {
        this.stories = stories;
    }

    public List<TopStoryBean> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<TopStoryBean> top_stories) {
        this.top_stories = top_stories;
    }

    @Override
    public String toString() {
        return "ZhihuBean{" +
                "date='" + date + '\'' +
                ", stories=" + stories +
                ", top_stories=" + top_stories +
                '}';
    }
}

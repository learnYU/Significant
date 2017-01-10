package com.bonc.kongdy.significant.model;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.Table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kongdy on 2016/12/1.
 */
@Table("story_bean")
public class StoryBean implements Serializable {
    @Column("title")
    private String title;
    @Column("ga_prefix")
    private String ga_prefix;
    
    private List<String> images = new ArrayList<>();
    @Column("type")
    private String type;
    @Column("id")
    private String id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "StoryBean{" +
                "title='" + title + '\'' +
                ", ga_prefix='" + ga_prefix + '\'' +
                ", images=" + images +
                ", type='" + type + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        StoryBean storyBean = (StoryBean) o;

        if (title != null ? !title.equals(storyBean.title) : storyBean.title != null)
            return false;
        if (ga_prefix != null ? !ga_prefix.equals(storyBean.ga_prefix) : storyBean.ga_prefix != null)
            return false;
//        if (images != null ? !images.equals(storyBean.images) : storyBean.images != null)
//            return false;
        if (type != null ? !type.equals(storyBean.type) : storyBean.type != null)
            return false;
        return id != null ? id.equals(storyBean.id) : storyBean.id == null;

    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (ga_prefix != null ? ga_prefix.hashCode() : 0);
        result = 31 * result + (images != null ? images.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }
}

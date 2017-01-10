package com.bonc.kongdy.significant.model;

import java.io.Serializable;

/**
 * Created by kongdy on 2016/12/1.
 */
public class TopStoryBean implements Serializable {
    private String title;
    private String ga_prefix;
    private String image;
    private String type;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
        return "TopStoryBean{" +
                "title='" + title + '\'' +
                ", ga_prefix='" + ga_prefix + '\'' +
                ", image='" + image + '\'' +
                ", type='" + type + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}

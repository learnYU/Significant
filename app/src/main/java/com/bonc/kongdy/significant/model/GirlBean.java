package com.bonc.kongdy.significant.model;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

import java.io.Serializable;

/**
 * Created by kongdy on 2016/11/23.
 */

@Table("GirlBean")
public class GirlBean implements Serializable{
    // 指定自增，每个对象需要有一个主键
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    private int id;
    @Column("url")
    public String url;
    @Column("type")
    public String type;
    @Column("desc")
    public String desc;
    @Column("who")
    public String who;
    @Column("used")
    public boolean used;
//   public Date createdAt;
//   public Date updatedAt;
//    public Date publishedAt;
    @Column("imageWidth")
    public int imageWidth;
    @Column("imageHeight")
    public int imageHeight;

    @Override
    public String toString() {
        return "GirlBean{" +
                "url='" + url + '\'' +
                ", type='" + type + '\'' +
                ", desc='" + desc + '\'' +
                ", who='" + who + '\'' +
                ", used=" + used +
                ", imageWidth=" + imageWidth +
                ", imageHeight=" + imageHeight +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        GirlBean girlBean = (GirlBean) o;

        if (used != girlBean.used)
            return false;
        if (imageWidth != girlBean.imageWidth)
            return false;
        if (imageHeight != girlBean.imageHeight)
            return false;
        if (url != null ? !url.equals(girlBean.url) : girlBean.url != null)
            return false;
        if (type != null ? !type.equals(girlBean.type) : girlBean.type != null)
            return false;
        if (desc != null ? !desc.equals(girlBean.desc) : girlBean.desc != null)
            return false;
        return who != null ? who.equals(girlBean.who) : girlBean.who == null;

    }

    @Override
    public int hashCode() {
        int result = url != null ? url.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (desc != null ? desc.hashCode() : 0);
        result = 31 * result + (who != null ? who.hashCode() : 0);
        result = 31 * result + (used ? 1 : 0);
        result = 31 * result + imageWidth;
        result = 31 * result + imageHeight;
        return result;
    }
}

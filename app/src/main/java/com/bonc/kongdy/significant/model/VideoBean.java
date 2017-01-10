package com.bonc.kongdy.significant.model;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.Mapping;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;
import com.litesuits.orm.db.enums.Relation;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kongdy on 2016/12/8.
 */
public class VideoBean implements Serializable {
    @Override
    public String toString() {
        return "VideoBean{" +
                "nextPageUrl='" + nextPageUrl + '\'' +
                ", nextPublishTime=" + nextPublishTime +
                ", newestIssueType='" + newestIssueType + '\'' +
                ", dialog=" + dialog +
                ", issueList=" + issueList +
                '}';
    }

    private String nextPageUrl;
    private long nextPublishTime;
    private String newestIssueType;
    private Object dialog;
    private List<IssueListBean> issueList;

    public String getNextPageUrl() {
        return nextPageUrl;
    }

    public void setNextPageUrl(String nextPageUrl) {
        this.nextPageUrl = nextPageUrl;
    }

    public long getNextPublishTime() {
        return nextPublishTime;
    }

    public void setNextPublishTime(long nextPublishTime) {
        this.nextPublishTime = nextPublishTime;
    }

    public String getNewestIssueType() {
        return newestIssueType;
    }

    public void setNewestIssueType(String newestIssueType) {
        this.newestIssueType = newestIssueType;
    }

    public Object getDialog() {
        return dialog;
    }

    public void setDialog(Object dialog) {
        this.dialog = dialog;
    }

    public List<IssueListBean> getIssueList() {
        return issueList;
    }

    public void setIssueList(List<IssueListBean> issueList) {
        this.issueList = issueList;
    }

    public static class IssueListBean implements Serializable{

        private long releaseTime;
        private String type;
        private long date;
        private long publishTime;
        private int count;
        private List<ItemListBean> itemList;

        @Override
        public String toString() {
            return "IssueListBean{" +
                    "releaseTime=" + releaseTime +
                    ", type='" + type + '\'' +
                    ", date=" + date +
                    ", publishTime=" + publishTime +
                    ", count=" + count +
                    ", itemList=" + itemList +
                    '}';
        }

        public long getReleaseTime() {
            return releaseTime;
        }

        public void setReleaseTime(long releaseTime) {
            this.releaseTime = releaseTime;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public long getDate() {
            return date;
        }

        public void setDate(long date) {
            this.date = date;
        }

        public long getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(long publishTime) {
            this.publishTime = publishTime;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<ItemListBean> getItemList() {
            return itemList;
        }

        public void setItemList(List<ItemListBean> itemList) {
            this.itemList = itemList;
        }

        @Table("ItemListBean")
        public static class ItemListBean implements Serializable{
            @PrimaryKey(AssignType.AUTO_INCREMENT)
            private int id;
            @Column("type")
            private String type;

//            @Column("data")
            @Mapping(Relation.OneToOne)
            private DataBean data;

            @Override
            public String toString() {
                return "ItemListBean{" +
                        "id='" + id + '\'' +
                        "type='" + type + '\'' +
                        ", data=" + data +
                        '}';
            }
            @Override
            public boolean equals(Object o) {
                if (this == o)
                    return true;
                if (o == null || getClass() != o.getClass())
                    return false;

                ItemListBean listBean = (ItemListBean) o;

                if (type != null ? !type.equals(listBean.type) : listBean.type != null)
                    return false;
                return data != null ? data.equals(listBean.data) : listBean.data == null;

            }

            @Override
            public int hashCode() {
                int result = type != null ? type.hashCode() : 0;
                result = 31 * result + (data != null ? data.hashCode() : 0);
                return result;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public DataBean getData() {
                return data;
            }

            public void setData(DataBean data) {
                this.data = data;
            }

            @Table("DataBean")
            public static class DataBean implements Serializable{
                @Column("dataType")
                private String dataType;
                @PrimaryKey(AssignType.BY_MYSELF)
                private int id;

                @Column("title")
                private String title;
                @Column("description")
                private String description;

//                @Column("provider")
//                private ProviderBean provider;
                @Column("category")
                private String category;
//                @Ignore
//                private Object author;

                @Mapping(Relation.OneToOne)
                private CoverBean cover;

                @Column("playUrl")
                private String playUrl;
                @Column("duration")
                private int duration;

//                @Column("webUrl")
//                private WebUrlBean webUrl;
                @Column("releaseTime")
                private long releaseTime;

                @Mapping(Relation.OneToOne)
                private ConsumptionBean consumption;
//                @Ignore
//                private Object campaign;
//                @Ignore
//                private Object waterMarks;
//                @Ignore
//                private Object adTrack;
                @Column("type")
                private String type;
                @Column("idx")
                private int idx;
//                @Ignore
//                private Object shareAdTrack;
//                @Ignore
//                private Object favoriteAdTrack;
//                @Ignore
//                private Object webAdTrack;
                @Column("date")
                private long date;
//                @Ignore
//                private Object promotion;
//                @Ignore
//                private Object label;
                @Column("collected")
                private boolean collected;
                @Column("played")
                private boolean played;

//                @Mapping(Relation.OneToOne)
//                @Ignore
//                private List<PlayInfoBean> playInfo;
//
//                @Ignore
//                private List<TagsBean> tags;

                @Override
                public boolean equals(Object o) {
                    if (this == o)
                        return true;
                    if (o == null || getClass() != o.getClass())
                        return false;

                    DataBean dataBean = (DataBean) o;

                    if (id != dataBean.id)
                        return false;
                    if (title != null ? !title.equals(dataBean.title) : dataBean.title != null)
                        return false;
                    return description != null ? description.equals(dataBean.description) : dataBean.description == null;

                }

                @Override
                public int hashCode() {
                    int result = id;
                    result = 31 * result + (title != null ? title.hashCode() : 0);
                    result = 31 * result + (description != null ? description.hashCode() : 0);
                    return result;
                }

                @Override
                public String toString() {
                    return "DataBean{" +
                            "dataType='" + dataType + '\'' +
                            ", id=" + id +
                            ", title='" + title + '\'' +
                            ", description='" + description + '\'' +
                            ", category='" + category + '\'' +
                            ", cover=" + cover +
                            ", playUrl='" + playUrl + '\'' +
                            ", duration=" + duration +
                            ", releaseTime=" + releaseTime +
                            ", consumption=" + consumption +
                            ", type='" + type + '\'' +
                            ", idx=" + idx +
                            ", date=" + date +
                            ", collected=" + collected +
                            ", played=" + played +
                            '}';
                }

                public String getDataType() {
                    return dataType;
                }

                public void setDataType(String dataType) {
                    this.dataType = dataType;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public String getCategory() {
                    return category;
                }

                public void setCategory(String category) {
                    this.category = category;
                }

                public CoverBean getCover() {
                    return cover;
                }

                public void setCover(CoverBean cover) {
                    this.cover = cover;
                }

                public String getPlayUrl() {
                    return playUrl;
                }

                public void setPlayUrl(String playUrl) {
                    this.playUrl = playUrl;
                }

                public int getDuration() {
                    return duration;
                }

                public void setDuration(int duration) {
                    this.duration = duration;
                }

                public long getReleaseTime() {
                    return releaseTime;
                }

                public void setReleaseTime(long releaseTime) {
                    this.releaseTime = releaseTime;
                }

                public ConsumptionBean getConsumption() {
                    return consumption;
                }

                public void setConsumption(ConsumptionBean consumption) {
                    this.consumption = consumption;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public int getIdx() {
                    return idx;
                }

                public void setIdx(int idx) {
                    this.idx = idx;
                }


                public long getDate() {
                    return date;
                }

                public void setDate(long date) {
                    this.date = date;
                }

                public boolean isCollected() {
                    return collected;
                }

                public void setCollected(boolean collected) {
                    this.collected = collected;
                }

                public boolean isPlayed() {
                    return played;
                }

                public void setPlayed(boolean played) {
                    this.played = played;
                }

                @Table("ProviderBean")
                public static class ProviderBean implements Serializable{
                    /**
                     * name : YouTube
                     * alias : youtube
                     * icon : http://img.kaiyanapp.com/fa20228bc5b921e837156923a58713f6.png
                     */

                    private String name;
                    private String alias;
                    private String icon;

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public String getAlias() {
                        return alias;
                    }

                    public void setAlias(String alias) {
                        this.alias = alias;
                    }

                    public String getIcon() {
                        return icon;
                    }

                    public void setIcon(String icon) {
                        this.icon = icon;
                    }
                }

                @Table("CoverBean")
                public static class CoverBean implements Serializable{
                    /**
                     * feed : http://img.kaiyanapp.com/e2badabd290a2e3a43946c1a16e07024.jpeg?imageMogr2/quality/60
                     * detail : http://img.kaiyanapp.com/e2badabd290a2e3a43946c1a16e07024.jpeg?imageMogr2/quality/60
                     * blurred : http://img.kaiyanapp.com/a8d83ce2229e6263bab20335d01d2594.jpeg?imageMogr2/quality/60
                     * sharing : null
                     */
                    @PrimaryKey(AssignType.AUTO_INCREMENT)
                    private int id;
                    private String feed;
                    private String detail;
                    private String blurred;
                    @Override
                    public String toString() {
                        return "CoverBean{" +
                                "feed='" + feed + '\'' +
                                ", detail='" + detail + '\'' +
                                ", blurred='" + blurred + '\'' +
                                '}';
                    }

                    public String getFeed() {
                        return feed;
                    }

                    public void setFeed(String feed) {
                        this.feed = feed;
                    }

                    public String getDetail() {
                        return detail;
                    }

                    public void setDetail(String detail) {
                        this.detail = detail;
                    }

                    public String getBlurred() {
                        return blurred;
                    }

                    public void setBlurred(String blurred) {
                        this.blurred = blurred;
                    }

                }

                @Table("WebUrlBean")
                public static class WebUrlBean implements Serializable{
                    /**
                     * raw : http://www.eyepetizer.net/detail.html?vid=11488
                     * forWeibo : http://wandou.im/3glax4
                     */

                    private String raw;
                    private String forWeibo;

                    public String getRaw() {
                        return raw;
                    }

                    public void setRaw(String raw) {
                        this.raw = raw;
                    }

                    public String getForWeibo() {
                        return forWeibo;
                    }

                    public void setForWeibo(String forWeibo) {
                        this.forWeibo = forWeibo;
                    }
                }

                @Table("ConsumptionBean")
                public static class ConsumptionBean implements Serializable{
                    /**
                     * collectionCount : 293
                     * shareCount : 291
                     * replyCount : 15
                     */
                    @PrimaryKey(AssignType.AUTO_INCREMENT)
                    private int id;
                    private int collectionCount;
                    private int shareCount;
                    private int replyCount;

                    public int getCollectionCount() {
                        return collectionCount;
                    }

                    public void setCollectionCount(int collectionCount) {
                        this.collectionCount = collectionCount;
                    }

                    public int getShareCount() {
                        return shareCount;
                    }

                    public void setShareCount(int shareCount) {
                        this.shareCount = shareCount;
                    }

                    public int getReplyCount() {
                        return replyCount;
                    }

                    public void setReplyCount(int replyCount) {
                        this.replyCount = replyCount;
                    }

                    @Override
                    public String toString() {
                        return "ConsumptionBean{" +
                                "collectionCount=" + collectionCount +
                                ", shareCount=" + shareCount +
                                ", replyCount=" + replyCount +
                                '}';
                    }
                }

                @Table("PlayInfoBean")
                public static class PlayInfoBean implements Serializable{
                    /**
                     * height : 480
                     * width : 854
                     * name : 标清
                     * type : normal
                     * url : http://baobab.kaiyanapp.com/api/v1/playUrl?vid=11488&editionType=normal
                     */

                    private int height;
                    private int width;
                    private String name;
                    private String type;
                    private String url;

                    public int getHeight() {
                        return height;
                    }

                    public void setHeight(int height) {
                        this.height = height;
                    }

                    public int getWidth() {
                        return width;
                    }

                    public void setWidth(int width) {
                        this.width = width;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public String getType() {
                        return type;
                    }

                    public void setType(String type) {
                        this.type = type;
                    }

                    public String getUrl() {
                        return url;
                    }

                    public void setUrl(String url) {
                        this.url = url;
                    }
                }

                @Table("TagsBean")
                public static class TagsBean implements Serializable{
                    /**
                     * id : 36
                     * name : 集锦
                     * actionUrl : eyepetizer://tag/36/?title=%E9%9B%86%E9%94%A6
                     * adTrack : null
                     */

                    private int id;
                    private String name;
                    private String actionUrl;

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public String getActionUrl() {
                        return actionUrl;
                    }

                    public void setActionUrl(String actionUrl) {
                        this.actionUrl = actionUrl;
                    }

                }
            }
        }
    }
}

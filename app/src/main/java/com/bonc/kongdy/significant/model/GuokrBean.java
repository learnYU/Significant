package com.bonc.kongdy.significant.model;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.Ignore;
import com.litesuits.orm.db.annotation.Table;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kongdy on 2016/12/6.
 */
public class GuokrBean implements Serializable{
    private String now;
    private boolean ok;
    private int limit;
    private int offset;
    private int total;
    private List<ResultBean> result;

    @Override
    public String toString() {
        return "GuokrBean{" +
                "now='" + now + '\'' +
                ", ok=" + ok +
                ", limit=" + limit +
                ", offset=" + offset +
                ", total=" + total +
                ", result=" + result +
                '}';
    }

    public String getNow() {
        return now;
    }

    public void setNow(String now) {
        this.now = now;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    @Table("result_bean")
    public static class ResultBean implements Serializable{

        private String image;
        private boolean is_replyable;
        private String preface;
        private int id;

        @Column("SubjectBean")
        private SubjectBean subject;
        private String copyright;

        @Column("AuthorBean")
        private AuthorBean author;
        private String image_description;
        private boolean is_show_summary;
        private Object minisite_key;

        @Column("ImageInfoBean")
        private ImageInfoBean image_info;
        private String subject_key;

        @Ignore
        private Object minisite;
        private String date_published;
        private int replies_count;
        private boolean is_author_external;
        private int recommends_count;
        private String title_hide;
        private String date_modified;
        private String url;
        private String title;
        private String small_image;
        private String summary;
        private String ukey_author;
        private String date_created;
        private String resource_url;

        @Ignore
        private List<ChannelsBean> channels;

        private List<String> channel_keys;

//        private List<?> tags;
//        private List<AuthorsBean> authors;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public boolean isIs_replyable() {
            return is_replyable;
        }

        public void setIs_replyable(boolean is_replyable) {
            this.is_replyable = is_replyable;
        }

        public String getPreface() {
            return preface;
        }

        public void setPreface(String preface) {
            this.preface = preface;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public SubjectBean getSubject() {
            return subject;
        }

        public void setSubject(SubjectBean subject) {
            this.subject = subject;
        }

        public String getCopyright() {
            return copyright;
        }

        public void setCopyright(String copyright) {
            this.copyright = copyright;
        }

        public AuthorBean getAuthor() {
            return author;
        }

        public void setAuthor(AuthorBean author) {
            this.author = author;
        }

        public String getImage_description() {
            return image_description;
        }

        public void setImage_description(String image_description) {
            this.image_description = image_description;
        }

        public boolean isIs_show_summary() {
            return is_show_summary;
        }

        public void setIs_show_summary(boolean is_show_summary) {
            this.is_show_summary = is_show_summary;
        }

        public Object getMinisite_key() {
            return minisite_key;
        }

        public void setMinisite_key(Object minisite_key) {
            this.minisite_key = minisite_key;
        }

        public ImageInfoBean getImage_info() {
            return image_info;
        }

        public void setImage_info(ImageInfoBean image_info) {
            this.image_info = image_info;
        }

        public String getSubject_key() {
            return subject_key;
        }

        public void setSubject_key(String subject_key) {
            this.subject_key = subject_key;
        }

        public Object getMinisite() {
            return minisite;
        }

        public void setMinisite(Object minisite) {
            this.minisite = minisite;
        }

        public String getDate_published() {
            return date_published;
        }

        public void setDate_published(String date_published) {
            this.date_published = date_published;
        }

        public int getReplies_count() {
            return replies_count;
        }

        public void setReplies_count(int replies_count) {
            this.replies_count = replies_count;
        }

        public boolean isIs_author_external() {
            return is_author_external;
        }

        public void setIs_author_external(boolean is_author_external) {
            this.is_author_external = is_author_external;
        }

        public int getRecommends_count() {
            return recommends_count;
        }

        public void setRecommends_count(int recommends_count) {
            this.recommends_count = recommends_count;
        }

        public String getTitle_hide() {
            return title_hide;
        }

        public void setTitle_hide(String title_hide) {
            this.title_hide = title_hide;
        }

        public String getDate_modified() {
            return date_modified;
        }

        public void setDate_modified(String date_modified) {
            this.date_modified = date_modified;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSmall_image() {
            return small_image;
        }

        public void setSmall_image(String small_image) {
            this.small_image = small_image;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getUkey_author() {
            return ukey_author;
        }

        public void setUkey_author(String ukey_author) {
            this.ukey_author = ukey_author;
        }

        public String getDate_created() {
            return date_created;
        }

        public void setDate_created(String date_created) {
            this.date_created = date_created;
        }

        public String getResource_url() {
            return resource_url;
        }

        public void setResource_url(String resource_url) {
            this.resource_url = resource_url;
        }

        public List<ChannelsBean> getChannels() {
            return channels;
        }

        public void setChannels(List<ChannelsBean> channels) {
            this.channels = channels;
        }

        public List<String> getChannel_keys() {
            return channel_keys;
        }

        public void setChannel_keys(List<String> channel_keys) {
            this.channel_keys = channel_keys;
        }

//        public List<?> getTags() {
//            return tags;
//        }
//        public void setTags(List<?> tags) {
//            this.tags = tags;
//        }
//        public List<AuthorsBean> getAuthors() {
//            return authors;
//        }
//        public void setAuthors(List<AuthorsBean> authors) {
//            this.authors = authors;
//        }


        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;

            ResultBean that = (ResultBean) o;

            return id == that.id;

        }

        @Override
        public int hashCode() {
            return id;
        }

        @Table("SubjectBean")
        public static class SubjectBean implements Serializable{
            /**
             * url : http://www.guokr.com/scientific/subject/chemistry/
             * date_created : 2014-05-23T16:22:09.282129+08:00
             * name : 化学
             * key : chemistry
             * articles_count : 208
             */

            private String url;
            private String date_created;
            private String name;
            private String key;
            private int articles_count;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getDate_created() {
                return date_created;
            }

            public void setDate_created(String date_created) {
                this.date_created = date_created;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }

            public int getArticles_count() {
                return articles_count;
            }

            public void setArticles_count(int articles_count) {
                this.articles_count = articles_count;
            }
        }

        @Table("AuthorBean")
        public static class AuthorBean implements Serializable{

            private String ukey;
            private boolean is_title_authorized;
            private String nickname;
            private String master_category;
            private String amended_reliability;
            private boolean is_exists;
            private String title;
            private String url;
            private String gender;
            private int followers_count;
            @Column("AvatarBean")
            private AvatarBean avatar;
            private String resource_url;

            public String getUkey() {
                return ukey;
            }

            public void setUkey(String ukey) {
                this.ukey = ukey;
            }

            public boolean isIs_title_authorized() {
                return is_title_authorized;
            }

            public void setIs_title_authorized(boolean is_title_authorized) {
                this.is_title_authorized = is_title_authorized;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getMaster_category() {
                return master_category;
            }

            public void setMaster_category(String master_category) {
                this.master_category = master_category;
            }

            public String getAmended_reliability() {
                return amended_reliability;
            }

            public void setAmended_reliability(String amended_reliability) {
                this.amended_reliability = amended_reliability;
            }

            public boolean isIs_exists() {
                return is_exists;
            }

            public void setIs_exists(boolean is_exists) {
                this.is_exists = is_exists;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }

            public int getFollowers_count() {
                return followers_count;
            }

            public void setFollowers_count(int followers_count) {
                this.followers_count = followers_count;
            }

            public AvatarBean getAvatar() {
                return avatar;
            }

            public void setAvatar(AvatarBean avatar) {
                this.avatar = avatar;
            }

            public String getResource_url() {
                return resource_url;
            }

            public void setResource_url(String resource_url) {
                this.resource_url = resource_url;
            }

            @Table("AvatarBean")
            public static class AvatarBean implements Serializable{
                /**
                 * large : http://2.im.guokr.com/xZ0FhncVKovg5Yc6qP0HnWAgHt4ByxLsuOalEg-D3RagAAAAoAAAAEpQ.jpg?imageView2/1/w/160/h/160
                 * small : http://2.im.guokr.com/xZ0FhncVKovg5Yc6qP0HnWAgHt4ByxLsuOalEg-D3RagAAAAoAAAAEpQ.jpg?imageView2/1/w/24/h/24
                 * normal : http://2.im.guokr.com/xZ0FhncVKovg5Yc6qP0HnWAgHt4ByxLsuOalEg-D3RagAAAAoAAAAEpQ.jpg?imageView2/1/w/48/h/48
                 */

                private String large;
                private String small;
                private String normal;

                public String getLarge() {
                    return large;
                }

                public void setLarge(String large) {
                    this.large = large;
                }

                public String getSmall() {
                    return small;
                }

                public void setSmall(String small) {
                    this.small = small;
                }

                public String getNormal() {
                    return normal;
                }

                public void setNormal(String normal) {
                    this.normal = normal;
                }
            }
        }

        @Table("ImageInfoBean")
        public static class ImageInfoBean implements Serializable {
            /**
             * url : http://1.im.guokr.com/OR5x3Sq2HGT0qSZUhp14zAdKjwQ916xGNp2tJkc9lH6QAQAACwEAAEpQ.jpg
             * width : 400
             * height : 267
             */

            private String url;
            private int width;
            private int height;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }
        }

        @Table("ChannelsBean")
        public static class ChannelsBean implements Serializable{
            /**
             * url : http://www.guokr.com/scientific/channel/hot/
             * date_created : 2014-05-23T16:22:09.282129+08:00
             * name : 热点
             * key : hot
             * articles_count : 1720
             */

            @Column("url")
            private String url;
            @Column("date_created")
            private String date_created;
            @Column("name")
            private String name;
            @Column("key")
            private String key;
            @Column("articles_count")
            private int articles_count;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getDate_created() {
                return date_created;
            }

            public void setDate_created(String date_created) {
                this.date_created = date_created;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }

            public int getArticles_count() {
                return articles_count;
            }

            public void setArticles_count(int articles_count) {
                this.articles_count = articles_count;
            }
        }

        public static class AuthorsBean implements Serializable {
            /**
             * ukey : tjzyvr
             * is_title_authorized : false
             * nickname : 稷下_uuey
             * master_category : normal
             * amended_reliability : 0
             * is_exists : true
             * title : 一桥大学环境伦理学博士
             * url : http://www.guokr.com/i/1787109975/
             * gender : male
             * followers_count : 3
             * avatar : {"large":"http://2.im.guokr.com/xZ0FhncVKovg5Yc6qP0HnWAgHt4ByxLsuOalEg-D3RagAAAAoAAAAEpQ.jpg?imageView2/1/w/160/h/160","small":"http://2.im.guokr.com/xZ0FhncVKovg5Yc6qP0HnWAgHt4ByxLsuOalEg-D3RagAAAAoAAAAEpQ.jpg?imageView2/1/w/24/h/24","normal":"http://2.im.guokr.com/xZ0FhncVKovg5Yc6qP0HnWAgHt4ByxLsuOalEg-D3RagAAAAoAAAAEpQ.jpg?imageView2/1/w/48/h/48"}
             * resource_url : http://apis.guokr.com/community/user/tjzyvr.json
             */

            private String ukey;
            private boolean is_title_authorized;
            private String nickname;
            private String master_category;
            private String amended_reliability;
            private boolean is_exists;
            private String title;
            private String url;
            private String gender;
            private int followers_count;
            private AvatarBeanX avatar;
            private String resource_url;

            public String getUkey() {
                return ukey;
            }

            public void setUkey(String ukey) {
                this.ukey = ukey;
            }

            public boolean isIs_title_authorized() {
                return is_title_authorized;
            }

            public void setIs_title_authorized(boolean is_title_authorized) {
                this.is_title_authorized = is_title_authorized;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getMaster_category() {
                return master_category;
            }

            public void setMaster_category(String master_category) {
                this.master_category = master_category;
            }

            public String getAmended_reliability() {
                return amended_reliability;
            }

            public void setAmended_reliability(String amended_reliability) {
                this.amended_reliability = amended_reliability;
            }

            public boolean isIs_exists() {
                return is_exists;
            }

            public void setIs_exists(boolean is_exists) {
                this.is_exists = is_exists;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }

            public int getFollowers_count() {
                return followers_count;
            }

            public void setFollowers_count(int followers_count) {
                this.followers_count = followers_count;
            }

            public AvatarBeanX getAvatar() {
                return avatar;
            }

            public void setAvatar(AvatarBeanX avatar) {
                this.avatar = avatar;
            }

            public String getResource_url() {
                return resource_url;
            }

            public void setResource_url(String resource_url) {
                this.resource_url = resource_url;
            }

            public static class AvatarBeanX implements Serializable{
                /**
                 * large : http://2.im.guokr.com/xZ0FhncVKovg5Yc6qP0HnWAgHt4ByxLsuOalEg-D3RagAAAAoAAAAEpQ.jpg?imageView2/1/w/160/h/160
                 * small : http://2.im.guokr.com/xZ0FhncVKovg5Yc6qP0HnWAgHt4ByxLsuOalEg-D3RagAAAAoAAAAEpQ.jpg?imageView2/1/w/24/h/24
                 * normal : http://2.im.guokr.com/xZ0FhncVKovg5Yc6qP0HnWAgHt4ByxLsuOalEg-D3RagAAAAoAAAAEpQ.jpg?imageView2/1/w/48/h/48
                 */

                private String large;
                private String small;
                private String normal;

                public String getLarge() {
                    return large;
                }

                public void setLarge(String large) {
                    this.large = large;
                }

                public String getSmall() {
                    return small;
                }

                public void setSmall(String small) {
                    this.small = small;
                }

                public String getNormal() {
                    return normal;
                }

                public void setNormal(String normal) {
                    this.normal = normal;
                }
            }
        }
    }
}

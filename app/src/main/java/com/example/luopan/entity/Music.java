package com.example.luopan.entity;

import java.io.Serializable;

public class Music implements Serializable {
    /**
     * 歌曲名称
     */
    private String songname;

    private int seconds;

    /**
     * 专辑id
     */

    private String albummid;

    private int songid;

    private int singerid;

    private String albumpic_big;

    /**
     * 专辑小图片，高宽90
     */
    private String albumpic_small;

    /**
     * mp3下载链接
     */

    private String downUrl;
    /**
     * 流媒体地址
     */

    private String url;

    /**
     * 歌手名
     */

    private String singername;

    private int albumid;

    public void setSongname(String songname) {
        this.songname = songname;
    }

    public String getSongname() {
        return this.songname;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public int getSeconds() {
        return this.seconds;
    }

    public void setAlbummid(String albummid) {
        this.albummid = albummid;
    }

    public String getAlbummid() {
        return this.albummid;
    }

    public void setSongid(int songid) {
        this.songid = songid;
    }

    public int getSongid() {
        return this.songid;
    }

    public void setSingerid(int singerid) {
        this.singerid = singerid;
    }

    public int getSingerid() {
        return this.singerid;
    }

    public void setAlbumpic_big(String albumpic_big) {
        this.albumpic_big = albumpic_big;
    }

    public String getAlbumpic_big() {
        return this.albumpic_big;
    }

    public void setAlbumpic_small(String albumpic_small) {
        this.albumpic_small = albumpic_small;
    }

    public String getAlbumpic_small() {
        return this.albumpic_small;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }

    public String getDownUrl() {
        return this.downUrl;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }

    public void setSingername(String singername) {
        this.singername = singername;
    }

    public String getSingername() {
        return this.singername;
    }

    public void setAlbumid(int albumid) {
        this.albumid = albumid;
    }

    public int getAlbumid() {
        return this.albumid;
    }

    @Override
    public String toString() {
        return "Music{" +
                "songname='" + songname + '\'' +
                ", seconds=" + seconds +
                ", albummid='" + albummid + '\'' +
                ", songid=" + songid +
                ", singerid=" + singerid +
                ", albumpic_big='" + albumpic_big + '\'' +
                ", albumpic_small='" + albumpic_small + '\'' +
                ", downUrl='" + downUrl + '\'' +
                ", url='" + url + '\'' +
                ", singername='" + singername + '\'' +
                ", albumid=" + albumid +
                '}';
    }
}

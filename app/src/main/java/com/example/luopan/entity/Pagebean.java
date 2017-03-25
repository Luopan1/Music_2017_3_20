package com.example.luopan.entity;

import java.util.List;

/**
 * Created by LuoPan on 2017/3/23 10:30.
 */
public class Pagebean {
    private List<Music> songlist ;

    private int total_song_num;

    private int ret_code;

    private String update_time;

    private int color;

    private int cur_song_num;

    private int comment_num;

    private int currentPage;

    private int song_begin;

    private int totalpage;

    public void setSonglist(List<Music> songlist){
        this.songlist = songlist;
    }
    public List<Music> getSonglist(){
        return this.songlist;
    }
    public void setTotal_song_num(int total_song_num){
        this.total_song_num = total_song_num;
    }
    public int getTotal_song_num(){
        return this.total_song_num;
    }
    public void setRet_code(int ret_code){
        this.ret_code = ret_code;
    }
    public int getRet_code(){
        return this.ret_code;
    }
    public void setUpdate_time(String update_time){
        this.update_time = update_time;
    }
    public String getUpdate_time(){
        return this.update_time;
    }
    public void setColor(int color){
        this.color = color;
    }
    public int getColor(){
        return this.color;
    }
    public void setCur_song_num(int cur_song_num){
        this.cur_song_num = cur_song_num;
    }
    public int getCur_song_num(){
        return this.cur_song_num;
    }
    public void setComment_num(int comment_num){
        this.comment_num = comment_num;
    }
    public int getComment_num(){
        return this.comment_num;
    }
    public void setCurrentPage(int currentPage){
        this.currentPage = currentPage;
    }
    public int getCurrentPage(){
        return this.currentPage;
    }
    public void setSong_begin(int song_begin){
        this.song_begin = song_begin;
    }
    public int getSong_begin(){
        return this.song_begin;
    }
    public void setTotalpage(int totalpage){
        this.totalpage = totalpage;
    }
    public int getTotalpage(){
        return this.totalpage;
    }
    @Override
    public String toString() {
        return "Pagebean{" +
                "Music=" + songlist +
                ", total_song_num=" + total_song_num +
                ", ret_code=" + ret_code +
                ", update_time='" + update_time + '\'' +
                ", color=" + color +
                ", cur_song_num=" + cur_song_num +
                ", comment_num=" + comment_num +
                ", currentPage=" + currentPage +
                ", song_begin=" + song_begin +
                ", totalpage=" + totalpage +
                '}';
    }
}

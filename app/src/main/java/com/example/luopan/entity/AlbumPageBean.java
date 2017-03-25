package com.example.luopan.entity;

import java.util.List;

/**
 * Created by LuoPan on 2017/3/23 14:31.
 */
public class AlbumPageBean {
    private String w;

    private int allPages;

    private int ret_code;

    private List<Contentlist> contentlist ;

    private int currentPage;

    private String notice;

    private int allNum;

    private int maxResult;

    public void setW(String w){
        this.w = w;
    }
    public String getW(){
        return this.w;
    }
    public void setAllPages(int allPages){
        this.allPages = allPages;
    }
    public int getAllPages(){
        return this.allPages;
    }
    public void setRet_code(int ret_code){
        this.ret_code = ret_code;
    }
    public int getRet_code(){
        return this.ret_code;
    }
    public void setContentlist(List<Contentlist> contentlist){
        this.contentlist = contentlist;
    }
    public List<Contentlist> getContentlist(){
        return this.contentlist;
    }
    public void setCurrentPage(int currentPage){
        this.currentPage = currentPage;
    }
    public int getCurrentPage(){
        return this.currentPage;
    }
    public void setNotice(String notice){
        this.notice = notice;
    }
    public String getNotice(){
        return this.notice;
    }
    public void setAllNum(int allNum){
        this.allNum = allNum;
    }
    public int getAllNum(){
        return this.allNum;
    }
    public void setMaxResult(int maxResult){
        this.maxResult = maxResult;
    }
    public int getMaxResult(){
        return this.maxResult;
    }

}

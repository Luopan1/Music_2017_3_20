package com.example.luopan.entity;

/**
 * Created by LuoPan on 2017/3/23 14:36.
 */
public class AlbumShowapi_res_body {
    private int ret_code;

    private AlbumPageBean pagebean;

    public void setRet_code(int ret_code){
        this.ret_code = ret_code;
    }
    public int getRet_code(){
        return this.ret_code;
    }
    public void setPagebean(AlbumPageBean pagebean){
        this.pagebean = pagebean;
    }
    public AlbumPageBean getPagebean(){
        return this.pagebean;
    }
}

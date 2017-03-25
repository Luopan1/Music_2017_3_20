package com.example.luopan.entity;

/**
 * Created by LuoPan on 2017/3/23 10:32.
 */
public class Showapi_res_body {
    private int ret_code;

    private Pagebean pagebean;

    public void setRet_code(int ret_code){
        this.ret_code = ret_code;
    }
    public int getRet_code(){
        return this.ret_code;
    }
    public void setPagebean(Pagebean pagebean){
        this.pagebean = pagebean;
    }
    public Pagebean getPagebean(){
        return this.pagebean;
    }

    @Override
    public String toString() {
        return "Showapi_res_body{" +
                "ret_code=" + ret_code +
                ", pagebean=" + pagebean +
                '}';
    }
}

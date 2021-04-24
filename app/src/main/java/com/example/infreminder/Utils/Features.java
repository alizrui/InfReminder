package com.example.infreminder.Utils;

public class Features {
    private String desc;
    private boolean only_once;

    public Features(){
    }

    public Features(String desc, boolean only_once /* Añadir más */){
        this.desc = desc;
        this.only_once = only_once;
    }

    public boolean isOnly_once() {
        return only_once;
    }

    public void setOnly_once(boolean only_once) {
        this.only_once = only_once;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

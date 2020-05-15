package com.example.cj.videoeditor.gpufilter;

import com.example.cj.videoeditor.gpufilter.helper.MagicFilterType;

/**
 * Created by Administrator on 2018/7/31.
 */

public class FilterList {
    String name;
    MagicFilterType filters ;
    public FilterList(){

    }
    public  FilterList(String name,MagicFilterType filter){
        this.name= name;
        this.filters= filter;
    }
    public MagicFilterType getFilters() {
        return filters;
    }

    public void setFilters(MagicFilterType filters) {
        this.filters = filters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

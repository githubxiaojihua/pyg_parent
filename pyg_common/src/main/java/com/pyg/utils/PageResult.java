package com.pyg.utils;

import java.io.Serializable;
import java.util.List;

public class PageResult<T> implements Serializable {
    private static final long serialVersionUID = 1l;
    //mybatis分页插件 pagehlper 插件
    //分页实现方式:
    //一、sql语句:
    //1,limit
    //2,count
    //二、分页插件:
    //配置插件: sqlMapConfig
    //导入插件jar文件


    //总记录数
    private Long total;

    public PageResult(Long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    //总记录
    private List<T> rows;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}

package com.wangjikai.util;

import java.util.List;

/**
 * Created by 22717 on 2017/11/13.
 * 简单分页类，与bootgrid集成
 */
public class Page<T> {
    private List rows;  //对象数据列表
    private int rowCount = 10;  //每页记录数
    private int current = 1; //当前页
    private int total;  //总行数

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}

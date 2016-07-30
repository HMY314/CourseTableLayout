package com.hmy.coursetablelayout.model;

import java.io.Serializable;

/**
 * Created by HMY on 2016/7
 */
public class CourseModel implements Serializable {
    private static final long serialVersionUID = 1L;

    public String id;
    public int week;
    public String name;
    /**
     * 开始上课节次
     */
    public int start;
    /**
     * 共几节课
     */
    public int step;
}

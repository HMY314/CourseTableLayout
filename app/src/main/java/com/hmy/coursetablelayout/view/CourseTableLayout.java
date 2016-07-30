package com.hmy.coursetablelayout.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hmy.coursetablelayout.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HMY on 2016/7
 */
public abstract class CourseTableLayout<T> extends LinearLayout {

    protected Context mContext;
    private ScrollView mScrollView;
    private LinearLayout mRootLinearLayout;
    private LinearLayout mDayLinearLayout;
    private LinearLayout mCourseTableLinearLayout;

    protected String[] mDayLabels = new String[]{"周一", "周二", "周三", "周四", "周五", "周六", "周日"};
    protected String[] mTimeLabels = new String[]{"1", "2", "3", "4", "5", "6", "7", "8"};
    /**
     * 列
     */
    protected int mDayCount = 7;
    /**
     * 行
     */
    protected int mTimeCount = 8;

    //***********宽高************
    protected int mWidth = 0;
    protected int mDayTableWidth = 80;
    protected int mDayTableHeight = 50;
    protected int mCourseTableWidth = mDayTableWidth;
    protected int mCourseTableHeight = 100;
    protected int mTimeTableWidth = 0;//默认为0，是为了用于判断是否可以自定义
    protected int mTimeTableHeight = mCourseTableHeight;
    //***************颜色**************
    protected int mDayTextColor = Color.GRAY;
    protected int mTimeTextColor = mDayTextColor;
    protected int mCourseTextColor = Color.WHITE;
    //***************背景*****************
    protected int mEmptyTableBgRes = R.drawable.bg_course_table_empty_selector;
    protected int mDayTableBgRes = R.drawable.bg_day_table_shape;
    protected int mTimeTableBgRes = mDayTableBgRes;
    //***************字体大小*************
    protected int mDayTextSize = 14;
    protected int mTimeTextSize = 14;
    protected int mCourseTextSize = 14;
    //************************************
    private List<T> mCourseList = new ArrayList<>();

    private boolean mIsFirst = true;
    private boolean mIsShowDefault = false;
    private int mDataTempPosition = 0;

    public CourseTableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CourseTableLayout(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mDayCount = mDayLabels.length;
        mTimeCount = mTimeLabels.length;
        initView(context);
    }

    private void initView(Context context) {
        mScrollView = new ScrollView(context);
        mScrollView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        mRootLinearLayout = new LinearLayout(context);
        mRootLinearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        mRootLinearLayout.setOrientation(VERTICAL);

        mDayLinearLayout = new LinearLayout(context);
        mDayLinearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        mDayLinearLayout.setOrientation(HORIZONTAL);

        mCourseTableLinearLayout = new LinearLayout(context);
        mCourseTableLinearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        mCourseTableLinearLayout.setOrientation(HORIZONTAL);

        addView(mRootLinearLayout);
        mRootLinearLayout.addView(mDayLinearLayout);
        mRootLinearLayout.addView(mScrollView);
        mScrollView.addView(mCourseTableLinearLayout);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mWidth = right - left;
        int itemWidth;
        if (mTimeTableWidth > 0) {
            itemWidth = (mWidth - mTimeTableWidth) / mDayCount;
        } else {
            itemWidth = mWidth / (mDayCount + 1);
            mTimeTableWidth = itemWidth;
        }

        mDayTableWidth = itemWidth;
        mDayTableHeight = mDayTableWidth * 3 / 4;
        mCourseTableWidth = mDayTableWidth;
        mCourseTableHeight = mCourseTableWidth * 4 / 3;
        mTimeTableHeight = mCourseTableHeight;

        if (mIsFirst) {
            if (mIsShowDefault) {
                mTimeCount = mTimeLabels.length;
            } else {
                mTimeCount = 0;
            }
            notifyDataSetChanged();
            mIsFirst = false;
        }
    }

    public void setTimeTableWidth(int width) {
        if (width > 0) {
            mTimeTableWidth = width;
        }
    }

    public void setIsShowDefault(boolean isShow) {
        mIsShowDefault = isShow;
    }

    public void setDayTableBackgroundResource(int res) {
        mDayTableBgRes = res;
    }

    public void setTimeTableBackgroundResource(int res) {
        mTimeTableBgRes = res;
    }

    public void setEmptyTableBackgroundResource(int res) {
        mEmptyTableBgRes = res;
    }

    public void setDayTextColor(int color) {
        mDayTextColor = color;
    }

    public void setTimeTextColor(int color) {
        mTimeTextColor = color;
    }

    public void setCourseTextColor(int color) {
        mCourseTextColor = color;
    }

    public void setDayTextSize(int size) {
        mDayTextSize = size;
    }

    public void setTimeTextSize(int size) {
        mTimeTextSize = size;
    }

    public void setCourseTextSize(int size) {
        mCourseTextSize = size;
    }

    public void setDayLabels(String[] dayLabels) {
        mDayLabels = dayLabels;
        mDayCount = dayLabels.length;
    }

    public void setCourseTimeLabels(String[] courseTimeLabels) {
        mTimeLabels = courseTimeLabels;
        mTimeCount = courseTimeLabels.length;
    }

    public List<T> getCourseList() {
        return mCourseList;
    }

    public void setData(List<T> list) {
        mCourseList = list;
        if (!mIsFirst) {
            notifyDataSetChanged();
        }
    }

    public void notifyDataSetChanged() {
        mDayLinearLayout.removeAllViews();
        initDayLayout();
        mCourseTableLinearLayout.removeAllViews();
        initTimeLayout();
        initCourseLayout();
    }

    private void initCourseLayout() {
        for (int day = 0; day < mDayCount; day++) {
            LinearLayout oneDayLinearLayout = new LinearLayout(mContext);
            oneDayLinearLayout.setOrientation(VERTICAL);
            oneDayLinearLayout.setLayoutParams(new ViewGroup.LayoutParams(mCourseTableWidth, mCourseTableHeight * mTimeCount));
            for (int time = 0; time < mTimeCount; time++) {
                CourseFlag courseFlag = getCourse(day, time);
                if (!courseFlag.isContinuousCourse) {
                    oneDayLinearLayout.addView(getCourseTextView(courseFlag.course, mDataTempPosition, day, time));
                }
            }
            mCourseTableLinearLayout.addView(oneDayLinearLayout);
        }
    }

    private CourseFlag getCourse(int day, int time) {
        int courseSize = getListSize(mCourseList);
        CourseFlag courseFlag = new CourseFlag();
        for (int i = 0; i < courseSize; i++) {
            T course = mCourseList.get(i);
            boolean[] find = compareToCourse(course, day, time);
            courseFlag.isContinuousCourse = find[1];
            if (find[0]) {
                mDataTempPosition = i;
                courseFlag.course = course;
                return courseFlag;
            }
        }
        return courseFlag;
    }

    private class CourseFlag {
        public T course = null;
        public boolean isContinuousCourse = false;
    }

    private TextView getCourseTextView(T course, int position, int day, int time) {
        TextView textView = new TextView(mContext);
        textView.setTextSize(mCourseTextColor);
        textView.setTextColor(mCourseTextColor);
        textView.setGravity(Gravity.CENTER);
        textView.setLayoutParams(new ViewGroup.LayoutParams(mCourseTableWidth, mCourseTableHeight));

        if (course == null) {
            textView.setBackgroundResource(mEmptyTableBgRes);
            showEmptyCourse(textView, day, time, mCourseTableWidth, mCourseTableHeight);
        } else {
            textView.setBackgroundResource(mTimeTableBgRes);
            showCourse(textView, course, position, day, time, mCourseTableWidth, mCourseTableHeight);
        }
        textView.setOnClickListener(new OnClickCourseListener(course, position, day, time));

        return textView;
    }

    private class OnClickCourseListener implements OnClickListener {
        private T course;
        private int position;
        private int day;
        private int time;

        private OnClickCourseListener(T course, int position, int day, int time) {
            this.course = course;
            this.position = position;
            this.day = day;
            this.time = time;
        }

        @Override
        public void onClick(View view) {
            if (course == null) {
                onClickEmptyCourse((TextView) view, day, time);
            } else {
                onClickCourse((TextView) view, mCourseList.get(position), position, day, time);
            }
        }
    }

    private void initTimeLayout() {
        LinearLayout timeLinearLayout = new LinearLayout(mContext);
        timeLinearLayout.setOrientation(VERTICAL);
        timeLinearLayout.setLayoutParams(new ViewGroup.LayoutParams(mTimeTableWidth, mTimeTableHeight * mTimeCount));

        for (int i = 0; i < mTimeCount; i++) {
            timeLinearLayout.addView(getTimeTextView(i));
        }
        mCourseTableLinearLayout.addView(timeLinearLayout);
    }

    private TextView getTimeTextView(int i) {
        TextView textView = new TextView(mContext);
        textView.setLayoutParams(new ViewGroup.LayoutParams(mTimeTableWidth, mTimeTableHeight));
        textView.setBackgroundResource(mTimeTableBgRes);
        textView.setText(mTimeLabels[i]);
        textView.setTextSize(mTimeTextSize);
        textView.setTextColor(mTimeTextColor);
        textView.setGravity(Gravity.CENTER);

        customTimeText(textView, i, mTimeLabels[i]);
        return textView;
    }

    private void initDayLayout() {
        //第一个空白块
        TextView firstTextView = new TextView(mContext);
        firstTextView.setLayoutParams(new ViewGroup.LayoutParams(mTimeTableWidth, mDayTableHeight));
        firstTextView.setBackgroundResource(mDayTableBgRes);
        firstTextView.setTextSize(mDayTextSize);
        firstTextView.setGravity(Gravity.CENTER);
        mDayLinearLayout.addView(firstTextView);
        showFirstTextView(firstTextView);

        //添加周
        for (int i = 0; i < mDayCount; i++) {
            mDayLinearLayout.addView(getDayTextView(i));
        }
    }

    private TextView getDayTextView(int i) {
        TextView textView = new TextView(mContext);
        textView.setLayoutParams(new ViewGroup.LayoutParams(mDayTableWidth, mDayTableHeight));
        textView.setBackgroundResource(mDayTableBgRes);
        textView.setText(mDayLabels[i]);
        textView.setTextSize(mDayTextSize);
        textView.setTextColor(mDayTextColor);
        textView.setGravity(Gravity.CENTER);

        customDayText(textView, i, mDayLabels[i]);
        return textView;
    }

    private int getListSize(List<T> list) {
        if (list == null || list.size() == 0) {
            return 0;
        }
        return list.size();
    }

    /**
     * 判断该课程是否是当前时间的课程
     *
     * @param course
     * @param dayPosition
     * @param timePosition
     * @return 返回boolean 数组长度为2，
     * boolean[0]：true 代表该课程是当前时间点的课程，boolean[1]：true 代表当前时间点是上面的连续课程
     */
    protected abstract boolean[] compareToCourse(T course, int dayPosition, int timePosition);

    /**
     * 对左上角第一个TextView自定义显示
     *
     * @param firstTextView
     */
    protected void showFirstTextView(TextView firstTextView) {
    }

    /**
     * 显示空课程（默认文本已显示，这里用于自定义效果）
     */
    protected void showEmptyCourse(TextView textView, int dayPosition, int timePosition, int oneTableWidth, int oneTableHeight) {
    }

    /**
     * 显示课程到控件，并且需要根据课程节数改变控件高度
     *
     * @param textView
     * @param course         课程数据
     * @param oneTableWidth  一个课程控件宽度
     * @param oneTableHeight 一个课程控件高度
     */
    protected abstract void showCourse(TextView textView, T course, int dataPosition, int dayPosition, int timePosition, int oneTableWidth, int oneTableHeight);

    /**
     * 自定义日期文本
     *
     * @param textView
     * @param dayPosition
     * @param dayLabel
     */
    protected void customDayText(TextView textView, int dayPosition, String dayLabel) {
    }

    /**
     * 自定义时间文本
     *
     * @param textView
     * @param timePosition
     * @param timeLabel
     */
    protected void customTimeText(TextView textView, int timePosition, String timeLabel) {
    }

    protected abstract void onClickEmptyCourse(TextView textView, int dayPosition, int timePosition);

    protected abstract void onClickCourse(TextView textView, T course, int dataPosition, int dayPosition, int timePosition);
}

package com.hmy.coursetablelayout.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.hmy.coursetablelayout.R;
import com.hmy.coursetablelayout.model.CourseModel;

/**
 * Created by HMY on 2016/7
 */

public class courseTableTest2Layout extends CourseTableLayout<CourseModel> {

    private int BG_COURSE[] = new int[]{R.drawable.bg_course_table_blue_selector, R.drawable.bg_course_table_green_selector,
            R.drawable.bg_course_table_red_selector};

    private OnClickCourseListener mOnClickCourseListener;

    public courseTableTest2Layout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData();
    }

    public courseTableTest2Layout(Context context) {
        super(context);
        initData();
    }

    private void initData() {
        setIsShowDefault(true);
    }

    @Override
    protected boolean[] compareToCourse(CourseModel course, int dayPosition, int timePosition) {
        boolean[] result = new boolean[2];
        if (course.week == dayPosition + 1 && course.start == timePosition + 1) {
            result[0] = true;
        }
        return result;
    }

    @Override
    protected void showCourse(TextView textView, CourseModel course, int dataPosition, int dayPosition, int timePosition, int oneTableWidth, int oneTableHeight) {
//        textView.setText(course.name);
//        textView.setBackgroundResource(BG_COURSE[dataPosition % BG_COURSE.length]);
        textView.setBackgroundResource(R.drawable.bg_course_table_blue_selector);
    }

    @Override
    protected void customDayText(TextView textView, int dayPosition, String dayLabel) {

    }

    @Override
    protected void onClickEmptyCourse(TextView textView, int dayPosition, int timePosition) {
        if (mOnClickCourseListener != null) {
            mOnClickCourseListener.onClickEmptyCourse(textView, dayPosition, timePosition);
        }
    }

    @Override
    protected void onClickCourse(TextView textView, CourseModel course, int dataPosition, int dayPosition, int timePosition) {
        if (mOnClickCourseListener != null) {
            mOnClickCourseListener.onClickCourse(textView, course, dataPosition, dayPosition, timePosition);
        }
    }

    public void setOnClickCourseListener(OnClickCourseListener listener) {
        mOnClickCourseListener = listener;
    }

    public interface OnClickCourseListener {
        void onClickCourse(TextView textView, CourseModel course, int dataPosition, int dayPosition, int timePosition);

        void onClickEmptyCourse(TextView textView, int dayPosition, int timePosition);
    }
}

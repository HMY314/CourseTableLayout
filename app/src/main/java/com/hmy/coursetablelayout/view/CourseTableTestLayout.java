package com.hmy.coursetablelayout.view;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.TextView;


import com.hmy.coursetablelayout.R;
import com.hmy.coursetablelayout.model.CourseModel;

import java.util.Calendar;

/**
 * Created by HMY on 2016/7
 */
public class CourseTableTestLayout extends CourseTableLayout<CourseModel> {

    private static final int TOTAL_DAY = 7;
    private int BG_COURSE[] = new int[]{R.drawable.bg_course_table_blue_selector, R.drawable.bg_course_table_green_selector,
            R.drawable.bg_course_table_red_selector};
    private int mCurrentMonth;
    private int mNextMonth;

    public CourseTableTestLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData();
    }

    public CourseTableTestLayout(Context context) {
        super(context);
        initData();
    }

    private void initData() {
        setIsShowDefault(true);
        Calendar calendar = Calendar.getInstance();
        mCurrentMonth = calendar.get(Calendar.MONTH) + 1;
        mNextMonth = mCurrentMonth + 1;
        calendar.add(Calendar.DAY_OF_MONTH, -1);

        String dayLabels[] = new String[TOTAL_DAY];
        //从当天时间取7天作为日期
        for (int i = 0; i < dayLabels.length; i++) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            int today = calendar.get(Calendar.DAY_OF_MONTH);
            dayLabels[i] = String.valueOf(today);
        }
        setDayLabels(dayLabels);
    }

    @Override
    protected boolean[] compareToCourse(CourseModel course, int dayPosition, int timePosition) {
        boolean[] result = new boolean[2];
        if (course.week == dayPosition + 1) {
            if (course.start == timePosition + 1) {
                result[0] = true;
                result[1] = false;
            } else if (course.start < timePosition + 1 && course.start + course.step > timePosition + 1) {
                result[0] = true;
                result[1] = true;
            }
        }
        return result;
    }

    @Override
    protected void showCourse(TextView textView, CourseModel course, int dataPosition, int dayPosition, int timePosition, int oneTableWidth, int oneTableHeight) {
        textView.setText(course.name);
        textView.setBackgroundResource(BG_COURSE[dataPosition % BG_COURSE.length]);
        textView.setLayoutParams(new ViewGroup.LayoutParams(oneTableWidth, oneTableHeight * course.step));
    }

    @Override
    protected void customDayText(TextView textView, int dayPosition, String dayLabel) {
        if (dayPosition == 0) {//第一列显示当月的月份
            SpannableString msp = getMonthDayTextSpannableString(mCurrentMonth, dayLabel);
            textView.setText(msp);
        } else if (dayLabel.equals("1")) {//当月下个月的1号显示月份
            SpannableString msp = getMonthDayTextSpannableString(mNextMonth, dayLabel);
            textView.setText(msp);
        }
    }

    private SpannableString getMonthDayTextSpannableString(int month, String dayLabel) {
        String monthStr = month + "月";
        String str = monthStr + dayLabel;
        SpannableString msp = new SpannableString(str);
        msp.setSpan(new AbsoluteSizeSpan(24), 0, monthStr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        msp.setSpan(new ForegroundColorSpan(Color.RED), 0, monthStr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        msp.setSpan(new ForegroundColorSpan(mDayTextColor), monthStr.length(), str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return msp;
    }

    @Override
    protected void onClickEmptyCourse(TextView textView, int dayPosition, int timePosition) {

    }

    @Override
    protected void onClickCourse(TextView textView, CourseModel course, int dataPosition, int dayPosition, int timePosition) {

    }
}

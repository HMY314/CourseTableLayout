package com.hmy.coursetablelayout;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hmy.coursetablelayout.model.CourseModel;
import com.hmy.coursetablelayout.view.courseTableTest2Layout;
import com.hmy.coursetablelayout.view.CourseTableTestLayout;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    private CourseTableTestLayout mCourseTableTestLayout;
    private courseTableTest2Layout mCourseTableDefaultLayout;

    List<CourseModel> mList;
    List<CourseModel> mList2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        setListener();

        mCourseTableTestLayout.setCourseTimeLabels(getTimeLabels());

        mList = getData();
        mList2 = getData2();
        mCourseTableTestLayout.setData(mList);
        mCourseTableDefaultLayout.setData(mList2);
    }

    private void initView() {
        mCourseTableTestLayout = (CourseTableTestLayout) findViewById(R.id.layout_course);
        mCourseTableDefaultLayout = (courseTableTest2Layout) findViewById(R.id.layout_course2);
    }

    private void setListener() {
        mCourseTableDefaultLayout.setOnClickCourseListener(new courseTableTest2Layout.OnClickCourseListener() {
            @Override
            public void onClickCourse(TextView textView, CourseModel course, int dataPosition, int dayPosition, int timePosition) {
                mList2.remove(course);
                mCourseTableDefaultLayout.notifyDataSetChanged();
            }

            @Override
            public void onClickEmptyCourse(TextView textView, int dayPosition, int timePosition) {
                CourseModel courseModel = new CourseModel();
                courseModel.start = timePosition + 1;
                courseModel.week = dayPosition + 1;
                mList2.add(courseModel);
                mCourseTableDefaultLayout.notifyDataSetChanged();
            }
        });
    }

    public void clickBtn(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                mCourseTableTestLayout.setVisibility(View.VISIBLE);
                mCourseTableDefaultLayout.setVisibility(View.GONE);
                break;
            case R.id.btn2:
                mCourseTableTestLayout.setVisibility(View.GONE);
                mCourseTableDefaultLayout.setVisibility(View.VISIBLE);
                break;
        }
    }

    private List<CourseModel> getData() {
        mList = new ArrayList<>();

        mList.add(getCourseModel(1, "数学", 1, 1));
        mList.add(getCourseModel(1, "语文", 4, 2));

        mList.add(getCourseModel(2, "生物", 2, 2));
        mList.add(getCourseModel(2, "地理", 5, 3));

        mList.add(getCourseModel(3, "化学", 3, 1));
        mList.add(getCourseModel(3, "物理", 5, 1));

        mList.add(getCourseModel(4, "数学", 4, 1));
        mList.add(getCourseModel(4, "数学", 6, 2));

        mList.add(getCourseModel(5, "数学", 1, 2));
        mList.add(getCourseModel(5, "体育", 3, 1));
        mList.add(getCourseModel(5, "英语", 5, 1));

        mList.add(getCourseModel(6, "英语", 7, 2));

        mList.add(getCourseModel(7, "政治", 1, 1));
        mList.add(getCourseModel(7, "语文", 2, 1));
        mList.add(getCourseModel(7, "地理", 6, 2));

        return mList;
    }


    private List<CourseModel> getData2() {
        mList2 = new ArrayList<>();

        mList2.add(getCourseModel(2, "生物", 2, 1));
        mList2.add(getCourseModel(2, "地理", 7, 1));

        mList2.add(getCourseModel(3, "化学", 4, 1));
        mList2.add(getCourseModel(3, "物理", 5, 1));

        mList2.add(getCourseModel(4, "数学", 3, 1));
        mList2.add(getCourseModel(4, "数学", 4, 1));
        mList2.add(getCourseModel(4, "数学", 5, 1));
        mList2.add(getCourseModel(4, "数学", 6, 1));

        mList2.add(getCourseModel(5, "体育", 4, 1));
        mList2.add(getCourseModel(5, "英语", 5, 1));

        mList2.add(getCourseModel(6, "英语", 2, 1));
        mList2.add(getCourseModel(6, "英语", 7, 1));

        return mList2;
    }

    private CourseModel getCourseModel(int week, String name, int start, int step) {
        CourseModel model = new CourseModel();
        model.week = week;
        model.name = name;
        model.start = start;
        model.step = step;
        return model;
    }

    private String[] getTimeLabels() {
        String[] timeLabels = new String[10];
        for (int i = 0; i < timeLabels.length; i++) {
            timeLabels[i] = (i + 1) + ":00";
        }

        return timeLabels;
    }
}

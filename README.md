# CourseTableLayout
一个Android自动生成课程表的自定义控件

![](https://github.com/HMY314/CourseTableLayout/blob/master/imageCache/course_table.gif)

----------
##一、介绍
        1、根据传入课程数据，或日期数据、时间数据，动态显示课程表；
        2、顶部日期行的显示样式可以自定义，空课程、课程的内容及背景也可自定义；
        3、第一列，及时间列的宽度可以设置，默认为0，则宽度与课程格子的宽度相同，内容与背景也可以自定义；
        4、当课程页面超出手机屏幕高度时，可以上下滑动，改变数据可刷新控件就可以更新界面；

----------
##二、使用方法

1、核心类是CourseTableLayout<T>，继承自LinearLayout的抽象类，所以我们实际项目使用需要继承它，这里使用了泛型，试课程表使用更加灵活，并要实现对应的抽象方法，如下：

        public abstract class CourseTableLayout<T> extends LinearLayout {
        //******************************其他代码省略**************************

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

----------
2、我这里用courseTableTestLayout继承CourseTableLayout实现，数据模型用CourseModel实体，这个根据具体业务可灵活更改。

        public class CourseModel implements Serializable {
            private static final long serialVersionUID = 1L;

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

        public class courseTableTestLayout extends CourseTableLayout<CourseModel> {

            private int BG_COURSE[] = new int[]{R.drawable.bg_course_table_blue_selector, R.drawable.bg_course_table_green_selector,
                    R.drawable.bg_course_table_red_selector};

            private OnClickCourseListener mOnClickCourseListener;

            public courseTableTestLayout(Context context, AttributeSet attrs) {
                super(context, attrs);
                initData();
            }

            public courseTableTestLayout(Context context) {
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

----------
3、在xml中实现

            <com.hmy.coursetablelayout.view.CourseTableTestLayout
                android:id="@+id/layout_course"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_marginTop="5dp">
            </com.hmy.coursetablelayout.view.CourseTableTestLayout>

----------
4、使用：

        List<CourseModel> mList = getData();//课程数据
        CourseTableTestLayout mCourseTableTestLayout = (CourseTableTestLayout) findViewById(R.id.layout_course);
        //其他设置方法请在代码中查看
        mCourseTableTestLayout.setData(mList);

----------

![](https://github.com/HMY314/CourseTableLayout/blob/master/imageCache/course_table1.png)![](https://github.com/HMY314/CourseTableLayout/blob/master/imageCache/course_table2.png)

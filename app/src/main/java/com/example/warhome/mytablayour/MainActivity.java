package com.example.warhome.mytablayour;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener, CompoundButton.OnCheckedChangeListener {

    public String[] months = {"Января", "Февраля", "Марта", "Апреля", "Мая",
    "Июня", "Июля", "Августа", "Сентября", "Октября", "Ноября", "Декабря" };
    public String[] daysOfWeek = {"Понедельник", "Вторник", "Среда","Четверг","Пятница","Суббота", "Воскресенье"};
    public static List<LessonForAdapter> mondayList;
    public static List<LessonForAdapter> tuesdayList;
    public static List<LessonForAdapter> wednesdayList;
    public static List<LessonForAdapter> thursdayList;
    public static List<LessonForAdapter> fridayList;
    public static List<LessonForAdapter> saturdayList;
    public static List<Lesson> lessonList;

    private DrawerLayout mDrawerLayout;
    private String JSON;
    private boolean isEnum = false; // 0 - числитель 1 - знаменатель

    List<MyFragment> myFragmentList;
    ViewPager viewPager;

    void initializeData() {

        lessonList = new ArrayList<>();
        mondayList = new ArrayList<>();
        tuesdayList = new ArrayList<>();
        wednesdayList = new ArrayList<>();
        thursdayList = new ArrayList<>();
        fridayList = new ArrayList<>();
        saturdayList = new ArrayList<>();

        AppDatabase database = App.getInstance().getDatabase();
        LessonDao lessonDao = database.lessonDao();
        List<Lesson> lessonList = lessonDao.getAll();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        int curr_course   = sharedPreferences.getInt("CURR_COURSE", -1);
        int curr_group    = sharedPreferences.getInt("CURR_GROUP", -1);

        //curr_course = 3;
        //curr_group = 7;
        boolean curr_isEnum = true;

        for(Lesson lesson : lessonList) {
            if (lesson.getGroup()==curr_group&&lesson.getCourse()==curr_course) {
                if(lesson.getNumerator().equals("0")) curr_isEnum=false;
                else if(lesson.getNumerator().equals("1")) curr_isEnum=true;

                if (lesson.getNumerator().equals("") || curr_isEnum == isEnum) {
                    switch (lesson.getDayWeek()) {
                        case "0":
                                mondayList.add(new LessonForAdapter(lesson.getTitle(), lesson.getTime(), lesson.getTeacher(), lesson.getRoom()));
                            break;
                        case "1":
                                tuesdayList.add(new LessonForAdapter(lesson.getTitle(), lesson.getTime(), lesson.getTeacher(), lesson.getRoom()));
                            break;
                        case "2":
                                wednesdayList.add(new LessonForAdapter(lesson.getTitle(), lesson.getTime(), lesson.getTeacher(), lesson.getRoom()));
                            break;
                        case "3":
                                thursdayList.add(new LessonForAdapter(lesson.getTitle(), lesson.getTime(), lesson.getTeacher(), lesson.getRoom()));
                            break;
                        case "4":
                                fridayList.add(new LessonForAdapter(lesson.getTitle(), lesson.getTime(), lesson.getTeacher(), lesson.getRoom()));
                            break;
                        case "5":
                                saturdayList.add(new LessonForAdapter(lesson.getTitle(), lesson.getTime(), lesson.getTeacher(), lesson.getRoom()));
                            break;
                    }
                }
            }
        }
        Collections.sort(mondayList);
        Collections.sort(tuesdayList);
        Collections.sort(wednesdayList);
        Collections.sort(thursdayList);
        Collections.sort(fridayList);
        Collections.sort(saturdayList);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        //TabPosition = tab.getPosition();
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        viewPager = findViewById(R.id.viewpager);
        isEnum = sharedPreferences.getBoolean("IS_ENUM", false);

        //------------------------------ ToolBar -----------------------------------------//
        Toolbar toolbar = findViewById(R.id.my_own_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setCustomView(R.layout.switch_layout);
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        //--------------------------------------------------------------------------------//

        //------------------------------- Switch -----------------------------------------//
        Switch sw = findViewById(R.id.app_switch);
        if(isEnum)sw.setChecked(true);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                boolean tempIsEnum = isEnum;
                isEnum = compoundButton.isChecked();
                initializeData();
                setupViewPager(viewPager);
                isEnum = tempIsEnum;
            }
        });

        /*if(onCheckedChangedStatus) {
            onCheckedChangedStatus = false;
            recreate();
        }*/
        //---------------------------------------------------------------------------------//

        //---------------------------- Working with calendar ------------------------------//
        int temp_day = sharedPreferences.getInt("TEMP_DAY", -1);
        MyCalendar myCalendar = new MyCalendar(temp_day, isEnum);
        editor.putBoolean("IS_ENUM" , myCalendar.isEnumCalendar);
        editor.putInt("TEMP_DAY", myCalendar.curr_day);
        editor.apply();
        isEnum = myCalendar.isEnumCalendar;
        //---------------------------------------------------------------------------------//
        initializeData();

        //-------------------------------- NavigationDrawer ------------------------------//
        mDrawerLayout = findViewById(R.id.drawer_layout);
        final NavigationView navigationView = findViewById(R.id.nav_view);

        // Print data in header
        View headerView = navigationView.getHeaderView(0);
        TextView title_day_view = headerView.findViewById(R.id.title_day);
        TextView title_enum_view = headerView.findViewById(R.id.title_enum);
        String s_title_day = myCalendar.curr_day + " " + months[myCalendar.month] + ", " + daysOfWeek[myCalendar.day_of_week];
        title_day_view.setText(s_title_day);

        if(myCalendar.isEnumCalendar) title_enum_view.setText("Знаменатель");
        else title_enum_view.setText("Числитель");

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);

                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        switch (menuItem.getItemId()){
                            case R.id.nav_choice_timetable:
                                startActivity(new Intent(navigationView.getContext(), NavChoiceActivity.class));
                                initializeData();
                                setupViewPager(viewPager);
                                break;
                            case R.id.nav_change_timetable:
                                startActivity(new Intent(navigationView.getContext(), NavEditActivity.class));
                                initializeData();
                                setupViewPager(viewPager);
                                break;
                            case R.id.nav_update_timetable:
                                if(InternetConnectionChecker.checkConnection(getApplicationContext())) {
                                    ApiService api = Retro.getApiService();
                                    Call<LessonList> call = api.getJson();
                                    call.enqueue(new Callback<LessonList>() {
                                        @Override
                                        public void onResponse(Call<LessonList> call, Response<LessonList> response) {
                                            if (response.isSuccessful()) {
                                                lessonList = response.body().getLessons();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<LessonList> call, Throwable t) {
                                            Toast toast = Toast.makeText(getApplicationContext(), t.getMessage(),Toast.LENGTH_SHORT);
                                            toast.show();
                                        }
                                    });

                                    AppDatabase database = App.getInstance().getDatabase();
                                    LessonDao lessonDao = database.lessonDao();
                                    List<Lesson> oldLessons = lessonDao.getAll();
                                    for (int i = 0; i < oldLessons.size(); i++) {
                                        lessonDao.delete(oldLessons.get(i));
                                    }
                                    for (int i = 0; i < lessonList.size(); i++) {
                                        lessonDao.insert(lessonList.get(i));
                                    }
                                    initializeData();
                                    setupViewPager(viewPager);
                                }
                                else {
                                    Toast toast = Toast.makeText(getApplicationContext(), "Отсутствует интернет соединение",Toast.LENGTH_SHORT);
                                    toast.show();
                                }
                                break;
                            default:
                                break;
                        }
                        return true;
                    }
                });
        //----------------------------------------------------------------------------------//

        setupViewPager(viewPager);
        TabLayout tabLayout = findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
        // Show the start fragment depending of day_of_week_learn
        if(myCalendar.day_of_week_learn < 2) viewPager.setCurrentItem(0);
        else viewPager.setCurrentItem(myCalendar.day_of_week_learn - 2);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initializeData();
        setupViewPager(viewPager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setupViewPager(ViewPager viewPager) {
        myFragmentList = new ArrayList<>();
        for(int i = 0; i < 6; i++) {
            Bundle bundle = new Bundle();
            bundle.putInt("key",i);
            MyFragment mf = new MyFragment();
            mf.setArguments(bundle);
            myFragmentList.add(mf);
        }

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        for(int i = 0; i < 6; i++) {
            adapter.addFragment(myFragmentList.get(i), daysOfWeek[i]);
        }
        viewPager.setAdapter(adapter);
    }
}
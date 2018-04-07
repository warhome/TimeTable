package com.example.warhome.mytablayour;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
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
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener, CompoundButton.OnCheckedChangeListener {

    public String[] months = {"Января", "Февраля", "Марта", "Апреля", "Мая",
    "Июня", "Июля", "Августа", "Сентября", "Октября", "Ноября", "Декабря" };
    public String[] daysOfWeek = {"Понедельник", "Вторник", "Среда","Четверг","Пятница","Суббота", "Воскресенье"};

    public static List<Lesson> mondayList;
    public static List<Lesson> tuesdayList;
    public static List<Lesson> wednesdayList;
    public static List<Lesson> thursdayList;
    public static List<Lesson> fridayList;
    public static List<Lesson> saturdayList;
    public static List<Lesson> testParserList;
    List<Lesson> lessonList;

    private String JSON;
    private DrawerLayout mDrawerLayout;
    private MyCalendar myCalendar;
    private boolean isEnum = false; // 0 - числитель 1 - знаменатель
    private boolean onCheckedChangedStatus = false;

    List<MyFragment> myFragmentList;

    private void initializeData() {

        lessonList = new ArrayList<>();
        mondayList = new ArrayList<>();
        tuesdayList = new ArrayList<>();
        wednesdayList = new ArrayList<>();
        thursdayList = new ArrayList<>();
        fridayList = new ArrayList<>();
        saturdayList = new ArrayList<>();
        testParserList = new ArrayList<>();

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        List<Allessons> AllessonsList = databaseAccess.getLessons();
        databaseAccess.close();

        final SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        int curr_course   = sharedPreferences.getInt("CURR_COURSE", -1);
        int curr_group    = sharedPreferences.getInt("CURR_GROUP", -1);

        curr_course = 3;
        curr_group = 7;
        boolean curr_isEnum = true;

        for(Allessons allessons: AllessonsList) {
            if (Integer.valueOf(allessons.getGroup())==curr_group&&Integer.valueOf(allessons.getCourse())==curr_course) {
                if(allessons.getIsEnum().equals("0")) curr_isEnum=false;
                else if(allessons.getIsEnum().equals("1")) curr_isEnum=true;

                switch (allessons.getDay_week()) {
                    case "0":
                        if (allessons.getIsEnum().equals("") || curr_isEnum == isEnum) {
                            mondayList.add(new Lesson(allessons.getTitle(), allessons.getTime(), allessons.getTeacher(), allessons.getRoom()));
                        }
                        break;
                    case "1":
                        if (allessons.getIsEnum().equals("") || curr_isEnum == isEnum) {
                            tuesdayList.add(new Lesson(allessons.getTitle(), allessons.getTime(), allessons.getTeacher(), allessons.getRoom()));
                        }
                        break;
                    case "2":
                        if (allessons.getIsEnum().equals("") || curr_isEnum == isEnum) {
                            wednesdayList.add(new Lesson(allessons.getTitle(), allessons.getTime(), allessons.getTeacher(), allessons.getRoom()));
                        }
                        break;
                    case "3":
                        if (allessons.getIsEnum().equals("") || curr_isEnum == isEnum) {
                            thursdayList.add(new Lesson(allessons.getTitle(), allessons.getTime(), allessons.getTeacher(), allessons.getRoom()));
                        }
                        break;
                    case "4":
                        if (allessons.getIsEnum().equals("") || curr_isEnum == isEnum) {
                            fridayList.add(new Lesson(allessons.getTitle(), allessons.getTime(), allessons.getTeacher(), allessons.getRoom()));
                        }
                        break;
                    case "5":
                        if (allessons.getIsEnum().equals("") || curr_isEnum == isEnum) {
                            saturdayList.add(new Lesson(allessons.getTitle(), allessons.getTime(), allessons.getTeacher(), allessons.getRoom()));
                        }
                        break;
                }
            }
        }

        /*mondayList.add(new Lesson("Теория Информации", "9:45-11:20", "Борзунов", "Л7"));
        mondayList.add(new Lesson("Теория Информации", "11:30-13:05", "Борзунов", "292"));
        mondayList.add(new Lesson("Мат. методы в ест.", "13:25-15:00", "Туровский", "-"));
        mondayList.add(new Lesson("Мат. методы в ест.", "13:25-15:00", "Туровский", "-"));
        mondayList.add(new Lesson("Мат. методы в ест.", "15:10-16:45", "Туровский", "-"));
        mondayList.add(new Lesson("test_lesson", "test_time", "test_teacher", "test_room"));
        tuesdayList.add(new Lesson("test_lesson_thuesday", "test_time", "test_teacher", "test_room"));
        wednesdayList.add(new Lesson("test_lesson_wednsday", "test_time", "test_teacher", "test_room"));
        thursdayList.add(new Lesson("test_lesson_thursday", "test_time", "test_teacher", "test_room"));
        fridayList.add(new Lesson("test_lesson_friday", "test_time", "test_teacher", "test_room"));
        saturdayList.add(new Lesson("test_lesson_saturday", "test_time", "test_teacher", "test_room"));*/
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

        final SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        isEnum = sharedPreferences.getBoolean("IS_ENUM", false);

        final ViewPager viewPager = findViewById(R.id.viewpager);
        Toolbar toolbar = findViewById(R.id.my_own_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setCustomView(R.layout.switch_layout);
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        }

        //------------------------------- Switch -----------------------------------------//
        Switch sw = findViewById(R.id.app_switch);
        if(sharedPreferences.getBoolean("IS_ENUM", false) == true)sw.setActivated(true);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()) editor.putBoolean("IS_ENUM", true);
                else editor.putBoolean("IS_ENUM", false);
                editor.apply();
                isEnum = sharedPreferences.getBoolean("IS_ENUM", false);
                initializeData();
                setupViewPager(viewPager);
            }
        });

        /*if(onCheckedChangedStatus) {
            onCheckedChangedStatus = false;
            recreate();
        }*/
        //---------------------------------------------------------------------------------//

        //---------------------------- Working with calendar ------------------------------//
        int temp_day = sharedPreferences.getInt("TEMP_DAY", -1);
        myCalendar = new MyCalendar(temp_day,isEnum);
        editor.putBoolean("IS_ENUM" , myCalendar.isEnumCalendar);
        editor.putInt("TEMP_DAY",myCalendar.curr_day);
        editor.apply();
        isEnum = myCalendar.isEnumCalendar;
        //---------------------------------------------------------------------------------//
        initializeData();

        //JSON
        /*URL url = null;
        Gson gson = new Gson();

        try {
            url = new URL("https://jsonplaceholder.typicode.com/posts");
            MyJSONParser myJSONParser = new MyJSONParser(url);
            myJSONParser.execute();
            JSON = myJSONParser.get();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Type collectionType = new TypeToken<Collection<Lesson>>(){}.getType();
        lessonList = gson.fromJson(JSON, collectionType);
        */
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
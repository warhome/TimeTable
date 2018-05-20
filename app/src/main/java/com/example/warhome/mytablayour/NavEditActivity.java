package com.example.warhome.mytablayour;

import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NavEditActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText editTitle;
    EditText editTime;
    EditText editTeacher;
    EditText editRoom;
    EditText editDayWeek;
    EditText editMod;
    Lesson lesson;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        // получим идентификатор выбранного пункта меню
        final int id = item.getItemId();
        editTitle = findViewById(R.id.editTitle);
        editTime = findViewById(R.id.editTime);
        editTeacher = findViewById(R.id.editTeacher);
        editRoom = findViewById(R.id.editPlace);
        editDayWeek = findViewById(R.id.editDayWeek);
        editMod = findViewById(R.id.editMod);
        Button button = findViewById(R.id.buttonAdd);
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        final int curr_course   = sharedPreferences.getInt("CURR_COURSE", -1);
        final int curr_group    = sharedPreferences.getInt("CURR_GROUP", -1);

        final String title = "test";
        final String time = "11:30-12:00";
        final String teacher = "teacher";
        final String room = "room";
        final String dayWeek = "0";
        final String numerator = "0";

        AppDatabase database = App.getInstance().getDatabase();
        final LessonDao lessonDao = database.lessonDao();
        List<Lesson> currLessons = lessonDao.getAll();
        int currId = 1;
        for (Lesson lesson:currLessons) {
            if(lesson.getId() > currId) currId = lesson.getId();
        }

        switch (id) {
            case R.id.newLessonEdit:
                SetActiveColorFilter();
                toolbar.setTitle("Новая пара");
                setSupportActionBar(toolbar);
                break;

            case R.id.updateLessonEdit:
                SetDefaultColorFilter();
                toolbar.setTitle("Изменение пары");
                setSupportActionBar(toolbar);
                break;
            case R.id.deleteLessonEdit:
                SetDefaultColorFilter();
                toolbar.setTitle("Удаление пары");
                setSupportActionBar(toolbar);
                break;
            default:
                break;
        }

        final int finalCurrId = currId;
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (id) {
                    //POST
                    case R.id.newLessonEdit:
                        if(InternetConnectionChecker.checkConnection(getApplicationContext())) {
                            lesson = new Lesson(
                                    finalCurrId+1,
                                    title,
                                    time,
                                    teacher,
                                    room,
                                    dayWeek,
                                    curr_course,
                                    curr_group,
                                    numerator
                            );

                            List<Lesson> list = new ArrayList<>();
                            list.add(lesson);
                            LessonList lessonList = new LessonList();
                            lessonList.setLessons(list);

                            ApiService api = Retro.getApiService();
                            Call<okhttp3.ResponseBody> call = api.newLesson(lessonList);
                            call.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    if(response.isSuccessful()) {
                                        Toast toast = Toast.makeText(getApplicationContext(), "Запись успешно добавлена", Toast.LENGTH_SHORT);
                                        toast.show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    Toast toast = Toast.makeText(getApplicationContext(), "Что-то пошло не так", Toast.LENGTH_SHORT);
                                    toast.show();
                                }
                            });
                        }
                        else  {
                            Toast toast = Toast.makeText(getApplicationContext() ,"Отсутствует интернет соединение", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                        break;
                    //UPDATE
                    case R.id.updateLessonEdit:
                        toolbar.setTitle("Изменение пары");
                        setSupportActionBar(toolbar);
                        break;
                    //DELETE
                    case R.id.deleteLessonEdit:
                        toolbar.setTitle("Удаление пары");
                        setSupportActionBar(toolbar);
                        break;
                    default:
                        break;
                }
            }


        };
        button.setOnClickListener(onClickListener);
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_edit);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Выберите способ редактирования");
        setSupportActionBar(toolbar);
        editTitle = findViewById(R.id.editTitle);
        editTime = findViewById(R.id.editTime);
        editTeacher = findViewById(R.id.editTeacher);
        editRoom = findViewById(R.id.editPlace);
        editDayWeek = findViewById(R.id.editDayWeek);
        editMod = findViewById(R.id.editMod);
        SetDefaultColorFilter();
    }

    public void SetDefaultColorFilter(){
        editTitle
                .getBackground()
                .setColorFilter(getResources().getColor(R.color.color_c1cc9c), PorterDuff.Mode.SRC_IN);
        editTime
                .getBackground()
                .setColorFilter(getResources().getColor(R.color.color_c1cc9c), PorterDuff.Mode.SRC_IN);
        editTeacher
                .getBackground()
                .setColorFilter(getResources().getColor(R.color.color_c1cc9c), PorterDuff.Mode.SRC_IN);
        editRoom
                .getBackground()
                .setColorFilter(getResources().getColor(R.color.color_c1cc9c), PorterDuff.Mode.SRC_IN);
        editDayWeek
                .getBackground()
                .setColorFilter(getResources().getColor(R.color.color_c1cc9c), PorterDuff.Mode.SRC_IN);
        editMod
                .getBackground()
                .setColorFilter(getResources().getColor(R.color.color_c1cc9c), PorterDuff.Mode.SRC_IN);
    }
    public void SetActiveColorFilter(){
        editTitle
                .getBackground()
                .setColorFilter(getResources().getColor(R.color.color_a11826), PorterDuff.Mode.SRC_IN);
        editTime
                .getBackground()
                .setColorFilter(getResources().getColor(R.color.color_a11826), PorterDuff.Mode.SRC_IN);
        editTeacher
                .getBackground()
                .setColorFilter(getResources().getColor(R.color.color_a11826), PorterDuff.Mode.SRC_IN);
        editRoom
                .getBackground()
                .setColorFilter(getResources().getColor(R.color.color_a11826), PorterDuff.Mode.SRC_IN);
        editDayWeek
                .getBackground()
                .setColorFilter(getResources().getColor(R.color.color_a11826), PorterDuff.Mode.SRC_IN);
        editMod
                .getBackground()
                .setColorFilter(getResources().getColor(R.color.color_a11826), PorterDuff.Mode.SRC_IN);
    }
    }


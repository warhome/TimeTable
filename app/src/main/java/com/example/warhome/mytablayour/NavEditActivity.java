package com.example.warhome.mytablayour;

import android.content.SharedPreferences;
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

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NavEditActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        // получим идентификатор выбранного пункта меню
        final int id = item.getItemId();
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        final int curr_course   = sharedPreferences.getInt("CURR_COURSE", -1);
        final int curr_group    = sharedPreferences.getInt("CURR_GROUP", -1);

        final EditText editTitle = findViewById(R.id.editTitle);
        final EditText editTime = findViewById(R.id.editTime);
        final EditText editTeacher = findViewById(R.id.editTeacher);
        final EditText editRoom = findViewById(R.id.editPlace);
        final EditText editDayWeek = findViewById(R.id.editDayWeek);
        final EditText editMod = findViewById(R.id.editMod);
        Button button = findViewById(R.id.buttonAdd);

        AppDatabase database = App.getInstance().getDatabase();
        LessonDao lessonDao = database.lessonDao();
        List<Lesson> currLessons = lessonDao.getAll();
        int currId = 1;
        for (Lesson lesson:currLessons) {
            if(lesson.getId() > currId) currId = lesson.getId();
        }

        final int finalCurrId = currId;
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (id) {
                    //POST
                    case R.id.newLessonEdit:
                        if(InternetConnectionChecker.checkConnection(getApplicationContext())) {
                            Lesson lesson = new Lesson(
                                    finalCurrId,
                                    editTitle.getText().toString(),
                                    editTime.getText().toString(),
                                    editTeacher.getText().toString(),
                                    editRoom.getText().toString(),
                                    editDayWeek.getText().toString(),
                                    curr_course,
                                    curr_group,
                                    editMod.getText().toString()
                            );
                            ApiService api = Retro.getApiService();
                            Call<okhttp3.ResponseBody> call = api.newLesson(lesson);
                            call.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    if (response.isSuccessful()) {
                                        Toast toast = Toast.makeText(getApplicationContext(), "Запись успешно добавлена", Toast.LENGTH_SHORT);
                                        toast.show();
                                    } else {
                                        Toast toast = Toast.makeText(getApplicationContext(), "Что-то пошло не так", Toast.LENGTH_SHORT);
                                        toast.show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    Toast toast = Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT);
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
                        break;
                    //DELETE
                    case R.id.deleteLessonEdit:
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
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Редактирование расписания");
        setSupportActionBar(toolbar);
    }
}

package com.example.warhome.mytablayour;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NavChoiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_choice);

        final EditText editCourse = findViewById(R.id.editTextCourse);
        final EditText editGroup = findViewById(R.id.editTextGroup);
        final EditText editSubGroup = findViewById(R.id.editTextSubGroup);

        final SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);

        final int curr_course   = sharedPreferences.getInt("CURR_COURSE", -1);
        final int curr_group    = sharedPreferences.getInt("CURR_GROUP", -1);
        final int curr_subgroup = sharedPreferences.getInt("CURR_SUBGROUP", -1);

        if(curr_course!= -1) editCourse.setText(String.valueOf(curr_course));
        if(curr_group != -1) editGroup.setText(String.valueOf(curr_group));
        if(curr_subgroup!= -1) editSubGroup.setText(String.valueOf(curr_subgroup));

        Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("CURR_COURSE",Integer.parseInt(editCourse.getText().toString()));
                editor.putInt("CURR_GROUP",Integer.parseInt(editGroup.getText().toString()));
                editor.putInt("CURR_SUBGROUP",Integer.parseInt(editSubGroup.getText().toString()));
                Toast toast = Toast.makeText(getApplicationContext(), "Сохранено",Toast.LENGTH_SHORT);
                toast.show();
                editor.apply();
            }
        });
    }
}

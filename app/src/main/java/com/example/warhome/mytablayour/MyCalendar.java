package com.example.warhome.mytablayour;
import java.util.Calendar;

/**
 * Created by warhome on 24.03.18.
 */

class MyCalendar {

    int curr_day;
    int day_of_week_learn;
    int month;
    int day_of_week;
    boolean isEnumCalendar;

    MyCalendar(int temp_day, boolean isEnum) {
        isEnumCalendar = isEnum;
        Calendar calendar = Calendar.getInstance();
        curr_day = calendar.get(Calendar.DATE);
        day_of_week_learn = calendar.get(Calendar.DAY_OF_WEEK);
        month = calendar.get(Calendar.MONTH);

        switch (day_of_week_learn){
            case 1:
                day_of_week = 6;
                break;
            default:
                day_of_week = day_of_week_learn - 2;
                break;
        }

        if (month == 6 && curr_day == 1) {
            isEnumCalendar = true;
        } else if (day_of_week_learn == 2 && curr_day != temp_day && temp_day != -1) {
            isEnumCalendar = !isEnum;
        }
    }
}

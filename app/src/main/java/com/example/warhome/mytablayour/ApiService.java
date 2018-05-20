package com.example.warhome.mytablayour;
import com.google.gson.JsonObject;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface ApiService {
    @GET("/")
    Call<LessonList> getJson();
    @POST("/admin/lesson/new")
    Call<ResponseBody> newLesson(@Body LessonList lessonList);
}

package com.example.warhome.mytablayour;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface ApiService {
    @GET("/")
    Call<LessonList> getJson();
    @POST("/lessons")
    Call<ResponseBody> newLesson(@Body Lesson lesson);
}

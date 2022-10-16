package com.psgtech.cholestrol.load_data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MainInterface {
    @GET("fetch.php")
    Call<String> STRING_CALL(
            @Query("cookie_id") String cookie_id,
            @Query("current_user") int current_user,
            @Query("to_user") int to_user,
            @Query("start") int start,
            @Query("limit") int limit
    );
}

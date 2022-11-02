package com.psgtech.cholestrol.load_messages;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MainInterface {
    @GET("fetch_messages.php")
    Call<String> STRING_CALL(
            @Query("cookie_id") String cookie_id,
            @Query("start") int start,
            @Query("limit") int limit
    );
}

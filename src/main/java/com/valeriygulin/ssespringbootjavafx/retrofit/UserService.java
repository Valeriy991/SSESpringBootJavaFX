package com.valeriygulin.ssespringbootjavafx.retrofit;

import com.valeriygulin.ssespringbootjavafx.dto.ResponseResult;
import com.valeriygulin.ssespringbootjavafx.model.User;
import retrofit2.Call;
import retrofit2.http.*;

public interface UserService {

    @POST(".")
    Call<ResponseResult<User>> post(@Body User user);

    @GET(".")
    Call<ResponseResult<User>> get(@Query("login") String login, @Query("password") String password);

}

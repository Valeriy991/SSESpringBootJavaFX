package com.valeriygulin.ssespringbootjavafx.retrofit;

import com.valeriygulin.ssespringbootjavafx.dto.ResponseResult;
import com.valeriygulin.ssespringbootjavafx.model.Message;
import com.valeriygulin.ssespringbootjavafx.model.User;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface MessageService {
    @POST("{id}")
    Call<ResponseResult<Message>> post(@Path("id") long id, @Body Message message);

    @GET(".")
    Call<ResponseResult<List<Long>>> get();


}

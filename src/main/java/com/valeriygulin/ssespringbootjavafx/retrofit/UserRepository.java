package com.valeriygulin.ssespringbootjavafx.retrofit;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.valeriygulin.ssespringbootjavafx.dto.ResponseResult;
import com.valeriygulin.ssespringbootjavafx.model.User;
import com.valeriygulin.ssespringbootjavafx.util.Constants;
import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;



import java.io.IOException;

public class UserRepository {
    private final ObjectMapper objectMapper;

    private UserService service;

    public UserRepository() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        OkHttpClient client = new OkHttpClient.Builder().build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.URL + "sse/user/")
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .client(client)
                .build();
        this.service = retrofit.create(UserService.class);
    }

    private <T> T getData(Response<ResponseResult<T>> execute) throws IOException {
        if (execute.code() == 400) {
            String message = objectMapper.readValue(execute.errorBody().string(),
                    new TypeReference<ResponseResult<T>>() {
                    }).getMessage();
            throw new IllegalArgumentException(message);
        }
        return execute.body().getData();
    }

    public User post(User user) throws IOException {
        Response<ResponseResult<User>> execute = this.service.post(user).execute();
        return getData(execute);
    }


    public User get(String login, String password) throws IOException {
        Response<ResponseResult<User>> execute = service.get(login, password).execute();
        return getData(execute);
    }


}

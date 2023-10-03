package com.valeriygulin.ssespringbootjavafx.retrofit;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.valeriygulin.ssespringbootjavafx.dto.ResponseResult;
import com.valeriygulin.ssespringbootjavafx.model.Message;
import com.valeriygulin.ssespringbootjavafx.util.Constants;
import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.List;

public class MessageRepository {
    private final ObjectMapper objectMapper;

    private MessageService service;

    public MessageRepository() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        OkHttpClient client = new OkHttpClient.Builder().build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.URL + "sse/chat/")
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .client(client)
                .build();
        this.service = retrofit.create(MessageService.class);
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

    public Message post(long idUser, Message message) throws IOException {
        Response<ResponseResult<Message>> execute = this.service.post(idUser,message).execute();
        return getData(execute);
    }


    public List<Long> get() throws IOException {
        Response<ResponseResult<List<Long>>> execute = service.get().execute();
        return getData(execute);
    }
}

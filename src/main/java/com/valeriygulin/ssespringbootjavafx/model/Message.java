package com.valeriygulin.ssespringbootjavafx.model;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Message {

    private long id;
    @NonNull
    private String content;

    private User user;

    private LocalDateTime localDateTime;


}

package com.valeriygulin.ssespringbootjavafx.controllers;


import java.io.IOException;

public interface ControllerData<T> {
    void initData(T value) throws IOException;
}

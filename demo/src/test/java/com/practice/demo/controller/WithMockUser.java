package com.practice.demo.controller;

public @interface WithMockUser {

    String password();

    String username();

    String roles();

}

package com.example.batch.mapper;

import com.example.batch.domain.User;

import java.util.List;

public interface UserMapper {
    List<User> selectUser();
}

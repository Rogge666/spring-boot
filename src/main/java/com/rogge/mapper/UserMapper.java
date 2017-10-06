package com.rogge.mapper;

import com.rogge.core.Mapper;
import com.rogge.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends Mapper<User> {
}
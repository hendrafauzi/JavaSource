package com.foldram.hendra.springmongo.controller;

import com.foldram.hendra.springmongo.dal.UserRepository;
import com.foldram.hendra.springmongo.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/")
public class UserController
{
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @GetMapping(value = "")
    public List<User> getAllUser()
    {
        logger.debug("Getting all users !!!");
        return userRepository.findAll();
    }

    @GetMapping(value = "/{userId")
    public User getUser(@PathVariable("userId") String userId)
    {
        logger.debug("Getting user By userId: {} ", userId);
        return userRepository.findOne(userId);
    }


}

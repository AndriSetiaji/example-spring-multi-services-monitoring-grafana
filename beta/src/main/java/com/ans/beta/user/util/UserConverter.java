package com.ans.beta.user.util;

import com.ans.beta.user.service.User;
import org.springframework.stereotype.Service;

@Service
public class UserConverter {

    final String address = "Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing " +
            "industries for previewing layouts and visual mockups. Lorem ipsum dolor sit amet, consectetur adipiscing " +
            "elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";

    public User createUser(int id, String name) {
        return User.builder()
                .id(id)
                .name(name)
                .address(address)
                .build();
    }
}

package com.ans.gamma.user.util;

import com.ans.gamma.user.persistence.domain.User;
import org.springframework.stereotype.Service;

@Service
public class UserConverter {

    final String address = "Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing " +
            "industries for previewing layouts and visual mockups. Lorem ipsum dolor sit amet, consectetur adipiscing " +
            "elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";

    public User createUser(Long id, String name) {
        User user = new User();
        user.setName(name);
        user.setAddress(address);
        return user;
    }
}

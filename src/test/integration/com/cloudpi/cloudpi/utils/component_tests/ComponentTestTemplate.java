package com.cloudpi.cloudpi.utils.component_tests;

import com.cloudpi.cloudpi.user.service.UserService;
import com.cloudpi.cloudpi.user.service.dto.CreateUser;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class ComponentTestTemplate {

    @Autowired
    protected UserService userService;
    @Autowired
    protected List<CreateUser> userRequestList;

    protected abstract void initTemplate();

    protected void initUsersInDB() {
        for (var user : userRequestList) {
            userService.createNewUser(user);
        }
    }

}

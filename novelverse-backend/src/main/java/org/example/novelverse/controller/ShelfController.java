package org.example.novelverse.controller;

import com.alibaba.druid.proxy.jdbc.WrapperProxyImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.example.novelverse.domain.User;
import org.example.novelverse.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shelf")
public class ShelfController {
    @Autowired
    UserService userService;

    @GetMapping
    public Result getShelf(HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute("userId");
        User user = userService.getById(userId);
        return new Result(Code.GET_OK, user.getShelf());
    }
}

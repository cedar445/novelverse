package org.example.novelverse.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.example.novelverse.domain.User;
import org.example.novelverse.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping
    public Result insertUser(@RequestBody User user) {
        userService.insert(user);
        return new Result(Code.INSERT_OK, user);
    }

    @PutMapping
    public Result updateUser(@RequestBody User user, HttpServletRequest request) {
        if (user.getId() == null) {
            user.setId((Integer) request.getAttribute("userId"));
        }
        userService.update(user);
        return new Result(Code.UPDATE_OK, user);
    }

    @DeleteMapping("/{id}")
    public Result deleteUser(@PathVariable("id") int id) {
        userService.delete(id);
        return new Result(Code.DELETE_OK, id);
    }

    @GetMapping("/{id}")
    public Result getUserById(@PathVariable("id") int id) {
        User user = userService.getById(id);
        return new Result(Code.GET_OK, user);
    }

    @GetMapping(params = "name")
    public Result getUserByName(@RequestParam("name") String name) {
        User user = userService.getByName(name);
        return new Result(Code.GET_OK, user);
    }

    @GetMapping
    public Result getUserAll() {
        List<User> users = userService.getAll();
        return new Result(Code.GET_OK, users);
    }
}

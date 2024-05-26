package com.springboot.flats.Controller;

import com.springboot.flats.Entity.User;
import com.springboot.flats.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody User user) {
        userService.create(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<?> remove(@PathVariable String username) {
        if (userService.remove(username)) return new ResponseEntity<>("Deleted", HttpStatus.OK);
        return new ResponseEntity<>("No such user", HttpStatus.NOT_FOUND);
    }
}

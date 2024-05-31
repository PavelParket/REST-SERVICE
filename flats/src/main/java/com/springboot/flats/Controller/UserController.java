package com.springboot.flats.Controller;

import com.springboot.flats.Entity.User;
import com.springboot.flats.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/user")
@Tag(name = "User Controller", description = "To process information about User")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping
    @Operation(summary = "Create a new user")
    @ApiResponses(value = @ApiResponse(responseCode = "201", description = "User created successfully"))
    public ResponseEntity<?> create(@RequestBody User user) {
        userService.create(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{username}")
    @Operation(summary = "Remove a user by username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<?> remove(@PathVariable String username) {
        if (userService.remove(username)) return new ResponseEntity<>("Deleted", HttpStatus.OK);
        return new ResponseEntity<>("No such user", HttpStatus.NOT_FOUND);
    }
}

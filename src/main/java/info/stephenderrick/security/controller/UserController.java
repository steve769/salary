package info.stephenderrick.security.controller;


import info.stephenderrick.security.entity.User;
import info.stephenderrick.security.model.UserModel;
import info.stephenderrick.security.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/save/")
    //I need to use ResponseEntity generic
    public ResponseEntity<String> saveUserToDataBase(@RequestBody UserModel userModel){

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }
}

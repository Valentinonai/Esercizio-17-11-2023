package Esercizio17112023.Esercizio17112023.controller;

import Esercizio17112023.Esercizio17112023.entities.User;
import Esercizio17112023.Esercizio17112023.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<User> getAllUsers(@RequestParam(defaultValue = "0")int page, @RequestParam(defaultValue = "5")int size, @RequestParam(defaultValue = "id") String order){
        return userService.getAllUsers(page,size>20?5:size,order);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User getSingleUser(@PathVariable int id){
        return userService.getSingleUser(id);
    }

    @DeleteMapping("/delete/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSingleUser(@AuthenticationPrincipal User u){
        userService.deleteUser(u);
    }


}

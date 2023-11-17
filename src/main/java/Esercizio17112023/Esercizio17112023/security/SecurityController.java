package Esercizio17112023.Esercizio17112023.security;

import Esercizio17112023.Esercizio17112023.entities.User;
import Esercizio17112023.Esercizio17112023.exceptions.BadRequest;
import Esercizio17112023.Esercizio17112023.payload.LogInPayload;
import Esercizio17112023.Esercizio17112023.payload.UserPayload;
import Esercizio17112023.Esercizio17112023.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class SecurityController {

    @Autowired
    private SecurityService securityService;
    @Autowired
    private UserService userService;
    @PostMapping("/login")
    public TokenPayload login(@RequestBody @Validated LogInPayload body, BindingResult validation){
        if(validation.hasErrors()){
            throw new BadRequest(validation.getAllErrors());
        }
            return new TokenPayload(securityService.autenticazione(body)) ;
    }
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public TokenPayload createUser(@RequestBody @Validated UserPayload userPayload, BindingResult validation){
        if(validation.hasErrors()){
            throw new BadRequest(validation.getAllErrors());
        }
         User u=userService.createUser(userPayload);
        return new TokenPayload(securityService.autenticazione(new LogInPayload(u.getEmail(),userPayload.password())));
    }
}

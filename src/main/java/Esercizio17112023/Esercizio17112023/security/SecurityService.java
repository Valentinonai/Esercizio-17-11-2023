package Esercizio17112023.Esercizio17112023.security;

import Esercizio17112023.Esercizio17112023.entities.User;
import Esercizio17112023.Esercizio17112023.exceptions.Unauthorized;
import Esercizio17112023.Esercizio17112023.payload.LogInPayload;
import Esercizio17112023.Esercizio17112023.payload.UserPayload;
import Esercizio17112023.Esercizio17112023.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public String autenticazione(LogInPayload body){
        User u=userService.findByEmail(body.email());
        System.out.println(u);
        System.err.println(u.getPassword());
        System.err.println(body.password());
        if(passwordEncoder.matches(body.password(),u.getPassword())){
            return jwtTools.creaToken(u);
        }
        else{
            throw new Unauthorized("Password errrata");
        }
    }
}

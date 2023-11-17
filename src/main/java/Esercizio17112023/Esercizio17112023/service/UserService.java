package Esercizio17112023.Esercizio17112023.service;

import Esercizio17112023.Esercizio17112023.entities.User;
import Esercizio17112023.Esercizio17112023.exceptions.NotFound;
import Esercizio17112023.Esercizio17112023.payload.UserPayload;
import Esercizio17112023.Esercizio17112023.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public Page<User> getAllUsers(int page, int size, String order){
        Pageable p= PageRequest.of(page,size, Sort.by(order));
        return userRepository.findAll(p);
    }

    public User getSingleUser(int id){
        User u=userRepository.findById(id).orElseThrow(()->new NotFound("User non trovato"));
        return u;
    }

    public User createUser(UserPayload userPayload){
        User u=User.builder().email(userPayload.email()).nome(userPayload.nome()).password(userPayload.password()).build();
        userRepository.save(u);
        return u;
    }


    public void deleteUser(User u){

        userRepository.delete(u);
    }


    public User findByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(()->new NotFound("User selezionato inesistente"));
    }
}

package com.example.lab12.Service;

import com.example.lab12.Api.ApiException;
import com.example.lab12.Repository.AuthRepository;
import com.example.lab12.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;


    public void registerUser(User userIN) {

        User user = authRepository.findUserByUsername(userIN.getUsername());
        if(user!=null){
            throw new ApiException("The username is taken");
        }

        User newUser = new User();
        newUser.setRole("USER");

        newUser.setUsername(userIN.getUsername());
        newUser.setPassword(new BCryptPasswordEncoder().encode(userIN.getPassword()));
        authRepository.save(newUser);

    }

    public void registerAdmin(User userIn) {

        User user = authRepository.findUserByUsername(userIn.getUsername());
        if(user!=null){
            throw new ApiException("The username is taken");
        }

        User newUser = new User();
        newUser.setRole("ADMIN");

        newUser.setUsername(userIn.getUsername());
        newUser.setPassword(new BCryptPasswordEncoder().encode(userIn.getPassword()));
        authRepository.save(newUser);

    }

    public List<User> adminGetAllUsers(Integer userId){
        User userAdmin = authRepository.findUserById(userId);
        if(userAdmin==null){
            throw new ApiException("user not found");
        }
        if (!"ADMIN".equalsIgnoreCase(userAdmin.getRole())) {
            throw new ApiException("you dont have access");
        }
        return authRepository.findAll();
    }

    public void adminUpdateUser(Integer adminId, User userIn){

        User userAdmin = authRepository.findUserById(adminId);
        if(userAdmin ==null){
            throw new ApiException("user not found");
        }
        if (!"ADMIN".equalsIgnoreCase(userAdmin.getRole())) {
            throw new ApiException("you dont have access");
        }

        User user = authRepository.findUserById(userIn.getId());
        if(user== null){
            throw new ApiException("User you try to edit not found");
        }

        if (userIn.getId() == null) {
            throw new ApiException("User id is required");
        }

        if (userIn.getUsername() == null || userIn.getUsername().isBlank()) {
            throw new ApiException("The username cant be blank");
        }

        if (userIn.getUsername().length() > 25) {
            throw new ApiException("Username must not exceed 25 characters");
        }

        User userCheckUsername = authRepository.findUserByUsername(userIn.getUsername());

        if (userCheckUsername != null && !userCheckUsername.getId().equals(user.getId())) {
            throw new ApiException("The username is taken");
        }

        user.setUsername(userIn.getUsername());

        authRepository.save(user);

    }

    public void adminDeleteUser(Integer adminId, Integer userID){
        User userAdmin = authRepository.findUserById(adminId);
        if(userAdmin ==null){
            throw new ApiException("user not found");
        }
        if (!"ADMIN".equalsIgnoreCase(userAdmin.getRole())) {
            throw new ApiException("you dont have access");
        }
        User user = authRepository.findUserById(userID);
        if(user== null){
            throw new ApiException("User you try to delete not found");
        }
        authRepository.delete(user);
    }

}

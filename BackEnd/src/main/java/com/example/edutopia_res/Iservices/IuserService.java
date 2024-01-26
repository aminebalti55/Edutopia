package com.example.edutopia_res.Iservices;

import com.example.edutopia_res.entities.User;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface IuserService {

    List<User> getSubscribedUsers();

    void addUser(User userDTO);

    User addUser2(User userDTO, MultipartFile profilePic) throws IOException ;

    List<UserRepresentation> getUser(String userName);

    void updateUser(String userId, User userDTO);

    void deleteUser(int userId);
    void sendVerificationLink(String userId);

    void sendResetPassword(String userId);

    UsersResource getInstance();

    List<User> getAllUsers();
    Optional<User> getUserById(int id);
}
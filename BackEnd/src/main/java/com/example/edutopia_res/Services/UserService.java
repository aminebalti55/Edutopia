package com.example.edutopia_res.Services;

import com.example.edutopia_res.Iservices.IuserService;
import com.example.edutopia_res.Repository.UserRepository;
import com.example.edutopia_res.configuration.Credentials;
import com.example.edutopia_res.configuration.KeycloakProvider;
import com.example.edutopia_res.entities.*;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Service
public class UserService implements IuserService {

 KeycloakRestTemplate keycloakRestTemplate;
 @Autowired
 UserRepository userRepository;
 Keycloak keycloak;
 @Autowired
 private BCryptPasswordEncoder passwordEncoder;

 private KeycloakSecurityContext keycloakSecurityContext;


 public List<User> getSubscribedUsers() {
  List<User> allUsers = userRepository.findAll();
  List<User> subscribedUsers = new ArrayList<>();
  for (User user : allUsers) {
   if (user.isSubscribedToRss()) {
    subscribedUsers.add(user);
   }
  }
  return subscribedUsers;
 }


 public User addUser2(User userDTO, MultipartFile profilePic) throws IOException {
  User dbUser = new User();
  dbUser.setFirstname(userDTO.getFirstname());
  dbUser.setLastname(userDTO.getLastname());
  dbUser.setEmail(userDTO.getEmail());
  String password = userDTO.getPassword();
  dbUser.setSpicinessTolerance(userDTO.getSpicinessTolerance());

  if (password != null) {
   dbUser.setPassword(passwordEncoder.encode(password));
  }

  dbUser.setUsername(userDTO.getUsername());
  dbUser.setGender(userDTO.getGender());
  dbUser.setSpicinessTolerance(userDTO.getSpicinessTolerance());

  // Save profile picture to the 'uploads' folder in your project directory
  if (profilePic != null && !profilePic.isEmpty()) {
   String fileName = profilePic.getOriginalFilename();
   Path filePath = Paths.get("C:/Users/user/Desktop/spring boot/Edutopia_res/Path/to/images", fileName);
   Files.copy(profilePic.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
   dbUser.setProfile_pic(filePath.toString());
   System.out.println("File saved to: " + filePath.toString());
  }

  System.out.println("Saving user: " + dbUser.toString());
  User savedUser = userRepository.save(dbUser);
  System.out.println("Saved user: " + savedUser.toString());

  return savedUser;
 }

 public List<User> getAllUsers() {
  List<User> users = userRepository.findAll();
  for (User user : users) {
   String profilePicPath = user.getProfile_pic();
   if (profilePicPath != null) {
    Path filePath = Paths.get(profilePicPath);
    byte[] imageData = null;
    try {
     imageData = Files.readAllBytes(filePath);
    } catch (IOException e) {
     // handle the exception appropriately
    }
    user.setProfilePicData(imageData);
   }
  }
  return users;
 }
 public void addUser(User userDTO){
  CredentialRepresentation credential = Credentials
          .createPasswordCredentials(userDTO.getPassword());
  UserRepresentation user = new UserRepresentation();
  user.setUsername(userDTO.getUsername());
  user.setFirstName(userDTO.getFirstname());
  user.setLastName(userDTO.getLastname());
  user.setEmail(userDTO.getEmail());
  user.setRealmRoles(Collections.singletonList(userDTO.getRole().toString()));
  Map<String, List<String>> attributes = new HashMap<>();
  attributes.put("loyaltyPoints", Collections.singletonList(String.valueOf(userDTO.getLoyaltyPoints())));
  attributes.put("orderCount", Collections.singletonList(String.valueOf(userDTO.getOrderCount())));
  attributes.put("spicinessTolerance", Collections.singletonList(userDTO.getSpicinessTolerance().toString()));
  attributes.put("isSubscribedToRss", Collections.singletonList(String.valueOf(userDTO.isSubscribedToRss())));
  attributes.put("gender", Collections.singletonList(userDTO.getGender().toString()));
  attributes.put("role", Collections.singletonList(userDTO.getRole().toString()));

  user.setAttributes(attributes);
  user.setCredentials(Collections.singletonList(credential));
  user.setEnabled(true);

  UsersResource instance = getInstance();
  instance.create(user);


  User dbUser = new User();
  dbUser.setIdCard(userDTO.getIdCard());
  dbUser.setFirstname(userDTO.getFirstname());
  dbUser.setLastname(userDTO.getLastname());
  dbUser.setEmail(userDTO.getEmail());
  dbUser.setPassword(userDTO.getPassword());
  dbUser.setUsername(userDTO.getUsername());
  dbUser.setLoyaltyPoints(userDTO.getLoyaltyPoints());
  dbUser.setOrderCount(userDTO.getOrderCount());
  dbUser.setSpicinessTolerance(userDTO.getSpicinessTolerance());
  dbUser.setSubscribedToRss(userDTO.isSubscribedToRss());
  dbUser.setGender(userDTO.getGender());
  dbUser.setRole(userDTO.getRole());
  dbUser.setProfile_pic(userDTO.getProfile_pic());

  userRepository.save(dbUser);
 }

 public List<UserRepresentation> getUser(String userName){
  UsersResource usersResource = getInstance();
  List<UserRepresentation> user = usersResource.search(userName, true);
  return user;

 }

 public void updateUser(String userId, User userDTO){
  CredentialRepresentation credential = Credentials
          .createPasswordCredentials(userDTO.getPassword());
  UserRepresentation user = new UserRepresentation();
  user.setUsername(userDTO.getUsername());
  user.setFirstName(userDTO.getFirstname());
  user.setLastName(userDTO.getLastname());
  user.setEmail(userDTO.getEmail());
  user.setCredentials(Collections.singletonList(credential));

  UsersResource usersResource = getInstance();
  usersResource.get(userId).update(user);
 }
 public void deleteUser(int userId) {
  userRepository.deleteById(userId);
 }


 public void sendVerificationLink(String userId){
  UsersResource usersResource = getInstance();
  usersResource.get(userId)
          .sendVerifyEmail();
 }

 public void sendResetPassword(String userId){
  UsersResource usersResource = getInstance();

  usersResource.get(userId)
          .executeActionsEmail(Arrays.asList("UPDATE_PASSWORD"));
 }
 public UsersResource getInstance(){
  return KeycloakProvider.getInstance().realm(KeycloakProvider.realm).users();
 }



 public Optional<User> getUserById(int id) {
  return userRepository.findById(id);
 }


 }







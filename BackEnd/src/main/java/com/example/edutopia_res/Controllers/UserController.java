package com.example.edutopia_res.Controllers;

import com.example.edutopia_res.Iservices.IPasswordEncryptionService;
import com.example.edutopia_res.Iservices.IuserService;
import com.example.edutopia_res.Repository.UserRepository;
import com.example.edutopia_res.Services.Loggin;
import com.example.edutopia_res.entities.*;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    IuserService service;
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    Loggin logz ;

    @Autowired
    IPasswordEncryptionService encrytionService;

    @PostMapping
    public String addUser(@RequestBody User userDTO){
        service.addUser(userDTO);
        return "User Added Successfully.";
    }
    @PostMapping("/ajouter")
    public ResponseEntity<User> addUser(@ModelAttribute User userDTO, @RequestParam(value = "profilePic", required = false) MultipartFile profilePic) {
        try {
            User savedUser = service.addUser2(userDTO, profilePic);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Autowired
    private SessionAuthenticationStrategy sessionAuthenticationStrategy;

    @PostMapping("/login")
    public LoginResponse login(HttpServletRequest requests, HttpServletResponse response, @RequestBody LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername());
        LoginResponse loginResponse = new LoginResponse();
        boolean isAuthenticated = false;

        if (user != null) {
            String encryptedPassword = user.getPassword();
            String decryptedPassword = encrytionService.decrypt(encryptedPassword, request.getPassword());
            isAuthenticated = decryptedPassword != null;
        }

        loginResponse.setAuthenticated(isAuthenticated);
        if (isAuthenticated) {
            loginResponse.setUser(user);
            HttpSession session = requests.getSession();
            session.setAttribute("user", user);
        } else {
            loginResponse.setMessage("Invalid username or password"); // Add this line to set the error message
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Set the HTTP status code to indicate an authentication failure
        }
        return loginResponse;
    }

    @GetMapping("/profile-picture/{id}")
    public ResponseEntity<byte[]> getProfilePicture(@PathVariable int id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null || user.getProfile_pic() == null) {
            return ResponseEntity.notFound().build();
        }
        Path filePath = Paths.get(user.getProfile_pic());
        byte[] imageData = null;
        try {
            imageData = Files.readAllBytes(filePath);
        } catch (IOException e) {
            // handle the exception appropriately
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentLength(imageData.length);
        return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
    }


    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // Invalidate the user's session
        }
        return "redirect:/login"; // Redirect the user to the login page
    }


    @GetMapping
    public List<User> getAllUsers() {
        List<User> users = service.getAllUsers();
        List<User> userDTOs = new ArrayList<>();
        for (User user : users) {
            User userDTO = new User();
            userDTO.setUserId(user.getUserId());
            userDTO.setFirstname(user.getFirstname());
            userDTO.setLastname(user.getLastname());
            userDTO.setEmail(user.getEmail());
            userDTO.setUsername(user.getUsername());
            userDTO.setGender(user.getGender());
            byte[] imageData = user.getProfilePicData();
            if (imageData != null) {
                userDTO.setProfile_pic(Base64.getEncoder().encodeToString(imageData));
            }
            userDTOs.add(userDTO);
        }
        return userDTOs;
    }
    @GetMapping("/getUserById/{id}")
    public ResponseEntity<User> GetUserByID(@PathVariable int id) {
        Optional<User> user = service.getUserById(id);
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/{userName}")
    public List<UserRepresentation> getUser(@PathVariable("userName") String userName){
        List<UserRepresentation> user = service.getUser(userName);
        return user;
    }

    @PutMapping(path = "/update/{userId}")
    public String updateUser(@PathVariable("userId") String userId,   @RequestBody User userDTO){
        service.updateUser(userId, userDTO);
        return "User Details Updated Successfully.";
    }

    @DeleteMapping(path = "/delete/user/{userId}")
    public String deleteUser(@PathVariable("userId") int userId){
        service.deleteUser(userId);
        return "User Deleted Successfully.";
    }

    @GetMapping(path = "/verification-link/{userId}")
    public String sendVerificationLink(@PathVariable("userId") String userId){
        service.sendVerificationLink(userId);
        return "Verification Link Send to Registered E-mail Id.";
    }

    @GetMapping(path = "/reset-password/{userId}")
    public String sendResetPassword(@PathVariable("userId") String userId){
        service.sendResetPassword(userId);
        return "Reset Password Link Send Successfully to Registered E-mail Id.";
    }
}









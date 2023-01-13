/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ist.challenge.ist.bayuhendrasetiawan.Controller;

import ist.challenge.ist.bayuhendrasetiawan.Exception.ErrorDetails;
import ist.challenge.ist.bayuhendrasetiawan.model.User;
import ist.challenge.ist.bayuhendrasetiawan.repository.UserRepository;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author BayuHS
 */
@RestController
@RequestMapping("/api/ist/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody User user) {

        List<User> users = userRepository.findAll();
        if (user.getPassword().isEmpty() || user.getPassword().isEmpty()) {
            ErrorDetails errorDetails = new ErrorDetails(new Date(), "BAD_REQUEST", "");
            return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
        }
        for (User other : users) {
            if (other.getUsername().equalsIgnoreCase(user.getUsername()) && other.getPassword().equalsIgnoreCase(user.getPassword())) {
                ErrorDetails errorDetails = new ErrorDetails(new Date(), "Sukses Login", other.getUsername());
                return new ResponseEntity<>(errorDetails, HttpStatus.OK);
            }
        }
        ErrorDetails errorDetails = new ErrorDetails(new Date(), "Gagal Login", "");
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }
}

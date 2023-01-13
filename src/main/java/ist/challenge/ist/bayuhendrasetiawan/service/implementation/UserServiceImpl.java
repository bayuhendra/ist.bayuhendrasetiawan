/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ist.challenge.ist.bayuhendrasetiawan.service.implementation;

import ist.challenge.ist.bayuhendrasetiawan.model.User;
import ist.challenge.ist.bayuhendrasetiawan.repository.UserRepository;
import ist.challenge.ist.bayuhendrasetiawan.service.UserService;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author BayuHS
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public void saveUser(User user) {

        user.setPassword(user.getPassword());
        userRepository.save(user);
    }

    @Override
    public List<Object> isUserPresent(User user) {
        boolean userExists = false;
        String message = null;
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            userExists = true;
            message = "Username sudah terpakai!";
        }
        return Arrays.asList(userExists, existingUser, message);
    }

}

package ist.challenge.ist.bayuhendrasetiawan.service;

import ist.challenge.ist.bayuhendrasetiawan.model.User;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
/**
 *
 * @author BayuHS
 */
public interface UserService {

    public void saveUser(User user);

    public List<Object> isUserPresent(User user);

}

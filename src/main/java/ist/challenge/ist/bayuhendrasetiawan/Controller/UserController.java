/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ist.challenge.ist.bayuhendrasetiawan.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ist.challenge.ist.bayuhendrasetiawan.Exception.ErrorDetails;
import ist.challenge.ist.bayuhendrasetiawan.Exception.ResourceNotFoundException;
import ist.challenge.ist.bayuhendrasetiawan.model.User;
import ist.challenge.ist.bayuhendrasetiawan.repository.UserRepository;
import ist.challenge.ist.bayuhendrasetiawan.service.UserService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author BayuHS
 */
@RestController
@RequestMapping("/api/ist/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/save-user")
    public ResponseEntity<?> createUser(@Valid @RequestBody User user) {
        List<Object> userPresentObj = userService.isUserPresent(user);
        if ((Boolean) userPresentObj.get(0)) {
            ErrorDetails errorDetails = new ErrorDetails(new Date(), userPresentObj.get(2).toString(), userPresentObj.get(1).toString());
            return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
        }
        userRepository.save(user);
        ErrorDetails errorDetails = new ErrorDetails(new Date(), "Data Created => username : " + user.getUsername() + " & Password : " + user.getPassword(), userPresentObj.get(1).toString());
        return new ResponseEntity<>(errorDetails, HttpStatus.CREATED);
    }

    @GetMapping("/get-list")
    public List<User> getAllEmployees() {
        return userRepository.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSave(@PathVariable(value = "id") Long id,
            @Valid @RequestBody User user) throws ResourceNotFoundException {
        User data = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + id));

        if (user.getUsername().equalsIgnoreCase(data.getUsername())) {
            ErrorDetails errorDetails = new ErrorDetails(new Date(), "Username sudah terpakai", "");
            return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
        } else if (user.getPassword().equalsIgnoreCase(data.getPassword())) {
            ErrorDetails errorDetails = new ErrorDetails(new Date(), "Password tidak boleh sama dengan password sebelumnya", "");
            return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
        } else {
            data.setUsername(user.getUsername());
            data.setPassword(user.getPassword());
            final User updatedUser = userRepository.save(data);
            ErrorDetails errorDetails = new ErrorDetails(new Date(), "Sukses", "");
            return new ResponseEntity<>(errorDetails, HttpStatus.CREATED);
        }
    }

    @GetMapping("/get-data-integration")
    public String getAllDataIntegration() throws MalformedURLException, IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        String json = null;
        ErrorDetails errorDetails = new ErrorDetails();
        try {
            URL url = new URL("https://swapi.py4e.com/api/people/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP Error code : "
                        + conn.getResponseCode());
            }
            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            BufferedReader br = new BufferedReader(in);

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            JSONObject jsonObj = new JSONObject(sb.toString());
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            json = gson.toJson(jsonObj);

            conn.disconnect();
        } catch (Exception e) {
            System.out.println("Exception in NetClientGet:- " + e);
        }

        return json;
    }

    String beautify(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Object obj = mapper.readValue(json, Object.class);
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
    }

}

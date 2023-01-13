package ist.challenge.ist.bayuhendrasetiawan;

import ist.challenge.ist.bayuhendrasetiawan.Main.Application;
import ist.challenge.ist.bayuhendrasetiawan.model.User;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @Test
    public void contextLoads() {
    }

    @Test
    public void testGetAllUser() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/get-list",
                HttpMethod.GET, entity, String.class);
        assertNotNull(response.getBody());
    }

    @Test
    public void testGetEmployeeById() {
        User user = restTemplate.getForObject(getRootUrl() + "/11", User.class);
        System.out.println(user.getUsername());
        assertNotNull(user);
    }

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setUsername("dummy123");
        user.setPassword("dummy123");
        ResponseEntity<User> postResponse = restTemplate.postForEntity(getRootUrl() + "/save-user", user, User.class);
        assertNotNull(postResponse);
        assertNotNull(postResponse.getBody());
    }

    @Test
    public void testUpdateUser() {
        int id = 1;
        User user = restTemplate.getForObject(getRootUrl() + "/" + id, User.class);
        user.setUsername("dummy1234");
        user.setPassword("dummy1234");
        restTemplate.put(getRootUrl() + "/employees/" + id, user);
        User updatedUser = restTemplate.getForObject(getRootUrl() + "/" + id, User.class);
        assertNotNull(updatedUser);
    }

}

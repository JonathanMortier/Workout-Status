package fr.djmojo.workout.clients;

import feign.Param;
import feign.RequestLine;
import fr.djmojo.workout.models.User;

import java.util.List;

/**
 * Created by DJMojo on 15/05/16.
 */
public interface UserClient {

    @RequestLine("GET /users")
    List<User> findAll();

    @RequestLine("GET /users/{id}")
    User findById(@Param("id") String id);

    @RequestLine("POST /users")
    User createUser(User user);

    @RequestLine("POST /users/{id}")
    User updateUser(@Param("id") String id, User user);

}

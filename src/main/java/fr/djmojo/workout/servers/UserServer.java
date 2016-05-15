package fr.djmojo.workout.servers;

import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import fr.djmojo.workout.clients.UserClient;

/**
 * Created by DJMojo on 15/05/16.
 */
public class UserServer {

    UserClient client = Feign.builder()
            .decoder(new GsonDecoder())
            .encoder(new GsonEncoder())
            .target(UserClient.class, "http://workout-status.herokuapp.com/");
}

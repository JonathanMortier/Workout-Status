package fr.djmojo.workout.servers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import fr.djmojo.workout.database.UserDAO;
import fr.djmojo.workout.models.User;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;

/**
 * Created by DJMojo on 15/05/16.
 */
public class UserServer {

    private static final Gson gson = new Gson();
    private static final Type USER_TYPE = User.class;

    public static void launchServer() {

        get("/users", (req, res) -> {

            List<User> clientList = new ArrayList<>();

            clientList = UserDAO.getInstance().findAll();

            Map<String, Object> attributes = new HashMap<>();
            attributes.put("users", clientList);
            return new ModelAndView(attributes, "listUser.ftl");
        }, new FreeMarkerEngine());

        get("/users/:id", (request, response) -> {

            User user = UserDAO.getInstance().findById(request.params(":id"));

            return gson.toJson(user, USER_TYPE);
        });

        post("/users", (req, res) -> {

            ObjectMapper mapper = new ObjectMapper();
            User user = mapper.readValue(req.body(), User.class);

            if (!user.isValid()) {
                res.status(401);
                return "";
            }
            res.status(200);

            User userBdd = UserDAO.getInstance().create(user);

            return gson.toJson(userBdd, USER_TYPE);
        });

    }
}

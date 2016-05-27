package fr.djmojo.workout.servers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import fr.djmojo.workout.database.UserDAO;
import fr.djmojo.workout.models.User;
import spark.ModelAndView;
import spark.Session;
import spark.template.freemarker.FreeMarkerEngine;

import javax.servlet.http.Cookie;
import java.lang.reflect.Type;
import java.net.URLDecoder;
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

        /*get("/users/:id", (request, response) -> {

            User user = UserDAO.getInstance().findById(request.params(":id"));

            if (user == null) {
                response.status(404);
                return "";
            }

            return gson.toJson(user, USER_TYPE);
        });*/

        post("/users", (req, res) -> {

            ObjectMapper mapper = new ObjectMapper();
            User user = mapper.readValue(req.body(), User.class);

            if (!user.isValid()) {
                res.status(401);
                return "";
            }
            res.status(200);

            User userBdd = UserDAO.getInstance().createUser(user);

            return gson.toJson(userBdd, USER_TYPE);
        });

        post("/users/connection", ((request, response) -> {

            String [] couple = request.body().split("&password=");

            String password = couple[1];
            String mail = couple[0].substring(5);

            mail = URLDecoder.decode(mail, "UTF-8");
            password = URLDecoder.decode(password, "UTF-8");

            System.out.println("mail : " + mail);
            System.out.println("password : " + password);

            User user = UserDAO.getInstance().connectUser(mail, password);

            Session session = request.session(true);
            session.attribute("CONNECTED", "true");
            session.attribute("UserID", user.getId());
            response.redirect("/weights/" + user.getId());
            return "";
        }));

        post("/users/:id", (request, response) -> {

            ObjectMapper mapper = new ObjectMapper();
            User user = mapper.readValue(request.body(), User.class);

            user = UserDAO.getInstance().updateUser(request.params(":id"), user);

            return gson.toJson(user, USER_TYPE);
        });


    }
}

package fr.djmojo.workout;

import fr.djmojo.workout.database.MachineDAO;
import fr.djmojo.workout.database.UserDAO;
import fr.djmojo.workout.models.Machine;
import fr.djmojo.workout.models.User;
import fr.djmojo.workout.servers.MachineServer;
import fr.djmojo.workout.servers.UserServer;
import fr.djmojo.workout.servers.WeightServer;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.staticFileLocation;

/**
 * Created by DJMojo on 14/05/16.
 */
public class Main {

    public static void main(String[] args) {

        port(Integer.parseInt(System.getenv("PORT")));
        staticFileLocation("/public");

        UserServer.launchServer();
        MachineServer.launchServer();
        WeightServer.launchServer();

        get("/", (req, res) -> {

            List<User> userList = UserDAO.getInstance().findAll();
            List<Machine> machineList = MachineDAO.getInstance().findAll();

            Map<String, Object> attributes = new HashMap<>();
            attributes.put("users", userList);
            attributes.put("machines", machineList);
            return new ModelAndView(attributes, "index.ftl");

        }, new FreeMarkerEngine());

    }

}

package fr.djmojo.workout.servers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import fr.djmojo.workout.database.MachineDAO;
import fr.djmojo.workout.database.UserDAO;
import fr.djmojo.workout.database.WeightDAO;
import fr.djmojo.workout.models.Machine;
import fr.djmojo.workout.models.User;
import fr.djmojo.workout.models.Weight;
import fr.djmojo.workout.view.MachineWeightView;
import fr.djmojo.workout.view.UserView;
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
public class WeightServer {

    private static final Gson gson = new Gson();
    private static final Type WEIGHT_TYPE = Weight.class;

    public static void launchServer () {

        get("/weights/:userId", (req, res) -> {

            List<Weight> weightList = new ArrayList<>();

            User user = UserDAO.getInstance().findById(req.params(":userId"));
            List<Machine> machineList = MachineDAO.getInstance().findAll();
            weightList = WeightDAO.getInstance().findByUserId(req.params(":userId"));

            UserView userView = prepareForUserView(user, machineList, weightList);

            Map<String, Object> attributes = new HashMap<>();
            attributes.put("userView", userView);
            return new ModelAndView(attributes, "user.ftl");
        }, new FreeMarkerEngine());

        post("/weights", (req, res) -> {

            ObjectMapper mapper = new ObjectMapper();
            Weight weight = mapper.readValue(req.body(), Weight.class);

            if (!weight.isValid()) {
                res.status(401);
                return "";
            }
            res.status(200);

            weight = WeightDAO.getInstance().createWeight(weight);

            return gson.toJson(weight, WEIGHT_TYPE);
        });
    }

    private static UserView prepareForUserView (User user, List<Machine> machineList, List<Weight> weightList) {

        UserView userView = new UserView();
        userView.setUser(user);

        for (Weight weight : weightList) {

            MachineWeightView machineWeightView = new MachineWeightView();
            machineWeightView.setWeight(weight);

            Machine machine = findMachineById(machineList, weight.getMachineId());
            machineWeightView.setMachine(machine);
            userView.getViewList().add(machineWeightView);
        }
        return userView;

    }

    private static Machine findMachineById(List<Machine> machineList, String machineId) {

        for (Machine machine : machineList) {

            if (machine.getId().equals(machineId)) {
                return machine;
            }
        }

        return null;
    }
}

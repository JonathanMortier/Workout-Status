package fr.djmojo.workout.servers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import fr.djmojo.workout.database.MachineDAO;
import fr.djmojo.workout.models.Machine;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;

/**
 * Created by DJMojo on 15/05/16.
 */
public class MachineServer {

    private static final Gson gson = new Gson();
    private static final Type MACHINE_TYPE = Machine.class;

    private MachineServer () {}

    public static void launchServer() {

        get("/machines", (req, res) -> {

            List<Machine> machineList;

            machineList = MachineDAO.getInstance().findAll();

            Map<String, Object> attributes = new HashMap<>();
            attributes.put("machines", machineList);
            return new ModelAndView(attributes, "listMachine.ftl");
        }, new FreeMarkerEngine());

        get("/machines/:id", (request, response) -> {

            Machine machine = MachineDAO.getInstance().findById(request.params(":id"));

            return gson.toJson(machine, MACHINE_TYPE);
        });

        post("/machines", (req, res) -> {

            ObjectMapper mapper = new ObjectMapper();
            Machine machineFromRequest = mapper.readValue(req.body(), Machine.class);

            if (!machineFromRequest.isValid()) {
                res.status(401);
                return "";
            }
            res.status(200);

            Machine machine = MachineDAO.getInstance().createMachine(machineFromRequest);

            return gson.toJson(machine, MACHINE_TYPE);
        });

    }
}

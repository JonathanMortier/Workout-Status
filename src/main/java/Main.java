import static spark.Spark.get;
import static javax.measure.unit.SI.KILOGRAM;
import static spark.Spark.port;
import static spark.Spark.staticFileLocation;

import javax.measure.quantity.Mass;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.heroku.sdk.jdbc.DatabaseUrl;
import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import fr.djmojo.workout.clients.UserClient;
import fr.djmojo.workout.database.MachineDAO;
import fr.djmojo.workout.database.UserDAO;
import fr.djmojo.workout.models.Machine;
import fr.djmojo.workout.models.User;
import fr.djmojo.workout.servers.MachineServer;
import fr.djmojo.workout.servers.UserServer;
import fr.djmojo.workout.servers.WeightServer;
import org.jscience.physics.model.RelativisticModel;
import org.jscience.physics.amount.Amount;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by DJMojo on 14/05/16.
 */
public class Main {

    public static void main(String[] args) {

        port(Integer.valueOf(System.getenv("PORT")));
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

        /*get("/hello", (req, res) -> {
            RelativisticModel.select();

            String energy = System.getenv().get("ENERGY");

            Amount<Mass> m = Amount.valueOf(energy).to(KILOGRAM);
            return "E=mc^2: " + energy + " = " + m.toString();
        });*/

        /*get("/db", (req, res) -> {
            Connection connection = null;
            Map<String, Object> attributes = new HashMap<>();
            try {
                connection = DatabaseUrl.extract().getConnection();

                Statement stmt = connection.createStatement();
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)");
                stmt.executeUpdate("INSERT INTO ticks VALUES (now())");
                ResultSet rs = stmt.executeQuery("SELECT tick FROM ticks");

                ArrayList<String> output = new ArrayList<String>();
                while (rs.next()) {
                    output.add( "Read from DB: " + rs.getTimestamp("tick"));
                }

                attributes.put("results", output);
                return new ModelAndView(attributes, "db.ftl");
            } catch (Exception e) {
                attributes.put("message", "There was an error: " + e);
                return new ModelAndView(attributes, "error.ftl");
            } finally {
                if (connection != null) try{connection.close();} catch(SQLException e){}
            }
        }, new FreeMarkerEngine());*/
    }

}

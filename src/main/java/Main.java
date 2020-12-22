import com.fasterxml.jackson.databind.ObjectMapper;
import fr.djmojo.workout.database.MachineDAO;
import fr.djmojo.workout.database.UserDAO;
import fr.djmojo.workout.database.WeightDAO;
import fr.djmojo.workout.models.Machine;
import fr.djmojo.workout.models.User;
import fr.djmojo.workout.models.Weight;
import fr.djmojo.workout.servers.MachineServer;
import fr.djmojo.workout.servers.UserServer;
import fr.djmojo.workout.servers.WeightServer;
import fr.djmojo.workout.view.UserView;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFileLocation;

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

        post("/", (req, res) -> {

            ObjectMapper mapper = new ObjectMapper();
            String mail = req.attribute("mail");
            String password = req.attribute("password");

            Map<String, String > params = req.params();

            System.out.println("mail "+mail);
            System.out.println("password "+password);
            System.out.println("params : " + params);
            String userIdFound = "2";

            User user = UserDAO.getInstance().findById(userIdFound);
            List<Machine> machineList = MachineDAO.getInstance().findAll();
            List<Weight> weightList = WeightDAO.getInstance().findByUserId(userIdFound);

            UserView userView = WeightServer.prepareForUserView(user, machineList, weightList);

            Map<String, Object> attributes = new HashMap<>();
            attributes.put("userView", userView);
            return new ModelAndView(attributes, "user.ftl");

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

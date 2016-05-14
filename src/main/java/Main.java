import static spark.Spark.get;
import static javax.measure.unit.SI.KILOGRAM;
import static spark.Spark.port;
import static spark.Spark.staticFileLocation;

import javax.measure.quantity.Mass;
import org.jscience.physics.model.RelativisticModel;
import org.jscience.physics.amount.Amount;

/**
 * Created by DJMojo on 14/05/16.
 */
public class Main {

    public static void main(String[] args) {

        port(Integer.valueOf(System.getenv("PORT")));
        staticFileLocation("/public");

        get("/hello", (req, res) -> {
            RelativisticModel.select();
            Amount<Mass> m = Amount.valueOf("12 GeV").to(KILOGRAM);
            return "E=mc^2: 12 GeV = " + m.toString();
        });
    }

}

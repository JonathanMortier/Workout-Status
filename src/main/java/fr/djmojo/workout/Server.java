package fr.djmojo.workout;

import static spark.Spark.get;
import static javax.measure.unit.SI.KILOGRAM;
import javax.measure.quantity.Mass;
import org.jscience.physics.model.RelativisticModel;
import org.jscience.physics.amount.Amount;

/**
 * Created by DJMojo on 14/05/16.
 */
public class Server {

    public static void main(String[] args) {
        get("/hello", (req, res) -> {
            RelativisticModel.select();
            Amount<Mass> m = Amount.valueOf("12 GeV").to(KILOGRAM);
            return "E=mc^2: 12 GeV = " + m.toString();
        });
    }

}

package fr.djmojo.workout;

import static spark.Spark.get;

/**
 * Created by DJMojo on 14/05/16.
 */
public class Server {

    public static void main(String[] args) {
        get("/hello", (req, res) -> "Hello World");
    }

}

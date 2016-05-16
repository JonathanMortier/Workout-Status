package fr.djmojo.workout.clients;

import feign.Param;
import feign.RequestLine;
import fr.djmojo.workout.models.Weight;

import java.util.List;

/**
 * Created by DJMojo on 15/05/16.
 */
public interface WeightClient {

    @RequestLine("GET /weights/{userId}")
    List<Weight> findByUserId(@Param("id") String userId);

    @RequestLine("POST /weights")
    Weight createWeight(Weight weight);

    @RequestLine("POST /weights/add/{mass}")
    Weight addWeight(Weight weight, @Param("mass") int mass);
}

package fr.djmojo.workout.clients;

import feign.Param;
import feign.RequestLine;
import fr.djmojo.workout.models.Machine;

import java.util.List;

/**
 * Created by DJMojo on 15/05/16.
 */
public interface MachineClient {

    @RequestLine("GET /machines")
    List<Machine> findAll();

    @RequestLine("GET /machines/{id}")
    Machine findById(@Param("id") String id);

    @RequestLine("POST /machines")
    Machine createMachine(Machine machine);
}

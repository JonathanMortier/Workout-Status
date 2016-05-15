package fr.djmojo.workout.view;

import fr.djmojo.workout.models.Machine;
import fr.djmojo.workout.models.Weight;

/**
 * Created by DJMojo on 15/05/16.
 */
public class MachineWeightView {

    private Machine machine;
    private Weight weight;

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public Weight getWeight() {
        return weight;
    }

    public void setWeight(Weight weight) {
        this.weight = weight;
    }
}

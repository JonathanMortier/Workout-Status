package fr.djmojo.workout.models;

/**
 * Created by DJMojo on 15/05/16.
 */
public class Weight {

    private String userId;
    private String machineId;
    private int weight;

    public Weight() {
    }

    public boolean isValid() {
        return userId != null && machineId != null && weight > 0;
    }

    @Override
    public String toString() {
        return "Weight{" +
                "userId='" + userId + '\'' +
                ", machineId='" + machineId + '\'' +
                ", weight=" + weight +
                '}';
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}

package fr.djmojo.workout.models;

/**
 * Created by DJMojo on 15/05/16.
 */
public class Machine {

    private String id;
    private String name;

    public Machine() {
    }

    public boolean isValid() {

        return id != null && name != null;
    }

    @Override
    public String toString() {
        return "Machine{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

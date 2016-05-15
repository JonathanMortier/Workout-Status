package fr.djmojo.workout.view;

import fr.djmojo.workout.models.Machine;
import fr.djmojo.workout.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DJMojo on 15/05/16.
 */
public class UserView {

    private User user;
    private List<MachineWeightView> viewList = new ArrayList<>();

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<MachineWeightView> getViewList() {
        return viewList;
    }

    public void setViewList(List<MachineWeightView> viewList) {
        this.viewList = viewList;
    }
}

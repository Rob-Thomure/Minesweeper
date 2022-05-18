package com.company;

public class UserTurn {
    private final Coordinates coordinates;
    private final ActionType actionType;

    public UserTurn(Coordinates coordinates, ActionType actionType) {
        this.coordinates = coordinates;
        this.actionType = actionType;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public ActionType getActionType() {
        return actionType;
    }
}

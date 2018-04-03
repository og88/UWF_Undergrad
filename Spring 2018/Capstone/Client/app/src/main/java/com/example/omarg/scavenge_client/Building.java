package com.example.omarg.scavenge_client;

public class Building {
    String building_id;
    String Location_type;
    String room;
    String Description;
    String _id;

    public String getBuilding_id() {
        return building_id;
    }

    public void setBuilding_id(String building_id) {
        this.building_id = building_id;
    }

    public String getLocation_type() {
        return Location_type;
    }

    public void setLocation_type(String location_type) {
        Location_type = location_type;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}

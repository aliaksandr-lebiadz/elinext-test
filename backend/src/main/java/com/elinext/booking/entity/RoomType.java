package com.elinext.booking.entity;

public enum RoomType {

    OPERATING("Operating"),
    WARD("Ward"),
    DELIVERY("Delivery"),
    DRESSING("Dressing");

    private String value;

    RoomType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static RoomType getRoomType(String value) {
        for(RoomType type : values()) {
            if(type.getValue().equals(value)) {
                return type;
            }
        }
        return null;
    }

}

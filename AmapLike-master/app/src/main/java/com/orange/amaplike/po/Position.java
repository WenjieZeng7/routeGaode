package com.orange.amaplike.po;

import java.util.Objects;

public class Position {
    double latitude;
    double longitude;
    int direction;
    float data;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Position position = (Position) o;
        return Double.compare(position.latitude, latitude) == 0 &&
                Double.compare(position.longitude, longitude) == 0 &&
                direction == position.direction &&
                Float.compare(position.data, data) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), latitude, longitude, direction, data);
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public float getData() {
        return data;
    }

    public void setData(float data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Position{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", direction=" + direction +
                ", data=" + data +
                '}';
    }

    public Position(double latitude, double longitude, int direction, float data) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.direction = direction;
        this.data = data;
    }

    public Position() {
    }
}

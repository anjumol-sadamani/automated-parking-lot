package com.parking.utils;

public class Constant {

    public enum VehicleType {
        CAR ("car");

        private final String value;
        VehicleType(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }

        public static VehicleType getVehicleType(String value) {
            for (int i = 0; i < VehicleType.values().length; i++) {
                if (value.equals(VehicleType.values()[i].value))
                    return VehicleType.values()[i];
            }
            return null;
        }
    }
}

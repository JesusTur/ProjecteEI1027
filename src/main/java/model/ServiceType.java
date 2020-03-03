package model;

public enum ServiceType {
    catering("catering"),
    regularServices("regular services");
    public final String type;
    ServiceType(String s) {
        this.type=s;
    }
}

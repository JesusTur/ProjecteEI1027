package es.projecteEI1027.model;

public enum ServiceType {
    catering("catering"),
    regularServices("regular services");
    public final String type;
    ServiceType(String s) {
        this.type=s;
    }
    @Override
    public String toString(){
        return type;
    }
}

package es.projecteEI1027.model;

public enum ServiceType {
    catering("catering"),
    limpieza_de_hogar("limpieza de hogar"),
    cuidado_personal("cuidado personal");
    public final String type;
    ServiceType(String s) {
        this.type=s;
    }
    @Override
    public String toString(){
        return type;
    }
}

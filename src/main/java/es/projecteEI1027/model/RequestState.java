package es.projecteEI1027.model;

public enum RequestState {
    approved("accepted"),
    rejected("rejected"),
    waiting("processing");
    public final String state;
    RequestState(String state){
        this.state=state;
    }
    @Override
    public String toString(){
        return state;
    }
}

package model;

public enum RequestState {
    approved("approved"),
    rejected("rejected"),
    waiting("waiting");
    public final String state;
    RequestState(String state){
        this.state=state;
    }
}

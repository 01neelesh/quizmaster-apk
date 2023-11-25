package com.example.quizmaster.model;

public class Questions {
    private int ansewerId;
    private boolean userResponse; //true of yes false for no

    public Questions(int ansewerId, boolean b) {
        this.ansewerId = ansewerId;
        this.userResponse = false;
    }

    public int getAnsewerId() {
        return ansewerId;
    }
    public boolean getUserResponse(){
        return  userResponse;
    }
    public void setUserResponse(boolean userResponse){
        this.userResponse = userResponse;
    }
}

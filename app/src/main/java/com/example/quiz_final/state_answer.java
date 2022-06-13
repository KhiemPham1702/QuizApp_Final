package com.example.quiz_final;

import android.widget.ImageView;

public class state_answer {
    String id;
    boolean state1;
    String state2;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isState1() {
        return state1;
    }

    public void setState1(boolean state1) {
        this.state1 = state1;
    }

    public String getState2() {
        return state2;
    }

    public void setState2(String state2) {
        this.state2 = state2;
    }

    public state_answer(String id, boolean state1, String state2) {
        this.id = id;
        this.state1 = state1;
        this.state2 = state2;

    }
    public state_answer(){

    }
}

package com.example.quiz_final;

public class rank_item {
    String name;
    String score;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
    public rank_item() {

    }
    public rank_item(String name, String score) {
        this.name = name;
        this.score = score;
    }
}

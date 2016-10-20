package com.example.yuki.prototipo;

/**
 * Created by yuki on 14/10/16.
 */

public class Question
{
    private int id;
    private  String question;
    private int foiVisualizado;

    public Question(){}

    public Question(int id, String question)
    {
        this.setId(id);
        this.setQuestion(question);
        this.setFoiVisualizado(0);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getFoiVisualizado() {
        return foiVisualizado;
    }

    public void setFoiVisualizado(int foiVisualizado) {
        this.foiVisualizado = foiVisualizado;
    }
}

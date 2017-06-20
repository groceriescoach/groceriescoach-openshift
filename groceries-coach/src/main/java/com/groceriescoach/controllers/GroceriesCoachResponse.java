package com.groceriescoach.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GroceriesCoachResponse<P> implements Serializable {

    private static final long serialVersionUID = 403119025778892030L;

    private P payload;
    private List<String> messages = new ArrayList<>();

    public GroceriesCoachResponse() {
    }

    public GroceriesCoachResponse(P payload) {
        this.payload = payload;
    }

    public P getPayload() {
        return payload;
    }

    public void setPayload(P payload) {
        this.payload = payload;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void addMessage(String message) {
        this.messages.add(message);
    }

    public void addMessages(List<String> messages) {
        this.messages.addAll(messages);
    }
}

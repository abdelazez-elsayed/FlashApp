package com.flash.chat;

public class Message {
    public String message;
    private String type;
    private boolean seen;
    private Long time;
    private String from;
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }


    public Message(){

    }
    public Message(String msg, String type, boolean seen, Long time) {
        this.message = message;
        this.type = type;
        this.seen = seen;
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message= message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean getSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}

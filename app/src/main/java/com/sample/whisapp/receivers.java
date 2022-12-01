package com.sample.whisapp;

public class receivers {
    String _email;
    String _message;


    public receivers(){}

    public receivers(String email, String message){
        this._email = email;
        this._message = message;
    }

    public String getEmail(){
        return this._email;
    }

    public void setEmail(String email){
        this._email = email;
    }

    public String getMessage(){
        return this._message;
    }

    public void setMessage(String message){
        this._message = message;
    }



}



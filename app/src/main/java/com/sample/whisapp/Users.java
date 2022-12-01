package com.sample.whisapp;

public class Users {
    String _name;
    String _password;
    String _email;
    String _message;



    public Users(){}

    public Users(String name, String password, String email, String message){
        this._name = name;
        this._password = password;
        this._email = email;
        this._message = message;



    }



    public String getName(){
        return this._name;
    }

    public void setName(String name){
        this._name = name;
    }

    public String getPassword(){
        return this._password;
    }

    public void setPassword(String password){
        this._password = password;
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


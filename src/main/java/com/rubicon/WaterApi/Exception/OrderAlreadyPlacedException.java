package com.rubicon.WaterApi.Exception;

public class OrderAlreadyPlacedException extends Exception{
    public OrderAlreadyPlacedException(String Message){
        super(Message);
    }
}

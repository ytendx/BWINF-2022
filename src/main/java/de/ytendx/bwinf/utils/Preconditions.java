package de.ytendx.bwinf.utils;

public class Preconditions {

    public static void require(boolean equation){
        if(!equation)
            throw new RuntimeException("A precondition was not valid.");
        else return;
    }

    public static void require(boolean equation, String errorMessage){
        if(!equation)
            throw new RuntimeException("A precondition was not valid. (msg: " + errorMessage + ")");
        else return;
    }

}

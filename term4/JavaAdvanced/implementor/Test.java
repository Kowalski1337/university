package ru.ifmo.rain.baydyuk.implementor;

import info.kgeorgiy.java.advanced.implementor.ImplerException;

import java.io.File;

public class Test {
    public static void main(String[] args) {
        try {
            Implementor implementor = new Implementor();
            implementor.implement(Class.forName("ru.ifmo.rain.baydyuk.implementor.Expresion"),
                    new File(System.getProperty("user.dir")).toPath());
        } catch (ClassNotFoundException e){
            e.getException().printStackTrace();
        } catch (ImplerException e) {
            e.printStackTrace();
        }
    }
}

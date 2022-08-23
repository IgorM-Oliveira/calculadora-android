package com.example.calculadoraandroid.utils;

public class InfoOperator {
    public Integer firstNumber;
    public char operatorChar;
    public Integer secondNumber;
    public Boolean done;

    public InfoOperator(Integer firstNumber, char operatorChar, Integer secondNumber, Boolean done) {
        this.firstNumber = firstNumber;
        this.operatorChar = operatorChar;
        this.secondNumber = secondNumber;
        this.done = done;
    }

    @Override
    public String toString() {
        return "InfoOperator{" +
                "firstNumber=" + firstNumber +
                ", operatorChar=" + operatorChar +
                ", secondNumber=" + secondNumber +
                ", done=" + done +
                '}';
    }
}

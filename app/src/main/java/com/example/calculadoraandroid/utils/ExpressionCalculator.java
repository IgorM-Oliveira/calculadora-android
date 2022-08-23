package com.example.calculadoraandroid.utils;

import java.util.ArrayList;

public class ExpressionCalculator {
    private String expression;
    private ArrayList<Float> operandValues;
    private ArrayList<InfoOperator> operators;

    public Integer precedence(char operatorPrecedence) {
        if (Character.compare(operatorPrecedence, '-') == 0 || Character.compare(operatorPrecedence, '+') == 0) {
            return -1;
        }
        if (Character.compare(operatorPrecedence, '*') == 0 || Character.compare(operatorPrecedence, '/') == 0) {
            return 1;
        }
        return 0; // Is not an operator.
    }

    public float calculate() throws Exception {
        float result = 0;

        // Calculate operations with operation precedence.
        for (int i = 0; i < operators.size(); i++) {
            if (!operators.get(i).done) {
                if (precedence(operators.get(i).operatorChar) == 1) {
                    if (Character.compare(operators.get(i).operatorChar, '*') == 0) {
                        result = (float) (operandValues.get(operators.get(i).firstNumber) * operandValues.get(operators.get(i).secondNumber));
                    }

                    if (Character.compare(operators.get(i).operatorChar, '/') == 0) {
                        if (operandValues.get(operators.get(i).secondNumber) == 0) {
                            throw new Exception("Divisão por zero");
                        }
                        result = operandValues.get(operators.get(i).firstNumber) / (float)operandValues.get(operators.get(i).secondNumber);
                    }
                    operandValues.set(operators.get(i).firstNumber, result);
                    operators.get(i).done = true;

                    int j = i;
                    boolean findNext = false;
                    while (j < operators.size() && !findNext) {
                        if (!operators.get(j).done) {
                            operators.get(j).firstNumber = operators.get(i).firstNumber;
                            findNext = true;
                        }
                        j++;
                    }
                }
            }
        }

        // Calculate other operations.
        for (int i = 0; i < operators.size(); i++) {
            if (!operators.get(i).done) {
                if (precedence(operators.get(i).operatorChar) == -1) {
                    if (Character.compare(operators.get(i).operatorChar, '+') == 0) {
                        result = (float) (operandValues.get(operators.get(i).firstNumber) + operandValues.get(operators.get(i).secondNumber));
                    }
                    if (Character.compare(operators.get(i).operatorChar, '-') == 0) {
                        result = (float) (operandValues.get(operators.get(i).firstNumber) - operandValues.get(operators.get(i).secondNumber));
                    }
                    operandValues.set(operators.get(i).firstNumber, result);
                    operators.get(i).done = true;

                    int j = i;
                    boolean findNext = false;
                    while (j < operators.size() && !findNext) {
                        if (!operators.get(j).done) {
                            operators.get(j).firstNumber = operators.get(i).firstNumber;
                            findNext = true;
                        }
                        j++;
                    }
                }
            }
        }

        return operandValues.get(0);
    }

    public void getValuesFromExpression() throws Exception {
        operandValues = new ArrayList<Float>();
        operators = new ArrayList<InfoOperator>();

        Integer expressionLength = expression.length();

        String number = "";
        int index = 0;

        if (expressionLength <= 0) {
            throw new Exception("Expressão vazia");
        }

        char firstCharacter = expression.charAt(0);
        char lastCharacter = expression.charAt(expressionLength-1);

        if (!Character.isDigit(firstCharacter) && Character.compare(firstCharacter, '-') != 0) {
            if (precedence(firstCharacter) != 0) {
                throw new Exception("Expressão sem operando");
            } else if (Character.compare(firstCharacter, '.') == 0) {
                throw new Exception("Ponto flutuante inválido");
            } else {
                throw new Exception("Caracter inválido");
            }
        }

        if (!Character.isDigit(lastCharacter)) {
            if (precedence(lastCharacter) != 0) {
                throw new Exception("Expressão sem operando");
            } else if (Character.compare(lastCharacter, '.') == 0) {
                throw new Exception("Ponto flutuante inválido");
            } else {
                throw new Exception("Caracter inválido");
            }
        }

        number += firstCharacter;
        index++;

        int numberOfOperators = 0;
        int numberOfPoints = 0;
        int indexValues = 0;
        int indexOperators = 0;

        for (int i = 1; i < expressionLength; i++) {
            boolean isDigit = Character.isDigit(expression.charAt(i));
            int isOperator = precedence(expression.charAt(i));

            // Is number or operator or float point.
            if (isDigit || isOperator != 0 || Character.compare(expression.charAt(i), '.') == 0) {
                if (isDigit || Character.compare(expression.charAt(i), '.') == 0) {
                    number += expression.charAt(i);
                    index++;

                    if (Character.compare(expression.charAt(i), '.') == 0) {
                        numberOfPoints++;
                        if (numberOfPoints > 1) {
                            throw new Exception("Operando com mais de um ponto flutuante");
                        }
                    }

                    if (i == expressionLength-1) {
                        if (Character.compare(expression.charAt(i), '.') != 0) {
                            index = 0;
                            operandValues.add(Float.parseFloat(number));
                            indexValues++;
                            number = "";
                        } else {
                            throw new Exception("Ponto flutuante inválido");
                        }
                    }
                } else if (isOperator != 0) {
                    numberOfOperators++;

                    if (index == 0) {
                        throw new Exception("Operadores consecutivos");
                    } else {
                        index = 0;
                        numberOfPoints = 0;
                        operandValues.add(Float.parseFloat(number));
                        operators.add(new InfoOperator(indexValues, expression.charAt(i), indexValues + 1, false));
                        indexValues++;
                        indexOperators++;
                        number = "";
                    }
                }
            } else {
                // Not a valid character, cause exception.
                throw new Exception("Caracter inválido");
            }
        }
    }

    // Constructor
    public ExpressionCalculator(String expression) {
        this.expression = expression;
    }

    // Getters and setters
    public ArrayList<Float> getOperandValues() {
        return operandValues;
    }
    public void setOperandValues(ArrayList<Float> operandValues) {
        this.operandValues = operandValues;
    }

    public ArrayList<InfoOperator> getOperators() {
        return operators;
    }
    public void setOperators(ArrayList<InfoOperator> operators) {
        this.operators = operators;
    }

    public String getExpression() {
        return expression;
    }
    public void setExpression(String expression) {
        this.expression = expression;
    }
}

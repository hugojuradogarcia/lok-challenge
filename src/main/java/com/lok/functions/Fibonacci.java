package com.lok.functions;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Fibonacci {
    public static void main(String args[]){
        Scanner in = new Scanner(System.in);

        System.out.println("Introduce un número entero referentes a los valores a imprimir de la serie de fibonacci:");
        try {
            int number = in.nextInt();
            if (number > 0) {
                List<Integer> fibonacciSerie = getFibonacciSeries(number);
                fibonacciSerie.forEach((n)-> {
                    System.out.println(n.intValue());
                });
            } else {
                System.out.println("Entrada incorrecta! Ingrese solo números enteros positivos.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada incorrecta! Ingrese solo números enteros positivos.");
        }

    }

    public static List<Integer> getFibonacciSeries(int number){
        int firstNumber = 0, secondNumber = 1, pivot;

        List<Integer> serie = new ArrayList<>();
        for (int i = 0 ; i < number ; i++){
            serie.add(firstNumber);
            pivot = firstNumber + secondNumber;
            firstNumber = secondNumber;
            secondNumber = pivot;
        }

        return serie;
    }
}

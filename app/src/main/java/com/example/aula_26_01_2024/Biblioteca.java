package com.example.aula_26_01_2024;

public class Biblioteca {
    public static boolean EPar(int num) {
        return num % 2 == 0;
    }
    public static boolean EPrimo(int num) {
        if (num <= 1) {
            return false;
        }

        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) {
                return false;  // Se for divisível por algum número além de 1 e ele mesmo, não é primo
            }
        }

        return true;  // Se não foi encontrado nenhum divisor, é primo
    }
}

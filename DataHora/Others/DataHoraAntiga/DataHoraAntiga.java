// Este programa exibe a data e hora atual usando a classe Date, que é considerada antiga e menos flexível.

import java.util.Date;

public class DataHoraAntiga {
    public static void main(String[] args) {
        Date agora = new Date();
        System.out.println("Data e Hora Atual: " + agora);
    }
}
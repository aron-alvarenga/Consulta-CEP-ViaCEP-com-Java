package br.com.aronalvarenga;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("----- CONSULTA CEP -----");
        System.out.print("Digite o CEP desejado: ");
        String cepDigitado = sc.next();

        try {
             String resultado = consultaCEP(cepDigitado);
            System.out.println(resultado);
        } catch (Exception e) {
            System.err.println("Erro na requisição: " + e.getMessage());
        }
    }

    public static String consultaCEP(String cep) throws Exception {
        // Formata o CEP removendo caracteres não numéricos
        cep = cep.replaceAll("[^\\d.]", "");

        // URL da API ViaCEP
        String url = "https://viacep.com.br/ws/" + cep + "/json/";

        // Criação da conexão HTTP
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // Configuração da requisição
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");

        // Verifica se a requisição foi bem-sucedida (código 200)
        int responseCode = con.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException("Erro na requisição. Código: " + responseCode);
        }

        // Leitura da resposta
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuilder response = new StringBuilder();
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // Retorna a resposta em formato de String
        return response.toString();
    }
}

package Client;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class EnviaComando implements Runnable {

    Socket socket;
    String nome;

    public EnviaComando(Socket socket, String nome) {
        this.socket = socket;
        this.nome = nome;
    }

    @Override
    public void run() {
        try {
            System.out.println("Pode enviar comandos!");
            PrintStream saida = new PrintStream(
                    socket.getOutputStream());
            Scanner teclado = new Scanner(System.in);
            while (teclado.hasNextLine()) {
                String linha = teclado.nextLine();
                if (linha.trim().equals("")) {
                    saida.println(linha);
                    break;
                }else{
                    saida.println(nome + " -> " + linha);
                }
            }
            saida.close();
            teclado.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

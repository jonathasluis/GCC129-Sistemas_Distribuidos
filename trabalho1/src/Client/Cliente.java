package Client;

import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) throws Exception {
        
        String nome;
        System.out.print("Digite seu nome: ");
        Scanner teclado = new Scanner(System.in);
        nome = teclado.nextLine();

        Socket socket = new Socket("localhost", 10000);
        System.out.println("===Conexão Estabelecida===");
        Thread enviaComando = new Thread(new EnviaComando(socket,nome));
        Thread recebeResposta = new Thread(new RecebeResposta(socket));

        enviaComando.start();
        recebeResposta.start();
        enviaComando.join();
        socket.close();
        teclado.close();
    }
}

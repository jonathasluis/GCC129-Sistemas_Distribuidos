package Server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class DistribuirTarefas implements Runnable {

    private final Socket socket;
    private final List<Socket> lista;

    public DistribuirTarefas(Socket socket, List<Socket> lista) {
        this.socket = socket;
        this.lista = lista;
    }

    @Override
    public void run() {
        System.out.println("Distribuindo as tarefas para o cliente " + socket);
        try {
            Scanner entradaCliente = new Scanner(socket.getInputStream());
            PrintStream saidaCliente = new PrintStream(socket.getOutputStream());
            while (entradaCliente.hasNextLine()) {
                String comando = entradaCliente.nextLine();
                System.out.println(comando);
                if (!"".equals(comando)) {
                    sendAll(comando, socket);
                } else {
                    lista.remove(socket);
                }
            }
            entradaCliente.close();
            saidaCliente.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void sendAll(String msg, Socket socket) throws IOException {
        lista.removeIf(Socket::isClosed);
        for (Socket s : lista) {
            PrintStream saidaCliente = new PrintStream(s.getOutputStream());
            if (!s.equals(socket)) {
                try {
                    saidaCliente.println(msg);
                } catch (Exception e) {
                    System.out.println("opa");
                }
            }

        }
    }
}

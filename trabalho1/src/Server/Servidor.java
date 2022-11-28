package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class Servidor {

    private final ServerSocket server;
    private final AtomicBoolean estaRodando;
    private final ExecutorService threadPool;

    private List<Socket> listaDeSockets;

    public Servidor() throws IOException {
        System.out.println("---- Iniciando Servidor ----");
        this.server = new ServerSocket(10000);
        this.estaRodando = new AtomicBoolean(true);
        this.threadPool = Executors.newCachedThreadPool();
        listaDeSockets = new ArrayList<>();
    }

    public void rodar() throws IOException {
        while (this.estaRodando.get()) {
            try {
                Socket socket = this.server.accept();
                System.out.println("Aceitando novo cliente na porta " + socket.getPort());
                listaDeSockets.add(socket);
                DistribuirTarefas distribuirTarefas = new DistribuirTarefas(socket, listaDeSockets);
                this.threadPool.execute(distribuirTarefas);
            } catch (SocketException e) {
                System.out.println("SocketException, está rodando? " + this.estaRodando);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Servidor servidor = new Servidor();
        servidor.rodar();
    }
}
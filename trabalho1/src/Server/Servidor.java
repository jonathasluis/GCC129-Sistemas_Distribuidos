package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class Servidor {

    private ServerSocket servidor;
    private AtomicBoolean estaRodando;
    private ExecutorService threadPool;


    private static volatile ArrayList<Socket> lista;

    public Servidor() throws IOException {
        System.out.println("---- Iniciando Servidor ----");
        this.servidor = new ServerSocket(10000);
        this.estaRodando = new AtomicBoolean(true);
        this.threadPool = Executors.newCachedThreadPool();
        lista = new ArrayList<>();
    }

    public void rodar() throws IOException {

        while (this.estaRodando.get()) {
            try {
                Socket socket = this.servidor.accept();
                System.out.println("Aceitando novo cliente na porta " + socket.getPort());
                lista.add(socket);
                DistribuirTarefas distribuirTarefas = new DistribuirTarefas(socket,lista,this);
                this.threadPool.execute(distribuirTarefas);
            } catch (SocketException e) {
                System.out.println("SocketException, está rodando? " + this.estaRodando);
            }
        }
    }

    public void parar() throws IOException {
        this.estaRodando.set(false);
        this.servidor.close();
    }

    public static void main(String[] args) throws Exception {
        Servidor servidor = new Servidor();
        servidor.rodar();
    }

    public static ArrayList<Socket> getLista() {
        return lista;
    }
}
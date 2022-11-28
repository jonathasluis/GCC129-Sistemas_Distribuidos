package Server;

import java.net.Socket;

public class ServerAdmin {

    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 10000);
        System.out.println("===Conexão Estabelecida admin===");
        //Thread enviaComando = new Thread(new EnviaComando(socket));

        //enviaComando.start();
        //enviaComando.join();
        socket.close();
    }

}

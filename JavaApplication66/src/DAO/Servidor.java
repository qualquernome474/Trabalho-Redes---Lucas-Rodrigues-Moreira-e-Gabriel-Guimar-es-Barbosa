package DAO;

import CONTROL.TrataCliente;
import MODEL.Mensagem;
import java.net.Socket;
import java.util.ArrayList;
import java.net.ServerSocket;
import java.io.IOException;

public class Servidor {

    ServerSocket soquete_servidor;
    ArrayList<Mensagem> mensagens;

    static String IPServidor = "10.90.37.83";

    public Servidor(String endereco, int porta) throws Exception {
        super();
        this.soquete_servidor = new ServerSocket(porta);
        this.mensagens = new ArrayList<Mensagem>();
    }

    public static void main(String[] args) throws Exception {
        Servidor servidor = new Servidor(IPServidor, 15500);
        
        while (true) {
            
            try {
                Socket soqueteCliente = null;
                soqueteCliente = servidor.soquete_servidor.accept();
                new Thread(new TrataCliente(soqueteCliente, servidor.mensagens)).start();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public static String RetornaIp() {
        return IPServidor;
    }
}

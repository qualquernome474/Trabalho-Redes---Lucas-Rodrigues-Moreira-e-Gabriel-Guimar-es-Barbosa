package MODEL;

import DAO.Servidor;
import VIEW.Frame;
import VIEW.TelaP;
import java.net.Socket;
import java.util.ArrayList;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class Cliente {

    private static Socket soquete;
    private ObjectOutputStream saida;
    private ObjectInputStream entrada;
    private static ArrayList<Mensagem> mensagens;

    private static Cliente cliente = null;

    private static String nome = null;

    private static ArrayList<String> clientesConectados = new ArrayList<>();

    public Cliente(String endereco, int porta) throws Exception {
        super();

        this.soquete = new Socket(endereco, porta);
        this.saida = new ObjectOutputStream(this.soquete.getOutputStream());
        this.entrada = new ObjectInputStream(this.soquete.getInputStream());
    }

    public void enviar_mensagem(Object mensagem) throws Exception {
        this.saida.writeObject(mensagem);

    }

    public Object receber_mensagem() throws Exception {
        return this.entrada.readObject();

    }

    public void finalizar() throws IOException {
        this.soquete.close();
    }

    public static void fazcadastro(String nome, String senha) throws Exception {

        Scanner ler = new Scanner(System.in);

        boolean estalogado = false;
        int x = 0;

        Cliente.cliente = new Cliente(Servidor.RetornaIp(), 15500);

        Cliente.nome = nome;

        Mensagem m = new Mensagem();
        m.setNome(nome);
        m.setCodigo(-1);
        m.setSenha(senha);

        Cliente.cliente.enviar_mensagem(m);

        estalogado = true;

    }

    public static boolean verificastatus(String nome) throws Exception {

        Cliente.cliente = new Cliente(Servidor.RetornaIp(), 15500);

        Cliente.nome = nome;

        Mensagem m = new Mensagem();
        m.setNome(nome);
        m.setCodigo(-6);

        Cliente.cliente.enviar_mensagem(m);

        boolean n;
        n = (boolean) cliente.receber_mensagem();

        return n;
    }

    public static void logOffforcado() throws Exception {
        Cliente.cliente = new Cliente(Servidor.RetornaIp(), 15500);
        Cliente.nome = nome;

        Mensagem m = new Mensagem();
        m.setNome(nome);
        m.setCodigo(-8);

        Cliente.cliente.enviar_mensagem(m);

    }

    public static void fazcalogin(String nome, String senha) throws Exception {
        Scanner ler = new Scanner(System.in);

        boolean estalogado = false;
        int x = 0;

        Cliente.cliente = new Cliente(Servidor.RetornaIp(), 15500);

        estalogado = verificastatus(nome);

        if (estalogado == true) {
            Cliente.nome = nome;

            Mensagem m = new Mensagem();
            m.setNome(nome);
            m.setCodigo(-5);
            m.setSenha(senha);

            Cliente.cliente.enviar_mensagem(m);

            m = (Mensagem) cliente.receber_mensagem();

            if (m.getCodigo() == -10) {
                Frame.trocaPainel("Tela Principal", new TelaP(Cliente.nome));

            } else {
                JOptionPane.showMessageDialog(null, "Dados incorretos", "ERRO", JOptionPane.INFORMATION_MESSAGE);

            }
        } else {

            JOptionPane.showMessageDialog(null, "Já tem alguem logado nesse nome", "ERRO", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    
}

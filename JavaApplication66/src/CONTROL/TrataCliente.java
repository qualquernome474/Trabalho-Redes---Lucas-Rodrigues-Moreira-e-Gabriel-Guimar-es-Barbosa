package CONTROL;

import DAO.DAO;
import MODEL.Mensagem;
import java.io.EOFException;
import java.net.Socket;
import java.util.ArrayList;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JOptionPane;

public class TrataCliente implements Runnable {

    private Socket soquete_cliente;
    private ObjectOutputStream saida;
    private ObjectInputStream entrada;
    private static ArrayList<Mensagem> mensagens;
    private static ArrayList<String> IPs = new ArrayList<String>();

    public TrataCliente(Socket soquete_cliente, ArrayList<Mensagem> mensagens) throws Exception {
        super();
        this.soquete_cliente = soquete_cliente;
        this.saida = new ObjectOutputStream(this.soquete_cliente.getOutputStream());
        this.entrada = new ObjectInputStream(this.soquete_cliente.getInputStream());
        this.mensagens = mensagens;
    }

    public void enviar_mensagem(Object mensagem) throws Exception {
        this.saida.writeObject(mensagem);
    }

    public Object receber_mensagem() throws Exception {
        return this.entrada.readObject();
    }

    @Override
    public void run() {

        try {

            while (true) {
                try {
                    Mensagem mensagem = (Mensagem) receber_mensagem();

                    if (mensagem != null) {

                        if (mensagem.getCodigo() >= 0) {

                            if (mensagem.getCodigo() == 1) {
                                DAO SM = new DAO();

                                Mensagem M = new Mensagem(mensagem.getNome(), mensagem.getTexto(), mensagem.getIP(), SM.pegaultimocodigo(), mensagem.getDest(), mensagem.getSenha());

                                SM.gravarMensagem(M);

                                mensagens.add(mensagem);
                            }

                            if (mensagem.getCodigo() == 2) {
                                DAO SM = new DAO();

                                if (SM.verificaNome(mensagem.getDest()) == true) {

                                    Mensagem M = new Mensagem(mensagem.getNome(), mensagem.getTexto(), mensagem.getIP(), SM.pegaultimocodigo(), mensagem.getDest(), mensagem.getSenha());

                                    SM.gravarMensagem(M);

                                    mensagens.add(mensagem);
                                } else {

                                    JOptionPane.showMessageDialog(null, "Destinatário inválido", "ERRO", JOptionPane.ERROR_MESSAGE);

                                }
                            }

                        } else {
                            if (mensagem.getCodigo() == -1 || mensagem.getCodigo() == -5) {
                                DAO SM = new DAO();

                                if (mensagem.getCodigo() == -1) {
                                    if (SM.verificaexistencia(mensagem.getNome(), mensagem.getSenha()) == false) {
                                        SM.gravarCliente(mensagem.getNome(), mensagem.getSenha());

                                    }
                                } else {

                                    if (SM.verificaexistencia(mensagem.getNome(), mensagem.getSenha()) == true) {
                                        SM.atualizastatus(mensagem.getNome());

                                        Mensagem m = new Mensagem();

                                        m.setCodigo(-10);

                                        enviar_mensagem(m);
                                    } else {
                                        Mensagem m = new Mensagem();

                                        m.setCodigo(-11);

                                        enviar_mensagem(m);
                                    }
                                }
                            }
                            if (mensagem.getCodigo() == -2) {
                                DAO SM = new DAO();
                                SM.atualizastatus(mensagem.getNome());

                            }

                            if (mensagem.getCodigo() == -4) {
                                DAO SM = new DAO();

                                ArrayList<String> clientesConectados = SM.verificaonlines();
                                enviar_mensagem(clientesConectados);

                            }

                            if (mensagem.getCodigo() == -6) {

                                DAO SM = new DAO();

                                boolean temlogado = SM.verificastatus(mensagem.getNome());

                                enviar_mensagem(temlogado);
                            }

                            if (mensagem.getCodigo() == -8) {

                                DAO SM = new DAO();

                                SM.logOffforcado(mensagem.getNome());

                            }

                            if (mensagem.getCodigo() == -7) {
                                DAO SM = new DAO();

                                ArrayList<String> listachatglobal = SM.retornachatglobal();
                                enviar_mensagem(listachatglobal);

                            }

                            if (mensagem.getCodigo() == -10) {
                                DAO SM = new DAO();

                                ArrayList<String> listachatprivado = SM.retornachatprivado(mensagem);
                                enviar_mensagem(listachatprivado);

                            }

                        }

                    } else {

                        DAO SM = new DAO();
                        mensagens = SM.RecuperaMensagens();
                        enviar_mensagem(mensagens);

                    }
                } catch (EOFException ex) {

                } catch (java.net.SocketException ex) {

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

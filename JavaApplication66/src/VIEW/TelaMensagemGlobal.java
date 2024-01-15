/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package VIEW;

import DAO.Servidor;
import MODEL.Cliente;
import MODEL.Mensagem;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

/**
 *
 * @author 0068952
 */
public class TelaMensagemGlobal extends javax.swing.JPanel implements PainelSwingWorker {

    /**
     * Creates new form TelaMensagemGlobal
     */
    private String Nome;
    private int controle;
    private SwingWorker atualizadorPontosG;

    private static ArrayList<String> clientesConectadosT = new ArrayList<>();

    public TelaMensagemGlobal(String nome) {
        initComponents();
        minhasconfiguracoes();

        this.Nome = nome;

    }

    public void minhasconfiguracoes() {

        dadosListaModel2 = new DefaultListModel<>();

        this.jList2.setModel(this.dadosListaModel2);

        dadosListaModel1 = new DefaultListModel<>();

       

    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            BufferedImage imagem = ImageIO.read(getClass().getResource("/RESOUCE/telamensagemglobal.PNG"));//por o nome da tela que vai estar em resouce

            g.drawImage(imagem, 0, 0, this);

        } catch (IOException ex) {
            System.err.println("arquivo não existe no resource");
        }
    }

    private DefaultListModel<String> dadosListaModel1;
    private DefaultListModel<Mensagem> dadosListaModel2;

    private ServerSocket soquete_servidor;
    private ArrayList<Mensagem> mensagens;
    private ArrayList<String> listaantiga;

    private ObjectOutputStream saida;
    private ObjectInputStream entrada;

    private ArrayList<Mensagem> listachatglobal = new ArrayList<>();
    private ArrayList<Mensagem> listachatglobalT = new ArrayList<>();

    private static ArrayList<String> clientesConectados = new ArrayList<>();
    private static ArrayList<String> mensagemglobal = new ArrayList<>();
    private static Cliente cliente = null;

    public void enviar_mensagem(Object mensagem) throws Exception {
        this.saida.writeObject(mensagem);

    }

    public Object receber_mensagem() throws Exception {
        return this.entrada.readObject();

    }

    public void telabk() {
        atualizadorPontosG = new SwingWorker() {

            @Override
            protected Object doInBackground() throws Exception {

                while (true) {

                    cliente = new Cliente(Servidor.RetornaIp(), 15500);

                    Mensagem m = new Mensagem();
                    m.setNome("a");
                    m.setCodigo(-7);

                    TelaMensagemGlobal.cliente.enviar_mensagem(m);

                    listachatglobalT = (ArrayList<Mensagem>) cliente.receber_mensagem();
                    dadosListaModel2 = new DefaultListModel<>();
                    
                    jList2.setModel(dadosListaModel2);

                    
                    dadosListaModel2.addAll(listachatglobalT);
                    

                    Thread.sleep(1000);

                }

            }

        };

        atualizadorPontosG.execute();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList<>();

        jButton1.setText("Enviar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Voltar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(jList2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(153, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(67, 67, 67))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)
                        .addGap(93, 93, 93)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(114, 114, 114)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(98, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        try {
            this.finalizaSW();
            controle = 1;
            Frame.trocaPainel("Tela Principal", new TelaP(Nome));
        } catch (Exception ex) {
            Logger.getLogger(TelaMensagemGlobal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        if (!jTextField1.getText().isBlank()) {
            try {
                cliente = new Cliente(Servidor.RetornaIp(), 15500);

                Mensagem m = new Mensagem();

                m.setCodigo(1);
                m.setDest(null);

                m.setIP(InetAddress.getLocalHost().getHostAddress());

                m.setNome(Nome);
                m.setTexto(jTextField1.getText());

                cliente.enviar_mensagem(m);

            } catch (UnknownHostException ex) {
                Logger.getLogger(TelaMensagemGlobal.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(TelaMensagemGlobal.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            JOptionPane.showMessageDialog(null, "Informe a mensagem para que a mesma seja enviada", "ERRO", JOptionPane.INFORMATION_MESSAGE);

        }


    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JList<Mensagem> jList2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void iniciaSW() {
        telabk();
    }

    @Override
    public void finalizaSW() {
        atualizadorPontosG.cancel(true);
    }
}
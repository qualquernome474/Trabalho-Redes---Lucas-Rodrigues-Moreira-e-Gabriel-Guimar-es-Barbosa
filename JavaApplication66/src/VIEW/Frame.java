package VIEW;

import MODEL.Mensagem;
import MODEL.Cliente;
import VIEW.TelaInicial;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
/**
 *
 * @author SYSTEM N.P
 */
public class Frame extends javax.swing.JFrame {

    private static Socket soquete;
    private ObjectOutputStream saida;
    private ObjectInputStream entrada;
    private static ArrayList<Mensagem> mensagens;

    private static Cliente cliente = null;

    private static String nome = null;

    private static ArrayList<String> clientesConectados = new ArrayList<>();

    public Frame(String endereco, int porta) throws Exception {
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

    private static JScrollPane painelRolagem;
    private static JPanel painelTroca;

    private static CardLayout baralhoPaineis;

    private static Hashtable<String, JPanel> historicoPaineis;
    private static Frame INSTANCE;

    /**
     * Creates new form Frame
     */
    public Frame() {
        initComponents();
        configuraTrocaPaineis();

        INSTANCE = this;
        trocaPainel("Tela Inicial", new TelaInicial());

    }

    private void configuraTrocaPaineis() {

        this.setLayout(new BorderLayout());
        baralhoPaineis = new CardLayout();
        painelTroca = new JPanel(baralhoPaineis);
        painelRolagem = new JScrollPane();

        this.add(painelRolagem);
        painelRolagem.setViewportView(this.painelTroca);
        historicoPaineis = new Hashtable<>();

    }

    public static void trocaPainel(String nome, JPanel novoPainel) {

        painelTroca.add(novoPainel, nome);

        baralhoPaineis.show(painelTroca, nome);

        painelTroca.setPreferredSize(novoPainel.getPreferredSize());
        painelTroca.setSize(novoPainel.getSize());

        historicoPaineis.put(nome, novoPainel);

        if (novoPainel.getClass() == TelaP.class || novoPainel.getClass() == TelaMensagemGlobal.class || novoPainel.getClass() == TelaMensagemEspecifica.class) {

            ((PainelSwingWorker) novoPainel).iniciaSW();

        }

        INSTANCE.setSize(new Dimension(painelTroca.getPreferredSize().width + 23, painelTroca.getPreferredSize().height + 25));

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(700, 400));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try {
            Cliente.logOffforcado();
            Thread.sleep(1000);
        } catch (Exception ex) {
            Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowClosing

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Frame().setVisible(true);
                } catch (Exception ex) {
                    Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}

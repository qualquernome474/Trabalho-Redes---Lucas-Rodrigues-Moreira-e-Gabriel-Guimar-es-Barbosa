package DAO;

import CONTROL.TrataCliente;
import MODEL.Mensagem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author 0068952
 */
public class DAO {

    public int pegaultimocodigo() {
        try {
            ArrayList<Mensagem> retorno = new ArrayList<Mensagem>();
            Connection c = Conexao.obterConexao();
            String sql = "SELECT * FROM lucas_chatbd.mensagens";
            PreparedStatement consulta = c.prepareStatement(sql);
            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()) {
                Mensagem atual = new Mensagem();

                retorno.add(atual);
            }
            int ultimocodigo = retorno.size() + 1;

            c.close();

            return ultimocodigo;
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro: " + ex.getMessage());
            return 0;
        }
    }

    public boolean verificaNome(String Nome) {
        try {
            boolean b = false;
            Connection c = Conexao.obterConexao();
            String sql = "SELECT * FROM lucas_chatbd.clientes";
            PreparedStatement consulta = c.prepareStatement(sql);
            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()) {
                Mensagem atual = new Mensagem();

                atual.setNome(resultado.getString("nome"));
                

                if (atual.getNome().equalsIgnoreCase(Nome) ) {
                    b = true;

                }
            }

            c.close();

            return b;
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro: " + ex.getMessage());
            return false;
        }

    }

    public boolean verificastatus(String nome) {
        try {

            Connection c = Conexao.obterConexao();
            String sql = "SELECT * FROM lucas_chatbd.clientesonline WHERE nome='" + nome + "' ";
            PreparedStatement consulta = c.prepareStatement(sql);
            ResultSet resultado = consulta.executeQuery();
            if (!resultado.next()) {
                c.close();
                return false;
            } else {

                if (resultado.getString("status").equalsIgnoreCase("1")) {
                    c.close();
                    return false;

                } else {
                    c.close();
                    return true;
                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro: " + ex.getMessage());
            return false;
        }

    }

    public void gravarMensagem(Mensagem dados) {
        try {
            Connection c = Conexao.obterConexao();
            String sql = "INSERT INTO lucas_chatbd.mensagens(mensagem,nome,ips, dest) "
                    + " VALUES (?,?,?,?);";
            PreparedStatement insercao = c.prepareStatement(sql);
            insercao.setString(1, dados.getTexto());
            insercao.setString(2, dados.getNome());
            insercao.setString(3, dados.getIP());
            insercao.setString(4, dados.getDest());

            insercao.execute();
            c.close();

        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    public ArrayList<Mensagem> RecuperaMensagens() {
        ArrayList<Mensagem> LM = new ArrayList<>();
        try {
            Connection c = Conexao.obterConexao();

            String sql = "SELECT * FROM lucas_chatbd.mensagens";
            Statement consulta = c.createStatement();

            ResultSet resultado = consulta.executeQuery(sql);

            while (resultado.next()) {
                Mensagem M = new Mensagem();
                M.setNome(resultado.getString("nome"));
                M.setTexto(resultado.getString("mensagem"));
                M.setIP(resultado.getString("ips"));
                M.setDest(resultado.getString("dest"));
                LM.add(M);
            }
            c.close();
            return LM;

        } catch (SQLException ex) {
            Logger.getLogger(TrataCliente.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro: " + ex.getMessage());
            return null;
        }

    }

    public boolean verificaexistencia(String nome, String senha) {
        try {
            boolean b = false;
            Connection c = Conexao.obterConexao();
            String sql = "SELECT * FROM lucas_chatbd.clientes";
            PreparedStatement consulta = c.prepareStatement(sql);
            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()) {
                Mensagem atual = new Mensagem();

                atual.setNome(resultado.getString("nome"));
                atual.setSenha(resultado.getString("senha"));

                if (atual.getNome().equalsIgnoreCase(nome) && atual.getSenha().equals(senha)) {
                    b = true;

                }
            }

            c.close();

            return b;
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro: " + ex.getMessage());
            return false;
        }

    }

    public void gravarCliente(String nome, String senha) {
        try {
            Connection c = Conexao.obterConexao();
            String sql = "INSERT INTO lucas_chatbd.clientes(nome, senha) "
                    + " VALUES (?,?);";
            PreparedStatement insercao = c.prepareStatement(sql);
            insercao.setString(1, nome);
            insercao.setString(2, senha);

            insercao.execute();
            c.close();

            c = Conexao.obterConexao();
            sql = "INSERT INTO lucas_chatbd.clientesonline(nome, status) "
                    + " VALUES (?, ?);";
            insercao = c.prepareStatement(sql);
            insercao.setString(1, nome);
            insercao.setInt(2, 0);

            insercao.execute();
            c.close();

        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    public boolean atualizastatus(String nome) throws SQLException {
        try {
            Connection c = Conexao.obterConexao();
            String sql = "SELECT * FROM lucas_chatbd.clientesonline WHERE nome='" + nome + "' ";

            Statement consulta = c.createStatement();

            ResultSet resultado = consulta.executeQuery(sql);

            if (resultado.next()) {
                int statusnovo = resultado.getInt("status");
                if (statusnovo == 0) {
                    statusnovo = 1;
                } else {
                    statusnovo = 0;
                }

                sql = "UPDATE lucas_chatbd.clientesonline "
                        + " SET nome=?, status=?"
                        + " WHERE nome='" + nome + "' ";
                PreparedStatement atualizacao = c.prepareStatement(sql);

                atualizacao.setString(1, nome);
                atualizacao.setInt(2, statusnovo);

                atualizacao.executeUpdate();
                c.close();

                return true;
            } else {
                return false;
            }

        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);

        }
        return false;

    }

    public ArrayList<String> verificaonlines() {

        ArrayList<String> clientesConectados = new ArrayList<>();
        try {
            Connection c = Conexao.obterConexao();
            String sql = "SELECT * FROM lucas_chatbd.clientesonline WHERE status=" + 1;

            Statement consulta = c.createStatement();

            ResultSet resultado = consulta.executeQuery(sql);

            while (resultado.next()) {
                clientesConectados.add(resultado.getString("nome"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);

        }
        return clientesConectados;
    }

    public void logOffforcado(String nome) throws SQLException {

        Connection c = Conexao.obterConexao();
        String sql = "UPDATE lucas_chatbd.clientesonline "
                + " SET nome=?, status=?"
                + " WHERE nome='" + nome + "' ";
        PreparedStatement insercao = c.prepareStatement(sql);
        insercao.setString(1, nome);
        insercao.setInt(2, 0);

        insercao.execute();
        c.close();
    }

    public ArrayList<String> retornachatglobal() {

        ArrayList<String> listachatglobal = new ArrayList<>();
        try {
            Connection c = Conexao.obterConexao();
            String sql = "SELECT * FROM lucas_chatbd.mensagens WHERE dest ISNULL";

            Statement consulta = c.createStatement();

            ResultSet resultado = consulta.executeQuery(sql);

            while (resultado.next()) {
                listachatglobal.add(resultado.getString("nome") + ": " + resultado.getString("mensagem"));
                
            }

        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);

        }
        return listachatglobal;

    }

    public ArrayList<String> retornachatprivado(Mensagem mensagem) {

        ArrayList<String> listachatprivado = new ArrayList<>();
        try {
            Connection c = Conexao.obterConexao();
            String sql = "SELECT * FROM lucas_chatbd.mensagens WHERE (nome='" + mensagem.getNome() + "' and dest='" + mensagem.getDest() + "') or (nome='" + mensagem.getDest() + "' and dest='" + mensagem.getNome() + "')";

            Statement consulta = c.createStatement();

            ResultSet resultado = consulta.executeQuery(sql);

            while (resultado.next()) {
                listachatprivado.add(resultado.getString("nome") + ": " + resultado.getString("mensagem"));
                
            }

        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);

        }
        return listachatprivado;
    }
}

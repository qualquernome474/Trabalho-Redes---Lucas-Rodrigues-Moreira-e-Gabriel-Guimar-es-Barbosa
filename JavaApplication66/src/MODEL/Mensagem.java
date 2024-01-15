package MODEL;


import java.io.Serializable;

public class Mensagem implements Serializable {

    private String nome;
    private String texto;
    private String IP;
    private int codigo;
    private String dest;
    private String senha;

    public Mensagem() {
        super();
    }

    public Mensagem(String nome, String texto, String IP, int codigo, String dest, String senha) {
        this.nome = nome;
        this.texto = texto;
        this.IP = IP;
        this.codigo = codigo;
        this.dest = dest;
        this.senha=senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getSenha() {
        return senha;
    }

    

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    @Override
    public String toString() {
        return nome + ": " + texto + " " + IP;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    
}

package pucrs.myflight.modelo;

public class Pais {
    private String nome;
    private String codigo;

    public Pais(String codigo, String nome) {
        this.nome = nome;
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public String getCodigo() {
        return codigo;
    }

    @Override
    public String toString() {
        return String.format("Nome: %s\nCodigo: %s\n",nome,codigo);
    }
}

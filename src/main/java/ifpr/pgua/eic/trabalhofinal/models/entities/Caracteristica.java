package ifpr.pgua.eic.trabalhofinal.models.entities;

public class Caracteristica {
    private int id;
    private int quantidade;
    private String descricao;

    public Caracteristica(int id, String descricao, int quantidade){
        this.id = id;
        this.quantidade = quantidade;
        this.descricao = descricao;

    }

    public Caracteristica(String descricao, int quantidade){
        this.descricao = descricao;
        this.quantidade = quantidade;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return this.descricao;
    }

}

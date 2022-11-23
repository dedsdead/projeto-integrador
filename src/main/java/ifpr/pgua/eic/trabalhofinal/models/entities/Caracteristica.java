package ifpr.pgua.eic.trabalhofinal.models.entities;

public class Caracteristica {
    private int id;
    private String descricao;

    public Caracteristica(int id, String descricao){
        this.id = id;
        this.descricao = descricao;

    }

    public Caracteristica(String descricao){
        this.descricao = descricao;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "Tipo [ id = " + id +
                    "\n nome = " + descricao + " ]";
    }

}

package ifpr.pgua.eic.trabalhofinal.models.entities;

public class Tipo {
    private int id;
    private String nome;

    public Tipo(int id, String nome){
        this.id = id;
        this.nome = nome;

    }

    public Tipo(String nome){
        this.nome = nome;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Tipo [ id = " + id +
                    "\n nome = " + nome + " ]";
    }
    
}

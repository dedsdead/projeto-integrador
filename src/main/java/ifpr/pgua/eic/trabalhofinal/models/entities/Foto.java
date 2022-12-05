package ifpr.pgua.eic.trabalhofinal.models.entities;

public class Foto {
    private int id;
    private String caminho;

    public Foto(int id, String caminho) {
        this.id = id;
        this.caminho = caminho;
        
    }

    public Foto(String caminho) {
        this.caminho = caminho;
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    @Override
    public String toString() {
        return "Foto [id = " + id + "\n, caminho = " + caminho + "]\n";
    }

}

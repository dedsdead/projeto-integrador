package ifpr.pgua.eic.trabalhofinal.models.entities;

public class Endereco {
    private int id;
    private String cep;
    private String uf;
    private String localidade;
    private String logradouro;
    private int numero;
    private String complemento;

    public Endereco(int id, String cep, String uf, String localidade, String logradouro, int numero, String complemento){
        this.id = id;
        this.cep = cep;
        this.uf = uf;
        this.localidade = localidade;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;

    }

    public Endereco(String cep, String uf, String localidade, String logradouro, int numero, String complemento){
        this.cep = cep;
        this.uf = uf;
        this.localidade = localidade;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getEstado() {
        return uf;
    }

    public void setEstado(String uf) {
        this.uf = uf;
    }

    public String getCidade() {
        return localidade;
    }

    public void setCidade(String localidade) {
        this.localidade = localidade;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    @Override
    public String toString() {
        return "Endereco [ id = " + id +
                        "\n cep = " + cep +
                        "\n estado = " + uf +
                        "\n cidade = "+ localidade +
                        "\n logradouro = " + logradouro +
                        "\n numero = " + numero +
                        "\n complemento = " + complemento + " ]";
    }

}

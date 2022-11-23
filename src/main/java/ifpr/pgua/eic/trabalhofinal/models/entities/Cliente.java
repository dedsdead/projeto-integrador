package ifpr.pgua.eic.trabalhofinal.models.entities;

public class Cliente {
    private int id;
    private int idEndereco;
    private int idTipo;
    private int idCaracteristica;
    private String nome;
    private String telefone;
    private String cpf;
    private String email;
    private boolean ativo;

    public Cliente(int id, int idEndereco, int idTipo, int idCaracteristica, String nome, String telefone, String cpf, String email, boolean ativo) {
        this.id = id;
        this.idEndereco = idEndereco;
        this.idTipo = idTipo;
        this.idCaracteristica = idCaracteristica;
        this.nome = nome;
        this.telefone = telefone;
        this.cpf = cpf;
        this.email = email;
        this.ativo = ativo;

    }

    public Cliente(int idEndereco, int idTipo, int idCaracteristica, String nome, String telefone, String cpf, String email, boolean ativo) {
        this.idEndereco = idEndereco;
        this.idTipo = idTipo;
        this.idCaracteristica = idCaracteristica;
        this.nome = nome;
        this.telefone = telefone;
        this.cpf = cpf;
        this.email = email;
        this.ativo = ativo;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdEndereco() {
        return idEndereco;
    }

    public void setIdEndereco(int idEndereco) {
        this.idEndereco = idEndereco;
    }

    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    public int getIdCaracteristica() {
        return idCaracteristica;
    }

    public void setIdCaracteristica(int idCaracteristica) {
        this.idCaracteristica = idCaracteristica;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public String toString() {
        return "Cliente [ id = " + id +
                        "\n idEndereco = " + idEndereco +
                        "\n idTipo = " + idTipo +
                        "\n idCaracteristica = "+ idCaracteristica +
                        "\n nome = " + nome +
                        "\n telefone = " + telefone +
                        "\n cpf = " + cpf +
                        "\n email = " + email +
                        "\n ativo = " + ativo + " ]";
    }
   
}

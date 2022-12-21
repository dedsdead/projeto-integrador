package ifpr.pgua.eic.trabalhofinal.models.entities;

import java.time.LocalDateTime;

public class Imovel {
    private int id;
    private int idTipo;
    private int idCaracteristica;
    private int idEndereco;
    private int idProprietario;
    private String descricao;
    private double metragem;
    private double valor;
    private String matricula;
    private LocalDateTime dataVenda;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataExclusao;

    public Imovel(int id,
                  int idTipo,
                  int idCaracteristica,
                  int idEndereco,
                  int idProprietario,
                  String descricao,
                  double metragem,
                  double valor,
                  String matricula,
                  LocalDateTime dataVenda,
                  LocalDateTime dataExclusao) {
        this.id = id;
        this.idTipo = idTipo;
        this.idCaracteristica = idCaracteristica;
        this.idEndereco = idEndereco;
        this.idProprietario = idProprietario;
        this.descricao = descricao;
        this.metragem = metragem;
        this.valor = valor;
        this.matricula = matricula;
        this.dataVenda = dataVenda;
        this.dataExclusao = dataExclusao;
        
    }

    public Imovel(int idTipo,
                  int idCaracteristica,
                  int idEndereco,
                  int idProprietario,
                  String descricao,
                  double metragem,
                  double valor,
                  String matricula) {
        this.idTipo = idTipo;
        this.idCaracteristica = idCaracteristica;
        this.idEndereco = idEndereco;
        this.idProprietario = idProprietario;
        this.descricao = descricao;
        this.metragem = metragem;
        this.valor = valor;
        this.matricula = matricula;
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getIdEndereco() {
        return idEndereco;
    }

    public void setIdEndereco(int idEndereco) {
        this.idEndereco = idEndereco;
    }

    public int getIdProprietario() {
        return idProprietario;
    }

    public void setIdProprietario(int idProprietario) {
        this.idProprietario = idProprietario;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getMetragem() {
        return metragem;
    }

    public void setMetragem(double metragem) {
        this.metragem = metragem;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public LocalDateTime getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(LocalDateTime dataVenda) {
        this.dataVenda = dataVenda;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataExclusao() {
        return dataExclusao;
    }

    public void setDataExclusao(LocalDateTime dataExclusao) {
        this.dataExclusao = dataExclusao;
    }

    @Override
    public String toString() {
        return "Imovel [id = " + id + "\n, idTipo = " + idTipo + "\n, idCaracteristica = "
                + idCaracteristica + "\n, idEndereco = " + idEndereco + "\n, idProprietario = " + idProprietario
                + "\n, descricao = " + descricao + "\n, metragem = " + metragem + "\n, valor = " + valor + "\n, matricula = "
                + matricula + "\n, dataVenda = " + dataVenda + "\n, dataCriacao = " + dataCriacao + "\n, dataExclusao = "
                + dataExclusao + "]\n";
    }

}
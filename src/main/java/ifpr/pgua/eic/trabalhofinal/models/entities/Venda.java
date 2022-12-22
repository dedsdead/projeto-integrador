package ifpr.pgua.eic.trabalhofinal.models.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Venda {
    private int id;
    private int idImovel;
    private int idComprador;
    private LocalDate dataVenda;
    private double valor;
    private String caminhoContrato;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataExclusao;

    public Venda(int id, 
                 int idImovel, 
                 int idComprador, 
                 LocalDate dataVenda, 
                 double valor, 
                 String caminhoContrato, 
                 LocalDateTime dataExclusao){
        this.id = id;
        this.idImovel = idImovel;
        this.idComprador = idComprador;
        this.dataVenda = dataVenda;
        this.valor = valor;
        this.caminhoContrato = caminhoContrato;
        this.dataExclusao = dataExclusao;

    }

    public Venda(int idImovel, 
                 int idComprador, 
                 LocalDate dataVenda, 
                 double valor, 
                 String caminhoContrato){
        this.idImovel = idImovel;
        this.idComprador = idComprador;
        this.dataVenda = dataVenda;
        this.valor = valor;
        this.caminhoContrato = caminhoContrato;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdImovel() {
        return idImovel;
    }

    public void setIdImovel(int idImovel) {
        this.idImovel = idImovel;
    }

    public int getIdComprador() {
        return idComprador;
    }

    public void setIdComprador(int idComprador) {
        this.idComprador = idComprador;
    }

    public LocalDate getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(LocalDate dataVenda) {
        this.dataVenda = dataVenda;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getCaminhoContrato() {
        return caminhoContrato;
    }

    public void setCaminhoContrato(String caminhoContrato) {
        this.caminhoContrato = caminhoContrato;
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
        return "Venda [id=" + id + ", idImovel=" + idImovel + ", idComprador=" + idComprador + ", dataVenda="
                + dataVenda + ", valor=" + valor + ", caminhoContrato=" + caminhoContrato + ", dataCriacao="
                + dataCriacao + ", dataExclusao=" + dataExclusao + "]";
    }

}

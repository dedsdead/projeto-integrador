package ifpr.pgua.eic.trabalhofinal.controllers.ViewModels;

import java.time.LocalDate;

import ifpr.pgua.eic.trabalhofinal.models.entities.Cliente;
import ifpr.pgua.eic.trabalhofinal.models.entities.Imovel;
import ifpr.pgua.eic.trabalhofinal.models.entities.Venda;
import ifpr.pgua.eic.trabalhofinal.models.repositories.ClientesRepository;
import ifpr.pgua.eic.trabalhofinal.models.repositories.ImoveisRepository;
import ifpr.pgua.eic.trabalhofinal.models.repositories.VendasRepository;
import ifpr.pgua.eic.trabalhofinal.models.results.Result;
import ifpr.pgua.eic.trabalhofinal.models.results.SuccessResult;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.SingleSelectionModel;

public class TelaVendasViewModel {
    private ObjectProperty<SingleSelectionModel<String>> spImovel = new SimpleObjectProperty<>();
    private ObjectProperty<SingleSelectionModel<String>> spCliente = new SimpleObjectProperty<>();

    private IntegerProperty ipId = new SimpleIntegerProperty();
    private StringProperty spDescricao = new SimpleStringProperty();
    private StringProperty spValor = new SimpleStringProperty();
    private StringProperty spContrato = new SimpleStringProperty();

    private StringProperty operacao = new SimpleStringProperty("Cadastrar");
    private BooleanProperty podeEditar = new SimpleBooleanProperty(true);
    private boolean atualizar = false;

    private ObservableList<Cliente> clientes = FXCollections.observableArrayList();
    private ObservableList<String> compradores = FXCollections.observableArrayList();

    private ObservableList<Imovel> imoveis = FXCollections.observableArrayList();
    private ObservableList<String> descricoes = FXCollections.observableArrayList();

    private ObjectProperty<VendaRow> selecionado = new SimpleObjectProperty<>();

    private ObjectProperty<LocalDate> dataProperty = new SimpleObjectProperty<>();

    private ObjectProperty<Result> alertProperty = new SimpleObjectProperty<>();

    private ObservableList<VendaRow> obsVendas = FXCollections.observableArrayList();

    private VendasRepository vendasRepository;
    private ImoveisRepository imoveisRepository;
    private ClientesRepository clientesRepository;

    public TelaVendasViewModel(VendasRepository vendasRepository, 
                               ImoveisRepository imoveisRepository, 
                               ClientesRepository clientesRepository){
        this.vendasRepository = vendasRepository;
        this.imoveisRepository = imoveisRepository;
        this.clientesRepository = clientesRepository;

        updateList();
        carregaImoveis();
        carregaClientes();

    }

    public ObjectProperty<SingleSelectionModel<String>> imovelProperty(){
        return spImovel;

    }

    public ObjectProperty<SingleSelectionModel<String>> clienteProperty(){
        return spCliente;

    }

    public ObservableList<VendaRow> getVendas(){
        return this.obsVendas;

    }

    public ObservableList<Imovel> getImoveis(){
        return this.imoveis;

    }

    public ObservableList<String> getDescricoes(){
        return this.descricoes;

    }

    public ObservableList<Cliente> getClientes(){
        return this.clientes;

    }
    
    public ObservableList<String> getCompradores(){
        return this.compradores;

    }

    public ObjectProperty<VendaRow> selecionadoProperty(){
        return selecionado;
        
    }

    public ObjectProperty<LocalDate> dataProperty(){
        return dataProperty;

    }

    public ObjectProperty<Result> alertProperty(){
        return alertProperty;
        
    }

    public StringProperty operacaoProperty(){
        return operacao;

    }

    public BooleanProperty podeEditarProperty(){
        return podeEditar;

    }

    public IntegerProperty idProperty(){
        return this.ipId;

    }

    public StringProperty descricaoProperty(){
        return this.spDescricao;

    }

    public StringProperty valorProperty(){
        return this.spValor;

    }

    public StringProperty contratoProperty(){
        return this.spContrato;

    }

    public void updateList(){
        obsVendas.clear();

        for (Venda venda : vendasRepository.getVendas()) {
            if(venda.getDataExclusao() == null){
                obsVendas.add(new VendaRow(venda));

            }

        }

    }

    public void carregaImoveis(){
        imoveis.clear();
        descricoes.clear();

        for (Imovel imovel : imoveisRepository.getImoveis()) {
            if(imovel.getDataExclusao() == null && imovel.getDataVenda() == null){
                imoveis.add(imovel);
                descricoes.add(imovel.getDescricao());

            }

        }

    }

    public void carregaClientes(){
        clientes.clear();
        compradores.clear();

        for (Cliente cliente : clientesRepository.getClientes()) {
            if(cliente.getDataExclusao() == null){
                clientes.add(cliente);
                compradores.add(cliente.getNome());

            }

        }

    }

    public void descartaProprietarioComprador(){
        if(spImovel.getValue().getSelectedItem() != null){
            Imovel i = imoveisRepository.buscaImovelProperty(spImovel);
            Cliente c = clientesRepository.buscaClienteImovel(i);
            
            clientes.clear();
            compradores.clear();

            for (Cliente cliente : clientesRepository.getClientes()) {
                if(cliente.getId() != c.getId()){
                    clientes.add(cliente);
                    compradores.add(cliente.getNome());
    
                }
    
            }

        }
        
    }

    public Result cadastrar(){
        int id = 0;
        int idImovel;
        int idComprador;
        LocalDate dataVenda;
        double valor = 0.0;
        String contrato;
        Result result;

        Imovel imovel = imoveisRepository.buscaImovelProperty(spImovel);

        if(imovel != null){
            idImovel = imovel.getId();

        } else {
            return Result.fail("Escolha um imóvel!");
            
        }

        Cliente cliente = clientesRepository.buscaCliente(spCliente);

        if(cliente != null){
            idComprador = cliente.getId();

        } else {
            return Result.fail("Escolha um comprador!");
            
        }

        if(dataProperty.getValue() == null){
            return Result.fail("Escolha uma data!");

        } else {
            dataVenda = dataProperty.getValue();

            if(dataVenda.isAfter(LocalDate.now())){
                return Result.fail("Data inválida, não pode criar uma venda no futuro!");

            }

        }

        if (spValor.getValue() == null || spValor.getValue() == ""){
            return Result.fail("Digite um valor!");

        }
        
        try {
            valor = Double.parseDouble(spValor.getValue());

        } catch (NumberFormatException e) {
            return Result.fail("Valor inválido!");
            
        }

        contrato = spContrato.getValue();

        if (atualizar) {
            id = ipId.getValue();
            Venda venda = new Venda(id, idImovel, idComprador, dataVenda, valor, contrato, null);
            result = vendasRepository.atualizarVenda(venda);
            
        } else {
            Venda venda = new Venda(idImovel, idComprador, dataVenda, valor, contrato);
            result = vendasRepository.adicionarVenda(venda);
            id = imovel.getId();
        }

        if(result instanceof SuccessResult){
            updateList();
            limpar();

        }

        return result;

    }

    public void atualizar() {
        operacao.setValue("Atualizar");
        podeEditar.setValue(false);
        atualizar = true;
        
        Venda venda = selecionado.get().getVenda();
        
        Imovel imovel = imoveisRepository.buscaImovelVenda(venda);
        spImovel.get().select(imovel.getDescricao());
        
        Cliente cliente = clientesRepository.buscaClienteVenda(venda);
        spCliente.get().select(cliente.getNome());

        ipId.setValue(venda.getId());
        dataProperty.setValue(venda.getDataVenda());
        spValor.setValue(String.valueOf(venda.getValor()));
        spContrato.setValue(venda.getCaminhoContrato());

    }

    public Result excluir(){
        Venda venda = selecionado.get().getVenda();
        Result result;

        result = vendasRepository.excluirVenda(venda.getId());

        if(result instanceof SuccessResult){
            updateList();
            limpar();

        }

        return result;

    }

    public void limpar() {
        ipId.setValue(0);

        spDescricao.setValue("");
        dataProperty.setValue(null);
        spValor.setValue("");
        spContrato.setValue("");
        
        podeEditar.setValue(true);
        atualizar = false;
        operacao.setValue("Cadastrar");

    }

}

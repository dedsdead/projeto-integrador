package ifpr.pgua.eic.trabalhofinal.controllers.ViewModels;

import java.util.Optional;

import ifpr.pgua.eic.trabalhofinal.models.entities.Caracteristica;
import ifpr.pgua.eic.trabalhofinal.models.entities.Cliente;
import ifpr.pgua.eic.trabalhofinal.models.entities.Endereco;
import ifpr.pgua.eic.trabalhofinal.models.entities.Tipo;
import ifpr.pgua.eic.trabalhofinal.models.repositories.CaracteristicasRepository;
import ifpr.pgua.eic.trabalhofinal.models.repositories.ClientesRepository;
import ifpr.pgua.eic.trabalhofinal.models.repositories.EnderecosRepository;
import ifpr.pgua.eic.trabalhofinal.models.repositories.TiposRepository;
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

public class TelaClientesViewModel {
    private ObjectProperty<SingleSelectionModel<String>> spTipo = new SimpleObjectProperty<>();
    private ObjectProperty<SingleSelectionModel<String>> spCaracteristica = new SimpleObjectProperty<>();
    private StringProperty spNome = new SimpleStringProperty();
    private StringProperty spTelefone = new SimpleStringProperty();
    private StringProperty spCpf = new SimpleStringProperty();
    private StringProperty spEmail = new SimpleStringProperty();

    private IntegerProperty spEndereco = new SimpleIntegerProperty();
    private StringProperty spCep = new SimpleStringProperty();
    private StringProperty spEstado = new SimpleStringProperty();
    private StringProperty spCidade = new SimpleStringProperty();
    private StringProperty spLogradouro = new SimpleStringProperty();
    private StringProperty spNumero = new SimpleStringProperty();
    private StringProperty spComplemento = new SimpleStringProperty();

    private StringProperty operacao = new SimpleStringProperty("Cadastrar");
    private BooleanProperty podeEditar = new SimpleBooleanProperty(true);
    private BooleanProperty pegarEndereco = new SimpleBooleanProperty(false);
    private boolean atualizar = false;

    private ObservableList<ClienteRow> obsClientes = FXCollections.observableArrayList();

    private ObservableList<Endereco> enderecos = FXCollections.observableArrayList();

    private ObservableList<Tipo> tipos = FXCollections.observableArrayList();
    private ObservableList<String> nomes = FXCollections.observableArrayList();

    private ObservableList<Caracteristica> caracteristicas = FXCollections.observableArrayList();
    private ObservableList<String> descricoes = FXCollections.observableArrayList();

    private ObjectProperty<ClienteRow> selecionado = new SimpleObjectProperty<>();

    private ObjectProperty<Result> alertProperty = new SimpleObjectProperty<>();

    private ClientesRepository clientesRepository;
    private EnderecosRepository enderecosRepository;
    private TiposRepository tiposRepository;
    private CaracteristicasRepository caracteristicasRepository;

    public TelaClientesViewModel(ClientesRepository clientesRepository, EnderecosRepository enderecosRepository, TiposRepository tiposRepository, CaracteristicasRepository caracteristicasRepository){
        this.clientesRepository = clientesRepository;
        this.enderecosRepository = enderecosRepository;
        this.tiposRepository = tiposRepository;
        this.caracteristicasRepository = caracteristicasRepository;

        updateList();
        carregaEnderecos();
        carregaTipos();
        carregaCaracteristicas();
        
    }

    public void carregaEnderecos(){
        enderecos.clear();
        
        for(Endereco e : enderecosRepository.getEnderecos()){
            enderecos.add(e);

        }
    }

    public void carregaTipos(){
        tipos.clear();
        nomes.clear();
        
        for(Tipo t : tiposRepository.getTipos()){
            tipos.add(t);
            nomes.add(t.getNome());

        }
    }

    public void carregaCaracteristicas(){
        caracteristicas.clear();
        descricoes.clear();
        
        for(Caracteristica c : caracteristicasRepository.getCaracteristicas()){
            caracteristicas.add(c);
            descricoes.add(c.getQuantidade()+" "+c.getDescricao());

        }
    }

    public void updateList(){
        obsClientes.clear();

        for(Cliente c : clientesRepository.getClientes()){
            if(c.getDataExclusao() == null)
                obsClientes.add(new ClienteRow(c));

        }

    }

    public ObservableList<ClienteRow> getClientes(){
        return this.obsClientes;

    }

    public ObservableList<Tipo> getTipos(){
        return this.tipos;

    }

    public ObservableList<String> getNomes(){
        return this.nomes;

    }

    public ObservableList<Caracteristica> getCaracteristicas(){
        return this.caracteristicas;

    }

    public ObservableList<String> getDescricoes(){
        return this.descricoes;

    }

    public ObjectProperty<ClienteRow> selecionadoProperty(){
        return selecionado;
        
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

    public BooleanProperty pegarEnderecoProperty(){
        return pegarEndereco;

    }

    public ObjectProperty<SingleSelectionModel<String>> tipoProperty(){
        return spTipo;

    }

    public ObjectProperty<SingleSelectionModel<String>> caracteristicaProperty(){
        return spCaracteristica;

    }

    public StringProperty nomeProperty(){
        return this.spNome;

    }

    public StringProperty telefoneProperty(){
        return this.spTelefone;

    }

    public StringProperty cpfProperty(){
        return this.spCpf;

    }

    public StringProperty emailProperty(){
        return this.spEmail;

    }

    public IntegerProperty enderecoProperty(){
        return this.spEndereco;

    }

    public StringProperty cepProperty() {
        return this.spCep;
    }

    public StringProperty estadoProperty() {
        return this.spEstado;
    }

    public StringProperty cidadeProperty() {
        return this.spCidade;
    }

    public StringProperty logradouroProperty() {
        return this.spLogradouro;
    }

    public StringProperty numeroProperty() {
        return this.spNumero;
    }

    public StringProperty complementoProperty() {
        return this.spComplemento;
    }

    public Result buscaCep(){
        if(spCep.getValue() == ""){
            return Result.fail("Insira um CEP!");

        }

        Endereco e = enderecosRepository.getEnderecoFromAPI(spCep.getValue());
        
        if(e == null || e.getCep() == null){
            return Result.fail("CEP não econtrado!");
        } else {
            spEstado.setValue(e.getEstado());
            spCidade.setValue(e.getCidade());
            spLogradouro.setValue(e.getLogradouro());
            spNumero.setValue(String.valueOf(e.getNumero()));
            spComplemento.setValue(e.getComplemento());

            return Result.success("CEP encontrado com sucesso!");

        }
    }

    public Tipo buscaTipo(){
        Optional<Tipo> busca = tipos.stream().filter((cli)->cli.getNome().equals(spTipo.getValue().getSelectedItem())).findFirst();
        if(busca.isPresent()){
            Tipo t = busca.get();
            return t;

        } else {
            return null;

        }

    }

    public Tipo buscaTipoId(Cliente cliente){
        Optional<Tipo> busca = tipos.stream().filter((cli)->cli.getId() == cliente.getIdTipo()).findFirst();
        
        if(busca.isPresent()){
            Tipo t = busca.get();
            return t;

        } else {
            return null;

        }

    }

    public Caracteristica buscaCaracteristica(){
        Optional<Caracteristica> busca = caracteristicas.stream().filter((cli)->(cli.getQuantidade()+" "+cli.getDescricao()).equals(spCaracteristica.getValue().getSelectedItem())).findFirst();
        if(busca.isPresent()){
            Caracteristica c = busca.get();
            return c;

        } else {
            return null;

        }

    }

    public Caracteristica buscaCaracteristicaId(Cliente cliente){
        Optional<Caracteristica> busca = caracteristicas.stream().filter((cli)->cli.getId() == cliente.getIdCaracteristica()).findFirst();
        
        if(busca.isPresent()){
            Caracteristica c = busca.get();
            return c;

        } else {
            return null;

        }

    }

    public Result cadastrar(int temTipo, int temCaracteristica) {
        int idEndereco = spEndereco.getValue();
        int idTipo;
        int idCaracteristica;

        if (temTipo == 1){
            Tipo t = buscaTipo();

            if(t != null){
                idTipo = t.getId();

            } else {
                idTipo = 0;

            }

        } else {
            idTipo = 0;

        }

        if (temCaracteristica == 1){
            Caracteristica c = buscaCaracteristica();

            if(c != null){
                idCaracteristica = c.getId();

            } else {
                idCaracteristica = 0;

            }

        } else {
            idCaracteristica = 0;

        }
       
        String nome = spNome.getValue();
        String cpf = spCpf.getValue();
        String telefone = spTelefone.getValue();
        String email = spEmail.getValue();

        Result result;

        if (atualizar) {
            result = clientesRepository.atualizarCliente(idEndereco, idTipo, idCaracteristica, nome, telefone, cpf, email);
        } else {
            result = clientesRepository.adicionarCliente(idEndereco, idTipo, idCaracteristica, nome, telefone, cpf, email);
        }

        if(result instanceof SuccessResult){
            updateList();
            limpar();

        }

        return result;
        
    }

    public Result cadastraEndereco(){
        Result result;

        String cep = spCep.getValue();
        String estado = spEstado.getValue();
        String cidade = spCidade.getValue();
        String logradouro = spLogradouro.getValue();
        int numero = 0;

        try{
            numero = Integer.parseInt(spNumero.getValue());

        }catch(NumberFormatException e){
            result = Result.fail("Numero inválido!");

        }

        String complemento = spComplemento.getValue();

        if(cep == "" || estado == "" || cidade == "" || logradouro == "" || numero == 0)
            result = Result.fail("Preencha os campos!");

        else{
            Endereco endereco = new Endereco(cep, estado, cidade, logradouro, numero, complemento);
            
            result = enderecosRepository.adicionarEndereco(endereco);

            if(result instanceof SuccessResult){
                spCep.setValue("");
                spEstado.setValue("");
                spCidade.setValue("");
                spLogradouro.setValue("");
                spNumero.setValue("");
                spComplemento.setValue("");

                spEndereco.setValue(endereco.getId());
                carregaEnderecos();
    
            }

        }

        return result;
        
    }

    public void atualizar() {
        operacao.setValue("Atualizar");
        podeEditar.setValue(false);
        atualizar = true;
        
        Cliente cliente = selecionado.get().getCliente();
        
        if(cliente.getIdEndereco() != 0) spEndereco.setValue(cliente.getIdEndereco());
        
        Tipo t = buscaTipoId(cliente);
        if(cliente.getIdTipo() != 0){
            spTipo.get().select(t.getNome());

        } else {
            spTipo.get().clearSelection();

        }
        
        Caracteristica c = buscaCaracteristicaId(cliente);

        if(cliente.getIdCaracteristica() != 0){
            spCaracteristica.get().select(c.getQuantidade()+" "+c.getDescricao());

        } else {
            spCaracteristica.get().clearSelection();

        }
        
        
        spNome.setValue(cliente.getNome());
        spTelefone.setValue(cliente.getTelefone());
        spCpf.setValue(cliente.getCpf());
        spEmail.setValue(cliente.getEmail());

    }

    public Result excluir(){
        Cliente cliente = selecionado.get().getCliente();
        Result result;

        result = clientesRepository.desativarCliente(cliente.getEmail());

        if(result instanceof SuccessResult){
            updateList();
            limpar();

        }

        return result;

    }

    public void limpar() {
        spEndereco.setValue(0);
        spCep.setValue("");
        spEstado.setValue("");
        spCidade.setValue("");
        spLogradouro.setValue("");
        spNumero.setValue("");
        spComplemento.setValue("");
        
        spNome.setValue("");
        spTelefone.setValue("");
        spCpf.setValue("");
        spEmail.setValue("");

        podeEditar.setValue(true);
        pegarEndereco.setValue(false);
        atualizar = false;
        operacao.setValue("Cadastrar");

    }

}

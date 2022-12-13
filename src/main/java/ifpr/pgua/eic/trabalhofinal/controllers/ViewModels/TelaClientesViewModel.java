package ifpr.pgua.eic.trabalhofinal.controllers.ViewModels;

import ifpr.pgua.eic.trabalhofinal.models.entities.Caracteristica;
import ifpr.pgua.eic.trabalhofinal.models.entities.Cliente;
import ifpr.pgua.eic.trabalhofinal.models.entities.Tipo;
import ifpr.pgua.eic.trabalhofinal.models.repositories.CaracteristicasRepository;
import ifpr.pgua.eic.trabalhofinal.models.repositories.ClientesRepository;
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

    private StringProperty operacao = new SimpleStringProperty("Cadastrar");
    private BooleanProperty podeEditar = new SimpleBooleanProperty(true);
    
    private boolean atualizar = false;

    private ObservableList<ClienteRow> obsClientes = FXCollections.observableArrayList();

    private ObservableList<Tipo> tipos = FXCollections.observableArrayList();
    private ObservableList<String> nomes = FXCollections.observableArrayList();

    private ObservableList<Caracteristica> caracteristicas = FXCollections.observableArrayList();
    private ObservableList<String> descricoes = FXCollections.observableArrayList();

    private ObjectProperty<ClienteRow> selecionado = new SimpleObjectProperty<>();

    private ObjectProperty<Result> alertProperty = new SimpleObjectProperty<>();

    private ClientesRepository clientesRepository;
    private TiposRepository tiposRepository;
    private CaracteristicasRepository caracteristicasRepository;

    public TelaClientesViewModel(ClientesRepository clientesRepository, TiposRepository tiposRepository, CaracteristicasRepository caracteristicasRepository){
        this.clientesRepository = clientesRepository;
        this.tiposRepository = tiposRepository;
        this.caracteristicasRepository = caracteristicasRepository;

        updateList();
        carregaTipos();
        carregaCaracteristicas();
        
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

    public Result cadastrar(int temTipo, int temCaracteristica) {
        int idEndereco = spEndereco.getValue();
        int idTipo;
        int idCaracteristica;

        if (temTipo == 1){
            Tipo t = tiposRepository.buscaTipo(spTipo);

            if(t != null){
                idTipo = t.getId();

            } else {
                idTipo = 0;

            }

        } else {
            idTipo = 0;

        }

        if (temCaracteristica == 1){
            Caracteristica c = caracteristicasRepository.buscaCaracteristica(spCaracteristica);

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

        if(nome == null || nome == ""){
            return Result.fail("Preencha o nome!");

        } else if (cpf == null || cpf == ""){
            return Result.fail("Preencha o cpf!");

        } else if (telefone == null || telefone == ""){
            return Result.fail("Preencha o numero de telefone!");

        } else if(email == null || email == ""){
            return Result.fail("Preencha o e-mail!");

        }

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

    public void atualizar() {
        operacao.setValue("Atualizar");
        podeEditar.setValue(false);
        atualizar = true;
        
        Cliente cliente = selecionado.get().getCliente();
        
        if(cliente.getIdEndereco() != 0) spEndereco.setValue(cliente.getIdEndereco());
        
        Tipo t = tiposRepository.buscaTipoId(cliente);
        if(cliente.getIdTipo() != 0){
            spTipo.get().select(t.getNome());

        } else {
            spTipo.get().clearSelection();

        }
        
        Caracteristica c = caracteristicasRepository.buscaCaracteristicaId(cliente);

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

        spNome.setValue("");
        spTelefone.setValue("");
        spCpf.setValue("");
        spEmail.setValue("");

        podeEditar.setValue(true);
        atualizar = false;
        operacao.setValue("Cadastrar");

    }

}

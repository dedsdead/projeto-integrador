package ifpr.pgua.eic.trabalhofinal.controllers.ViewModels;

import java.util.Optional;

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
    private IntegerProperty spEndereco = new SimpleIntegerProperty();
    private ObjectProperty<SingleSelectionModel<String>> spTipo = new SimpleObjectProperty<>();
    private ObjectProperty<SingleSelectionModel<String>> spCaracteristica = new SimpleObjectProperty<>();
    private StringProperty spNome = new SimpleStringProperty();
    private StringProperty spTelefone = new SimpleStringProperty();
    private StringProperty spCpf = new SimpleStringProperty();
    private StringProperty spEmail = new SimpleStringProperty();

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

    }

    public void updateList(){
        obsClientes.clear();
        tipos.clear();
        caracteristicas.clear();
        nomes.clear();
        descricoes.clear();

        for(Cliente c : clientesRepository.getClientes()){
            obsClientes.add(new ClienteRow(c));

        }

        for(Tipo t : tiposRepository.getTipos()){
            tipos.add(t);
            nomes.add(t.getNome());
        }

        for(Caracteristica c : caracteristicasRepository.getCaracteristicas()){
            caracteristicas.add(c);
            descricoes.add(c.getDescricao());

        }

    }

    public ObservableList<ClienteRow> getClientes(){
        return this.obsClientes;

    }

    public ObservableList<String> getTipos(){
        return nomes;

    }

    public ObservableList<String> getCaracteristicas(){
        return descricoes;
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

    public IntegerProperty enderecoProperty(){
        return spEndereco;

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

    public Result cadastrar() {
        int idEndereco = spEndereco.getValue();

        String nomeTipo = spTipo.getValue().getSelectedItem();
        //VER UMA SOLUÇÃO MELHOR
        Optional<Tipo> escolha = tipos.stream().filter((cli)->cli.getNome().equals(nomeTipo)).findFirst();
        Tipo tipo = escolha.get();
        int idTipo = tipo.getId();

        String descCaracteristica = spCaracteristica.getValue().getSelectedItem();
        //VER UMA SOLUÇÃO MELHOR2
        Optional<Caracteristica> escolha2 = caracteristicas.stream().filter((cli)->cli.getDescricao().equals(descCaracteristica)).findFirst();
        Caracteristica caracteristica = escolha2.get();
        int idCaracteristica = caracteristica.getId();

        String nome = spNome.getValue();
        String cpf = spCpf.getValue();
        String telefone = spTelefone.getValue();
        String email = spEmail.getValue();

        Result result;

        if (atualizar) {
            result = clientesRepository.atualizarCliente(nome, telefone, cpf, email);
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
        spNome.setValue(cliente.getNome());
        spTelefone.setValue(cliente.getTelefone());
        spCpf.setValue(cliente.getCpf());

    }

    public void limpar() {
        spEndereco.setValue(0);
        spTipo.getValue().clearSelection();
        spCaracteristica.getValue().clearSelection();
        spNome.setValue("");
        spTelefone.setValue("");
        spCpf.setValue("");
        spEmail.setValue("");
        podeEditar.setValue(true);
        atualizar = false;
        operacao.setValue("Cadastrar");

    }

}

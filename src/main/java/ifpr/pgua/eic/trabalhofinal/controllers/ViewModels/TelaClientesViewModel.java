package ifpr.pgua.eic.trabalhofinal.controllers.ViewModels;

import ifpr.pgua.eic.trabalhofinal.models.entities.Cliente;
import ifpr.pgua.eic.trabalhofinal.models.repositories.ClientesRepository;
import ifpr.pgua.eic.trabalhofinal.models.results.Result;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TelaClientesViewModel {
    private StringProperty spNome = new SimpleStringProperty();
    private StringProperty spTelefone = new SimpleStringProperty();
    private StringProperty spCpf = new SimpleStringProperty();
    private StringProperty spEmail = new SimpleStringProperty();

    private StringProperty operacao = new SimpleStringProperty("Cadastrar");
    private BooleanProperty podeEditar = new SimpleBooleanProperty(true);
    private boolean atualizar = false;

    private ObservableList<ClienteRow> obsClientes = FXCollections.observableArrayList();

    private ObjectProperty<ClienteRow> selecionado = new SimpleObjectProperty<>();

    private ObjectProperty<Result> alertProperty = new SimpleObjectProperty<>();

    private ClientesRepository repository;

    public TelaClientesViewModel(ClientesRepository repository){
        this.repository = repository;

        updateList();

    }

    private void updateList(){
        obsClientes.clear();

        for(Cliente c : repository.getClientes()){
            obsClientes.add(new ClienteRow(c));

        }
    }

    public ObservableList<ClienteRow> getClientes(){
        return this.obsClientes;

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

    public void cadastrar() {
        String nome = spNome.getValue();
        String cpf = spCpf.getValue();
        String telefone = spTelefone.getValue();
        String email = spEmail.getValue();

        if (atualizar) {
            repository.atualizarCliente(nome, telefone, cpf, email);
        } else {
            repository.adicionarCliente(1, -1, -1, nome, telefone, cpf, email);
        }

        updateList();

        limpar();
    }

    public void atualizar() {
        operacao.setValue("Atualizar");
        podeEditar.setValue(false);
        atualizar = true;
        Cliente cliente = selecionado.get().getCliente();
        spNome.setValue(cliente.getNome());
        spTelefone.setValue(cliente.getTelefone());
        spCpf.setValue(cliente.getCpf());
        spEmail.setValue(cliente.getEmail());

    }

    public void limpar() {
        spNome.setValue("");
        spTelefone.setValue("");
        spCpf.setValue("");
        spEmail.setValue("");
        podeEditar.setValue(true);
        atualizar = false;
        operacao.setValue("Cadastrar");

    }

}

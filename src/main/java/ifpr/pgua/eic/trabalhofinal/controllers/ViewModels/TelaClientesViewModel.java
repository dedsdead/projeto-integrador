package ifpr.pgua.eic.trabalhofinal.controllers.ViewModels;

import java.util.List;

import ifpr.pgua.eic.trabalhofinal.models.entities.Caracteristica;
import ifpr.pgua.eic.trabalhofinal.models.entities.Cliente;
import ifpr.pgua.eic.trabalhofinal.models.entities.Endereco;
import ifpr.pgua.eic.trabalhofinal.models.entities.Imovel;
import ifpr.pgua.eic.trabalhofinal.models.entities.Tipo;
import ifpr.pgua.eic.trabalhofinal.models.repositories.CaracteristicasRepository;
import ifpr.pgua.eic.trabalhofinal.models.repositories.ClientesRepository;
import ifpr.pgua.eic.trabalhofinal.models.repositories.EmailsRepository;
import ifpr.pgua.eic.trabalhofinal.models.repositories.EnderecosRepository;
import ifpr.pgua.eic.trabalhofinal.models.repositories.ImoveisRepository;
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
    private ImoveisRepository imoveisRepository;
    private EnderecosRepository enderecosRepository;
    private EmailsRepository emailsRepository;

    public TelaClientesViewModel(ClientesRepository clientesRepository, TiposRepository tiposRepository, CaracteristicasRepository caracteristicasRepository, ImoveisRepository imoveisRepository, EnderecosRepository enderecosRepository, EmailsRepository emailsRepository){
        this.clientesRepository = clientesRepository;
        this.tiposRepository = tiposRepository;
        this.caracteristicasRepository = caracteristicasRepository;
        this.imoveisRepository = imoveisRepository;
        this.enderecosRepository = enderecosRepository;
        this.emailsRepository = emailsRepository;

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
        if(idEndereco == 0) return Result.fail("Preencha um endereço!");
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

        }

        return result;
        
    }

    public List<Imovel> buscaImoveis(int temTipo, int temCaracteristica) {
        if(temTipo == 1 && temCaracteristica == 1){
            Tipo t = tiposRepository.buscaTipo(spTipo);
            Caracteristica c = caracteristicasRepository.buscaCaracteristica(spCaracteristica);

            return imoveisRepository.buscaImovelTipoCaracteristica(t.getId(), c.getId());

        } else if(temCaracteristica == 0) {
            Tipo t = tiposRepository.buscaTipo(spTipo);

            return imoveisRepository.buscaImovelTipo(t.getId());

        } else if(temTipo == 0){
            Caracteristica c = caracteristicasRepository.buscaCaracteristica(spCaracteristica);

            return imoveisRepository.buscaImovelCaracteristica(c.getId());

        } else {
            return null;

        }

    }

    public Result mandarEmails(int temTipo, int temCaracteristica){
        List<Imovel> imoveis = buscaImoveis(temTipo, temCaracteristica);

        if(imoveis.size() > 0){
            String assunto = "Novo(s) Imóvel(is) para o cliente";
            String conteudo = "Cliente: \n "+nomeProperty().getValue();
            conteudo = "Lista de imóveis: \n";

            for (Imovel im : imoveis) {
                Tipo t = tiposRepository.buscaTipoId(im);

                if(t != null){
                    conteudo += " "+t.getNome();

                }

                Caracteristica c = caracteristicasRepository.buscaCaracteristicaId(im);

                if(c != null){
                    conteudo += "\n Conta com: "+c.getQuantidade()+" "+c.getDescricao();
                    
                }

                Endereco e = enderecosRepository.buscaEnderecoId(im.getIdEndereco());

                if(e != null){
                    conteudo += "situado no bairro: "+e.getBairro();

                }
                
                conteudo += "\n Descrição: "+im.getDescricao();

                String metragem = String.valueOf(im.getMetragem());
                metragem = metragem.replace(".0", ",00");
                metragem = metragem.replace(".", ",");
                conteudo += "\n Metragem: "+metragem+" m²";

                String valor = String.valueOf(im.getValor());
                valor = valor.replace(".0", ",00");
                valor = valor.replace(".", ",");
                conteudo += "\n Valor: "+valor+" reais";

                conteudo += "\n-------------------------------\n";
                
            }

            return emailsRepository.send(assunto, conteudo);

        }

        return Result.fail("Nenhum imóvel compatível");

    }

    public void atualizar() {
        operacao.setValue("Atualizar");
        podeEditar.setValue(false);
        atualizar = true;
        
        Cliente cliente = selecionado.get().getCliente();
        
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

        spEndereco.setValue(cliente.getIdEndereco());
        
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

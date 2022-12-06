package ifpr.pgua.eic.trabalhofinal.controllers.ViewModels;

import java.util.Optional;

import ifpr.pgua.eic.trabalhofinal.models.entities.Caracteristica;
import ifpr.pgua.eic.trabalhofinal.models.entities.Cliente;
import ifpr.pgua.eic.trabalhofinal.models.entities.Endereco;
import ifpr.pgua.eic.trabalhofinal.models.entities.Foto;
import ifpr.pgua.eic.trabalhofinal.models.entities.Imovel;
import ifpr.pgua.eic.trabalhofinal.models.entities.Tipo;
import ifpr.pgua.eic.trabalhofinal.models.repositories.CaracteristicasRepository;
import ifpr.pgua.eic.trabalhofinal.models.repositories.ClientesRepository;
import ifpr.pgua.eic.trabalhofinal.models.repositories.EnderecosRepository;
import ifpr.pgua.eic.trabalhofinal.models.repositories.FotosRepository;
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

public class TelaImoveisViewModel {
    private ObjectProperty<SingleSelectionModel<String>> spTipo = new SimpleObjectProperty<>();
    private ObjectProperty<SingleSelectionModel<String>> spCaracteristica = new SimpleObjectProperty<>();
    private ObjectProperty<SingleSelectionModel<String>> spCliente = new SimpleObjectProperty<>();

    private StringProperty spDescricao = new SimpleStringProperty();
    private StringProperty spMetragem = new SimpleStringProperty();
    private StringProperty spValor = new SimpleStringProperty();
    private StringProperty spMatricula = new SimpleStringProperty();

    private StringProperty spCaminho = new SimpleStringProperty();

    private IntegerProperty spFoto = new SimpleIntegerProperty();
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

    private ObservableList<ImovelRow> obsImoveis = FXCollections.observableArrayList();

    private ObservableList<Foto> fotos = FXCollections.observableArrayList();

    private ObservableList<Tipo> tipos = FXCollections.observableArrayList();
    private ObservableList<String> nomes = FXCollections.observableArrayList();

    private ObservableList<Caracteristica> caracteristicas = FXCollections.observableArrayList();
    private ObservableList<String> descricoes = FXCollections.observableArrayList();

    private ObservableList<Cliente> clientes = FXCollections.observableArrayList();

    private ObservableList<Endereco> enderecos = FXCollections.observableArrayList();

    private ObjectProperty<ImovelRow> selecionado = new SimpleObjectProperty<>();

    private ObjectProperty<Result> alertProperty = new SimpleObjectProperty<>();

    private ImoveisRepository imoveisRepository;
    private FotosRepository fotosRepository;
    private TiposRepository tiposRepository;
    private CaracteristicasRepository caracteristicasRepository;
    private EnderecosRepository enderecosRepository;
    private ClientesRepository clientesRepository;

    public TelaImoveisViewModel(ImoveisRepository imoveisRepository,
                                FotosRepository fotosRepository,
                                TiposRepository tiposRepository,
                                CaracteristicasRepository caracteristicasRepository,
                                EnderecosRepository enderecosRepository,
                                ClientesRepository clientesRepository) {
        this.imoveisRepository = imoveisRepository;
        this.fotosRepository = fotosRepository;
        this.tiposRepository = tiposRepository;
        this.caracteristicasRepository = caracteristicasRepository;
        this.enderecosRepository = enderecosRepository;
        this.clientesRepository = clientesRepository;

        updateList();
        carregaFotos();
        carregaTipos();
        carregaCaracteristicas();
        carregaEnderecos();
        carregaClientes();
    
    }

    public void updateList(){
        obsImoveis.clear();

        for(Imovel i : imoveisRepository.getImoveis()){
            if(i.getDataExclusao() == null)
            obsImoveis.add(new ImovelRow(i));

        }

    }

    public void carregaFotos(){
        fotos.clear();
        
        for(Foto f : fotosRepository.getFotos()){
            fotos.add(f);

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

    public void carregaEnderecos(){
        enderecos.clear();
        
        for(Endereco e : enderecosRepository.getEnderecos()){
            enderecos.add(e);

        }
        
    }

    public void carregaClientes(){
        clientes.clear();
        
        for(Cliente c : clientesRepository.getClientes()){
            clientes.add(c);

        }
        
    }

    public ObjectProperty<SingleSelectionModel<String>> tipoProperty(){
        return spTipo;

    }

    public ObjectProperty<SingleSelectionModel<String>> caracteristicaProperty(){
        return spCaracteristica;

    }

    public ObjectProperty<SingleSelectionModel<String>> clienteProperty(){
        return spCliente;

    }

    public ObservableList<ImovelRow> getImoveis(){
        return this.obsImoveis;

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

    public ObservableList<Cliente> getClientes(){
        return this.clientes;

    }

    public ObjectProperty<ImovelRow> selecionadoProperty(){
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

    public StringProperty caminhoProperty(){
        return this.spCaminho;
        
    }

    public StringProperty descricaoProperty(){
        return this.spDescricao;

    }

    public StringProperty metragemProperty(){
        return this.spMetragem;

    }

    public StringProperty valorProperty(){
        return this.spValor;

    }

    public StringProperty matriculaProperty(){
        return this.spMatricula;

    }

    public IntegerProperty fotoProperty(){
        return this.spFoto;

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

    public Result cadastraFoto(){
        Result result;

        String caminho = spCaminho.getValue();

        if(caminho == "")
            result = Result.fail("Selecione uma foto!");

        else{
            Foto foto = new Foto(caminho);

            result = fotosRepository.adicionarFoto(foto);

            if(result instanceof SuccessResult){
                limpar();
                spFoto.setValue(foto.getId());
                carregaEnderecos();
    
            }

        }

        return result;

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

    public Tipo buscaTipoId(Imovel imovel){
        Optional<Tipo> busca = tipos.stream().filter((cli)->cli.getId() == imovel.getIdTipo()).findFirst();
        
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

    public Caracteristica buscaCaracteristicaId(Imovel imovel){
        Optional<Caracteristica> busca = caracteristicas.stream().filter((cli)->cli.getId() == imovel.getIdCaracteristica()).findFirst();
        
        if(busca.isPresent()){
            Caracteristica c = busca.get();
            return c;

        } else {
            return null;

        }

    }

    public Result buscaCep(){
        if(spCep.getValue() == ""){
            return Result.fail("Insira um CEP!");

        }

        Endereco e = enderecosRepository.getEnderecoFromAPI(spCep.getValue());
        
        if(e == null){
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

    public Cliente buscaCliente(){
        Optional<Cliente> busca = clientes.stream().filter((cli)->cli.getNome().equals(spCliente.getValue().getSelectedItem())).findFirst();
        if(busca.isPresent()){
            Cliente c = busca.get();
            return c;

        } else {
            return null;

        }

    }

    public Cliente buscaClienteId(Imovel imovel){
        Optional<Cliente> busca = clientes.stream().filter((cli)->cli.getId() == imovel.getIdProprietario()).findFirst();
        
        if(busca.isPresent()){
            Cliente c = busca.get();
            return c;

        } else {
            return null;

        }

    }

    public Result cadastrar(int temCliente, int temTipo, int temCaracteristica) {
        int idTipo;
        int idCaracteristica;
        int idCliente;

        if(temCliente == 1){
            Cliente c = buscaCliente();

            if(c != null)
                idCliente = c.getId();
            else
                return Result.fail("Proprietário não encontrado!");

        } else {
            return Result.fail("Esccolha um proprietário!");

        }


        if (temTipo == 1){
            Tipo t = buscaTipo();

            if(t != null){
                idTipo = t.getId();

            } else {
                return Result.fail("Erro ao escolher o tipo!");

            }

        } else {
            return Result.fail("Escolha um tipo!");

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

        int idEndereco = spEndereco.getValue();
       
        String descricao = spDescricao.getValue();
        double metragem = 0.0;
        double valor = 0.0;

        try {
            metragem = Double.parseDouble(spMetragem.getValue());

        } catch (NumberFormatException e) {
            return Result.fail("Metragem inválida!");
            
        }

        try {
            valor = Double.parseDouble(spValor.getValue());

        } catch (NumberFormatException e) {
            return Result.fail("Valor inválido!");
            
        }

        String matricula = spMatricula.getValue();

        Result result;

        if (atualizar) {
            result = imoveisRepository.atualizarImovel(1, idTipo, idCaracteristica, idCliente, descricao, metragem, valor, matricula);
        } else {
            result = imoveisRepository.adicionarImovel(1, idTipo, idCaracteristica, idEndereco, idCliente, descricao, metragem, valor, matricula);
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
        
        Imovel imovel = selecionado.get().getImovel();
        
        Tipo t = buscaTipoId(imovel);
        if(imovel.getIdTipo() != 0){
            spTipo.get().select(t.getNome());

        } else {
            spTipo.get().clearSelection();

        }
        
        Caracteristica c = buscaCaracteristicaId(imovel);

        if(imovel.getIdCaracteristica() != 0){
            spCaracteristica.get().select(c.getQuantidade()+" "+c.getDescricao());

        } else {
            spCaracteristica.get().clearSelection();

        }

        if(imovel.getIdEndereco() != 0) spEndereco.setValue(imovel.getIdEndereco());
        
        Cliente cli = buscaClienteId(imovel);
        spCliente.get().select(cli.getNome());

        spDescricao.setValue(imovel.getDescricao());
        spMetragem.setValue(String.valueOf(imovel.getMetragem()));
        spValor.setValue(String.valueOf(imovel.getValor()));
        spMatricula.setValue(imovel.getMatricula());

    }

    public Result excluir(){
        Imovel imovel = selecionado.get().getImovel();
        Result result;

        result = imoveisRepository.excluirImovel(imovel.getDescricao());

        if(result instanceof SuccessResult){
            updateList();
            limpar();

        }

        return result;

    }

    public void limpar() {
        spFoto.setValue(0);
        spCaminho.setValue("");
        spEndereco.setValue(0);
        spDescricao.setValue("");
        spMetragem.setValue("");
        spValor.setValue("");
        spMatricula.setValue("");
        spCep.setValue("");
        spEstado.setValue("");
        spCidade.setValue("");
        spLogradouro.setValue("");
        spNumero.setValue("");
        spComplemento.setValue("");

        pegarEndereco.setValue(false);
        podeEditar.setValue(true);
        atualizar = false;
        operacao.setValue("Cadastrar");

    }

}

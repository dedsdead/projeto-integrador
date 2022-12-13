package ifpr.pgua.eic.trabalhofinal.controllers.ViewModels;

import ifpr.pgua.eic.trabalhofinal.models.entities.Caracteristica;
import ifpr.pgua.eic.trabalhofinal.models.entities.Cliente;
import ifpr.pgua.eic.trabalhofinal.models.entities.Foto;
import ifpr.pgua.eic.trabalhofinal.models.entities.Imovel;
import ifpr.pgua.eic.trabalhofinal.models.entities.Tipo;
import ifpr.pgua.eic.trabalhofinal.models.repositories.CaracteristicasRepository;
import ifpr.pgua.eic.trabalhofinal.models.repositories.ClientesRepository;
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

    private IntegerProperty spId = new SimpleIntegerProperty();
    private StringProperty spDescricao = new SimpleStringProperty();
    private StringProperty spMetragem = new SimpleStringProperty();
    private StringProperty spValor = new SimpleStringProperty();
    private StringProperty spMatricula = new SimpleStringProperty();

    private StringProperty spCaminho = new SimpleStringProperty();

    private IntegerProperty spFoto = new SimpleIntegerProperty();
    private IntegerProperty spEndereco = new SimpleIntegerProperty();

    private StringProperty operacao = new SimpleStringProperty("Cadastrar");
    private BooleanProperty podeEditar = new SimpleBooleanProperty(true);
    private boolean atualizar = false;

    private ObservableList<ImovelRow> obsImoveis = FXCollections.observableArrayList();

    private ObservableList<Foto> fotos = FXCollections.observableArrayList();

    private ObservableList<Tipo> tipos = FXCollections.observableArrayList();
    private ObservableList<String> nomes = FXCollections.observableArrayList();

    private ObservableList<Caracteristica> caracteristicas = FXCollections.observableArrayList();
    private ObservableList<String> descricoes = FXCollections.observableArrayList();

    private ObservableList<Cliente> clientes = FXCollections.observableArrayList();
    private ObservableList<String> proprietarios = FXCollections.observableArrayList();

    private ObjectProperty<ImovelRow> selecionado = new SimpleObjectProperty<>();

    private ObjectProperty<Result> alertProperty = new SimpleObjectProperty<>();

    private ImoveisRepository imoveisRepository;
    private FotosRepository fotosRepository;
    private TiposRepository tiposRepository;
    private CaracteristicasRepository caracteristicasRepository;
    private ClientesRepository clientesRepository;

    public TelaImoveisViewModel(ImoveisRepository imoveisRepository,
                                FotosRepository fotosRepository,
                                TiposRepository tiposRepository,
                                CaracteristicasRepository caracteristicasRepository,
                                ClientesRepository clientesRepository) {
        this.imoveisRepository = imoveisRepository;
        this.fotosRepository = fotosRepository;
        this.tiposRepository = tiposRepository;
        this.caracteristicasRepository = caracteristicasRepository;
        this.clientesRepository = clientesRepository;

        updateList();
        carregaFotos();
        carregaTipos();
        carregaCaracteristicas();
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

    public void carregaClientes(){
        clientes.clear();
        proprietarios.clear();
        
        for(Cliente c : clientesRepository.getClientes()){
            if(c.getDataExclusao() == null){
                clientes.add(c);
                proprietarios.add(c.getNome());

            }

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
    
    public ObservableList<String> getProprietarios(){
        return this.proprietarios;

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

    public StringProperty caminhoProperty(){
        return this.spCaminho;
        
    }

    public IntegerProperty idProperty(){
        return this.spId;

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

    public Result cadastraFoto(){
        Result result;

        String caminho = spCaminho.getValue();

        if(caminho == "")
            result = Result.fail("Selecione uma foto!");

        else{
            Foto foto = new Foto(caminho);

            result = fotosRepository.adicionarFoto(foto);

            if(result instanceof SuccessResult){
                spFoto.setValue(0);
                spCaminho.setValue("");
                spFoto.setValue(foto.getId());
                carregaFotos();
    
            }

        }

        return result;

    }

    public Result cadastrar(int temCaracteristica) {
        int id = 0;
        int idFoto;
        int idTipo;
        int idCaracteristica;
        int idCliente;

        if(spId.getValue() != 0 && spId.getValue() != null){
            id = spId.getValue();
            
        }

        if(spFoto.getValue() != 0){
            idFoto = spFoto.getValue();

        } else {
            return Result.fail("Escolha uma foto!");

        }

        Tipo t = tiposRepository.buscaTipo(spTipo);

        if(t != null){
            idTipo = t.getId();

        } else {
            return Result.fail("Escolha um tipo!");

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

        int idEndereco = spEndereco.getValue();

        Cliente cliente = clientesRepository.buscaCliente(spCliente);

        if(cliente != null){
            idCliente = cliente.getId();

        } else {
            return Result.fail("Escolha um proprietário!");
            
        }
       
        String descricao = spDescricao.getValue();
        double metragem = 0.0;
        double valor = 0.0;

        if (descricao == null || descricao == ""){
            return Result.fail("Escreva uma descricao!");

        }

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
            result = imoveisRepository.atualizarImovel(id, idFoto, idTipo, idCaracteristica, idCliente, descricao, metragem, valor, matricula);
        
        } else {
            result = imoveisRepository.adicionarImovel(idFoto, idTipo, idCaracteristica, idEndereco, idCliente, descricao, metragem, valor, matricula);
        
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
        
        Imovel imovel = selecionado.get().getImovel();

        Foto f = fotosRepository.buscaFotoId(imovel);
        spCaminho.setValue(f.getCaminho());
        spFoto.setValue(f.getId());
        
        Tipo t = tiposRepository.buscaTipoId(imovel);
        spTipo.get().select(t.getNome());
        
        Caracteristica c = caracteristicasRepository.buscaCaracteristicaId(imovel);

        if(imovel.getIdCaracteristica() != 0){
            spCaracteristica.get().select(c.getQuantidade()+" "+c.getDescricao());

        } else {
            spCaracteristica.get().clearSelection();

        }

        if(imovel.getIdEndereco() != 0) spEndereco.setValue(imovel.getIdEndereco());
        
        Cliente cli = clientesRepository.buscaClienteId(imovel);
        spCliente.get().select(cli.getNome());

        spId.setValue(imovel.getId());
        spDescricao.setValue(imovel.getDescricao());
        spMetragem.setValue(String.valueOf(imovel.getMetragem()));
        spValor.setValue(String.valueOf(imovel.getValor()));
        spMatricula.setValue(imovel.getMatricula());

    }

    public Result excluir(){
        Imovel imovel = selecionado.get().getImovel();
        Result result;

        result = imoveisRepository.excluirImovel(imovel.getId());

        if(result instanceof SuccessResult){
            updateList();
            limpar();

        }

        return result;

    }

    public void limpar() {
        spId.setValue(0);
        spFoto.setValue(0);
        spCaminho.setValue("");
        spEndereco.setValue(0);
        spDescricao.setValue("");
        spMetragem.setValue("");
        spValor.setValue("");
        spMatricula.setValue("");
        podeEditar.setValue(true);
        atualizar = false;
        operacao.setValue("Cadastrar");

    }

}

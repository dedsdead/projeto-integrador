package ifpr.pgua.eic.trabalhofinal.controllers.ViewModels;

import ifpr.pgua.eic.trabalhofinal.models.entities.Endereco;
import ifpr.pgua.eic.trabalhofinal.models.repositories.EnderecosRepository;
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

public class TelaEnderecosViewModel {
    private IntegerProperty spEndereco = new SimpleIntegerProperty();
    private StringProperty spCep = new SimpleStringProperty();
    private StringProperty spEstado = new SimpleStringProperty();
    private StringProperty spCidade = new SimpleStringProperty();
    private StringProperty spBairro = new SimpleStringProperty();
    private StringProperty spLogradouro = new SimpleStringProperty();
    private StringProperty spNumero = new SimpleStringProperty();
    private StringProperty spComplemento = new SimpleStringProperty();

    private BooleanProperty pegarEndereco = new SimpleBooleanProperty(false);

    private ObservableList<Endereco> enderecos = FXCollections.observableArrayList();

    private ObjectProperty<Result> alertProperty = new SimpleObjectProperty<>();

    private EnderecosRepository enderecosRepository;
    private TelaClientesViewModel clientesViewModel;
    private TelaImoveisViewModel imoveisViewModel;

    public TelaEnderecosViewModel(EnderecosRepository enderecosRepository, TelaClientesViewModel clientesViewModel, TelaImoveisViewModel imoveisViewModel){
        this.enderecosRepository = enderecosRepository;
        this.clientesViewModel = clientesViewModel;
        this.imoveisViewModel = imoveisViewModel;
        
        updateList();

        if(clientesViewModel.enderecoProperty().getValue() != 0 && 
            clientesViewModel.enderecoProperty().getValue() != -1){
            this.spEndereco.setValue(clientesViewModel.enderecoProperty().getValue());

        } else if(imoveisViewModel.enderecoProperty().getValue() != 0){
            this.spEndereco.setValue(imoveisViewModel.enderecoProperty().getValue());

        } else {
            this.spEndereco.setValue(0);

        }

    }

    public void updateList(){
        enderecos.clear();
        
        for(Endereco e : enderecosRepository.getEnderecos()){
            enderecos.add(e);

        }
        
    }

    public ObjectProperty<Result> alertProperty(){
        return alertProperty;
        
    }

    public BooleanProperty pegarEnderecoProperty(){
        return pegarEndereco;

    }

    public IntegerProperty enderecoProperty() {
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

    public StringProperty bairroProperty() {
        return this.spBairro;
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
        String cep = spCep.getValue();

        if(cep == "" || cep == null){
            return Result.fail("Insira um CEP!");

        }

        Endereco e = enderecosRepository.getEnderecoFromAPI(spCep.getValue());
        
        if(e == null || e.getCep() == null){
            return Result.fail("CEP não econtrado!");
        } else {
            spEstado.setValue(e.getEstado());
            spCidade.setValue(e.getCidade());
            spBairro.setValue(e.getBairro());
            spLogradouro.setValue(e.getLogradouro());
            spNumero.setValue(String.valueOf(e.getNumero()));
            spComplemento.setValue(e.getComplemento());

            return Result.success("CEP encontrado com sucesso!");

        }
    }

    public Result cadastraEndereco(){
        Result result;

        String cep = spCep.getValue();
        String estado = spEstado.getValue();
        String cidade = spCidade.getValue();
        String bairro = spBairro.getValue();
        String logradouro = spLogradouro.getValue();
        int numero = 0;

        try{
            numero = Integer.parseInt(spNumero.getValue());

        }catch(NumberFormatException e){
            result = Result.fail("Número inválido!");

        }

        String complemento = spComplemento.getValue();

        if(cep == "" || estado == "" || cidade == "" || bairro == "" || logradouro == "" || numero == 0)
            result = Result.fail("Preencha os campos!");

        else{
            Endereco endereco = new Endereco(cep, estado, cidade, bairro, logradouro, numero, complemento);
            
            result = enderecosRepository.adicionarEndereco(endereco);

            if(result instanceof SuccessResult){
                limpar();

                if(clientesViewModel.enderecoProperty().getValue() == -1){
                    clientesViewModel.enderecoProperty().set(endereco.getId());

                } else {
                    imoveisViewModel.enderecoProperty().set(endereco.getId());

                }

                updateList();
    
            }

        }

        return result;
        
    }

    public void atualizaEndereco(){
        Endereco e = enderecosRepository.buscaEnderecoId(spEndereco.getValue());

        spEstado.setValue(e.getEstado());
        spCidade.setValue(e.getCidade());
        spBairro.setValue(e.getBairro());
        spLogradouro.setValue(e.getLogradouro());
        spNumero.setValue(String.valueOf(e.getNumero()));
        spComplemento.setValue(e.getComplemento());
        
    }

    public void limpar() {
        spEndereco.setValue(0);
        spCep.setValue("");
        spEstado.setValue("");
        spCidade.setValue("");
        spBairro.setValue("");
        spLogradouro.setValue("");
        spNumero.setValue("");
        spComplemento.setValue("");

        pegarEndereco.setValue(false);

    }
    
}

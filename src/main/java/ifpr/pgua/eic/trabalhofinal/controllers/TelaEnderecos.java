package ifpr.pgua.eic.trabalhofinal.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import ifpr.pgua.eic.trabalhofinal.App;
import ifpr.pgua.eic.trabalhofinal.controllers.ViewModels.TelaEnderecosViewModel;
import ifpr.pgua.eic.trabalhofinal.models.results.Result;
import ifpr.pgua.eic.trabalhofinal.models.results.SuccessResult;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class TelaEnderecos extends BaseController implements Initializable{
    @FXML
    private TextField tfCep;

    @FXML
    private TextField tfEstado;

    @FXML
    private TextField tfCidade;

    @FXML
    private TextField tfBairro;

    @FXML
    private TextField tfLogradouro;

    @FXML
    private TextField tfNumero;

    @FXML
    private TextField tfComplemento;

    @FXML
    private Label lbCep;

    @FXML
    private Label lbEstado;

    @FXML
    private Label lbCidade;

    @FXML
    private Label lbBairro;

    @FXML
    private Label lbLogradouro;

    @FXML
    private Label lbNumero;

    @FXML
    private Label lbComplemento;

    @FXML
    private Button btBuscarCEP;

    @FXML
    private Button btSalvarEndereco;

    @FXML
    private Button btSair;
    
    private TelaEnderecosViewModel viewModel;

    public TelaEnderecos(TelaEnderecosViewModel viewModel){
        this.viewModel = viewModel;
        
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        if(viewModel.enderecoProperty().getValue() != 0){
            viewModel.pegarEnderecoProperty().set(!viewModel.pegarEnderecoProperty().getValue());
            viewModel.atualizaEndereco();
            
        }

        tfCep.textProperty().bindBidirectional(viewModel.cepProperty());
        tfEstado.textProperty().bindBidirectional(viewModel.estadoProperty());
        tfCidade.textProperty().bindBidirectional(viewModel.cidadeProperty());
        tfBairro.textProperty().bindBidirectional(viewModel.bairroProperty());
        tfLogradouro.textProperty().bindBidirectional(viewModel.logradouroProperty());
        tfNumero.textProperty().bindBidirectional(viewModel.numeroProperty());
        tfComplemento.textProperty().bindBidirectional(viewModel.complementoProperty());

        tfCep.managedProperty().bind(viewModel.pegarEnderecoProperty().not());
        tfCep.visibleProperty().bind(viewModel.pegarEnderecoProperty().not());

        tfEstado.managedProperty().bind(viewModel.pegarEnderecoProperty());
        tfEstado.visibleProperty().bind(viewModel.pegarEnderecoProperty());

        tfCidade.managedProperty().bind(viewModel.pegarEnderecoProperty());
        tfCidade.visibleProperty().bind(viewModel.pegarEnderecoProperty());

        tfBairro.managedProperty().bind(viewModel.pegarEnderecoProperty());
        tfBairro.visibleProperty().bind(viewModel.pegarEnderecoProperty());

        tfLogradouro.managedProperty().bind(viewModel.pegarEnderecoProperty());
        tfLogradouro.visibleProperty().bind(viewModel.pegarEnderecoProperty());

        tfNumero.managedProperty().bind(viewModel.pegarEnderecoProperty());
        tfNumero.visibleProperty().bind(viewModel.pegarEnderecoProperty());

        tfComplemento.managedProperty().bind(viewModel.pegarEnderecoProperty());
        tfComplemento.visibleProperty().bind(viewModel.pegarEnderecoProperty());

        lbCep.managedProperty().bind(viewModel.pegarEnderecoProperty().not());
        lbCep.visibleProperty().bind(viewModel.pegarEnderecoProperty().not());

        lbEstado.managedProperty().bind(viewModel.pegarEnderecoProperty());
        lbEstado.visibleProperty().bind(viewModel.pegarEnderecoProperty());

        lbCidade.managedProperty().bind(viewModel.pegarEnderecoProperty());
        lbCidade.visibleProperty().bind(viewModel.pegarEnderecoProperty());

        lbBairro.managedProperty().bind(viewModel.pegarEnderecoProperty());
        lbBairro.visibleProperty().bind(viewModel.pegarEnderecoProperty());

        lbLogradouro.managedProperty().bind(viewModel.pegarEnderecoProperty());
        lbLogradouro.visibleProperty().bind(viewModel.pegarEnderecoProperty());

        lbNumero.managedProperty().bind(viewModel.pegarEnderecoProperty());
        lbNumero.visibleProperty().bind(viewModel.pegarEnderecoProperty());

        lbComplemento.managedProperty().bind(viewModel.pegarEnderecoProperty());
        lbComplemento.visibleProperty().bind(viewModel.pegarEnderecoProperty());

        btBuscarCEP.managedProperty().bind(viewModel.pegarEnderecoProperty().not());
        btBuscarCEP.visibleProperty().bind(viewModel.pegarEnderecoProperty().not());

        btSalvarEndereco.managedProperty().bind(viewModel.pegarEnderecoProperty());
        btSalvarEndereco.visibleProperty().bind(viewModel.pegarEnderecoProperty());

        viewModel.alertProperty().addListener((ChangeListener<Result>) (observable, oldVal, newVal) -> {
            showMessage(newVal);

        });
        
    }

    @FXML
    private void buscarCep(){
        Result result = viewModel.buscaCep();
        showMessage(result);

        if(result instanceof SuccessResult){
            viewModel.pegarEnderecoProperty().set(!viewModel.pegarEnderecoProperty().getValue());
            btSair.setText("Voltar");

        }

    }

    @FXML
    private void salvarEndereco(){
        Result result = viewModel.cadastraEndereco();

        showMessage(result);

        if(result instanceof SuccessResult){
            viewModel.limpar();
            btSair.setText("Sair");
            
            App.popScreen();
            
        }
        
    }

    @FXML
    private void sair(){
        if(viewModel.pegarEnderecoProperty().getValue() == true){
            viewModel.limpar();
            btSair.setText("Sair");

            App.popScreen();

        } else {
            viewModel.limpar();
            App.popScreen();
            
        }
        
    }
    
}

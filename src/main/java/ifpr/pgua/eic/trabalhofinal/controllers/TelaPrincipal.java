package ifpr.pgua.eic.trabalhofinal.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import ifpr.pgua.eic.trabalhofinal.App;
import ifpr.pgua.eic.trabalhofinal.controllers.ViewModels.TelaPrincipalViewModel;
import ifpr.pgua.eic.trabalhofinal.models.results.Result;
import ifpr.pgua.eic.trabalhofinal.models.results.SuccessResult;
import ifpr.pgua.eic.trabalhofinal.utils.Navigator.BorderPaneRegion;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

public class TelaPrincipal extends BaseController implements Initializable{
    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfSenha;

    @FXML
    private PasswordField pfSenha;

    @FXML
    private Button btLogar;
    
    @FXML
    private Button btLimpar;

    @FXML
    private CheckBox ckMostrar;

    public TelaPrincipalViewModel viewModel;

    public TelaPrincipal(TelaPrincipalViewModel viewModel){
        this.viewModel = viewModel;

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        tfEmail.textProperty().bindBidirectional(viewModel.emailProperty());
        
        pfSenha.textProperty().bindBidirectional(viewModel.senhaProperty());
        tfSenha.textProperty().bindBidirectional(viewModel.senhaProperty());

        tfSenha.managedProperty().bind(ckMostrar.selectedProperty());
        tfSenha.visibleProperty().bind(ckMostrar.selectedProperty());

        pfSenha.managedProperty().bind(ckMostrar.selectedProperty().not());
        pfSenha.visibleProperty().bind(ckMostrar.selectedProperty().not());

        btLogar.setDefaultButton(true);

        viewModel.alertProperty().addListener((ChangeListener<Result>) (observable, oldVal, newVal) -> {
            showMessage(newVal);

        });

        btLogar.setOnKeyPressed((evt) -> {
            if(evt.getCode().equals(KeyCode.ENTER)){
                btLogar.fire();
                evt.consume();

            }
        });

    }

    @FXML
    private void logar(){
        Result result = viewModel.logar();
        showMessage(result);

        if (result instanceof SuccessResult){
            App.changeScreenRegion("CLIENTES", BorderPaneRegion.CENTER);

        }
        
    }

    @FXML
    private void carregarClientes(){
        if(viewModel.logadoProperty().getValue()){
            App.changeScreenRegion("CLIENTES", BorderPaneRegion.CENTER);

        } else {
            showMessage(Result.fail("Faça login primeiro!"));
        }

    }

    @FXML
    private void carregarImoveis(){
        if(viewModel.logadoProperty().getValue()){
            App.changeScreenRegion("IMOVEIS", BorderPaneRegion.CENTER);

        } else {
            showMessage(Result.fail("Faça login primeiro!"));
        }

    }
    
}

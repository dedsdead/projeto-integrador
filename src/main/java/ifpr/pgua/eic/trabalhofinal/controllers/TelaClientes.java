package ifpr.pgua.eic.trabalhofinal.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import ifpr.pgua.eic.trabalhofinal.App;
import ifpr.pgua.eic.trabalhofinal.controllers.ViewModels.ClienteRow;
import ifpr.pgua.eic.trabalhofinal.controllers.ViewModels.TelaClientesViewModel;
import ifpr.pgua.eic.trabalhofinal.models.results.Result;
import ifpr.pgua.eic.trabalhofinal.models.results.SuccessResult;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class TelaClientes extends BaseController implements Initializable{
    @FXML
    private ComboBox<String> cbTipos;

    @FXML
    private ComboBox<String> cbCaracteristicas;

    @FXML
    private TableColumn<ClienteRow, String> tbcNome;

    @FXML
    private TableColumn<ClienteRow, String> tbcTelefone;

    @FXML
    private TableColumn<ClienteRow,String> tbcCpf;

    @FXML
    private TableColumn<ClienteRow, String> tbcEmail;

    @FXML
    private TableColumn<ClienteRow, String> tbcId;

    @FXML
    private TableView<ClienteRow> tbClientes;

    @FXML
    private TextField tfNome;

    @FXML
    private TextField tfTelefone;

    @FXML
    private TextField tfCpf;

    @FXML
    private TextField tfEmail;

    @FXML
    private Button btCadastrar;

    @FXML
    private Button btEnderecos;

    @FXML
    private Button btExcluir;
    
    @FXML
    private Button btLimpar;

    private TelaClientesViewModel viewModel;

    int temTipo = 0;
    int temCaracteristica = 0;
    int limpar = 0;

    public TelaClientes(TelaClientesViewModel viewModel){
        this.viewModel = viewModel;

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        tbcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tbcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tbcTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        tbcCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        tbcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        tbClientes.setItems(viewModel.getClientes());

        viewModel.selecionadoProperty().bind(tbClientes.getSelectionModel().selectedItemProperty());

        viewModel.alertProperty().addListener((ChangeListener<Result>) (observable, oldVal, newVal) -> {
            showMessage(newVal);

        });

        viewModel.tipoProperty().bindBidirectional(cbTipos.selectionModelProperty());
        cbTipos.setItems(viewModel.getNomes());

        viewModel.caracteristicaProperty().bindBidirectional(cbCaracteristicas.selectionModelProperty());
        cbCaracteristicas.setItems(viewModel.getDescricoes());

        tfNome.textProperty().bindBidirectional(viewModel.nomeProperty());

        tfTelefone.textProperty().bindBidirectional(viewModel.telefoneProperty());

        tfCpf.textProperty().bindBidirectional(viewModel.cpfProperty());

        tfEmail.textProperty().bindBidirectional(viewModel.emailProperty());
        tfEmail.editableProperty().bind(viewModel.podeEditarProperty());

        btCadastrar.textProperty().bind(viewModel.operacaoProperty());

        btExcluir.managedProperty().bind(viewModel.podeEditarProperty().not());
        btExcluir.visibleProperty().bind(viewModel.podeEditarProperty().not());

        viewModel.updateList();

        cbTipos.setOnAction((evt)->{
            if(limpar == 0){
                temTipo = 1;

                if(temCaracteristica == 1 && viewModel.podeEditarProperty().getValue()){
                    cbCaracteristicas.setItems(null);
                    temCaracteristica = 0;
    
                } else {
                    if(cbTipos.getValue().equals("APARTAMENTO")
                     || cbTipos.getValue().equals("COMERCIAL")
                     || cbTipos.getValue().equals("CASA")
                     || cbTipos.getValue().equals("SOBRADO")
                     ){
                        cbCaracteristicas.setItems(viewModel.getDescricoes());

                    } else {
                        cbCaracteristicas.setItems(null);
                        temCaracteristica = 0;
    
                    }
                }

            }

        });

        cbCaracteristicas.setOnAction((evt)->{
            if(limpar == 0) temCaracteristica = 1;

        });

    }

    @FXML
    private void telaEnderecos(){
        if(viewModel.podeEditarProperty().getValue())
            viewModel.enderecoProperty().set(-1);
            
        App.pushScreen("ENDERECOS");

    }

    @FXML
    private void cadastrar(){
        Result result = viewModel.cadastrar(temTipo, temCaracteristica);
        showMessage(result);

        if(result instanceof SuccessResult){
            result = viewModel.mandarEmails(temTipo, temCaracteristica);
            showMessage(result);
            limpar();
            
        }

    }

    @FXML
    private void atualizar(MouseEvent event){
        if(event.getClickCount() == 2){
            viewModel.atualizar();

        }
        
    }

    @FXML
    private void excluir(){
        Result result = viewModel.excluir();
        showMessage(result);
        limpar();

    }

    @FXML
    private void limpar(){
        limpar = 1;
        viewModel.limpar();
        temCaracteristica = 0;
        temTipo = 0;
        cbTipos.setItems(null);
        cbTipos.setItems(viewModel.getNomes());
        cbCaracteristicas.setItems(null);
        cbCaracteristicas.setItems(viewModel.getDescricoes());
        limpar = 0;

    }

}

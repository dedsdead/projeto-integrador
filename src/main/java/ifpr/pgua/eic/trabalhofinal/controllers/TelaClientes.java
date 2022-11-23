package ifpr.pgua.eic.trabalhofinal.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import ifpr.pgua.eic.trabalhofinal.controllers.ViewModels.ClienteRow;
import ifpr.pgua.eic.trabalhofinal.controllers.ViewModels.TelaClientesViewModel;
import ifpr.pgua.eic.trabalhofinal.models.results.Result;
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
    private Button btLimpar;

    private TelaClientesViewModel viewModel;


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
        cbTipos.setItems(viewModel.getTipos());

        viewModel.tipoProperty().bindBidirectional(cbCaracteristicas.selectionModelProperty());
        cbCaracteristicas.setItems(viewModel.getCaracteristicas());

        tfNome.textProperty().bindBidirectional(viewModel.nomeProperty());
        tfNome.editableProperty().bind(viewModel.podeEditarProperty());

        tfTelefone.textProperty().bindBidirectional(viewModel.telefoneProperty());
        tfTelefone.editableProperty().bind(viewModel.podeEditarProperty());

        tfCpf.textProperty().bindBidirectional(viewModel.cpfProperty());
        tfCpf.editableProperty().bind(viewModel.podeEditarProperty());

        tfEmail.textProperty().bindBidirectional(viewModel.emailProperty());

        btCadastrar.textProperty().bind(viewModel.operacaoProperty());

        viewModel.updateList();
    }

    @FXML
    private void cadastrar(){
        Result result = viewModel.cadastrar();
        showMessage(result);
    }

    @FXML
    private void limpar(){
        viewModel.limpar();
    }

    @FXML
    private void atualizar(MouseEvent event){
        if(event.getClickCount() == 2){
            viewModel.atualizar();
        }
        
    }

}

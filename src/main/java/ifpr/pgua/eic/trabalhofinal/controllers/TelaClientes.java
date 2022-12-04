package ifpr.pgua.eic.trabalhofinal.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import ifpr.pgua.eic.trabalhofinal.controllers.ViewModels.ClienteRow;
import ifpr.pgua.eic.trabalhofinal.controllers.ViewModels.TelaClientesViewModel;
import ifpr.pgua.eic.trabalhofinal.models.results.Result;
import ifpr.pgua.eic.trabalhofinal.models.results.SuccessResult;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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
    private TextField tfCep;

    @FXML
    private TextField tfEstado;

    @FXML
    private TextField tfCidade;

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
    private Label lbLogradouro;

    @FXML
    private Label lbNumero;

    @FXML
    private Label lbComplemento;

    @FXML
    private Button btCadastrar;

    @FXML
    private Button btEndereco;

    @FXML
    private Button btBuscar;

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

        tbClientes.managedProperty().bind(viewModel.pegarEnderecoProperty().not());
        tbClientes.visibleProperty().bind(viewModel.pegarEnderecoProperty().not());

        tfCep.textProperty().bindBidirectional(viewModel.cepProperty());
        tfEstado.textProperty().bindBidirectional(viewModel.estadoProperty());
        tfCidade.textProperty().bindBidirectional(viewModel.cidadeProperty());
        tfLogradouro.textProperty().bindBidirectional(viewModel.logradouroProperty());
        tfNumero.textProperty().bindBidirectional(viewModel.numeroProperty());
        tfComplemento.textProperty().bindBidirectional(viewModel.complementoProperty());

        tfCep.managedProperty().bind(viewModel.pegarEnderecoProperty());
        tfCep.visibleProperty().bind(viewModel.pegarEnderecoProperty());

        tfEstado.managedProperty().bind(viewModel.pegarEnderecoProperty());
        tfEstado.visibleProperty().bind(viewModel.pegarEnderecoProperty());

        tfCidade.managedProperty().bind(viewModel.pegarEnderecoProperty());
        tfCidade.visibleProperty().bind(viewModel.pegarEnderecoProperty());

        tfLogradouro.managedProperty().bind(viewModel.pegarEnderecoProperty());
        tfLogradouro.visibleProperty().bind(viewModel.pegarEnderecoProperty());

        tfNumero.managedProperty().bind(viewModel.pegarEnderecoProperty());
        tfNumero.visibleProperty().bind(viewModel.pegarEnderecoProperty());

        tfComplemento.managedProperty().bind(viewModel.pegarEnderecoProperty());
        tfComplemento.visibleProperty().bind(viewModel.pegarEnderecoProperty());

        lbCep.managedProperty().bind(viewModel.pegarEnderecoProperty());
        lbCep.visibleProperty().bind(viewModel.pegarEnderecoProperty());

        lbEstado.managedProperty().bind(viewModel.pegarEnderecoProperty());
        lbEstado.visibleProperty().bind(viewModel.pegarEnderecoProperty());

        lbCidade.managedProperty().bind(viewModel.pegarEnderecoProperty());
        lbCidade.visibleProperty().bind(viewModel.pegarEnderecoProperty());

        lbLogradouro.managedProperty().bind(viewModel.pegarEnderecoProperty());
        lbLogradouro.visibleProperty().bind(viewModel.pegarEnderecoProperty());

        lbNumero.managedProperty().bind(viewModel.pegarEnderecoProperty());
        lbNumero.visibleProperty().bind(viewModel.pegarEnderecoProperty());

        lbComplemento.managedProperty().bind(viewModel.pegarEnderecoProperty());
        lbComplemento.visibleProperty().bind(viewModel.pegarEnderecoProperty());

        btBuscar.managedProperty().bind(viewModel.pegarEnderecoProperty());
        btBuscar.visibleProperty().bind(viewModel.pegarEnderecoProperty());

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
    private void cadastrar(){
        Result result = viewModel.cadastrar(temTipo, temCaracteristica);
        showMessage(result);
        limpar();

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

        btEndereco.setText("Endereço");
        viewModel.pegarEnderecoProperty().set(false);

    }

    @FXML
    private void carregaEndereco(){
        if(!viewModel.pegarEnderecoProperty().getValue()){
            btEndereco.setText("Finalizar");

            viewModel.pegarEnderecoProperty().set(!viewModel.pegarEnderecoProperty().getValue());

        } else {
            Result result = viewModel.cadastraEndereco();
            
            if(result instanceof SuccessResult){
                btEndereco.setText("Endereço");

            }

            showMessage(result);
            
        }

    }

    @FXML
    private void atualizar(MouseEvent event){
        if(event.getClickCount() == 2){
            viewModel.atualizar();

        }
        
    }

    @FXML
    private void buscar(){
        Result result = viewModel.buscaCep();
        showMessage(result);
        
    }

}

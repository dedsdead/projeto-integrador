package ifpr.pgua.eic.trabalhofinal.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import ifpr.pgua.eic.trabalhofinal.controllers.ViewModels.TelaVendasViewModel;
import ifpr.pgua.eic.trabalhofinal.controllers.ViewModels.VendaRow;
import ifpr.pgua.eic.trabalhofinal.models.results.Result;
import ifpr.pgua.eic.trabalhofinal.models.results.SuccessResult;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class TelaVendas extends BaseController implements Initializable{
    @FXML
    private ComboBox<String> cbImoveis;
    
    @FXML
    private ComboBox<String> cbClientes;

    @FXML
    private TableColumn<VendaRow, String> tbcData;

    @FXML
    private TableColumn<VendaRow,String> tbcValor;

    @FXML
    private TableColumn<VendaRow, String> tbcContrato;

    @FXML
    private TableColumn<VendaRow, String> tbcId;

    @FXML
    private TableView<VendaRow> tbVendas;

    @FXML
    private DatePicker dpVenda;

    @FXML
    private TextField tfValor;

    @FXML
    private Button btCadastrar;

    @FXML
    private TextField tfContrato;

    @FXML
    private Button btExcluir;
    
    @FXML
    private Button btLimpar;

    private TelaVendasViewModel viewModel;

    private boolean limpar = false;

    public TelaVendas(TelaVendasViewModel viewModel){
        this.viewModel = viewModel;

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        tbcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tbcData.setCellValueFactory(new PropertyValueFactory<>("data"));
        tbcValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        tbcContrato.setCellValueFactory(new PropertyValueFactory<>("contrato"));

        tbVendas.setItems(viewModel.getVendas());

        viewModel.selecionadoProperty().bind(tbVendas.getSelectionModel().selectedItemProperty());

        viewModel.alertProperty().addListener((ChangeListener<Result>) (observable, oldVal, newVal) -> {
            showMessage(newVal);

        });

        dpVenda.valueProperty().bindBidirectional(viewModel.dataProperty());
        
        viewModel.imovelProperty().bindBidirectional(cbImoveis.selectionModelProperty());
        cbImoveis.setItems(viewModel.getDescricoes());
        
        viewModel.clienteProperty().bindBidirectional(cbClientes.selectionModelProperty());
        cbClientes.setItems(viewModel.getCompradores());

        tfValor.textProperty().bindBidirectional(viewModel.valorProperty());
        tfContrato.textProperty().bindBidirectional(viewModel.contratoProperty());

        btCadastrar.textProperty().bind(viewModel.operacaoProperty());

        btExcluir.managedProperty().bind(viewModel.podeEditarProperty().not());
        btExcluir.visibleProperty().bind(viewModel.podeEditarProperty().not());

        viewModel.updateList();
        viewModel.carregaImoveis();
        viewModel.carregaClientes();

        cbImoveis.setOnAction(evt -> {
            if(limpar == false){
                viewModel.descartaProprietarioComprador();

            }

        });
        
    }

    @FXML
    private void cadastrar(){
        Result result = viewModel.cadastrar();
        showMessage(result);

        if(result instanceof SuccessResult)
            limpar();

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
        limpar = true;
        viewModel.limpar();
        cbImoveis.setItems(null);
        cbImoveis.setItems(viewModel.getDescricoes());
        cbClientes.setItems(null);
        cbClientes.setItems(viewModel.getCompradores());
        limpar = false;

    }

}

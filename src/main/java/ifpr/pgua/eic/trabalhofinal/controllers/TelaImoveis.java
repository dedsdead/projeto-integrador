package ifpr.pgua.eic.trabalhofinal.controllers;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import ifpr.pgua.eic.trabalhofinal.App;
import ifpr.pgua.eic.trabalhofinal.controllers.ViewModels.ImovelRow;
import ifpr.pgua.eic.trabalhofinal.controllers.ViewModels.TelaImoveisViewModel;
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
import javafx.stage.FileChooser;

public class TelaImoveis extends BaseController implements Initializable{
    @FXML
    private ComboBox<String> cbTipos;

    @FXML
    private ComboBox<String> cbCaracteristicas;

    @FXML
    private ComboBox<String> cbClientes;

    @FXML
    private TableColumn<ImovelRow, String> tbcDescricao;

    @FXML
    private TableColumn<ImovelRow, String> tbcMetragem;

    @FXML
    private TableColumn<ImovelRow,String> tbcValor;

    @FXML
    private TableColumn<ImovelRow, String> tbcMatricula;

    @FXML
    private TableColumn<ImovelRow, String> tbcId;

    @FXML
    private TableView<ImovelRow> tbImoveis;

    @FXML
    private TextField tfFoto;

    @FXML
    private TextField tfDescricao;

    @FXML
    private TextField tfMetragem;

    @FXML
    private TextField tfValor;

    @FXML
    private TextField tfMatricula;

    @FXML
    private Button btCadastrar;

    @FXML
    private Button btEnderecos;

    @FXML
    private Button btBuscarFoto;

    @FXML
    private Button btSalvarFoto;

    @FXML
    private Button btExcluir;
    
    @FXML
    private Button btLimpar;

    private TelaImoveisViewModel viewModel;

    int temCaracteristica = 0;
    int limpar = 0;
    FileChooser fcFoto = new FileChooser();

    public TelaImoveis(TelaImoveisViewModel viewModel){
        this.viewModel = viewModel;

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        tbcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tbcDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        tbcMetragem.setCellValueFactory(new PropertyValueFactory<>("metragem"));
        tbcValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        tbcMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));

        tbImoveis.setItems(viewModel.getImoveis());

        viewModel.selecionadoProperty().bind(tbImoveis.getSelectionModel().selectedItemProperty());

        viewModel.alertProperty().addListener((ChangeListener<Result>) (observable, oldVal, newVal) -> {
            showMessage(newVal);

        });

        viewModel.tipoProperty().bindBidirectional(cbTipos.selectionModelProperty());
        cbTipos.setItems(viewModel.getNomes());

        viewModel.caracteristicaProperty().bindBidirectional(cbCaracteristicas.selectionModelProperty());
        cbCaracteristicas.setItems(viewModel.getDescricoes());

        viewModel.clienteProperty().bindBidirectional(cbClientes.selectionModelProperty());
        cbClientes.setItems(viewModel.getProprietarios());

        tfDescricao.textProperty().bindBidirectional(viewModel.descricaoProperty());

        tfMetragem.textProperty().bindBidirectional(viewModel.metragemProperty());

        tfValor.textProperty().bindBidirectional(viewModel.valorProperty());

        tfMatricula.textProperty().bindBidirectional(viewModel.matriculaProperty());

        btCadastrar.textProperty().bind(viewModel.operacaoProperty());

        btExcluir.managedProperty().bind(viewModel.podeEditarProperty().not());
        btExcluir.visibleProperty().bind(viewModel.podeEditarProperty().not());

        tfFoto.textProperty().bindBidirectional(viewModel.caminhoProperty());

        viewModel.updateList();

        cbTipos.setOnAction((evt)->{
            if(limpar == 0){

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
    private void buscarFoto(){
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Image files", "*.jpg", "*.jpeg", "*.png");
        fcFoto.getExtensionFilters().add(filter);
        File f = fcFoto.showOpenDialog(null);

        if(f != null)
            tfFoto.setText(f.getAbsolutePath());

    }

    @FXML
    private void salvarFoto(){
        Result result = viewModel.cadastraFoto();

        if(result instanceof SuccessResult)
            tfFoto.clear();

        showMessage(result);

    }

    @FXML
    private void telaEnderecos(){
        App.pushScreen("ENDERECOS");
        
    }

    @FXML
    private void cadastrar(){
        Result result = viewModel.cadastrar(temCaracteristica);
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
        limpar = 1;
        viewModel.limpar();
        temCaracteristica = 0;
        cbTipos.setItems(null);
        cbTipos.setItems(viewModel.getNomes());
        cbCaracteristicas.setItems(null);
        cbCaracteristicas.setItems(viewModel.getDescricoes());
        cbClientes.setItems(null);
        cbClientes.setItems(viewModel.getProprietarios());
        limpar = 0;

        tfFoto.clear();

    }

}

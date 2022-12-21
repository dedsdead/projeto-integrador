package ifpr.pgua.eic.trabalhofinal.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

import ifpr.pgua.eic.trabalhofinal.App;
import ifpr.pgua.eic.trabalhofinal.controllers.ViewModels.TelaFotosViewModel;
import ifpr.pgua.eic.trabalhofinal.models.results.Result;
import ifpr.pgua.eic.trabalhofinal.models.results.SuccessResult;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.stage.FileChooser;

public class TelaFotos extends BaseController implements Initializable{
    @FXML
    private TextField tfFoto;

    @FXML
    private Button btSelecionarFoto;

    @FXML
    private Button btSalvarFoto;

    @FXML
    private Button btSair;
    
    @FXML
    private TilePane painel;

    private TelaFotosViewModel viewModel;

    public TelaFotos(TelaFotosViewModel viewModel){
        this.viewModel = viewModel;

    }
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        
        viewModel.alertProperty().addListener((ChangeListener<Result>) (observable, oldVal, newVal) -> {
            showMessage(newVal);

        });

        viewModel.painelProperty().addListener((ChangeListener<TilePane>) (obs, oldPane, newPane) -> {
            painel.getChildren().clear();
            painel.getChildren().addAll(newPane);
            
        });

        tfFoto.textProperty().bindBidirectional(viewModel.caminhoProperty());
        
        viewModel.getCaminhos();
        viewModel.getMiniaturas();

    }

    @FXML
    private void selecionarFoto(){
        FileChooser fcFoto = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Image files", "*.jpg", "*.jpeg", "*.png");
        fcFoto.getExtensionFilters().add(filter);

        try {
            File f = fcFoto.showOpenDialog(null);
            if(f != null){
                tfFoto.setText(f.getAbsolutePath());
                Image image = new Image(new FileInputStream(f));
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(100); 
                imageView.setFitWidth(100);
                painel.getChildren().clear();
                painel.getChildren().add(imageView);

            }

        } catch (FileNotFoundException e) {
            e.getStackTrace();
            showMessage(Result.fail(e.getMessage()));

        }

    }

    @FXML
    private void salvarFoto(){
        Result result = viewModel.cadastraFoto();

        if(result instanceof SuccessResult)
            tfFoto.clear();

        showMessage(result);

    }

    @FXML
    private void sair(){
        tfFoto.clear();
        App.popScreen();
        
    }
    
}

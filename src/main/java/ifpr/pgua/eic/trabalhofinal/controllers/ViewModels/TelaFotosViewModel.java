package ifpr.pgua.eic.trabalhofinal.controllers.ViewModels;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Optional;

import ifpr.pgua.eic.trabalhofinal.models.entities.Foto;
import ifpr.pgua.eic.trabalhofinal.models.repositories.FotosRepository;
import ifpr.pgua.eic.trabalhofinal.models.results.Result;
import ifpr.pgua.eic.trabalhofinal.models.results.SuccessResult;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;

public class TelaFotosViewModel {
    private StringProperty spCaminho = new SimpleStringProperty();

    private ObjectProperty<Result> alertProperty = new SimpleObjectProperty<>();

    private ObjectProperty<TilePane> painelProperty = new SimpleObjectProperty<>();
    
    private ObservableList<Foto> fotos = FXCollections.observableArrayList();
    private ObservableList<String> caminhos = FXCollections.observableArrayList();
    private ArrayList<Integer> idsFotos = new ArrayList<>();
    
    private FotosRepository fotosRepository;
    private TelaImoveisViewModel imoveisViewModel;

    public TelaFotosViewModel(FotosRepository fotosRepository, TelaImoveisViewModel imoveisViewModel){
        this.fotosRepository = fotosRepository;
        this.imoveisViewModel = imoveisViewModel;

        updateList();

        if(!imoveisViewModel.getIdsFotos().isEmpty()){
            idsFotos.clear();
            idsFotos.addAll(imoveisViewModel.getIdsFotos());

        } else {
            idsFotos.clear();
            for (Foto foto : fotosRepository.getFotos()) {
                idsFotos.add(foto.getId());
                imoveisViewModel.getIdsFotos().add(foto.getId());

            }

        }

    }

    public void updateList(){
        fotos.clear();

        for (Foto f : fotosRepository.getFotos()) {
            fotos.add(f);

        }

    }

    public void getCaminhos(){
        caminhos.clear();

        for (int id : idsFotos) {
            String caminho = fotosRepository.getCaminhoFoto(id);
            caminhos.add(caminho);

        }

    }

    public void getMiniaturas(){
        painelProperty.set(new TilePane());
        if(!caminhos.isEmpty()){
            painelProperty.getValue().getChildren().clear();
            for (String caminho : caminhos) {
                try {
                    File f = new File(caminho);
                    Image image = new Image(new FileInputStream(f));
                    ImageView imageView = new ImageView(image);
                    imageView.setFitHeight(100); 
                    imageView.setFitWidth(100);
                    imageView.setId(caminho);
                    imageView.setOnMouseClicked((evt) -> {
                        if(evt.getClickCount() == 2){
                            this.removeFoto(caminho);

                        }
                    });
                    painelProperty.getValue().getChildren().add(imageView);
        
                } catch (FileNotFoundException e) {
                    e.getStackTrace();
        
                }

            }

        }

    }

    public ObservableList<String> caminhosProperty(){
        return this.caminhos;

    }

    public StringProperty caminhoProperty(){
        return this.spCaminho;
        
    }

    public ArrayList<Integer> getIdsFotos(){
        return this.idsFotos;

    }

    public ObjectProperty<Result> alertProperty(){
        return alertProperty;
        
    }

    public ObjectProperty<TilePane> painelProperty(){
        return painelProperty;

    }

    public Result cadastraFoto(){
        Result result;

        String caminho = spCaminho.getValue();

        if(caminho == "" || caminho == null)
            result = Result.fail("Selecione uma foto!");

        else{
            Foto foto = new Foto(caminho);

            result = fotosRepository.adicionarFoto(foto);

            if(result instanceof SuccessResult){
                spCaminho.setValue("");
                idsFotos.add(foto.getId());
                imoveisViewModel.getIdsFotos().add(foto.getId());

                updateList();
                getCaminhos();
                getMiniaturas();
    
            }

        }

        return result;

    }

    public Result removeFoto(String caminho){
        caminhos.remove(caminho);

        Optional<Foto> busca = fotos.stream().filter(ft -> ft.getCaminho().equals(caminho)).findFirst();
        if(busca.isPresent()){
            Foto f = busca.get();
            int i = idsFotos.indexOf(f.getId());
            idsFotos.remove(i);
            imoveisViewModel.getIdsFotos().remove(i);
            fotos.remove(f);

            updateList();
            getCaminhos();
            getMiniaturas();

            return Result.success("Foto removida com sucesso!");

        }

        return Result.fail("Erro ao encontrar a foto!");

    }

}

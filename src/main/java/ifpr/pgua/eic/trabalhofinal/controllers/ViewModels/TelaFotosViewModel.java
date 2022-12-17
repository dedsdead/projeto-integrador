package ifpr.pgua.eic.trabalhofinal.controllers.ViewModels;

import java.util.ArrayList;

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

public class TelaFotosViewModel {
    private StringProperty spCaminho = new SimpleStringProperty();

    private ObjectProperty<Result> alertProperty = new SimpleObjectProperty<>();
    
    private ObservableList<Foto> fotos = FXCollections.observableArrayList();
    private ArrayList<Integer> idsFotos = new ArrayList<>();
    
    private FotosRepository fotosRepository;
    private TelaImoveisViewModel imoveisViewModel;

    public TelaFotosViewModel(FotosRepository fotosRepository, TelaImoveisViewModel imoveisViewModel){
        this.fotosRepository = fotosRepository;
        this.imoveisViewModel = imoveisViewModel;

        updateList();

        if(!imoveisViewModel.getIdsFotos().isEmpty()){
            this.idsFotos.addAll(imoveisViewModel.getIdsFotos());

        } else {
            idsFotos.clear();

        }

    }

    public void updateList(){
        fotos.clear();

        for (Foto f : fotosRepository.getFotos()) {
            fotos.add(f);

        }

    }

    public StringProperty caminhoProperty(){
        return this.spCaminho;
        
    }

    public ObjectProperty<Result> alertProperty(){
        return alertProperty;
        
    }

    public Result cadastraFoto(){
        Result result;

        String caminho = spCaminho.getValue();

        if(caminho == "")
            result = Result.fail("Selecione uma foto!");

        else{
            Foto foto = new Foto(caminho);

            result = fotosRepository.adicionarFoto(foto);

            if(result instanceof SuccessResult){
                spCaminho.setValue("");
                idsFotos.add(foto.getId());
                imoveisViewModel.getIdsFotos().add(foto.getId());

                updateList();
    
            }

        }

        return result;

    }

}

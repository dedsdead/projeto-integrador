package ifpr.pgua.eic.trabalhofinal.controllers.ViewModels;

import ifpr.pgua.eic.trabalhofinal.models.entities.Imovel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ImovelRow {
    private Imovel imovel;

    public ImovelRow(Imovel imovel){
        this.imovel = imovel;

    }

    public Imovel getImovel(){
        return imovel;

    }

    public StringProperty idProperty(){
        return new SimpleStringProperty(String.valueOf(imovel.getId()));
        
    }

    public StringProperty idTipoProperty(){
        return new SimpleStringProperty(String.valueOf(imovel.getIdTipo()));
        
    }

    public StringProperty idCaracteristicaProperty(){
        return new SimpleStringProperty(String.valueOf(imovel.getIdCaracteristica()));
        
    }

    public StringProperty idEnderecoProperty(){
        return new SimpleStringProperty(String.valueOf(imovel.getIdEndereco()));
        
    }

    public StringProperty descricaoProperty(){
        return new SimpleStringProperty(imovel.getDescricao());
        
    }

    public StringProperty metragemProperty(){
        return new SimpleStringProperty(String.valueOf(imovel.getMetragem()));
        
    }

    public StringProperty valorProperty(){
        return new SimpleStringProperty(String.valueOf(imovel.getValor()));
        
    }

    public StringProperty matriculaProperty(){
        return new SimpleStringProperty(imovel.getMatricula());
        
    }

}

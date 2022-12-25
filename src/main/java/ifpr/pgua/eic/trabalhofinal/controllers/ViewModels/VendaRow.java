package ifpr.pgua.eic.trabalhofinal.controllers.ViewModels;

import ifpr.pgua.eic.trabalhofinal.models.entities.Venda;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class VendaRow {
    private Venda venda;

    public VendaRow(Venda venda){
        this.venda = venda;

    }

    public Venda getVenda(){
        return venda;

    }

    public StringProperty idProperty(){
        return new SimpleStringProperty(String.valueOf(venda.getId()));
        
    }

    public StringProperty imovelProperty(){
        return new SimpleStringProperty(String.valueOf(venda.getIdImovel()));
        
    }

    public StringProperty compradorProperty(){
        return new SimpleStringProperty(String.valueOf(venda.getIdComprador()));
        
    }

    public StringProperty dataProperty(){
        return new SimpleStringProperty(String.valueOf(venda.getDataVenda()));
        
    }

    public StringProperty valorProperty(){
        return new SimpleStringProperty(String.valueOf(venda.getValor()));
        
    }

    public StringProperty contratoProperty(){
        return new SimpleStringProperty(venda.getCaminhoContrato());
        
    }
    
}
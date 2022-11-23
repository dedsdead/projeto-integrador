package ifpr.pgua.eic.trabalhofinal.controllers.ViewModels;

import ifpr.pgua.eic.trabalhofinal.models.entities.Cliente;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ClienteRow {
    private Cliente cliente;

    public ClienteRow(Cliente cliente){
        this.cliente = cliente;

    }

    public Cliente getCliente(){
        return cliente;

    }

    public StringProperty idProperty(){
        return new SimpleStringProperty(String.valueOf(cliente.getId()));
        
    }

    public StringProperty idEnderecoProperty(){
        return new SimpleStringProperty(String.valueOf(cliente.getIdEndereco()));
        
    }

    public StringProperty idTipoProperty(){
        return new SimpleStringProperty(String.valueOf(cliente.getIdTipo()));
        
    }

    public StringProperty idCaracteristicaProperty(){
        return new SimpleStringProperty(String.valueOf(cliente.getIdCaracteristica()));
        
    }

    public StringProperty nomeProperty(){
        return new SimpleStringProperty(cliente.getNome());
        
    }

    public StringProperty telefoneProperty(){
        return new SimpleStringProperty(cliente.getTelefone());
        
    }

    public StringProperty cpfProperty(){
        return new SimpleStringProperty(cliente.getCpf());
        
    }

    public StringProperty emailProperty(){
        return new SimpleStringProperty(cliente.getEmail());
        
    }
    
}
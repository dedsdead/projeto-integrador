package ifpr.pgua.eic.trabalhofinal.models.repositories;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import ifpr.pgua.eic.trabalhofinal.models.daos.TipoDAO;
import ifpr.pgua.eic.trabalhofinal.models.entities.Cliente;
import ifpr.pgua.eic.trabalhofinal.models.entities.Imovel;
import ifpr.pgua.eic.trabalhofinal.models.entities.Tipo;
import javafx.beans.property.ObjectProperty;
import javafx.scene.control.SingleSelectionModel;

public class TiposRepository {
    private List<Tipo> tipos;
    private TipoDAO dao;

    public TiposRepository(TipoDAO dao){
        this.dao = dao;

    }

    public List<Tipo> getTipos(){
        tipos = dao.getAll();
        return Collections.unmodifiableList(tipos);

    }

    public Tipo buscaTipo(ObjectProperty<SingleSelectionModel<String>> spTipo){
        Optional<Tipo> busca = tipos.stream().filter((cli)->cli.getNome().equals(spTipo.getValue().getSelectedItem())).findFirst();
        if(busca.isPresent()){
            Tipo t = busca.get();
            return t;

        } else {
            return null;

        }

    }

    public Tipo buscaTipoId(Cliente cliente){
        Optional<Tipo> busca = tipos.stream().filter((cli)->cli.getId() == cliente.getIdTipo()).findFirst();
        
        if(busca.isPresent()){
            Tipo t = busca.get();
            return t;

        } else {
            return null;

        }

    }

    public Tipo buscaTipoId(Imovel imovel){
        Optional<Tipo> busca = tipos.stream().filter((cli)->cli.getId() == imovel.getIdTipo()).findFirst();
        
        if(busca.isPresent()){
            Tipo t = busca.get();
            return t;

        } else {
            return null;

        }

    }
    
}

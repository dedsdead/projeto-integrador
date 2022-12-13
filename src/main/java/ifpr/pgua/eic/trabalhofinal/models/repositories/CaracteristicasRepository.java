package ifpr.pgua.eic.trabalhofinal.models.repositories;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import ifpr.pgua.eic.trabalhofinal.models.daos.CaracteristicaDAO;
import ifpr.pgua.eic.trabalhofinal.models.entities.Caracteristica;
import ifpr.pgua.eic.trabalhofinal.models.entities.Cliente;
import ifpr.pgua.eic.trabalhofinal.models.entities.Imovel;
import javafx.beans.property.ObjectProperty;
import javafx.scene.control.SingleSelectionModel;

public class CaracteristicasRepository {
    private List<Caracteristica> caracteristicas;
    private CaracteristicaDAO dao;

    public CaracteristicasRepository(CaracteristicaDAO dao){
        this.dao = dao;

    }

    public List<Caracteristica> getCaracteristicas(){
        caracteristicas = dao.getAll();
        return Collections.unmodifiableList(caracteristicas);

    }

    public Caracteristica buscaCaracteristica(ObjectProperty<SingleSelectionModel<String>> spCaracteristica){
        Optional<Caracteristica> busca = caracteristicas.stream().filter((cli)->(cli.getQuantidade()+" "+cli.getDescricao()).equals(spCaracteristica.getValue().getSelectedItem())).findFirst();
        if(busca.isPresent()){
            Caracteristica c = busca.get();
            return c;

        } else {
            return null;

        }

    }

    public Caracteristica buscaCaracteristicaId(Cliente cliente){
        Optional<Caracteristica> busca = caracteristicas.stream().filter((cli)->cli.getId() == cliente.getIdCaracteristica()).findFirst();
        
        if(busca.isPresent()){
            Caracteristica c = busca.get();
            return c;

        } else {
            return null;

        }

    }

    public Caracteristica buscaCaracteristicaId(Imovel imovel){
        Optional<Caracteristica> busca = caracteristicas.stream().filter((cli)->cli.getId() == imovel.getIdCaracteristica()).findFirst();
        
        if(busca.isPresent()){
            Caracteristica c = busca.get();
            return c;

        } else {
            return null;

        }

    }

}

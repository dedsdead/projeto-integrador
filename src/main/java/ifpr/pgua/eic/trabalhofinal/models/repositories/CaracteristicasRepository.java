package ifpr.pgua.eic.trabalhofinal.models.repositories;

import java.util.Collections;
import java.util.List;

import ifpr.pgua.eic.trabalhofinal.models.daos.CaracteristicaDAO;
import ifpr.pgua.eic.trabalhofinal.models.entities.Caracteristica;

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
}

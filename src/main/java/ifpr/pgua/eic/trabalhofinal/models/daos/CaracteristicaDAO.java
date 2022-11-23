package ifpr.pgua.eic.trabalhofinal.models.daos;

import java.util.List;

import ifpr.pgua.eic.trabalhofinal.models.entities.Caracteristica;

public interface CaracteristicaDAO {
    List<Caracteristica> getAll();
    
}

package ifpr.pgua.eic.trabalhofinal.models.daos;

import java.util.List;

import ifpr.pgua.eic.trabalhofinal.models.entities.Tipo;

public interface TipoDAO {
    List<Tipo> getAll();
    
}

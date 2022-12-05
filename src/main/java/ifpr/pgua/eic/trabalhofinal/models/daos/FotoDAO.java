package ifpr.pgua.eic.trabalhofinal.models.daos;

import java.util.List;

import ifpr.pgua.eic.trabalhofinal.models.entities.Foto;
import ifpr.pgua.eic.trabalhofinal.models.results.Result;

public interface FotoDAO {
    Result create(Foto foto);
    List<Foto> getAll();
    
}

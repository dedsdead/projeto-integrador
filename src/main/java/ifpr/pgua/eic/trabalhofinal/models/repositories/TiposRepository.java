package ifpr.pgua.eic.trabalhofinal.models.repositories;

import java.util.Collections;
import java.util.List;

import ifpr.pgua.eic.trabalhofinal.models.daos.TipoDAO;
import ifpr.pgua.eic.trabalhofinal.models.entities.Tipo;

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
}

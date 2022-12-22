package ifpr.pgua.eic.trabalhofinal.models.daos;

import java.util.List;

import ifpr.pgua.eic.trabalhofinal.models.entities.Venda;
import ifpr.pgua.eic.trabalhofinal.models.results.Result;

public interface VendaDAO {
    Result create(Venda venda);
    Result update(Venda venda);
    Result delete(int id);
    Venda getById(int id);
    List<Venda> getAll();
    
}

package ifpr.pgua.eic.trabalhofinal.models.daos;

import java.util.List;

import ifpr.pgua.eic.trabalhofinal.models.entities.Imovel;
import ifpr.pgua.eic.trabalhofinal.models.results.Result;

public interface ImovelDAO {
    Result create(Imovel imovel);
    Result update(int id, Imovel imovel);
    Result delete(int id);
    Imovel getById(int id);
    List<Imovel> getAll();
    
}

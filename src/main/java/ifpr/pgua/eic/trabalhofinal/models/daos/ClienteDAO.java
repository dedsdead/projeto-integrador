package ifpr.pgua.eic.trabalhofinal.models.daos;

import java.util.List;

import ifpr.pgua.eic.trabalhofinal.models.entities.Cliente;
import ifpr.pgua.eic.trabalhofinal.models.results.Result;

public interface ClienteDAO {
    Result create(Cliente cliente);
    Result update(int id, Cliente cliente);
    Result delete(int id);
    Cliente getById(int id);
    List<Cliente> getAll();
    
}

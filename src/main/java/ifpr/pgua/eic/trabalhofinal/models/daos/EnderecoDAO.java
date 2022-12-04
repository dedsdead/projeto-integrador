package ifpr.pgua.eic.trabalhofinal.models.daos;

import java.util.List;

import ifpr.pgua.eic.trabalhofinal.models.entities.Endereco;
import ifpr.pgua.eic.trabalhofinal.models.results.Result;

public interface EnderecoDAO {
    Result create(Endereco endereco);
    List<Endereco> getAll();

}

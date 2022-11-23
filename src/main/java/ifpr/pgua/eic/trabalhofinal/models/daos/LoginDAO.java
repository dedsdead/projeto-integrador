package ifpr.pgua.eic.trabalhofinal.models.daos;

import ifpr.pgua.eic.trabalhofinal.models.results.Result;

public interface LoginDAO {
    String verify(String email);
    
}

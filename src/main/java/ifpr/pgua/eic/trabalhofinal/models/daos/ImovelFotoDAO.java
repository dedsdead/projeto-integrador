package ifpr.pgua.eic.trabalhofinal.models.daos;

import java.util.ArrayList;

import ifpr.pgua.eic.trabalhofinal.models.results.Result;

public interface ImovelFotoDAO {
    Result create(int idImovel, int idFoto);
    void delete(int idImovel);
    ArrayList<Integer> getPhotos(int id);

}
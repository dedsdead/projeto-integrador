package ifpr.pgua.eic.trabalhofinal.models.repositories;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import ifpr.pgua.eic.trabalhofinal.models.daos.FotoDAO;
import ifpr.pgua.eic.trabalhofinal.models.daos.ImovelFotoDAO;
import ifpr.pgua.eic.trabalhofinal.models.entities.Foto;
import ifpr.pgua.eic.trabalhofinal.models.results.Result;
import ifpr.pgua.eic.trabalhofinal.models.results.SuccessResult;

public class FotosRepository {
    private List<Foto> fotos;
    private FotoDAO dao;
    private ImovelFotoDAO ifDao;

    public FotosRepository(FotoDAO dao, ImovelFotoDAO ifDao){
        this.dao = dao;
        this.ifDao = ifDao;

        getFotos();
        
    }

    public Result adicionarFoto(Foto foto){
        Optional<Foto> busca = fotos.stream().filter((cli)->cli.getCaminho().equals(foto.getCaminho())).findFirst();

        if(busca.isPresent()){
            foto.setId(busca.get().getId());

            return Result.success("Foto encontrada com sucesso!");

        }

        return dao.create(foto);

    }

    public Result adicionarImovelFoto(int id, ArrayList<Integer> idsFotos){
        int i = 0;
        Result result;

        for (int idFoto : idsFotos) {
            result = ifDao.create(id, idFoto);

            if(result instanceof SuccessResult)
                i++;
        }

        if (i > 0) {
            return Result.success(i+"Foto(s) cadastrada(s) com sucesso!");

        } else {
            return Result.fail("Erro ao cadastrar as fotos!");

        }

    }

    public List<Foto> getFotos(){
        fotos = dao.getAll();

        return Collections.unmodifiableList(fotos);
        
    }

}

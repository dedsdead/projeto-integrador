package ifpr.pgua.eic.trabalhofinal.models.repositories;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import ifpr.pgua.eic.trabalhofinal.models.daos.FotoDAO;
import ifpr.pgua.eic.trabalhofinal.models.entities.Foto;
import ifpr.pgua.eic.trabalhofinal.models.entities.Imovel;
import ifpr.pgua.eic.trabalhofinal.models.results.Result;

public class FotosRepository {
    private List<Foto> fotos;
    private FotoDAO dao;

    public FotosRepository(FotoDAO dao){
        this.dao = dao;

    }

    public Result adicionarFoto(Foto foto){
        Optional<Foto> busca = fotos.stream().filter((cli)->cli.getCaminho().equals(foto.getCaminho())).findFirst();

        if(busca.isPresent()){
            foto.setId(busca.get().getId());

            return Result.success("Foto encontrada com sucesso!");

        }

        return dao.create(foto);

    }

    public List<Foto> getFotos(){
        fotos = dao.getAll();

        return Collections.unmodifiableList(fotos);
        
    }

    public Foto buscaFotoId(Imovel imovel){
        Optional<Foto> busca = fotos.stream().filter((cli)->cli.getId() == imovel.getIdFoto()).findFirst();
        if(busca.isPresent()){
            Foto f = busca.get();
            return f;

        } else {
            return null;

        }

    }

}

package ifpr.pgua.eic.trabalhofinal.models.repositories;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import ifpr.pgua.eic.trabalhofinal.models.daos.ImovelDAO;
import ifpr.pgua.eic.trabalhofinal.models.entities.Imovel;
import ifpr.pgua.eic.trabalhofinal.models.results.Result;

public class ImoveisRepository {
    private List<Imovel> imoveis;
    private ImovelDAO dao;

    public ImoveisRepository(ImovelDAO dao){
        this.dao = dao;

    }

    public Result adicionarImovel(int idFoto,
                                  int idTipo,
                                  int idCaracteristica,
                                  int idEndereco,
                                  int idProprietario,
                                  String descricao,
                                  double metragem,
                                  double valor,
                                  String matricula){
        if(idEndereco == 0) return Result.fail("Adicione um enderço!");
        
        Optional<Imovel> busca = imoveis.stream().filter((cli)->cli.getIdTipo() == idTipo).filter((cli)->cli.getIdProprietario() == idProprietario).filter((cli)->cli.getDescricao().equals(descricao)).findFirst();

        if(busca.isPresent()){
            return Result.fail("Imóvel já cadastrado!");
        }

        Imovel imovel = new Imovel(idFoto, idTipo, idCaracteristica, idEndereco, idProprietario, descricao, metragem, valor, matricula);
        
        return dao.create(imovel);

    }

    public Result atualizarImovel(int idFoto,
                                  int idTipo,
                                  int idCaracteristica,
                                  int idProprietario,
                                  String descricao,
                                  double metragem,
                                  double valor,
                                  String matricula){
        Optional<Imovel> busca = imoveis.stream().filter((cli)->cli.getDescricao().equals(descricao)).findFirst();

        if(busca.isPresent()){
            Imovel imovel = busca.get();
            int id = imovel.getId();

            if(idFoto != 0) imovel.setIdFoto(idFoto);
            if(idTipo != 0) imovel.setIdTipo(idTipo);
            if(idCaracteristica != 0) imovel.setIdCaracteristica(idCaracteristica);
            if(idProprietario != 0) imovel.setIdProprietario(idProprietario);
            if(descricao != "")imovel.setDescricao(descricao);
            if(metragem != 0.0)imovel.setMetragem(metragem);
            if(valor != 0.0)imovel.setValor(valor);
            if(matricula != "")imovel.setMatricula(matricula);

            return dao.update(id, imovel);

        }

        return Result.fail("Imóvel não encontrado!");

    }

    public Result excluirImovel(String descricao){
        Optional<Imovel> busca = imoveis.stream().filter((cli)->cli.getDescricao().equals(descricao)).findFirst();
        
        if(busca.isPresent()){
            Imovel imovel = busca.get();
            int id = imovel.getId();

            return dao.delete(id);

        }

        return Result.fail("Imovel não encontrado!");

    }

    public Imovel getImovel(int id){
        return dao.getById(id);

    }
        
    public List<Imovel> getImoveis(){
        imoveis = dao.getAll();
        return Collections.unmodifiableList(imoveis);

    }

}

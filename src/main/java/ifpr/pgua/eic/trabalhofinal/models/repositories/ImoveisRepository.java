package ifpr.pgua.eic.trabalhofinal.models.repositories;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import ifpr.pgua.eic.trabalhofinal.models.daos.ImovelDAO;
import ifpr.pgua.eic.trabalhofinal.models.entities.Imovel;
import ifpr.pgua.eic.trabalhofinal.models.entities.Venda;
import ifpr.pgua.eic.trabalhofinal.models.results.Result;
import javafx.beans.property.ObjectProperty;
import javafx.scene.control.SingleSelectionModel;

public class ImoveisRepository {
    private List<Imovel> imoveis;
    private ImovelDAO dao;

    public ImoveisRepository(ImovelDAO dao){
        this.dao = dao;

    }

    public Result adicionarImovel(Imovel imovel){
        if(imovel.getIdTipo() == 0) return Result.fail("Adicione um tipo!");
        if(imovel.getIdProprietario() == 0) return Result.fail("Adicione um proprietário!");
        if(imovel.getIdEndereco() == 0) return Result.fail("Adicione um endereço!");
        
        Optional<Imovel> busca = imoveis.stream().filter((im)->im.getIdTipo() == imovel.getIdTipo()).filter((im)->im.getIdProprietario() == imovel.getIdProprietario()).filter((im)->im.getDescricao().equals(imovel.getDescricao())).findFirst();

        if(busca.isPresent()){
            return Result.fail("Imóvel já cadastrado!");

        }

        return dao.create(imovel);

    }

    public Result atualizarImovel(Imovel imovel){
        return dao.update(imovel);

    }

    public Result excluirImovel(int id){
        return dao.delete(id);

    }

    public Imovel getImovel(int id){
        return dao.getById(id);

    }
        
    public List<Imovel> getImoveis(){
        imoveis = dao.getAll();
        return Collections.unmodifiableList(imoveis);

    }

    public Imovel buscaImovel(ObjectProperty<SingleSelectionModel<String>> spImovel){
        Optional<Imovel> busca = imoveis.stream().filter((im)->im.getDescricao().equals(spImovel.getValue().getSelectedItem())).findFirst();
        if(busca.isPresent()){
            Imovel i = busca.get();
            return i;

        } else {
            return null;

        }

    }

    public Imovel buscaImovelId(Venda venda){
        Optional<Imovel> busca = imoveis.stream().filter((im)->im.getId() == venda.getIdImovel()).findFirst();
        
        if(busca.isPresent()){
            Imovel imovel = busca.get();
            return imovel;

        } else {
            return null;

        }

    }

}

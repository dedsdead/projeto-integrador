package ifpr.pgua.eic.trabalhofinal.models.repositories;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public Imovel buscaImovelProperty(ObjectProperty<SingleSelectionModel<String>> spImovel){
        Optional<Imovel> busca = imoveis.stream().filter((im)->im.getDescricao().equals(spImovel.getValue().getSelectedItem())).findFirst();
        if(busca.isPresent()){
            Imovel i = busca.get();
            return i;

        } else {
            return null;

        }

    }

    public Imovel buscaImovelVenda(Venda venda){
        Optional<Imovel> busca = imoveis.stream().filter((im)->im.getId() == venda.getIdImovel()).findFirst();
        
        if(busca.isPresent()){
            Imovel imovel = busca.get();
            return imovel;

        } else {
            return null;

        }

    }

    public int buscaIdImovel(Imovel imovel){
        Optional<Imovel> busca = imoveis.stream().filter((im)->im.getDescricao().equals(imovel.getDescricao())).filter((im)->im.getIdProprietario() == imovel.getIdProprietario()).findFirst();
        
        if(busca.isPresent()){
            Imovel i = busca.get();
            return i.getId();

        } else {
            return 0;

        }

    }

    public Imovel buscaImovelDescricao(String descricao){
        Optional<Imovel> busca = imoveis.stream().filter((im)->im.getDescricao().equals(descricao)).findFirst();
        
        if(busca.isPresent()){
            Imovel imovel = busca.get();
            return imovel;

        } else {
            return null;

        }

    }

    public List<Imovel> buscaImovelTipo(int idTipo){
        List<Imovel> busca = imoveis.stream().filter((im)->im.getDataVenda() == null).filter((im)->im.getIdTipo() == idTipo).collect(Collectors.toList());
        
        if(!busca.isEmpty()){
            return busca;

        } else {
            return null;

        }

    }

    public List<Imovel> buscaImovelCaracteristica(int idCaracteristica){
        List<Imovel> busca = imoveis.stream().filter((im)->im.getDataExclusao() == null).filter((im)->im.getDataVenda() == null).filter((im)->im.getIdCaracteristica() == idCaracteristica).collect(Collectors.toList());
        
        if(!busca.isEmpty()){
            return busca;

        } else {
            return null;

        }

    }

    public List<Imovel> buscaImovelTipoCaracteristica(int idTipo, int idCaracteristica){
        List<Imovel> busca = imoveis.stream().filter((im)->im.getDataExclusao() == null).filter((im)->im.getDataVenda() == null).filter((im)->im.getIdTipo() == idTipo).filter((im)->im.getIdCaracteristica() == idCaracteristica).collect(Collectors.toList());
        
        if(!busca.isEmpty()){
            return busca;

        } else {
            return null;

        }

    }

}

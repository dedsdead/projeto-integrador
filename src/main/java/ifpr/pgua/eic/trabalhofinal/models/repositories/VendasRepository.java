package ifpr.pgua.eic.trabalhofinal.models.repositories;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import ifpr.pgua.eic.trabalhofinal.models.daos.VendaDAO;
import ifpr.pgua.eic.trabalhofinal.models.entities.Venda;
import ifpr.pgua.eic.trabalhofinal.models.results.Result;

public class VendasRepository {
    private List<Venda> vendas;
    private VendaDAO dao;

    public VendasRepository(VendaDAO dao){
        this.dao = dao;

    }

    public Result adicionarVenda(Venda venda){
        Optional<Venda> busca = vendas.stream().filter(ven -> ven.getIdImovel() == venda.getIdImovel()).filter(ven -> ven.getIdComprador() == venda.getIdComprador()).findFirst();
        
        if(busca.isPresent()){
            return Result.fail("Venda já cadastrada!");

        }

        return dao.create(venda);

    }

    public Result atualizarVenda(Venda venda){
        return dao.update(venda);
        
    }

    public Result excluirVenda(int id){
        return dao.delete(id);

    }

    public Venda getVenda(int id){
        return dao.getById(id);

    }

    public List<Venda> getVendas(){
        vendas = dao.getAll();
        return Collections.unmodifiableList(vendas);

    }

}

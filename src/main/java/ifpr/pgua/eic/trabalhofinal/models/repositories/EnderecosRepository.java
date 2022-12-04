package ifpr.pgua.eic.trabalhofinal.models.repositories;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

import ifpr.pgua.eic.trabalhofinal.models.daos.EnderecoDAO;
import ifpr.pgua.eic.trabalhofinal.models.entities.Endereco;
import ifpr.pgua.eic.trabalhofinal.models.results.Result;

public class EnderecosRepository {
    private List<Endereco> enderecos;
    private EnderecoDAO dao;

    public EnderecosRepository(EnderecoDAO dao){
        this.dao = dao;

    }

    public Result adicionarEndereco(Endereco endereco){
        Optional<Endereco> busca = enderecos.stream().filter((cli)->cli.getLogradouro().equals(endereco.getLogradouro())).filter((cli)->cli.getNumero() == endereco.getNumero()).findFirst();
        
        if(busca.isPresent()){
            endereco.setId(busca.get().getId());
            return Result.success("Endereço encontrado com sucesso!");

        }

        return dao.create(endereco);

    }

    public List<Endereco> getEnderecos(){
        enderecos = dao.getAll();
        return Collections.unmodifiableList(enderecos);

    }
    
}

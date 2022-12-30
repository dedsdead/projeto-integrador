package ifpr.pgua.eic.trabalhofinal.models.repositories;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import ifpr.pgua.eic.trabalhofinal.models.daos.EnderecoDAO;
import ifpr.pgua.eic.trabalhofinal.models.entities.Endereco;
import ifpr.pgua.eic.trabalhofinal.models.results.Result;

public class EnderecosRepository {
    private List<Endereco> enderecos;
    private EnderecoDAO dao;

    public EnderecosRepository(EnderecoDAO dao){
        this.dao = dao;

    }

    public Endereco getEnderecoFromAPI(String cep){
        try {
            if(cep.length() == 8){
                HttpResponse<JsonNode> apiResponse = Unirest.get("https://viacep.com.br/ws/"+cep+"/json/").asJson();
                Endereco endereco = new Gson().fromJson(apiResponse.getBody().toString(), Endereco.class);
                return endereco;

            }

        } catch (UnirestException e) {
            e.printStackTrace();

        }

        return null;
    }

    public Result adicionarEndereco(Endereco endereco){
        Optional<Endereco> busca = enderecos.stream().filter((cli)->cli.getCep().equals(endereco.getCep())).filter((cli)->cli.getLogradouro().equals(endereco.getLogradouro())).filter((cli)->cli.getNumero() == endereco.getNumero()).findFirst();
        
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

    public Endereco buscaEnderecoId(int id){
        Optional<Endereco> busca = enderecos.stream().filter((end)->end.getId() == id).findFirst();

        if(busca.isPresent()){
            Endereco e = busca.get();
            return e;

        } else {
            return null;

        }

    }
    
}

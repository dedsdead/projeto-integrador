package ifpr.pgua.eic.trabalhofinal.models.entities;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class GetEndereco {
    public static Endereco getEnderecoFromAPI(String cep){
        try {
            HttpResponse<JsonNode> apiResponse = Unirest.get("https://viacep.com.br/ws/"+cep+"/json/").asJson();
            Endereco endereco = new Gson().fromJson(apiResponse.getBody().toString(), Endereco.class);

            return endereco;

        } catch (UnirestException e) {
            e.printStackTrace();

        }

        return null;
    }
}

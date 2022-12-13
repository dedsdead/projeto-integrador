package ifpr.pgua.eic.trabalhofinal.models.repositories;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import ifpr.pgua.eic.trabalhofinal.models.daos.ClienteDAO;
import ifpr.pgua.eic.trabalhofinal.models.entities.Cliente;
import ifpr.pgua.eic.trabalhofinal.models.entities.Imovel;
import ifpr.pgua.eic.trabalhofinal.models.results.Result;
import javafx.beans.property.ObjectProperty;
import javafx.scene.control.SingleSelectionModel;

public class ClientesRepository {
    private List<Cliente> clientes;
    private ClienteDAO dao;

    public ClientesRepository(ClienteDAO dao){
        this.dao = dao;

    }

    public Result adicionarCliente(int idEndereco, int idTipo, int idCaracteristica, String nome, String telefone, String cpf, String email){
        if(idEndereco == 0) return Result.fail("Adicione um endereço!");
        
        Optional<Cliente> busca = clientes.stream().filter((cli)->cli.getEmail().equals(email)).findFirst();
        
        if(busca.isPresent()){
            return Result.fail("Cliente já cadastrado!");
        }

        Cliente cliente = new Cliente(idEndereco, idTipo, idCaracteristica, nome, telefone, cpf, email);
        
        return dao.create(cliente);

    }

    public Result atualizarCliente(int idEndereco, int idTipo, int idCaracteristica, String nome, String telefone, String cpf, String email){
        Optional<Cliente> busca = clientes.stream().filter((cli)->cli.getEmail().equals(email)).findFirst();
        
        if(busca.isPresent()){
            Cliente cliente = busca.get();
            int id = cliente.getId();

            if(idEndereco != 0) cliente.setIdEndereco(idEndereco);
            if(idTipo != 0) cliente.setIdTipo(idTipo);
            if(idCaracteristica != 0) cliente.setIdCaracteristica(idCaracteristica);
            if(nome != "")cliente.setNome(nome);
            if(telefone != "")cliente.setTelefone(telefone);
            if(cpf != "")cliente.setCpf(cpf);

            return dao.update(id, cliente);

        }

        return Result.fail("Cliente não encontrado!");

    }

    public Result desativarCliente(String email){
        Optional<Cliente> busca = clientes.stream().filter((cli)->cli.getEmail().equals(email)).findFirst();
        
        if(busca.isPresent()){
            Cliente cliente = busca.get();
            int id = cliente.getId();

            return dao.delete(id);

        }

        return Result.fail("Cliente não encontrado!");

    }

    public Cliente getCliente(int id){
        return dao.getById(id);

    }
        
    public List<Cliente> getClientes(){
        clientes = dao.getAll();
        return Collections.unmodifiableList(clientes);

    }

    public Cliente buscaCliente(ObjectProperty<SingleSelectionModel<String>> spCliente){
        Optional<Cliente> busca = clientes.stream().filter((cli)->cli.getNome().equals(spCliente.getValue().getSelectedItem())).findFirst();
        if(busca.isPresent()){
            Cliente c = busca.get();
            return c;

        } else {
            return null;

        }

    }

    public Cliente buscaClienteId(Imovel imovel){
        Optional<Cliente> busca = clientes.stream().filter((cli)->cli.getId() == imovel.getIdProprietario()).findFirst();
        
        if(busca.isPresent()){
            Cliente c = busca.get();
            return c;

        } else {
            return null;

        }

    }

}

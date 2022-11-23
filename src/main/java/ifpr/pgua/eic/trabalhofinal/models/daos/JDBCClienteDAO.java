package ifpr.pgua.eic.trabalhofinal.models.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ifpr.pgua.eic.trabalhofinal.models.FabricaConexoes;
import ifpr.pgua.eic.trabalhofinal.models.entities.Cliente;
import ifpr.pgua.eic.trabalhofinal.models.results.Result;

public class JDBCClienteDAO implements ClienteDAO{
    private static final String INSERT = "INSERT INTO TF_Cliente(codigo_endereco,codigo_tipo,codigo_caracteristica,nome,telefone,cpf,email,ativo) VALUES (?,?,?,?,?,?,?,?)";
    private static final String UPDATE = "UPDATE TF_Cliente set nome=?, telefone=?, cpf=? WHERE id=?";
    private static final String DELETE = "UPDATE TF_Cliente set ativo=0 WHERE id=?";
    private static final String SELECT_ALL = "SELECT * FROM TF_Cliente";
    private static final String SELECT_ID = "SELECT * FROM TF_Cliente WHERE id=?";

    private FabricaConexoes fabricaConexoes;


    public JDBCClienteDAO(FabricaConexoes fabricaConexoes){
        this.fabricaConexoes = fabricaConexoes;

    }

    @Override
    public Result create(Cliente cliente) {
        try {
            Connection con = fabricaConexoes.getConnection();

            PreparedStatement pstm = con.prepareStatement(INSERT);

            pstm.setInt(1, cliente.getIdEndereco());
            pstm.setInt(2, cliente.getIdTipo());
            pstm.setInt(3, cliente.getIdCaracteristica());
            pstm.setString(4, cliente.getNome());
            pstm.setString(5, cliente.getTelefone());
            pstm.setString(6, cliente.getCpf());
            pstm.setString(7, cliente.getEmail());
            pstm.setBoolean(8, cliente.isAtivo());

            pstm.execute();

            pstm.close();
            con.close();

            return Result.success("Cliente cadastrado com sucesso!");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return Result.fail(e.getMessage());
        }
    }

    @Override
    public Result update(int id, Cliente cliente) {
        try{
            Connection con = fabricaConexoes.getConnection(); 
            
            PreparedStatement pstm = con.prepareStatement(UPDATE);
            
            pstm.setString(1, cliente.getNome());
            pstm.setString(2, cliente.getTelefone());
            pstm.setString(3, cliente.getCpf());
            pstm.setInt(4, id);

            pstm.execute();

            pstm.close();
            con.close();

            return Result.success("Cliente atualizado com sucesso!");

        }catch(SQLException e){
            System.out.println(e.getMessage());
            return Result.fail(e.getMessage());
        }
    }

    @Override
    public Result delete(int id) {
        try{
            Connection con = fabricaConexoes.getConnection(); 
            
            PreparedStatement pstm = con.prepareStatement(DELETE);
            
            pstm.setInt(1, id);

            pstm.execute();

            pstm.close();
            con.close();

            return Result.success("Cliente desativado com sucesso!");

        }catch(SQLException e){
            System.out.println(e.getMessage());
            return Result.fail(e.getMessage());

        }
    }

    @Override
    public Cliente getById(int id) {
        try{
            Connection con = fabricaConexoes.getConnection(); 
            
            PreparedStatement pstm = con.prepareStatement(SELECT_ID);

            pstm.setInt(1, id);

            ResultSet rsc = pstm.executeQuery();

            rsc.next();

            int idEndereco = rsc.getInt("codigo_endereco");
            String nome = rsc.getString("nome");
            String telefone = rsc.getString("telefone");
            String cpf = rsc.getString("cpf");
            String email = rsc.getString("email");
            boolean ativo = rsc.getBoolean("ativo");

            Cliente c = new Cliente(id, idEndereco, nome, telefone, cpf, email, ativo);

            rsc.close();
            pstm.close();
            con.close();

            return c;

        }catch(SQLException e){
            System.out.println(e.getMessage());
            return null;

        }
    }

    @Override
    public List<Cliente> getAll() {
        ArrayList<Cliente> clientes = new ArrayList<>();
        try{
            Connection con = fabricaConexoes.getConnection(); 
            
            PreparedStatement pstm = con.prepareStatement(SELECT_ALL);

            ResultSet rs = pstm.executeQuery();
            
            while(rs.next()){
                int id = rs.getInt("codigo");
                int idEndereco = rs.getInt("codigo_endereco");
                String nome = rs.getString("nome");
                String telefone = rs.getString("telefone");
                String cpf = rs.getString("cpf");
                String email = rs.getString("email");
                boolean ativo = rs.getBoolean("ativo");

                Cliente c = new Cliente(id, idEndereco, nome, telefone, cpf, email, ativo);
                clientes.add(c);
                
            }

            rs.close();
            pstm.close();
            con.close();
            
            return clientes;

        }catch(SQLException e){
            System.out.println(e.getMessage());
            return null;

        }
    }
    
}

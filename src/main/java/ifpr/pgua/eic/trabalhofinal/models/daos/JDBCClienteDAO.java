package ifpr.pgua.eic.trabalhofinal.models.daos;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import ifpr.pgua.eic.trabalhofinal.models.FabricaConexoes;
import ifpr.pgua.eic.trabalhofinal.models.entities.Cliente;
import ifpr.pgua.eic.trabalhofinal.models.results.Result;

public class JDBCClienteDAO implements ClienteDAO{
    private static final String INSERT = "INSERT INTO TF_Cliente(codigo_endereco,codigo_tipo,codigo_caracteristica,nome,telefone,cpf,email,ativo) VALUES (?,?,?,?,?,?,?,1)";
    private static final String UPDATE = "UPDATE TF_Cliente set codigo_endereco=?, codigo_tipo=?, codigo_caracteristica=?, nome=?, telefone=?, cpf=? WHERE codigo=?";
    private static final String DELETE = "UPDATE TF_Cliente set ativo=0 WHERE codigo=?";
    private static final String SELECT_ALL = "SELECT * FROM TF_Cliente";
    private static final String SELECT_ID = "SELECT * FROM TF_Cliente WHERE codigo=?";
    private static final String CALL_CPF = "{? = call TF_Validar_cpf(?)}";
    private static final String CALL_REGEX = "{? = call TF_Regex_email(?)}";

    private FabricaConexoes fabricaConexoes;


    public JDBCClienteDAO(FabricaConexoes fabricaConexoes){
        this.fabricaConexoes = fabricaConexoes;

    }

    @Override
    public Result create(Cliente cliente) {
        try {
            if(mysqlValidaCpf(cliente.getCpf()) == false) return Result.fail("Insira um cpf válido!");
            if(mysqlRegexEmail(cliente.getEmail()) == false) return Result.fail("Insira um email válido!");

            Connection con = fabricaConexoes.getConnection();

            PreparedStatement pstm = con.prepareStatement(INSERT);

            // ALTERAR QUANDO FOR ADICIONAR ENDEREÇO !!!!!!!!!!!!!!!!!!!!!!!!
            // ALTERAR QUANDO FOR ADICIONAR ENDEREÇO !!!!!!!!!!!!!!!!!!!!!!!!
            // ALTERAR QUANDO FOR ADICIONAR ENDEREÇO !!!!!!!!!!!!!!!!!!!!!!!!
            int endereco = cliente.getIdEndereco();
            
            if(endereco == 0){
                endereco=1;
            }

            pstm.setInt(1, endereco);

            if(cliente.getIdTipo() == 0){
                pstm.setNull(2, 1);
            } else {
                pstm.setInt(2, cliente.getIdTipo());
            }

            if(cliente.getIdCaracteristica() == 0){
                pstm.setNull(3, 1);
            } else {
                pstm.setInt(3, cliente.getIdCaracteristica());
            }
            
            pstm.setString(4, cliente.getNome());
            pstm.setString(5, cliente.getTelefone());
            pstm.setString(6, cliente.getCpf());
            pstm.setString(7, cliente.getEmail());

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

            // ALTERAR QUANDO FOR ADICIONAR ENDEREÇO !!!!!!!!!!!!!!!!!!!!!!!!
            // ALTERAR QUANDO FOR ADICIONAR ENDEREÇO !!!!!!!!!!!!!!!!!!!!!!!!
            // ALTERAR QUANDO FOR ADICIONAR ENDEREÇO !!!!!!!!!!!!!!!!!!!!!!!!
            int endereco = cliente.getIdEndereco();
            
            if(endereco == 0){
                endereco=1;
            }

            pstm.setInt(1, endereco);

            if(cliente.getIdTipo() == 0){
                pstm.setNull(2, 1);
            } else {
                pstm.setInt(2, cliente.getIdTipo());
            }

            if(cliente.getIdCaracteristica() == 0){
                pstm.setNull(3, 1);
            } else {
                pstm.setInt(3, cliente.getIdCaracteristica());
            }

            pstm.setString(4, cliente.getNome());
            pstm.setString(5, cliente.getTelefone());
            pstm.setString(6, cliente.getCpf());
            pstm.setInt(7, id);

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
            int idTipo = rsc.getInt("codigo_tipo");
            int idCaracteristica = rsc.getInt("codigo_caracteristica");
            String nome = rsc.getString("nome");
            String telefone = rsc.getString("telefone");
            String cpf = rsc.getString("cpf");
            String email = rsc.getString("email");
            boolean ativo = rsc.getBoolean("ativo");

            Cliente c = new Cliente(id, idEndereco, idTipo, idCaracteristica, nome, telefone, cpf, email, ativo);

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
                int idTipo = rs.getInt("codigo_tipo");
                int idCaracteristica = rs.getInt("codigo_caracteristica");
                String nome = rs.getString("nome");
                String telefone = rs.getString("telefone");
                String cpf = rs.getString("cpf");
                String email = rs.getString("email");
                boolean ativo = rs.getBoolean("ativo");

                Cliente c = new Cliente(id, idEndereco, idTipo, idCaracteristica, nome, telefone, cpf, email, ativo);
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

    public boolean mysqlRegexEmail(String email){
        try {
            Connection con = fabricaConexoes.getConnection();
            CallableStatement cstm = con.prepareCall(CALL_REGEX);
            cstm.registerOutParameter(1, Types.SMALLINT);
            cstm.setString(2, email);
            cstm.execute();

            boolean result = cstm.getBoolean(1);

            cstm.close();
            con.close();

            return result;

        } catch (SQLException e) {
            System.out.println("Erro ao chamar a função mysql");
            return false;

        }
    }

    public boolean mysqlValidaCpf(String cpf){
        try {
            Connection con = fabricaConexoes.getConnection();
            CallableStatement cstm = con.prepareCall(CALL_CPF);
            cstm.registerOutParameter(1, Types.SMALLINT);
            cstm.setString(2, cpf);
            cstm.execute();

            boolean result = cstm.getBoolean(1);

            cstm.close();
            con.close();

            return result;

        } catch (SQLException e) {
            System.out.println("Erro ao chamar a função mysql");
            return false;

        }
    } 
    
}

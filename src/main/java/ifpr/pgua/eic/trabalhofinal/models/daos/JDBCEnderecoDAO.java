package ifpr.pgua.eic.trabalhofinal.models.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import ifpr.pgua.eic.trabalhofinal.models.FabricaConexoes;
import ifpr.pgua.eic.trabalhofinal.models.entities.Endereco;
import ifpr.pgua.eic.trabalhofinal.models.results.Result;

public class JDBCEnderecoDAO implements EnderecoDAO{
    private static final String INSERT = "INSERT INTO Endereco(cep,estado,cidade,logradouro,numero,complemento) VALUES (?,?,?,?,?,?)";
    private static final String SELECT_ALL = "SELECT * FROM Endereco";

    private FabricaConexoes fabricaConexoes;

    public JDBCEnderecoDAO(FabricaConexoes fabricaConexoes){
        this.fabricaConexoes = fabricaConexoes;

    }

    @Override
    public Result create(Endereco endereco) {
        try {
            Connection con = fabricaConexoes.getConnection();

            PreparedStatement pstm = con.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);

            pstm.setString(1, endereco.getCep());
            pstm.setString(2, endereco.getEstado());
            pstm.setString(3, endereco.getCidade());
            pstm.setString(4, endereco.getLogradouro());
            pstm.setInt(5, endereco.getNumero());
            

            if(endereco.getComplemento().equals("")){
                pstm.setNull(6, Types.VARCHAR);
            } else {
                pstm.setString(6, endereco.getComplemento());
            }

            pstm.execute();

            try (ResultSet rs = pstm.getGeneratedKeys()){
                if(rs.next()){
                    endereco.setId(rs.getInt(1));

                }
                rs.close();
                
            }
            
            pstm.close();
            con.close();

            return Result.success("Endereço cadastrado com sucesso!");
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return Result.fail(e.getMessage());

        }
    }

    @Override
    public List<Endereco> getAll() {
        ArrayList<Endereco> enderecos = new ArrayList<>();
        try{
            Connection con = fabricaConexoes.getConnection(); 
            
            PreparedStatement pstm = con.prepareStatement(SELECT_ALL);

            ResultSet rs = pstm.executeQuery();
            
            while(rs.next()){
                int id = rs.getInt("codigo");
                String cep = rs.getString("cep");
                String estado = rs.getString("estado");
                String cidade = rs.getString("cidade");
                String logradouro = rs.getString("logradouro");
                int numero = rs.getInt("numero");
                String complemento = rs.getString("complemento");

                Endereco e = new Endereco(id, cep, estado, cidade, logradouro, numero, complemento);
                enderecos.add(e);
                
            }

            rs.close();
            pstm.close();
            con.close();
            
            return enderecos;

        }catch(SQLException e){
            System.out.println(e.getMessage());
            return null;

        }
    }

    
}

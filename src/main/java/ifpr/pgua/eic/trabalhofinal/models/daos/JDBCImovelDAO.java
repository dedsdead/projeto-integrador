package ifpr.pgua.eic.trabalhofinal.models.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import ifpr.pgua.eic.trabalhofinal.models.FabricaConexoes;
import ifpr.pgua.eic.trabalhofinal.models.entities.Imovel;
import ifpr.pgua.eic.trabalhofinal.models.results.Result;

public class JDBCImovelDAO implements ImovelDAO{
    private static final String INSERT = "INSERT INTO TF_Imovel(codigo_foto,codigo_tipo,codigo_caracteristica,codigo_endereco,codigo_proprietario,descricao,metragem,valor,matricula) VALUES (?,?,?,?,?,?,?,?,?)";
    private static final String UPDATE = "UPDATE TF_Imovel set codigo_foto=?, codigo_tipo=?, codigo_caracteristica=?, codigo_proprietario=?, descricao=?, metragem=?, valor=?, matricula=? WHERE codigo=?";
    private static final String DELETE = "UPDATE TF_Imovel set excluido_em=? WHERE codigo=?";
    private static final String SELECT_ID = "SELECT * FROM TF_Imovel WHERE codigo=?";
    private static final String SELECT_ALL = "SELECT * FROM TF_Imovel";

    private FabricaConexoes fabricaConexoes;

    public JDBCImovelDAO(FabricaConexoes fabricaConexoes) {
        this.fabricaConexoes = fabricaConexoes;

    }

    @Override
    public Result create(Imovel imovel) {
        try {
            Connection con = fabricaConexoes.getConnection();
            
            PreparedStatement pstm = con.prepareStatement(INSERT);

            pstm.setInt(1, imovel.getIdFoto());

            pstm.setInt(2, imovel.getIdTipo());

            if(imovel.getIdCaracteristica() == 0){
                pstm.setNull(3, Types.INTEGER);
            } else {
                pstm.setInt(3, imovel.getIdCaracteristica());
            }
            
            pstm.setInt(4, imovel.getIdEndereco());
            pstm.setInt(5, imovel.getIdProprietario());
            pstm.setString(6, imovel.getDescricao());
            pstm.setDouble(7, imovel.getMetragem());
            pstm.setDouble(8, imovel.getValor());

            if(imovel.getMatricula().equals("")){
                pstm.setNull(9, Types.VARCHAR);
            } else {
                pstm.setString(9, imovel.getMatricula());
            }

            pstm.execute();

            pstm.close();
            con.close();

            return Result.success("Imóvel cadastrado com sucesso!");

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.fail(e.getMessage());
            
        }
        
    }

    @Override
    public Result update(Imovel imovel) {
        try {
            Connection con = fabricaConexoes.getConnection();
            
            PreparedStatement pstm = con.prepareStatement(UPDATE);

            pstm.setInt(1, imovel.getIdFoto());

            pstm.setInt(2, imovel.getIdTipo());

            if(imovel.getIdCaracteristica() == 0){
                pstm.setNull(3, Types.INTEGER);
            } else {
                pstm.setInt(3, imovel.getIdCaracteristica());
            }
            
            pstm.setInt(4, imovel.getIdProprietario());
            pstm.setString(5, imovel.getDescricao());
            pstm.setDouble(6, imovel.getMetragem());
            pstm.setDouble(7, imovel.getValor());
            pstm.setString(8, imovel.getMatricula());
            pstm.setInt(9, imovel.getId());

            pstm.execute();

            pstm.close();
            con.close();

            return Result.success("Imóvel atualizado com sucesso!");

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.fail(e.getMessage());
            
        }
        
    }

    @Override
    public Result delete(int id) {
        try{
            Connection con = fabricaConexoes.getConnection(); 
            
            PreparedStatement pstm = con.prepareStatement(DELETE);

            Timestamp time = new Timestamp((System.currentTimeMillis()+10800000));
            
            pstm.setTimestamp(1, time);
            pstm.setInt(2, id);

            pstm.execute();

            pstm.close();
            con.close();

            return Result.success("Imóvel excluído com sucesso!");

        }catch(SQLException e){
            System.out.println(e.getMessage());
            return Result.fail(e.getMessage());

        }
        
    }

    @Override
    public Imovel getById(int id) {
        try{
            Connection con = fabricaConexoes.getConnection(); 
            
            PreparedStatement pstm = con.prepareStatement(SELECT_ID);

            pstm.setInt(1, id);

            ResultSet rsc = pstm.executeQuery();

            LocalDateTime dataVenda = null;
            LocalDateTime dataExclusao = null;

            rsc.next();

            int idFoto = rsc.getInt("codigo_foto");
            int idTipo = rsc.getInt("codigo_tipo");
            int idCaracteristica = rsc.getInt("codigo_caracteristica");
            int idEndereco = rsc.getInt("codigo_endereco");
            int idProprietario = rsc.getInt("codigo_proprietario");
            String descricao = rsc.getString("descricao");
            double metragem = rsc.getDouble("metragem");
            double valor = rsc.getDouble("valor");
            String matricula = rsc.getString("matricula");
            if(rsc.getTimestamp("vendido_em") != null)
                dataVenda = rsc.getTimestamp("vendido_em").toInstant().atZone(ZoneId.of("UTC")).toLocalDateTime();
            if(rsc.getTimestamp("excluido_em") != null)
                dataExclusao = rsc.getTimestamp("excluido_em").toInstant().atZone(ZoneId.of("UTC")).toLocalDateTime();

            Imovel i = new Imovel(id, idFoto, idTipo, idCaracteristica, idEndereco, idProprietario, descricao, metragem, valor, matricula, dataVenda, dataExclusao);

            rsc.close();
            pstm.close();
            con.close();

            return i;

        }catch(SQLException e){
            System.out.println(e.getMessage());
            return null;

        }
        
    }

    @Override
    public List<Imovel> getAll() {
        ArrayList<Imovel> imoveis = new ArrayList<>();

        try{
            Connection con = fabricaConexoes.getConnection(); 
            
            PreparedStatement pstm = con.prepareStatement(SELECT_ALL);

            ResultSet rs = pstm.executeQuery();

            while(rs.next()){
                int id = rs.getInt("codigo");
                int idFoto = rs.getInt("codigo_foto");
                int idTipo = rs.getInt("codigo_tipo");
                int idCaracteristica = rs.getInt("codigo_caracteristica");
                int idEndereco = rs.getInt("codigo_endereco");
                int idProprietario = rs.getInt("codigo_proprietario");
                String descricao = rs.getString("descricao");
                double metragem = rs.getDouble("metragem");
                double valor = rs.getDouble("valor");
                String matricula = rs.getString("matricula");
                LocalDateTime dataVenda = null;
                LocalDateTime dataExclusao = null;
                
                if(rs.getTimestamp("vendido_em") != null)
                    dataVenda = rs.getTimestamp("vendido_em").toInstant().atZone(ZoneId.of("UTC")).toLocalDateTime();
                if(rs.getTimestamp("excluido_em") != null)
                    dataExclusao = rs.getTimestamp("excluido_em").toInstant().atZone(ZoneId.of("UTC")).toLocalDateTime();
    
                Imovel i = new Imovel(id, idFoto, idTipo, idCaracteristica, idEndereco, idProprietario, descricao, metragem, valor, matricula, dataVenda, dataExclusao);
                imoveis.add(i);

            }

            rs.close();
            pstm.close();
            con.close();

            return imoveis;

        }catch(SQLException e){
            System.out.println(e.getMessage());
            return null;

        }
        
    }

}

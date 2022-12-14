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
import ifpr.pgua.eic.trabalhofinal.models.entities.Foto;
import ifpr.pgua.eic.trabalhofinal.models.entities.Imovel;
import ifpr.pgua.eic.trabalhofinal.models.results.Result;

public class JDBCImovelDAO implements ImovelDAO{
    private static final String INSERT = "INSERT INTO Imovel(codigo_tipo,codigo_caracteristica,codigo_endereco,codigo_proprietario,descricao,metragem,valor,matricula) VALUES (?,?,?,?,?,?,?,?)";
    private static final String UPDATE = "UPDATE Imovel set codigo_tipo=?, codigo_caracteristica=?, codigo_proprietario=?, descricao=?, metragem=?, valor=?, matricula=? WHERE codigo=?";
    private static final String DELETE = "UPDATE Imovel set excluido_em=? WHERE codigo=?";
    private static final String SELECT_ID = "SELECT * FROM Imovel WHERE codigo=?";
    private static final String SELECT_ALL = "SELECT * FROM Imovel";

    private FabricaConexoes fabricaConexoes;
    private JDBCImovelFotoDAO ifDao;
    private JDBCFotoDAO fotoDao;

    public JDBCImovelDAO(FabricaConexoes fabricaConexoes) {
        this.fabricaConexoes = fabricaConexoes;
        this.ifDao = new JDBCImovelFotoDAO(fabricaConexoes);
        this.fotoDao = new JDBCFotoDAO(fabricaConexoes);

    }

    @Override
    public Result create(Imovel imovel) {
        try {
            Connection con = fabricaConexoes.getConnection();
            
            PreparedStatement pstm = con.prepareStatement(INSERT);

            pstm.setInt(1, imovel.getIdTipo());

            if(imovel.getIdCaracteristica() == 0){
                pstm.setNull(2, Types.INTEGER);
            } else {
                pstm.setInt(2, imovel.getIdCaracteristica());
            }
            
            pstm.setInt(3, imovel.getIdEndereco());
            pstm.setInt(4, imovel.getIdProprietario());
            pstm.setString(5, imovel.getDescricao());
            pstm.setDouble(6, imovel.getMetragem());
            pstm.setDouble(7, imovel.getValor());

            if(imovel.getMatricula().equals("")){
                pstm.setNull(8, Types.VARCHAR);
            } else {
                pstm.setString(8, imovel.getMatricula());
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

            pstm.setInt(1, imovel.getIdTipo());

            if(imovel.getIdCaracteristica() == 0){
                pstm.setNull(2, Types.INTEGER);
            } else {
                pstm.setInt(2, imovel.getIdCaracteristica());
            }
            
            pstm.setInt(3, imovel.getIdProprietario());
            pstm.setString(4, imovel.getDescricao());
            pstm.setDouble(5, imovel.getMetragem());
            pstm.setDouble(6, imovel.getValor());
            pstm.setString(7, imovel.getMatricula());
            pstm.setInt(8, imovel.getId());

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

            ArrayList<Integer> idsFotos = ifDao.getPhotos(id);
            ArrayList<Foto> fotos = new ArrayList<>();
            
            for (int idFoto : idsFotos) {
                fotos.add(fotoDao.getPhoto(idFoto));
                
            }

            Imovel i = new Imovel(id, idTipo, idCaracteristica, idEndereco, idProprietario, descricao, metragem, valor, matricula, dataVenda, dataExclusao, fotos);

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
    
                ArrayList<Integer> idsFotos = ifDao.getPhotos(id);
                ArrayList<Foto> fotos = new ArrayList<>();
                
                for (int idFoto : idsFotos) {
                    fotos.add(fotoDao.getPhoto(idFoto));
                    
                }

                Imovel i = new Imovel(id, idTipo, idCaracteristica, idEndereco, idProprietario, descricao, metragem, valor, matricula, dataVenda, dataExclusao, fotos);
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

package ifpr.pgua.eic.trabalhofinal.models.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import ifpr.pgua.eic.trabalhofinal.models.FabricaConexoes;
import ifpr.pgua.eic.trabalhofinal.models.results.Result;

public class JDBCImovelFotoDAO implements ImovelFotoDAO{
    private static final String INSERT = "INSERT INTO Imovel_tem_Foto(codigo_imovel, codigo_foto) VALUES (?,?)";
    private static final String SELECT_PHOTOS = "SELECT * FROM Imovel_tem_Foto WHERE codigo_imovel=?";

    private FabricaConexoes fabricaConexoes;

    public JDBCImovelFotoDAO(FabricaConexoes fabricaConexoes){
        this.fabricaConexoes = fabricaConexoes;
        
    }
    
    @Override
    public Result create(int idImovel, int idFoto) {
        try {
            Connection con = fabricaConexoes.getConnection();

            PreparedStatement pstm = con.prepareStatement(INSERT);

            pstm.setInt(1, idImovel);
            pstm.setInt(1, idFoto);

            pstm.execute();
            
            pstm.close();
            con.close();

            return Result.success("Foto cadastrada com sucesso!");
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return Result.fail(e.getMessage());

        }

    }

    @Override
    public ArrayList<Integer> getPhotos(int id) {
        ArrayList<Integer> idsFotos = new ArrayList<>();
        
        try{
            Connection con = fabricaConexoes.getConnection(); 
            
            PreparedStatement pstm = con.prepareStatement(SELECT_PHOTOS);

            pstm.setInt(1, id);

            ResultSet rs = pstm.executeQuery();
            
            while(rs.next()){
                int idFoto = rs.getInt("codigo_foto");
                idsFotos.add(idFoto);
                
            }

            rs.close();
            pstm.close();
            con.close();
            
            return idsFotos;

        }catch(SQLException e){
            System.out.println(e.getMessage());
            return null;

        }

    }
    
}

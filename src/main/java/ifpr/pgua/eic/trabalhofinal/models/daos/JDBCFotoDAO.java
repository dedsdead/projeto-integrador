package ifpr.pgua.eic.trabalhofinal.models.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ifpr.pgua.eic.trabalhofinal.models.FabricaConexoes;
import ifpr.pgua.eic.trabalhofinal.models.entities.Foto;
import ifpr.pgua.eic.trabalhofinal.models.results.Result;

public class JDBCFotoDAO implements FotoDAO{
    private static final String INSERT = "INSERT INTO Foto(caminho_foto) VALUES (?)";
    private static final String SELECT_ALL = "SELECT * FROM Foto";
    private static final String SELECT_ID = "SELECT * FROM Foto WHERE codigo=?";

    private FabricaConexoes fabricaConexoes;

    public JDBCFotoDAO(FabricaConexoes fabricaConexoes){
        this.fabricaConexoes = fabricaConexoes;

    }
    
    @Override
    public Result create(Foto foto) {
        try {
            Connection con = fabricaConexoes.getConnection();

            PreparedStatement pstm = con.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);

            pstm.setString(1, foto.getCaminho());

            pstm.execute();

            try (ResultSet rs = pstm.getGeneratedKeys()){
                if(rs.next()){
                    foto.setId(rs.getInt(1));

                }
                rs.close();
                
            }
            
            pstm.close();
            con.close();

            return Result.success("Foto cadastrada com sucesso!");
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return Result.fail(e.getMessage());

        }

    }

    @Override
    public List<Foto> getAll() {
        ArrayList<Foto> fotos = new ArrayList<>();
        try{
            Connection con = fabricaConexoes.getConnection(); 
            
            PreparedStatement pstm = con.prepareStatement(SELECT_ALL);

            ResultSet rs = pstm.executeQuery();
            
            while(rs.next()){
                int id = rs.getInt("codigo");
                String caminho = rs.getString("caminho_foto");

                Foto f = new Foto(id, caminho);
                fotos.add(f);
                
            }

            rs.close();
            pstm.close();
            con.close();
            
            return fotos;

        }catch(SQLException e){
            System.out.println(e.getMessage());
            return null;

        }

    }

    @Override
    public Foto getPhoto(int id) {
        try{
            Connection con = fabricaConexoes.getConnection(); 
            
            PreparedStatement pstm = con.prepareStatement(SELECT_ALL);

            pstm.setInt(1, id);

            ResultSet rs = pstm.executeQuery();
            
            rs.next();

            String caminho = rs.getString("caminho_foto");

            Foto f = new Foto(id, caminho);

            rs.close();
            pstm.close();
            con.close();
            
            return f;

        }catch(SQLException e){
            System.out.println(e.getMessage());
            return null;

        }

    }
    
}

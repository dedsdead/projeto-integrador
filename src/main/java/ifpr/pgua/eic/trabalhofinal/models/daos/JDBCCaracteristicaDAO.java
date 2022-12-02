package ifpr.pgua.eic.trabalhofinal.models.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ifpr.pgua.eic.trabalhofinal.models.FabricaConexoes;
import ifpr.pgua.eic.trabalhofinal.models.entities.Caracteristica;

public class JDBCCaracteristicaDAO implements CaracteristicaDAO{
    private static final String SELECT_ALL = "SELECT * FROM TF_Caracteristica";

    private FabricaConexoes fabricaConexoes;

    public JDBCCaracteristicaDAO(FabricaConexoes fabricaConexoes){
        this.fabricaConexoes = fabricaConexoes;

    }

    @Override
    public List<Caracteristica> getAll() {
        ArrayList<Caracteristica> caracteristicas = new ArrayList<>();

        try {
            Connection con = fabricaConexoes.getConnection(); 
            
            PreparedStatement pstm = con.prepareStatement(SELECT_ALL);

            ResultSet rs = pstm.executeQuery();
            
            while(rs.next()){
                int id = rs.getInt("codigo");
                int quantidade = rs.getInt("quantidade");
                String descricao = rs.getString("descricao");

                Caracteristica c = new Caracteristica(id, descricao, quantidade);
                caracteristicas.add(c);
                
            }

            rs.close();
            pstm.close();
            con.close();
            
            return caracteristicas;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;

        }
    }
    
}

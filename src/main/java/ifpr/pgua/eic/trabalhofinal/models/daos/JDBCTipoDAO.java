package ifpr.pgua.eic.trabalhofinal.models.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ifpr.pgua.eic.trabalhofinal.models.FabricaConexoes;
import ifpr.pgua.eic.trabalhofinal.models.entities.Tipo;

public class JDBCTipoDAO implements TipoDAO{
    private static final String SELECT_ALL = "SELECT * FROM TF_Tipo";

    private FabricaConexoes fabricaConexoes;

    public JDBCTipoDAO(FabricaConexoes fabricaConexoes){
        this.fabricaConexoes = fabricaConexoes;

    }

    @Override
    public List<Tipo> getAll() {
        ArrayList<Tipo> tipos = new ArrayList<>();

        try {
            Connection con = fabricaConexoes.getConnection(); 
            
            PreparedStatement pstm = con.prepareStatement(SELECT_ALL);

            ResultSet rs = pstm.executeQuery();
            
            while(rs.next()){
                int id = rs.getInt("codigo");
                String nome = rs.getString("nome");

                Tipo t = new Tipo(id, nome);
                tipos.add(t);
                
            }

            rs.close();
            pstm.close();
            con.close();
            
            return tipos;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;

        }
    }
    
}

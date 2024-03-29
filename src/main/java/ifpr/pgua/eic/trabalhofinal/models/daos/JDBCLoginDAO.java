package ifpr.pgua.eic.trabalhofinal.models.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ifpr.pgua.eic.trabalhofinal.models.FabricaConexoes;

public class JDBCLoginDAO implements LoginDAO{
    private static final String SELECT_EMAIL = "SELECT * FROM Corretor WHERE email=?";

    private FabricaConexoes fabricaConexoes;

    public JDBCLoginDAO(FabricaConexoes fabricaConexoes){
        this.fabricaConexoes = fabricaConexoes;

    }

    @Override
    public String verify(String email) {
        try{
            Connection con = fabricaConexoes.getConnection(); 
            
            PreparedStatement pstm = con.prepareStatement(SELECT_EMAIL);

            pstm.setString(1, email);

            ResultSet rsc = pstm.executeQuery();

            rsc.next();

            String hash = rsc.getString("senha");

            rsc.close();
            pstm.close();
            con.close();

            return hash;

        }catch(SQLException e){
            System.out.println(e.getMessage());
            return null;

        }
    }
    
}

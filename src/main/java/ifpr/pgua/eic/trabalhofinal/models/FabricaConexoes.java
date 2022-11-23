package ifpr.pgua.eic.trabalhofinal.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import ifpr.pgua.eic.trabalhofinal.utils.Env;

public class FabricaConexoes {
    private static final int MAX_CONEXOES=5;

    private static FabricaConexoes instance;

    private Connection[] conexoes;

    private FabricaConexoes(){
        conexoes = new Connection[MAX_CONEXOES];
    }

    public static FabricaConexoes getInstance(){
        if(instance == null){
            instance = new FabricaConexoes();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException{

        String user = Env.get("DB_USER");
        String password = Env.get("DB_PASSWORD");
        String url = Env.get("DB_URL");


        for(int i=0;i<conexoes.length;i++){
            if(conexoes[i]==null || conexoes[i].isClosed()){
                conexoes[i] = DriverManager.getConnection(url, 
                                                          user, 
                                                          password);
                return conexoes[i];
            }
        }
        throw new SQLException("Não há conexões disponíveis! Esqueceu de fechar?");
    }
}

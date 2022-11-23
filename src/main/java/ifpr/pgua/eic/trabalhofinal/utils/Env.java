package ifpr.pgua.eic.trabalhofinal.utils;

import io.github.cdimascio.dotenv.Dotenv;

public class Env {
    
    private static Dotenv dotenv; 
    private Env(){
    }

    public static String get(String key){
        if(dotenv == null){
            dotenv = Dotenv.load();
        }

        return dotenv.get(key);
    }



}

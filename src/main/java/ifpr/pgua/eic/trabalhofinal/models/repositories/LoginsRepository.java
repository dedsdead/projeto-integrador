package ifpr.pgua.eic.trabalhofinal.models.repositories;

import java.security.NoSuchAlgorithmException;

import ifpr.pgua.eic.trabalhofinal.models.daos.LoginDAO;
import ifpr.pgua.eic.trabalhofinal.models.results.Result;
import ifpr.pgua.eic.trabalhofinal.utils.HashPassword;

public class LoginsRepository {
    private LoginDAO dao;

    public LoginsRepository(LoginDAO dao){
        this.dao = dao;

    }

    public Result verificaLogin(String email, String senha){
        String hash = dao.verify(email);

        if(hash != null){
            try {
                if(HashPassword.toHexString(HashPassword.getSHA(senha)).equals(hash)){
                    return Result.success("Login bem sucedido!");
                } else {
                    return Result.fail("Senha incorreta!");
                }

            } catch (NoSuchAlgorithmException e) {
                System.out.println("Exception thrown for incorrect algorithm: " + e);
                return Result.fail("Falha na ultiização do algoritmo");
                
            }

        } else {
            return Result.fail("Email incorreto!");
        }
    }
}

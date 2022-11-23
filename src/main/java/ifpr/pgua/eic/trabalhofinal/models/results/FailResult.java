package ifpr.pgua.eic.trabalhofinal.models.results;


/***
 * Classe responsável por representar um resultado
 * falho de uma operação. 
 */

public class FailResult extends Result {

    /**
     * Cria um objeto que representa um resultado falho de uma
     * operação.
     * 
     * @param msg Mensagem de falha.
     */
    public FailResult(String msg) {
        super(msg);
    }
    
}

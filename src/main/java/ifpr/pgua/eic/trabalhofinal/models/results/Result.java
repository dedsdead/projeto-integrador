package ifpr.pgua.eic.trabalhofinal.models.results;

/***
 * Classe responsável por representar o resultado de uma operação 
 * realizada. 
 */


public abstract class Result {
    private String msg;

    public Result(String msg){
        this.msg = msg;
    }

    public static Result success(String msg){
        return new SuccessResult(msg);
    }

    public static Result fail(String msg){
        return new FailResult(msg);
    }

    public String getMsg(){
        return msg;
    }
}

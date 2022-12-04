package ifpr.pgua.eic.trabalhofinal.models.entities;

public class EnderecoResponse {
    private String message;
    private String status;

    public EnderecoResponse(String message, String status){
        this.message = message;
        this.status = status;

    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }
    
}

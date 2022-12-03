package ifpr.pgua.eic.trabalhofinal.controllers.ViewModels;

import ifpr.pgua.eic.trabalhofinal.models.repositories.LoginsRepository;
import ifpr.pgua.eic.trabalhofinal.models.results.Result;
import ifpr.pgua.eic.trabalhofinal.models.results.SuccessResult;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TelaPrincipalViewModel {
    private StringProperty spEmail = new SimpleStringProperty();
    private StringProperty spSenha = new SimpleStringProperty();
    private BooleanProperty logado = new SimpleBooleanProperty(false);
    
    private ObjectProperty<Result> alertProperty = new SimpleObjectProperty<>();

    private LoginsRepository loginsRepository;

    public TelaPrincipalViewModel(LoginsRepository loginsRepository){
        this.loginsRepository = loginsRepository;

    }

    public StringProperty emailProperty() {
        return spEmail;
    }

    public StringProperty senhaProperty() {
        return spSenha;
    }

    public BooleanProperty logadoProperty() {
        return logado;
    }

    public ObjectProperty<Result> alertProperty() {
        return alertProperty;
    }

    public Result logar(){
        String email = spEmail.getValue();
        String senha = spSenha.getValue();

        Result result;

        result = loginsRepository.verificaLogin(email, senha);

        if(result instanceof SuccessResult){
            logado.setValue(true);
            limpar();

        }

        return result;

    }

    public void limpar(){
        spEmail.setValue("");
        spSenha.setValue("");

    }

}
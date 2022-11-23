package ifpr.pgua.eic.trabalhofinal.controllers;

import ifpr.pgua.eic.trabalhofinal.App;
import ifpr.pgua.eic.trabalhofinal.utils.Navigator.BorderPaneRegion;
import javafx.fxml.FXML;

public class TelaPrincipal extends BaseController{
    @FXML
    private void carregarClientes(){
        App.changeScreenRegion("CLIENTES", BorderPaneRegion.CENTER);
    }
    
}

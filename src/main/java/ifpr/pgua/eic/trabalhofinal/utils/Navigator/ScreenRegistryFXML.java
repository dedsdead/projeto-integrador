package ifpr.pgua.eic.trabalhofinal.utils.Navigator;


import java.net.URL;

import javafx.scene.Parent;
import javafx.util.Callback;

public class ScreenRegistryFXML implements ScreenRegistry{
    
    private final Class classe;
    private final String fxml;
    private final Callback controler;
    
    public ScreenRegistryFXML(Class classe, String fxml, Callback controler) {
        this.classe = classe;
        this.fxml = fxml;
        this.controler = controler;
    }

    public Parent getRoot(){
        return LoaderFXML.loadFxml(classe.getResource(fxml), controler);
    }
    
}

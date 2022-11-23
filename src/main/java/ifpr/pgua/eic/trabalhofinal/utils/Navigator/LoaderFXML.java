package ifpr.pgua.eic.trabalhofinal.utils.Navigator;

import java.io.IOException;
import java.net.URL;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.util.Callback;

public class LoaderFXML {
        /**
     * Método que permite carregar um fxml.
     * 
     * @param fxml Localização do arquivo fxml. Geralmente está dentro da pasta resources do projeto.
     * @param controlerMaker Função anônima para criar um controlador da janela.
     * @return
     */
    public static Parent loadFxml(URL fxml, Callback controlerMaker){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(fxml);
            loader.setControllerFactory(controlerMaker);

            Parent root = loader.load();

            return root;
        }catch(IOException e){
            System.out.println("PROBLEMA AO CARREGAR O FXML!!! O arquivo "+fxml+" existe?");
            e.printStackTrace();
            Platform.exit();
        }
        return null;
    }


}

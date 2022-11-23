package ifpr.pgua.eic.trabalhofinal.utils.Navigator;

import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Classe responsável por criar uma aplicação que permite a 
 * troca do conteúdo da janela. Deve ser extendida por outra classe.
 * Para cada janela da aplicação deve ser criado um atributo final
 * do tipo Parent ou ScreenRegistry. Exemplo:
 * 
 * public static final ScreenRegistry TELA_PRINCIPAL = new ScreenRegistry("fxml/principal.fxml",o->new Principal());
 * 
 */


public abstract class BaseAppNavigator extends Application{

    private static StackPane root;
    private static Scene cena;
    private Stage palco;
    private static Map<String,ScreenRegistry> registroTelas = new HashMap<>();

    @Override
    public final void start(Stage primaryStage) throws Exception {

        registrarTelas();

        root = new StackPane();
        
        String homeRegistry = getHome();
        if(homeRegistry != null){
            pushScreen(homeRegistry);
        }else{
            throw new RuntimeException("Um dos métodos getHome deve retornar algo diferente de null!");
        }

        cena = new Scene(root,Region.USE_PREF_SIZE,Region.USE_PREF_SIZE);
        this.palco = primaryStage;

        atualizaEstilo();

        this.palco.setScene(cena);
        this.palco.setTitle(getAppTitle());
        this.palco.show();
    }

    /**
     * Método responsável por trocar todo o conteúdo da janela por uma
     * nova cena. A nova cena será empilhada sobre a cena anterior,
     * e a cena anterior ficará invisível.     * 
     * 
     * @param screen Objeto do tipo ScreenRegistry que indica qual o fxml
     * e seu controlador.
     */

    public final static void pushScreen(String nomeTela){
        ScreenRegistry screen = pegaTela(nomeTela);
        if(root.getChildren().size() > 0){
            root.getChildren().get(root.getChildren().size()-1).setVisible(false);
        }

        Parent tela = screen.getRoot();
        root.getChildren().add(tela);
    }

    
        
    /***
     * Método responsável por desempilhar a tela que está no topo e mostrar
     * a tela anterior. Deve ser utilizado em conjunto com o pushScreen.
     */

    public final static void popScreen(){
        if(root.getChildren().size() < 2){
            throw new RuntimeException("NO SCREENS TO POP!!");
        }
        root.getChildren().remove(root.getChildren().size()-1);
        root.getChildren().get(root.getChildren().size()-1).setVisible(true);
    }

    /**
     * Método que permite substituir somente uma região da tela. Importante que
     * para o correto funcionamento, a tela que está sendo mostrada deve ter
     * como elemento root um objeto do tipo BorderPane.
     * 
     * @param screenPiece objeto que indica o fxml e seu controlador que será inseridos em 
     * uma parte da janela.
     * @param regiao localização onde a parte será inserida.
     */

    public final static void changeScreenRegion(String nomeTela, BorderPaneRegion regiao){
        try{
            ScreenRegistry screenPiece = pegaTela(nomeTela);
            BorderPane borderPane = (BorderPane) root.getChildren().get(root.getChildren().size()-1);
            Parent root = screenPiece.getRoot();
            
            switch(regiao){
                case LEFT: 
                    borderPane.setLeft(root);
                break;
                case RIGHT: 
                    borderPane.setRight(root);
                break;
                case TOP: 
                    borderPane.setTop(root);
                break;
                case BOTTOM: 
                    borderPane.setBottom(root);
                break;
                case CENTER:
                    borderPane.setCenter(root);
                break;
                
            }
        }catch(Exception e){
            System.out.println("PROBLEMA AO CARREGAR O FXML!!! O root da cena é um BorderPane?");
            e.printStackTrace();
            Platform.exit();
        }
    }



    /**
     * Método responsável por indicar quel é a tela inicial da aplicação.
     * 
     * @return o ScreenRegistry da tela inicial.
     */
    public abstract String getHome();

    /**
     * Método que retorna o título da aplicação.
     * 
     * @return
     */
    public abstract String getAppTitle();

    public void registraTela(String nome, ScreenRegistry tela){
        if(nome.isBlank() || nome.isEmpty() || nome == null){
            throw new RuntimeException("[REGISTRA TELA] Nome inválido!");
        }

        if(tela == null){
            throw new RuntimeException("[REGISTRA TELA] Tela inválida!");
        }

        if(registroTelas.containsKey(nome)){
            throw new RuntimeException("[REGISTRA TELA] Tela já registrada!");
        }

        registroTelas.put(nome, tela);

    }

    private static ScreenRegistry pegaTela(String nome){
        if(!registroTelas.containsKey(nome)){
            throw new RuntimeException("[PEGA TELA] Tela não registrada!");
        }

        return registroTelas.get(nome);
    }

    public void adicionarArquivoEstilo(String arquivo){
        root.getStylesheets().add(arquivo);
    }

    public void atualizaEstilo(){}

    public abstract void registrarTelas();



}

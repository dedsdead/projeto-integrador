package ifpr.pgua.eic.trabalhofinal;

import ifpr.pgua.eic.trabalhofinal.controllers.TelaClientes;
import ifpr.pgua.eic.trabalhofinal.controllers.TelaLogin;
import ifpr.pgua.eic.trabalhofinal.controllers.ViewModels.TelaClientesViewModel;
import ifpr.pgua.eic.trabalhofinal.models.FabricaConexoes;
import ifpr.pgua.eic.trabalhofinal.models.daos.ClienteDAO;
import ifpr.pgua.eic.trabalhofinal.models.daos.JDBCClienteDAO;
import ifpr.pgua.eic.trabalhofinal.models.repositories.ClientesRepository;
import ifpr.pgua.eic.trabalhofinal.utils.Navigator.BaseAppNavigator;
import ifpr.pgua.eic.trabalhofinal.utils.Navigator.ScreenRegistryFXML;

public final class App extends BaseAppNavigator{
    private ClienteDAO clienteDao;
    private ClientesRepository clientesRepository;

    @Override
    public void init() throws Exception{
        super.init();

        clienteDao = new JDBCClienteDAO(FabricaConexoes.getInstance());
        clientesRepository = new ClientesRepository(clienteDao);

    }

    @Override
    public void stop() throws Exception{
        super.stop();

    }

    @Override
    public String getHome() {
        // TODO Auto-generated method stub
        return "Login";
    }

    @Override
    public String getAppTitle() {
        // TODO Auto-generated method stub
        return "Projeto Integrador";
    }

    @Override
    public void registrarTelas() {
        registraTela("LOGIN", new ScreenRegistryFXML(getClass(), "fxml/login.fxml", (o)->new TelaLogin()));
        registraTela("CLIENTES", new ScreenRegistryFXML(getClass(), "fxml/clientes.fxml", (o)->new TelaClientes(new TelaClientesViewModel(clientesRepository))));
    }

}

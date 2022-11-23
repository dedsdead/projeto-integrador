package ifpr.pgua.eic.trabalhofinal;

import ifpr.pgua.eic.trabalhofinal.controllers.TelaClientes;
import ifpr.pgua.eic.trabalhofinal.controllers.TelaPrincipal;
import ifpr.pgua.eic.trabalhofinal.controllers.ViewModels.TelaClientesViewModel;
import ifpr.pgua.eic.trabalhofinal.models.FabricaConexoes;
import ifpr.pgua.eic.trabalhofinal.models.daos.CaracteristicaDAO;
import ifpr.pgua.eic.trabalhofinal.models.daos.ClienteDAO;
import ifpr.pgua.eic.trabalhofinal.models.daos.JDBCCaracteristicaDAO;
import ifpr.pgua.eic.trabalhofinal.models.daos.JDBCClienteDAO;
import ifpr.pgua.eic.trabalhofinal.models.daos.JDBCTipoDAO;
import ifpr.pgua.eic.trabalhofinal.models.daos.LoginDAO;
import ifpr.pgua.eic.trabalhofinal.models.daos.TipoDAO;
import ifpr.pgua.eic.trabalhofinal.models.repositories.CaracteristicasRepository;
import ifpr.pgua.eic.trabalhofinal.models.repositories.ClientesRepository;
import ifpr.pgua.eic.trabalhofinal.models.repositories.LoginRepository;
import ifpr.pgua.eic.trabalhofinal.models.repositories.TiposRepository;
import ifpr.pgua.eic.trabalhofinal.utils.Navigator.BaseAppNavigator;
import ifpr.pgua.eic.trabalhofinal.utils.Navigator.ScreenRegistryFXML;

public final class App extends BaseAppNavigator{
    private LoginDAO loginDao;
    private LoginRepository loginRepository;

    private ClienteDAO clienteDao;
    private ClientesRepository clientesRepository;

    private TipoDAO tipoDao;
    private TiposRepository tiposRepository;

    private CaracteristicaDAO caracteristicaDAO;
    private CaracteristicasRepository caracteristicasRepository;

    @Override
    public void init() throws Exception{
        super.init();

        clienteDao = new JDBCClienteDAO(FabricaConexoes.getInstance());
        clientesRepository = new ClientesRepository(clienteDao);

        tipoDao = new JDBCTipoDAO(FabricaConexoes.getInstance());
        tiposRepository = new TiposRepository(tipoDao);

        caracteristicaDAO = new JDBCCaracteristicaDAO(FabricaConexoes.getInstance());
        caracteristicasRepository = new CaracteristicasRepository(caracteristicaDAO);

    }

    @Override
    public void stop() throws Exception{
        super.stop();

    }

    @Override
    public String getHome() {
        return "PRINCIPAL";

    }

    @Override
    public String getAppTitle() {
        return "Projeto Integrador";
        
    }

    @Override
    public void registrarTelas() {
        registraTela("PRINCIPAL", new ScreenRegistryFXML(getClass(), "fxml/principal.fxml", (o)->new TelaPrincipal()));
        registraTela("CLIENTES", new ScreenRegistryFXML(getClass(), "fxml/clientes.fxml", (o)->new TelaClientes(new TelaClientesViewModel(clientesRepository, tiposRepository, caracteristicasRepository))));
    }

}

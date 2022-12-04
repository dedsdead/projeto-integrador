package ifpr.pgua.eic.trabalhofinal;

import ifpr.pgua.eic.trabalhofinal.controllers.TelaClientes;
import ifpr.pgua.eic.trabalhofinal.controllers.TelaPrincipal;
import ifpr.pgua.eic.trabalhofinal.controllers.ViewModels.TelaClientesViewModel;
import ifpr.pgua.eic.trabalhofinal.controllers.ViewModels.TelaPrincipalViewModel;
import ifpr.pgua.eic.trabalhofinal.models.FabricaConexoes;
import ifpr.pgua.eic.trabalhofinal.models.daos.CaracteristicaDAO;
import ifpr.pgua.eic.trabalhofinal.models.daos.ClienteDAO;
import ifpr.pgua.eic.trabalhofinal.models.daos.EnderecoDAO;
import ifpr.pgua.eic.trabalhofinal.models.daos.JDBCCaracteristicaDAO;
import ifpr.pgua.eic.trabalhofinal.models.daos.JDBCClienteDAO;
import ifpr.pgua.eic.trabalhofinal.models.daos.JDBCEnderecoDAO;
import ifpr.pgua.eic.trabalhofinal.models.daos.JDBCLoginDAO;
import ifpr.pgua.eic.trabalhofinal.models.daos.JDBCTipoDAO;
import ifpr.pgua.eic.trabalhofinal.models.daos.LoginDAO;
import ifpr.pgua.eic.trabalhofinal.models.daos.TipoDAO;
import ifpr.pgua.eic.trabalhofinal.models.repositories.CaracteristicasRepository;
import ifpr.pgua.eic.trabalhofinal.models.repositories.ClientesRepository;
import ifpr.pgua.eic.trabalhofinal.models.repositories.EnderecosRepository;
import ifpr.pgua.eic.trabalhofinal.models.repositories.LoginsRepository;
import ifpr.pgua.eic.trabalhofinal.models.repositories.TiposRepository;
import ifpr.pgua.eic.trabalhofinal.utils.Navigator.BaseAppNavigator;
import ifpr.pgua.eic.trabalhofinal.utils.Navigator.ScreenRegistryFXML;

public final class App extends BaseAppNavigator{
    private LoginDAO loginDAO;
    private LoginsRepository loginsRepository;

    private ClienteDAO clienteDao;
    private ClientesRepository clientesRepository;

    private EnderecoDAO enderecoDAO;
    private EnderecosRepository enderecosRepository;

    private TipoDAO tipoDao;
    private TiposRepository tiposRepository;

    private CaracteristicaDAO caracteristicaDAO;
    private CaracteristicasRepository caracteristicasRepository;

    @Override
    public void init() throws Exception{
        super.init();

        loginDAO = new JDBCLoginDAO(FabricaConexoes.getInstance());
        loginsRepository = new LoginsRepository(loginDAO);

        enderecoDAO = new JDBCEnderecoDAO(FabricaConexoes.getInstance());
        enderecosRepository = new EnderecosRepository(enderecoDAO);

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
        registraTela("PRINCIPAL", new ScreenRegistryFXML(getClass(), "fxml/principal.fxml", (o)->new TelaPrincipal(new TelaPrincipalViewModel(loginsRepository))));
        registraTela("CLIENTES", new ScreenRegistryFXML(getClass(), "fxml/clientes.fxml", (o)->new TelaClientes(new TelaClientesViewModel(clientesRepository, enderecosRepository, tiposRepository, caracteristicasRepository))));
    }

}

package ifpr.pgua.eic.trabalhofinal;

import ifpr.pgua.eic.trabalhofinal.controllers.TelaClientes;
import ifpr.pgua.eic.trabalhofinal.controllers.TelaEnderecos;
import ifpr.pgua.eic.trabalhofinal.controllers.TelaFotos;
import ifpr.pgua.eic.trabalhofinal.controllers.TelaImoveis;
import ifpr.pgua.eic.trabalhofinal.controllers.TelaPrincipal;
import ifpr.pgua.eic.trabalhofinal.controllers.TelaVendas;
import ifpr.pgua.eic.trabalhofinal.controllers.ViewModels.TelaClientesViewModel;
import ifpr.pgua.eic.trabalhofinal.controllers.ViewModels.TelaEnderecosViewModel;
import ifpr.pgua.eic.trabalhofinal.controllers.ViewModels.TelaFotosViewModel;
import ifpr.pgua.eic.trabalhofinal.controllers.ViewModels.TelaImoveisViewModel;
import ifpr.pgua.eic.trabalhofinal.controllers.ViewModels.TelaPrincipalViewModel;
import ifpr.pgua.eic.trabalhofinal.controllers.ViewModels.TelaVendasViewModel;
import ifpr.pgua.eic.trabalhofinal.models.FabricaConexoes;
import ifpr.pgua.eic.trabalhofinal.models.daos.CaracteristicaDAO;
import ifpr.pgua.eic.trabalhofinal.models.daos.ClienteDAO;
import ifpr.pgua.eic.trabalhofinal.models.daos.EnderecoDAO;
import ifpr.pgua.eic.trabalhofinal.models.daos.FotoDAO;
import ifpr.pgua.eic.trabalhofinal.models.daos.ImovelDAO;
import ifpr.pgua.eic.trabalhofinal.models.daos.ImovelFotoDAO;
import ifpr.pgua.eic.trabalhofinal.models.daos.JDBCCaracteristicaDAO;
import ifpr.pgua.eic.trabalhofinal.models.daos.JDBCClienteDAO;
import ifpr.pgua.eic.trabalhofinal.models.daos.JDBCEnderecoDAO;
import ifpr.pgua.eic.trabalhofinal.models.daos.JDBCFotoDAO;
import ifpr.pgua.eic.trabalhofinal.models.daos.JDBCImovelDAO;
import ifpr.pgua.eic.trabalhofinal.models.daos.JDBCImovelFotoDAO;
import ifpr.pgua.eic.trabalhofinal.models.daos.JDBCLoginDAO;
import ifpr.pgua.eic.trabalhofinal.models.daos.JDBCTipoDAO;
import ifpr.pgua.eic.trabalhofinal.models.daos.JDBCVendaDAO;
import ifpr.pgua.eic.trabalhofinal.models.daos.LoginDAO;
import ifpr.pgua.eic.trabalhofinal.models.daos.TipoDAO;
import ifpr.pgua.eic.trabalhofinal.models.daos.VendaDAO;
import ifpr.pgua.eic.trabalhofinal.models.repositories.CaracteristicasRepository;
import ifpr.pgua.eic.trabalhofinal.models.repositories.ClientesRepository;
import ifpr.pgua.eic.trabalhofinal.models.repositories.EmailsRepository;
import ifpr.pgua.eic.trabalhofinal.models.repositories.EnderecosRepository;
import ifpr.pgua.eic.trabalhofinal.models.repositories.FotosRepository;
import ifpr.pgua.eic.trabalhofinal.models.repositories.ImoveisRepository;
import ifpr.pgua.eic.trabalhofinal.models.repositories.LoginsRepository;
import ifpr.pgua.eic.trabalhofinal.models.repositories.TiposRepository;
import ifpr.pgua.eic.trabalhofinal.models.repositories.VendasRepository;
import ifpr.pgua.eic.trabalhofinal.utils.Navigator.BaseAppNavigator;
import ifpr.pgua.eic.trabalhofinal.utils.Navigator.ScreenRegistryFXML;

public final class App extends BaseAppNavigator{
    private LoginDAO loginDAO;
    private LoginsRepository loginsRepository;

    private ClienteDAO clienteDao;
    private ClientesRepository clientesRepository;
    private TelaClientesViewModel clientesViewModel;

    private ImovelDAO imovelDao;
    private ImoveisRepository imoveisRepository;
    private TelaImoveisViewModel imoveisViewModel;

    private VendaDAO vendaDAO;
    private VendasRepository vendasRepository;
    private TelaVendasViewModel vendasViewModel;

    private FotoDAO fotoDao;
    private ImovelFotoDAO ifDao;
    private FotosRepository fotosRepository;

    private EnderecoDAO enderecoDao;
    private EnderecosRepository enderecosRepository;

    private TipoDAO tipoDao;
    private TiposRepository tiposRepository;

    private CaracteristicaDAO caracteristicaDao;
    private CaracteristicasRepository caracteristicasRepository;

    private EmailsRepository emailsRepository;

    @Override
    public void init() throws Exception{
        super.init();

        super.setUserAgentStylesheet(STYLESHEET_CASPIAN);

        loginDAO = new JDBCLoginDAO(FabricaConexoes.getInstance());
        loginsRepository = new LoginsRepository(loginDAO);

        clienteDao = new JDBCClienteDAO(FabricaConexoes.getInstance());
        clientesRepository = new ClientesRepository(clienteDao);

        imovelDao = new JDBCImovelDAO(FabricaConexoes.getInstance());
        imoveisRepository = new ImoveisRepository(imovelDao);

        vendaDAO = new JDBCVendaDAO(FabricaConexoes.getInstance());
        vendasRepository = new VendasRepository(vendaDAO);

        fotoDao = new JDBCFotoDAO(FabricaConexoes.getInstance());
        ifDao = new JDBCImovelFotoDAO(FabricaConexoes.getInstance());
        fotosRepository = new FotosRepository(fotoDao, ifDao);

        enderecoDao = new JDBCEnderecoDAO(FabricaConexoes.getInstance());
        enderecosRepository = new EnderecosRepository(enderecoDao);

        tipoDao = new JDBCTipoDAO(FabricaConexoes.getInstance());
        tiposRepository = new TiposRepository(tipoDao);

        caracteristicaDao = new JDBCCaracteristicaDAO(FabricaConexoes.getInstance());
        caracteristicasRepository = new CaracteristicasRepository(caracteristicaDao);

        emailsRepository = new EmailsRepository();

        clientesViewModel = new TelaClientesViewModel(clientesRepository, tiposRepository, caracteristicasRepository, imoveisRepository, enderecosRepository, emailsRepository);
        imoveisViewModel = new TelaImoveisViewModel(imoveisRepository, fotosRepository, tiposRepository, caracteristicasRepository, clientesRepository, emailsRepository);
        vendasViewModel = new TelaVendasViewModel(vendasRepository, imoveisRepository, clientesRepository);

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
        registraTela("CLIENTES", new ScreenRegistryFXML(getClass(), "fxml/clientes.fxml", (o)->new TelaClientes(clientesViewModel)));
        registraTela("IMOVEIS", new ScreenRegistryFXML(getClass(), "fxml/imoveis.fxml", (o)->new TelaImoveis(imoveisViewModel)));
        registraTela("VENDAS", new ScreenRegistryFXML(getClass(), "fxml/vendas.fxml", (o)->new TelaVendas(vendasViewModel)));
    

        registraTela("ENDERECOS", new ScreenRegistryFXML(getClass(), "fxml/enderecos.fxml", (o)->new TelaEnderecos(new TelaEnderecosViewModel(enderecosRepository, clientesViewModel, imoveisViewModel))));
        registraTela("FOTOS", new ScreenRegistryFXML(getClass(), "fxml/fotos.fxml", (o)->new TelaFotos(new TelaFotosViewModel(fotosRepository, imoveisViewModel))));

    }

}

package ifpr.pgua.eic.trabalhofinal.models.daos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import ifpr.pgua.eic.trabalhofinal.models.FabricaConexoes;
import ifpr.pgua.eic.trabalhofinal.models.entities.Venda;
import ifpr.pgua.eic.trabalhofinal.models.results.Result;

public class JDBCVendaDAO implements VendaDAO{
    private static final String INSERT = "INSERT INTO Venda(codigo_imovel,codigo_comprador,data_venda,valor,caminho_contrato) VALUES (?,?,?,?,?)";
    private static final String UPDATE = "UPDATE Venda set codigo_imovel=?, codigo_comprador=?, data_venda=?, valor=?, caminho_contrato=? WHERE codigo=?";
    private static final String DELETE = "UPDATE Venda set excluido_em=? WHERE codigo=?";
    private static final String SELECT_ALL = "SELECT * FROM Venda";
    private static final String SELECT_ID = "SELECT * FROM Venda WHERE codigo=?";

    private FabricaConexoes fabricaConexoes;

    public JDBCVendaDAO(FabricaConexoes fabricaConexoes){
        this.fabricaConexoes = fabricaConexoes;

    }

    @Override
    public Result create(Venda venda) {
        try {
            Connection con = fabricaConexoes.getConnection();

            PreparedStatement pstm = con.prepareStatement(INSERT);

            pstm.setInt(1, venda.getIdImovel()); 
            pstm.setInt(2, venda.getIdComprador());
            pstm.setDate(3, Date.valueOf(venda.getDataVenda()));
            pstm.setDouble(4, venda.getValor());

            if(venda.getCaminhoContrato() == null || venda.getCaminhoContrato().equals("")){
                pstm.setNull(5, Types.INTEGER);
            } else {
                pstm.setString(5, venda.getCaminhoContrato());
            }

            pstm.execute();

            pstm.close();
            con.close();

            return Result.success("Venda cadastrada com sucesso!");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return Result.fail(e.getMessage());

        }

    }

    @Override
    public Result update(Venda venda) {
        try {
            Connection con = fabricaConexoes.getConnection();

            PreparedStatement pstm = con.prepareStatement(UPDATE);

            pstm.setInt(1, venda.getIdImovel()); 
            pstm.setInt(2, venda.getIdComprador());
            pstm.setDate(3, Date.valueOf(venda.getDataVenda()));
            pstm.setDouble(4, venda.getValor());

            if(venda.getCaminhoContrato() == null || venda.getCaminhoContrato().equals("")){
                pstm.setNull(5, Types.INTEGER);
            } else {
                pstm.setString(5, venda.getCaminhoContrato());
            }

            pstm.setInt(6, venda.getId());

            pstm.execute();

            pstm.close();
            con.close();

            return Result.success("Venda atualizada com sucesso!");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return Result.fail(e.getMessage());

        }

    }

    @Override
    public Result delete(int id) {
        try{
            Connection con = fabricaConexoes.getConnection(); 
            
            PreparedStatement pstm = con.prepareStatement(DELETE);

            Timestamp time = new Timestamp((System.currentTimeMillis()+10800000));
            
            pstm.setTimestamp(1, time);
            pstm.setInt(2, id);

            pstm.execute();

            pstm.close();
            con.close();

            return Result.success("Venda excluída com sucesso!");

        }catch(SQLException e){
            System.out.println(e.getMessage());
            return Result.fail(e.getMessage());

        }
        
    }

    @Override
    public Venda getById(int id) {
        try {
            Connection con = fabricaConexoes.getConnection(); 
            
            PreparedStatement pstm = con.prepareStatement(SELECT_ID);

            pstm.setInt(1, id);

            ResultSet rsc = pstm.executeQuery();

            LocalDateTime dataExclusao = null;

            rsc.next();

            int idImovel = rsc.getInt("codigo_imovel");
            int idComprador = rsc.getInt("codigo_comprador");
            LocalDate dataVenda = rsc.getDate("data_venda").toLocalDate();
            double valor = rsc.getDouble("valor");
            String matricula = rsc.getString("caminho_contrato");
            
            if(rsc.getTimestamp("excluido_em") != null)
                dataExclusao = rsc.getTimestamp("excluido_em").toInstant().atZone(ZoneId.of("UTC")).toLocalDateTime();

            Venda v = new Venda(id, idImovel, idComprador, dataVenda, valor, matricula, dataExclusao);

            rsc.close();
            pstm.close();
            con.close();

            return v;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;

        }

    }

    @Override
    public List<Venda> getAll() {
        ArrayList<Venda> vendas = new ArrayList<>();

        try {
            Connection con = fabricaConexoes.getConnection(); 
            
            PreparedStatement pstm = con.prepareStatement(SELECT_ALL);

            ResultSet rs = pstm.executeQuery();

            while(rs.next()){
                int id = rs.getInt("codigo");
                int idImovel = rs.getInt("codigo_imovel");
                int idComprador = rs.getInt("codigo_comprador");
                LocalDate dataVenda = rs.getDate("data_venda").toLocalDate();
                double valor = rs.getDouble("valor");
                String contrato = rs.getString("caminho_contrato");
                LocalDateTime dataExclusao = null;
                
                if(rs.getTimestamp("excluida_em") != null)
                    dataExclusao = rs.getTimestamp("excluida_em").toInstant().atZone(ZoneId.of("UTC")).toLocalDateTime();

                Venda v = new Venda(id, idImovel, idComprador, dataVenda, valor, contrato, dataExclusao);
                vendas.add(v);

            }

            rs.close();
            pstm.close();
            con.close();

            return vendas;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;

        }

    }
    
}

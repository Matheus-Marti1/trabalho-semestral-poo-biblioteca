package edu.curso;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmprestimoDAOImpl implements EmprestimoDAO {

    private final static String DB_CLASS = "org.mariadb.jdbc.Driver";
    private final static String DB_URL = "jdbc:mariadb://localhost:3306/bibliotecadb?allowPublicKeyRetrieval=true";
    private final static String DB_USER = "root";
    private final static String DB_PASS = "alunofatec";

    private Connection con = null;

    public EmprestimoDAOImpl() throws BibliotecaException {
        try {
            Class.forName(DB_CLASS);
            con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        } catch (ClassNotFoundException | SQLException e) {
            throw new BibliotecaException(e);
        }
    }

    @Override
    public void inserir(Emprestimo e) throws BibliotecaException {
        try {
            String SQL = """
                    INSERT INTO emprestimos (nome_cliente, data_emprestimo, data_devolucao, id_livro, status)
                    VALUES (?, ?, ?, ?, ?)
                    """;
            PreparedStatement stm = con.prepareStatement(SQL);
            stm.setString(1, e.getNomeCliente());
            stm.setDate(2, Date.valueOf(e.getDataEmprestimo()));
            stm.setDate(3, Date.valueOf(e.getDataDevolucao()));
            stm.setLong(4, e.getIdLivro());
            stm.setBoolean(5, e.isStatus());
            stm.executeUpdate();
        } catch (SQLException e1) {
            throw new BibliotecaException(e1);
        }
    }

    @Override
    public void atualizar(Emprestimo e) throws BibliotecaException {
        try {
            String SQL = """
                    UPDATE emprestimos SET nome_cliente = ?, data_emprestimo = ?, data_devolucao = ?, id_livro = ?, status = ?
                    WHERE emprestimo_id = ?
                    """;
            PreparedStatement stm = con.prepareStatement(SQL);
            stm.setString(1, e.getNomeCliente());
            stm.setDate(2, Date.valueOf(e.getDataEmprestimo()));
            stm.setDate(3, Date.valueOf(e.getDataDevolucao()));
            stm.setLong(4, e.getIdLivro());
            stm.setBoolean(5, e.isStatus());
            stm.setLong(6, e.getId());
            stm.executeUpdate();
        } catch (SQLException e1) {
            throw new BibliotecaException(e1);
        }
    }

    @Override
    public void remover(Emprestimo e) throws BibliotecaException {
        try {
            String SQL = """
                    DELETE FROM emprestimos WHERE emprestimo_id = ?
                    """;
            PreparedStatement stm = con.prepareStatement(SQL);
            stm.setLong(1, e.getId());
            stm.executeUpdate();
        } catch (SQLException e1) {
            throw new BibliotecaException(e1);
        }
    }

    @Override
    public List<Emprestimo> pesquisarPorNomeCliente(String nomeCliente) throws BibliotecaException {
        List<Emprestimo> lista = new ArrayList<>();
        try {
            String SQL = """
                    SELECT * FROM emprestimos WHERE nome_cliente LIKE ?
                    """;
            PreparedStatement stm = con.prepareStatement(SQL);
            stm.setString(1, "%" + nomeCliente + "%");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Emprestimo e = new Emprestimo();
                e.setId(rs.getLong("emprestimo_id"));
                e.setNomeCliente(rs.getString("nome_cliente"));
                e.setDataEmprestimo(rs.getDate("data_emprestimo").toLocalDate());
                e.setDataDevolucao(rs.getDate("data_devolucao").toLocalDate());
                e.setIdLivro(rs.getLong("id_livro"));
                e.setStatus(rs.getBoolean("status"));
                lista.add(e);
            }
        } catch (SQLException e) {
            throw new BibliotecaException(e);
        }
        return lista;
    }

}

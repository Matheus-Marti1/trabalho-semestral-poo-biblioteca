package edu.curso;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LivroDAOImpl implements LivroDAO {

    private final static String DB_CLASS = "org.mariadb.jdbc.Driver";
    private final static String DB_URL = "jdbc:mariadb://localhost:3306/bibliotecadb?allowPublicKeyRetrieval=true";
    private final static String DB_USER = "root";
    private final static String DB_PASS = "alunofatec";

    private Connection con = null;

    public LivroDAOImpl() throws BibliotecaException {
        try {
            Class.forName(DB_CLASS);
            con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        } catch (ClassNotFoundException | SQLException e) {
            throw new BibliotecaException(e);
        }
    }

    @Override
    public void inserir(Livro l) throws BibliotecaException {
        try {
            String sql = """
                    INSERT INTO livros (titulo, autor,
                    editora, ano_publicacao, isbn)
                    VALUES (?, ?, ?, ?, ?)
                    """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, l.getTitulo());
            stm.setString(2, l.getAutor());
            stm.setString(3, l.getEditora());
            stm.setInt(4, l.getAnoPublicacao());
            stm.setString(5, l.getIsbn());
            stm.executeUpdate();
        } catch (SQLException err) {
            throw new BibliotecaException(err);
        }
    }

    @Override
    public void atualizar(Livro l) throws BibliotecaException {
        try {
            String sql = """
                    UPDATE livros SET titulo=?, autor=?,
                    editora=?, ano_publicacao=?, isbn=?
                    WHERE livro_id = ?
                    """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, l.getTitulo());
            stm.setString(2, l.getAutor());
            stm.setString(3, l.getEditora());
            stm.setInt(4, l.getAnoPublicacao());
            stm.setString(5, l.getIsbn());
            stm.executeUpdate();
        } catch (SQLException err) {
            throw new BibliotecaException(err);
        }
    }

    @Override
    public void remover(Livro l) throws BibliotecaException {
        try {
            String sql = """
                    DELETE FROM livros
                    WHERE livro_id = ?
                    """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setLong(1, l.getId());
            stm.executeUpdate();
        } catch (SQLException err) {
            throw new BibliotecaException(err);
        }
    }

    @Override
    public List<Livro> pesquisarPorTitulo(String titulo) throws BibliotecaException {
        List<Livro> lista = new ArrayList<>();
        try {
            String sql = """
                    SELECT * FROM livros
                    WHERE titulo LIKE ?
                    """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, "%" + titulo + "%");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Livro livro = new Livro();
                livro.setId(rs.getLong("livro_id"));
                livro.setTitulo(rs.getString("titulo"));
                livro.setAutor(rs.getString("autor"));
                livro.setEditora(rs.getString("editora"));
                livro.setAnoPublicacao(rs.getInt("ano_publicacao"));
                livro.setIsbn(rs.getString("isbn"));
                lista.add(livro);
            }
        } catch (SQLException err) {
            throw new BibliotecaException(err);
        }
        return lista;
    }

    @Override
    public List<Livro> pesquisarTodosLivros() throws BibliotecaException {
        List<Livro> lista = new ArrayList<>();
        try {
            String sql = "SELECT livro_id, titulo, autor, editora, ano_publicacao, isbn FROM livros";
            PreparedStatement stm = con.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Livro livro = new Livro();
                livro.setId(rs.getInt("livro_id"));
                livro.setTitulo(rs.getString("titulo"));
                livro.setAutor(rs.getString("autor"));
                livro.setEditora(rs.getString("editora"));
                livro.setAnoPublicacao(rs.getInt("ano_publicacao"));
                livro.setIsbn(rs.getString("isbn"));
                lista.add(livro);
            }
        } catch (SQLException err) {
            throw new BibliotecaException(err);
        }
        return lista;
    }
}

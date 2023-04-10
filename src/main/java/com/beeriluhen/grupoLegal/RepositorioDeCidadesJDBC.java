package com.beeriluhen.grupoLegal;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class RepositorioDeCidadesJDBC implements IRepositorioDeCidades {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public RepositorioDeCidadesJDBC(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcTemplate.execute("DROP TABLE cidades IF EXISTS");
        this.jdbcTemplate.execute("CREATE TABLE cidades("

                + "id long, nome VARCHAR(255), custobasico float(23), PRIMARY KEY(id))");
        cadastra(new Cidade(1, "Porto Alegre", 25));
        cadastra(new Cidade(2, "Florianópolis", 20));
        cadastra(new Cidade(3, "Curitiba", 15));
        cadastra(new Cidade(4, "São Paulo", 10));
    }

    public boolean cadastra(Cidade cidade) {
        this.jdbcTemplate.update("INSERT INTO cidades(id,nome,custobasico) VALUES (?,?,?)", cidade.getId(),
                cidade.getNome(), cidade.getCustoBasico());
        return true;
    }

    public List<Cidade> todas() {
        List<Cidade> resp = this.jdbcTemplate.query("SELECT * from cidades",
                (rs, rowNum) -> new Cidade(rs.getInt("id"), rs.getString("nome"), rs.getDouble("custobasico")));
        return resp;
    }

    public Cidade get(long id) {
        String query = "select * from cidades where id = " + id;
        List<Cidade> cidades = jdbcTemplate.query(query,
                (rs, rowNum) -> new Cidade(rs.getLong("id"), rs.getString("nome"), rs.getDouble("custobasico")));
        if (cidades.isEmpty()) {
            
            return null;
        } else {
            return cidades.get(0);
        }
    }

    public Cidade get(String nome) {
        String query = "select * from cidades where nome=\'" + nome + "\'";
        List<Cidade> cidades = jdbcTemplate.query(query,
                (rs, rowNum) -> new Cidade(rs.getLong("id"), rs.getString("nome"), rs.getDouble("custobasico")));
        if (cidades.isEmpty()) {
            return null;
        } else {
            return cidades.get(0);
        }
    }
}
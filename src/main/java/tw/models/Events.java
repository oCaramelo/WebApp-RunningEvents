package tw.models;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "events")
public class Events implements Serializable {

    @Id
    private int id;

    private String nome, local, descricao;

    private Timestamp data;

    private float valor;

    protected Events() {
    }

    public Events(String nome, String descricao, String local, Timestamp data, float valor) {
        this.nome = nome;
        this.descricao = descricao;
        this.local = local;
        this.data = data;
        this.valor = valor;
    }

    public String toString() {
        return "{\"nome\":\""+ this.nome +"\", \"id\":\""+ this.valor +"\", \"valor\":\"" + this.valor + "\"}";
    }

    public int getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public String getLocal() {
        return this.local;
    }

    public Timestamp getData() {
        return this.data;
    }

    public float getValor() {
        return this.valor;
    }

    public void setId(int id) {
        this.id = id;
    }
}


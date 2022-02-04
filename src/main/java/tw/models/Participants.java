package tw.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "participants")
@IdClass(CompositeKeyParticipants.class)
public class Participants implements Serializable {
    @Id
    private int idEvent;
    @Id
    private int dorsal;

    private String username, nome, genero, escalao, lastSeen, pago, entidade, referencia, tempo, nameEvent;
    private Integer posGeral, posInter;
    private Long milliseconds;
    private float valor;

    protected Participants() {
    }

    public Participants(int idEvent, String username, String nome, String genero, String escalao) {
        this.idEvent = idEvent;
        this.nameEvent = nameEvent;
        this.nome = nome;
        this.username = username;
        this.genero = genero;
        this.escalao = escalao;
        this.lastSeen = "-----";
        this.posGeral = 0;
        this.posInter = 0;
        this.milliseconds = 0L;
        this.pago = "Não";
        this.tempo = "00:00:00";

    }

    public String toString() {

        return ("nome: " + nome + "   genero: " + genero + "   escalao: " + escalao
                + "   dorsal: " + dorsal);

    }

    public String getEntidade() {
        return entidade;
    }

    public void setEntidade(String entidade) {
        this.entidade = entidade;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public int getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(int idEvent) {
        this.idEvent = idEvent;
    }

    public int getDorsal() {
        return dorsal;
    }

    public void setDorsal(int dorsal) {
        this.dorsal = dorsal;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getEscalao() {
        return escalao;
    }

    public void setEscalao(String escalao) {
        this.escalao = escalao;
    }

    public String getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(String lastSeen) {
        this.lastSeen = lastSeen;
    }

    public String getPago() {
        return pago;
    }

    public void setPago(String pago) {
        this.pago = pago;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public Integer getPosGeral() {
        return posGeral;
    }

    public void setPosGeral(Integer posGeral) {
        this.posGeral = posGeral;
    }

    public Long getMilliseconds() {
        return milliseconds;
    }

    public void setMilliseconds(Long milliseconds) {
        this.milliseconds = milliseconds;
    }

    public String getTempo() {
        return tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }

    public Integer getPosInter() {
        return posInter;
    }

    public void setPosInter(Integer posInter) {
        this.posInter = posInter;
    }

    public String getNameEvent() {
        return nameEvent;
    }

    public void setNameEvent(String nameEvent) {
        this.nameEvent = nameEvent;
    }
}

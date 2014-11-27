/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author koonjshah
 */
@Entity
@Table(name = "transacoes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transacoes.findAll", query = "SELECT t FROM Transacoes t"),
    @NamedQuery(name = "Transacoes.findById", query = "SELECT t FROM Transacoes t WHERE t.id = :id"),
    @NamedQuery(name = "Transacoes.findByData", query = "SELECT t FROM Transacoes t WHERE t.data = :data"),
    @NamedQuery(name = "Transacoes.findByTipo", query = "SELECT t FROM Transacoes t WHERE t.tipo = :tipo"),
    @NamedQuery(name = "Transacoes.findByValor", query = "SELECT t FROM Transacoes t WHERE t.valor = :valor")})
public class Transacoes implements Serializable {
    
    public enum Tipo{
        DEPOSITO, SAQUE, TRANSFERENCIAS;
    }

    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "tipo")
    private Tipo tipo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor")
    private BigDecimal valor;
    @JoinColumn(name = "id_origem", referencedColumnName = "id")
    @ManyToOne
    private Contacorrentes idOrigem;
    @JoinColumn(name = "id_destino", referencedColumnName = "id")
    @ManyToOne
    private Contacorrentes idDestino;

    public Transacoes() {
    }

    public Transacoes(Integer id) {
        this.id = id;
    }

    public Transacoes(Date data, Integer tipo, BigDecimal valor, Contacorrentes idOrigem, Contacorrentes idDestino) {
        this.data = data;
        this.tipo = Tipo.values()[tipo];
        this.valor = valor;
        this.idOrigem = idOrigem;
        this.idDestino = idDestino;
    }
    
     

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Contacorrentes getIdOrigem() {
        return idOrigem;
    }

    public void setIdOrigem(Contacorrentes idOrigem) {
        this.idOrigem = idOrigem;
    }

    public Contacorrentes getIdDestino() {
        return idDestino;
    }

    public void setIdDestino(Contacorrentes idDestino) {
        this.idDestino = idDestino;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transacoes)) {
            return false;
        }
        Transacoes other = (Transacoes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "clientMyMoney.Transacoes[ id=" + id + " ]";
    }
    
}

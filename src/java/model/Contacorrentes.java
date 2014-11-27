/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author koonjshah
 */
@Entity
@Table(name = "contacorrentes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Contacorrentes.findAll", query = "SELECT c FROM Contacorrentes c"),
    @NamedQuery(name = "Contacorrentes.findById", query = "SELECT c FROM Contacorrentes c WHERE c.id = :id"),
    @NamedQuery(name = "Contacorrentes.findByCodigo", query = "SELECT c FROM Contacorrentes c WHERE c.codigo = :codigo"),
    @NamedQuery(name = "Contacorrentes.findByDigito", query = "SELECT c FROM Contacorrentes c WHERE c.digito = :digito"),
    @NamedQuery(name = "Contacorrentes.findBySaldo", query = "SELECT c FROM Contacorrentes c WHERE c.saldo = :saldo")})
public class Contacorrentes implements Serializable {
    @OneToMany(mappedBy = "idOrigem")
    private Collection<Transacoes> transacoesCollection;
    @OneToMany(mappedBy = "idDestino")
    private Collection<Transacoes> transacoesCollection1;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "codigo")
    private Integer codigo;
    @Column(name = "digito")
    private Integer digito;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "saldo")
    private Double saldo;
    @JoinColumn(name = "id_clientes", referencedColumnName = "id")
    @ManyToOne
    private Clientes cliente;
    @JoinColumn(name = "id_agencias", referencedColumnName = "id")
    @ManyToOne
    private Agencias agencia;

    public Contacorrentes() {
    }

    public Contacorrentes(Integer id, Integer codigo, Integer digito, Double saldo, Agencias agencia, Clientes cliente) {
        this.id = id;
        this.codigo = codigo;
        this.digito = digito;
        this.saldo = saldo;
        this.cliente = cliente;
        this.agencia = agencia;
    }
    
    

    public Contacorrentes(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getDigito() {
        return digito;
    }

    public void setDigito(Integer digito) {
        this.digito = digito;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Clientes getCliente() {
        return cliente;
    }

    public void setCliente(Clientes idClientes) {
        this.cliente = idClientes;
    }

    public Agencias getAgencia() {
        return agencia;
    }

    public void setAgencia(Agencias idAgencias) {
        this.agencia = idAgencias;
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
        if (!(object instanceof Contacorrentes)) {
            return false;
        }
        Contacorrentes other = (Contacorrentes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model2.Contacorrentes[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<Transacoes> getTransacoesCollection() {
        return transacoesCollection;
    }

    public void setTransacoesCollection(Collection<Transacoes> transacoesCollection) {
        this.transacoesCollection = transacoesCollection;
    }

    @XmlTransient
    public Collection<Transacoes> getTransacoesCollection1() {
        return transacoesCollection1;
    }

    public void setTransacoesCollection1(Collection<Transacoes> transacoesCollection1) {
        this.transacoesCollection1 = transacoesCollection1;
    }
    
}

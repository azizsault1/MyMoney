package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Agencias;
import model.Clientes;
import model.Transacoes;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2014-11-26T21:57:39")
@StaticMetamodel(Contacorrentes.class)
public class Contacorrentes_ { 

    public static volatile CollectionAttribute<Contacorrentes, Transacoes> transacoesCollection;
    public static volatile SingularAttribute<Contacorrentes, Clientes> cliente;
    public static volatile SingularAttribute<Contacorrentes, Integer> codigo;
    public static volatile SingularAttribute<Contacorrentes, Integer> id;
    public static volatile SingularAttribute<Contacorrentes, Double> saldo;
    public static volatile SingularAttribute<Contacorrentes, Integer> digito;
    public static volatile SingularAttribute<Contacorrentes, Agencias> agencia;
    public static volatile CollectionAttribute<Contacorrentes, Transacoes> transacoesCollection1;

}
package model;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Contacorrentes;
import model.Transacoes.Tipo;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2014-11-26T21:57:39")
@StaticMetamodel(Transacoes.class)
public class Transacoes_ { 

    public static volatile SingularAttribute<Transacoes, Tipo> tipo;
    public static volatile SingularAttribute<Transacoes, Date> data;
    public static volatile SingularAttribute<Transacoes, Contacorrentes> idOrigem;
    public static volatile SingularAttribute<Transacoes, BigDecimal> valor;
    public static volatile SingularAttribute<Transacoes, Integer> id;
    public static volatile SingularAttribute<Transacoes, Contacorrentes> idDestino;

}
package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Contacorrentes;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2014-11-26T21:57:39")
@StaticMetamodel(Clientes.class)
public class Clientes_ { 

    public static volatile CollectionAttribute<Clientes, Contacorrentes> contacorrentesCollection;
    public static volatile SingularAttribute<Clientes, String> cpf;
    public static volatile SingularAttribute<Clientes, String> nome;
    public static volatile SingularAttribute<Clientes, Integer> id;
    public static volatile SingularAttribute<Clientes, Date> dataNascimento;
    public static volatile SingularAttribute<Clientes, Date> dataCadastro;

}
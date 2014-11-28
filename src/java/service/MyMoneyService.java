/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import controllers.AgenciasJpaController;
import controllers.ContacorrentesJpaController;
import controllers.TransacoesJpaController;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.UserTransaction;
import model.Agencias;
import model.Contacorrentes;
import model.Transacoes;

/**
 *
 * @author koonjshah
 */
@WebService(serviceName = "MyMoneyService")
public class MyMoneyService {
    
    @Resource
    private UserTransaction userTransactional;

    @PersistenceUnit(unitName = "MyMoneyPU") //inject from your application server
    private EntityManagerFactory emf;

    private AgenciasJpaController agenciasController;

    private ContacorrentesJpaController contaCorrenteController;
    private TransacoesJpaController transacoesController;

     @PostConstruct
    public void construct(){
        agenciasController = new AgenciasJpaController(userTransactional, emf);
        contaCorrenteController = new ContacorrentesJpaController(userTransactional, emf);
        transacoesController = new TransacoesJpaController(userTransactional, emf);
    }
    
       /**
     * This is a sample web service operation
     *
     * @param data
     * @return
     */
    @WebMethod(operationName = "getTransaction")
    public Collection<Transacoes> getTransaction(@WebParam(name = "date") Date data) {

        if (data != null) {

            Collection<Transacoes> selected = new ArrayList<>();

            Date dataProcuradaSemTempo = removerTempo(data);

            for (Transacoes transacao : transacoesController.findTransacoesEntities()) {
                Date dataTransacao = removerTempo(transacao.getData());
                if (dataProcuradaSemTempo.equals(dataTransacao)) {
                    selected.add(transacao);
                }
            }

            return selected;
        } else {
            return Collections.emptyList();
        }
    }

    private Date removerTempo(Date data) {
        Calendar d1 = Calendar.getInstance();
        d1.setTime(data);
        d1.set(Calendar.HOUR, 0);
        d1.set(Calendar.MINUTE, 0);
        d1.set(Calendar.SECOND, 0);
        d1.set(Calendar.MILLISECOND, 0);
        return d1.getTime();
    }

    private Date createData(int ano, int mes, int dia) {
        Calendar d1 = Calendar.getInstance();
        d1.set(ano, mes - 1, dia);
        return this.removerTempo(d1.getTime());
    }

    /**
     * Operação de Web service
     *
     * @return
     */
    @WebMethod(operationName = "testDataBase")
    public Collection<Agencias> testDataBase() {
        if (emf == null) {
            System.out.println("EntityManagerFactory está nulo");
            return Collections.emptyList();
        } else {
            System.out.println("Diferente de nulo");
        }
        return agenciasController.findAgenciasEntities();
    }

    /**
     * Operação de Web service
     * @param data
     * @param tipo
     * @param valor
     * @param idContaCorrenteOrigem
     * @param idContaCorrenteDestino
     * @return 
     */
    @WebMethod(operationName = "addTransacao")
    public String addTransacao(String data,int tipo, double  valor, int idContaCorrenteOrigem, int idContaCorrenteDestino) {
        SimpleDateFormat sdf = new SimpleDateFormat("d/MM/yyyy");
        
        
        Contacorrentes ccOrigem = contaCorrenteController.findContacorrentes(idContaCorrenteOrigem);
        Contacorrentes ccDestino = contaCorrenteController.findContacorrentes(idContaCorrenteDestino);
        
        
        try {
        Transacoes transacao = new Transacoes(sdf.parse(data),tipo, BigDecimal.valueOf(valor), ccOrigem, ccDestino);
            transacoesController.create(transacao);
            return "Transacao criada com sucesso.";
        } catch (Exception ex) {
            Logger.getLogger(MyMoneyService.class.getName()).log(Level.SEVERE, null, ex);
            return "Erro ao criar a transacao. Detalhe:"+ ex.getMessage();
        }
        
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wyp;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Łukasz
 */
public class Mes {
    Session session = null;
    public Mes() {
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
    }
    
    public List getMesLista(int C){
        List <Wiadomosci> w = null;
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        try {
            Query q = session.createQuery("from Wiadomosci as w order by data desc");
            q.setMaxResults(C);
            w = (List<Wiadomosci>)q.list();
            tx.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return w;
    }
    
}

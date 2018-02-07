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
class News {
    Session session = null;
    List getNewsLista(int c) {
        List <Wiadomosci> w = null;
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        try {
            Query q = session.createQuery("from Ksiazki as w order by dataDodania desc");
            q.setMaxResults(c);
            w = (List<Wiadomosci>)q.list();
            tx.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return w;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wyp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import org.hibernate.Query;
import org.hibernate.Session;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author Łukasz
 */
class stuff {

   
    public void addF(int i, int bn, String nazwa, String filename) {
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        try {
            Ksiazki k = (Ksiazki) session.get(Ksiazki.class, bn);
            if(k!=null){
                PlikiId id = new PlikiId(nazwa, bn);
                Pliki ka = new Pliki(id,filename);
                session.persist(ka);
                tx.commit();
            }
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeBlock(int id, boolean b) {
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
        org.hibernate.Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Czytelnicy stat = (Czytelnicy)session.get(Czytelnicy.class,id);
            stat.setBlokada(b);
            tx.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    Session session = null;
    
    public int addBooks(Integer id, int isbn) {
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        try {
            Wypozyczenia wypp = new Wypozyczenia();
            wypp.setStatus(1);
            WypozyczeniaId wyp = new WypozyczeniaId();
            wyp.setIdCzytelnika(id);
            wyp.setIsbn(isbn);
            Date date = new Date();
            wyp.setDataWypozyczenia(date);
            wypp.setId(wyp);
            session.persist(wypp);
            tx.commit();
            session.flush();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public String KategorieById(int id) {
        Kategorie w = null;
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        try {
            Query q = session.createQuery("from Kategorie as w where w.idKategori="+id);
            w = (Kategorie)q.uniqueResult();
            tx.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return w.getNazwa();
    }
    
    public TreeNode KategorieLista() {
        TreeNode root = null;
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        try {
            root = new DefaultTreeNode("Root Node", null);
            Query q = session.createQuery("from Kategorie as k");
            List <Kategorie> ks = q.list();
            tx.commit();
            
            List <DefaultTreeNode> tr = new ArrayList<DefaultTreeNode>();
            for( Kategorie k : ks ){
                tr.add(new DefaultTreeNode(k, null));
            }
            for( DefaultTreeNode t : tr){
                Kategorie k = (Kategorie)t.getData();
                if(k.getIdRodzica()!=0){
                    for( DefaultTreeNode tt : tr){
                        Kategorie kk = (Kategorie)tt.getData();
                        if(k.getIdRodzica()==kk.getIdKategori()){
                            t.setParent(tt);
                            tt.getChildren().add(t);
                        }
                    }
                } else {
                    t.setParent(root);
                    root.getChildren().add(t);
                }
                
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return root;
    }

    public List TagiLista() {
        List<Tagi> w = null;
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        try {
            Query q = session.createQuery("from Tagi as t");
            w = (List<Tagi>)q.list();
            tx.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return w;
    }
    
    public String TagiById(int id) {
        Tagi w = null;
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        try {
            Query q = session.createQuery("from Tagi as w where w.idTagu="+id);
            w = (Tagi)q.uniqueResult();
            tx.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return w.getNazwa();
    }

    public List TagiList(int bn) {
        List<Tagi> w = null;
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        try {
            Query q = session.createQuery("select tt.idTagu as idTagu, tt.nazwa as nazwa from Tagi as tt,TagiK as t where t.id.isbn='"+bn+"' and tt.idTagu=t.id.idTagu");
            w = (List<Tagi>)q.list();
            tx.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return w;
    }
    
    public Czytelnicy CzytelnikById(int isbn) {
        Czytelnicy ks = null;
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        try {
            Query q = session.createQuery("from Czytelnicy as k where k.idCzytelnika="+isbn);
            ks = (Czytelnicy) q.uniqueResult();
            tx.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return ks;
    }
    
    public Ksiazki KsiazkaById(int isbn) {
        Ksiazki ks = null;
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        try {
            Query q = session.createQuery("from Ksiazki as k where k.isbn="+isbn);
            ks = (Ksiazki) q.uniqueResult();
            tx.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return ks;
    }
    public List KsiazkiListaByy( String a, String k, String t,  String tyt) {
        List<Ksiazki> ks = null;
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        try {
            String ck="",ctyt="",ct="",ca="";
            if(!"".equals(k))
                ck = " and k.nazwa LIKE CONCAT('%', '" + k + "','%') ";
            if(!"".equals(tyt))
                ctyt = " and tt.tytul LIKE CONCAT('%', '" + tyt + "','%') ";
            if(!"".equals(t))
                ct = " and t.nazwa LIKE CONCAT('%', '" + t + "','%') ";
            if(!"".equals(a))
                ca = " and (a.imie LIKE CONCAT('%', '" + a + "','%') or a.nazwisko LIKE CONCAT('%', '" + a + "','%')) ";
            Query q = session.createQuery("select distinct tt.isbn,tt.tytul,tt.idKategori,tt.stan,tt.dataDodania from Kategorie as k, Autorzy as a, Tagi as t, Ksiazki as tt, KsiazkiAutorzy as ka, TagiK as tk "
                    + "where tt.isbn = ka.id.isbn and tt.isbn = tk.id.isbn and k.idKategori = tt.idKategori and  a.idAutora = ka.id.idAutora and t.idTagu = tk.id.idTagu"
                    + ck + ctyt + ct + ca);
            ks = (List<Ksiazki>)q.list();
            tx.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return ks;
    }
    public List KsiazkiListaBy(int k, int w, int t, int a) {
        List<Ksiazki> ks = null;
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        try {
            String czyk="",czyw="",czyt="",czya="";
            if(k!=0)
                czyk = " and tt.idKategori = " + k + " ";
            if(w!=0)
                czyw = " and tt.idWydawcy = " + w + " ";
            if(t!=0)
                czyt = " and tk.id.idTagu = " + t + " ";
            if(a!=0)
                czya = " and ka.id.idAutora = " + a + " ";
            Query q = session.createQuery("select distinct tt.isbn,tt.tytul,tt.idKategori,tt.stan,tt.dataDodania from Ksiazki as tt, KsiazkiAutorzy as ka, TagiK as tk "
                    + "where tt.isbn = ka.id.isbn and tt.isbn = tk.id.isbn"
                    + czyk + czyw + czyt + czya);
            ks = (List<Ksiazki>)q.list();
            tx.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return ks;
    }
    
    public List getWypozyczeniaById(int bn){
        List<Ksiazki> ks = null;
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        try {
            Query q = session.createQuery("select tt.isbn,tt.tytul,tt.idKategori,t.status,t.id.dataWypozyczenia,t.dataOddania from Ksiazki as tt, Wypozyczenia as t"
                    + " where t.id.isbn = tt.isbn and t.id.idCzytelnika="+bn);
            ks = (List<Ksiazki>)q.list();
            tx.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return ks;
        
    }

    public List Files(int bn) {
        List<Pliki> w = null;
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        try {
            Query q = session.createQuery("from Pliki as w where w.id.isbn="+bn);
            w = (List<Pliki>)q.list();
            tx.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return w;
    }

    public List Autorzy(int bn) {
        List<Autorzy> w = null;
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        try {
            Query q = session.createQuery("select a.idAutora, a.imie, a.nazwisko from Autorzy as a, KsiazkiAutorzy as ka where a.idAutora = ka.id.idAutora and ka.id.isbn="+bn);
            w = (List<Autorzy>)q.list();
            tx.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return w;
    }

    public String Wydawcy(int id) {
        Wydawcy w = null;
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        try {
            Query q = session.createQuery("from Wydawcy as w where w.idWydawcy="+id);
            w = (Wydawcy)q.uniqueResult();
            tx.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return w.getNazwa();
    }

    void addq(int isbn, int i) {
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        try {
            Query q = session.createQuery("update Ksiazki as k set k.stan=k.stan+"+ i +" where k.isbn="+isbn);
            q.executeUpdate();
            tx.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeStatus(Integer idCzytelnika, int bn, Date data, int status) {
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
        org.hibernate.Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Wypozyczenia stat = (Wypozyczenia)session.get(Wypozyczenia.class, new WypozyczeniaId(idCzytelnika, bn, data));
            if(status==5){
                stat.setDataOddania(new Date());
            }
            stat.setStatus(status);
            tx.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }    

    void deleteUsr(int parseInt) {
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
        org.hibernate.Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Czytelnicy cz = (Czytelnicy) session.get(Czytelnicy.class, parseInt);
            session.delete(cz);
            tx.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    void changeActivate(int id, boolean bb) {
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
        org.hibernate.Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Czytelnicy stat = (Czytelnicy)session.get(Czytelnicy.class,id);
            stat.setAktywny(bb);
            tx.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    void changeStopien(int id, int bb) {
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
        org.hibernate.Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Czytelnicy stat = (Czytelnicy)session.get(Czytelnicy.class,id);
            stat.setStopien(bb);
            tx.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List getCzytelnicy(boolean b) {
        List<Czytelnicy> w = null;
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        try {
            Query q = session.createQuery("from Czytelnicy as c where c.aktywny="+b);
            w = (List<Czytelnicy>)q.list();
            tx.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return w;
    }

    public List AutorzyList() {
        List<Autorzy> w = null;
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        try {
            Query q = session.createQuery("from Autorzy as t");
            w = (List<Autorzy>)q.list();
            tx.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return w;
    }

    public List WydawcyList() {
        List<Wydawcy> w = null;
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        try {
            Query q = session.createQuery("from Wydawcy as t");
            w = (List<Wydawcy>)q.list();
            tx.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return w;
    }

    public List KategorieList() {
        List<Kategorie> w = null;
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        try {
            Query q = session.createQuery("from Kategorie as t");
            w = (List<Kategorie>)q.list();
            tx.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return w;
    }

    public List KsiazkiList() {
        List<Ksiazki> w = null;
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        try {
            Query q = session.createQuery("from Ksiazki as t");
            w = (List<Ksiazki>)q.list();
            tx.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return w;
    }
    public List WypozyczeniaList() {
        List<Wypozyczenia> w = null;
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        try {
            Query q = session.createQuery("from Wypozyczenia as t");
            w = (List<Wypozyczenia>)q.list();
            tx.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return w;
    }

    void addBook(int bn, String title, String opis, int wydawca, int kategoria, int ilosc) {
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        try {
            if(session.get(Ksiazki.class, bn)==null){
                Ksiazki k;
                k = new Ksiazki(bn, title, kategoria, ilosc, ilosc, new Date(), wydawca, opis);
                session.persist(k);
                tx.commit();
            }
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    void deleteB(int bn) {
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        try {
            Ksiazki k = (Ksiazki) session.get(Ksiazki.class, bn);
            if(k!=null){
                session.delete(k);
                tx.commit();
            }
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    void addAutorzyB(int bn, int[] autorzy) {
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        try {
            Ksiazki k = (Ksiazki) session.get(Ksiazki.class, bn);
            if(k!=null){
                for(int c : autorzy){
                    KsiazkiAutorzyId id = new KsiazkiAutorzyId(bn, c);
                    KsiazkiAutorzy ka = new KsiazkiAutorzy(id);
                    session.persist(ka);
                }
                tx.commit();
            }
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    void addTagiB(int bn, int[] tagi) {
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        try {
            Ksiazki k = (Ksiazki) session.get(Ksiazki.class, bn);
            if(k!=null){
                for(int c : tagi){
                    TagiKId id = new TagiKId(c, bn);
                    TagiK ka = new TagiK(id);
                    session.persist(ka);
                }
                tx.commit();
            }
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    void addA(String A, String B) {
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        try {
                Autorzy k;
                k = new Autorzy(A,B);
                session.persist(k);
                tx.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    void deleteA(int bn) {
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        try {
            Autorzy k = (Autorzy) session.get(Autorzy.class, bn);
            if(k!=null){
                session.delete(k);
                tx.commit();
            }
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    void addT(String A) {
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        try {
                Tagi k;
                k = new Tagi(A);
                session.persist(k);
                tx.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    void deleteT(int bn) {
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        try {
            session.get(Tagi.class, bn);
            Tagi k = (Tagi) session.get(Tagi.class,bn);
            if(k!=null){
                session.delete(k);
                tx.commit();
            }
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    void addW(String A) {
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        try {
                Wydawcy k;
                k = new Wydawcy(A);
                session.persist(k);
                tx.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    void deleteW(int bn) {
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        try {
            Wydawcy k = (Wydawcy) session.get(Wydawcy.class, bn);
            if(k!=null){
                session.delete(k);
                tx.commit();
            }
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    void addK(String A, int B) {
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        try {
                Kategorie k;
                k = new Kategorie(A);
                if(B!=0)
                    k.setIdRodzica(B);
                session.persist(k);
                tx.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    void deleteK(int bn) {
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        try {
            Kategorie k = (Kategorie) session.get(Kategorie.class, bn);
            if(k!=null){
                session.delete(k);
                tx.commit();
            }
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    void addM(int id, String A, String B) {
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        try {
                Wiadomosci k;
                k = new Wiadomosci(A, B, new Date());
                if(id!=0)k.setIdWiadomosci(id);
                session.persist(k);
                tx.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    void deleteM(int bn) {
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        try {
            Wiadomosci k = (Wiadomosci) session.get(Wiadomosci.class,bn);
            if(k!=null){
                session.delete(k);
                tx.commit();
            }
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    Wiadomosci getWiadomosc(int bn) {
        Wiadomosci k = null;
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        try {
            k = (Wiadomosci)session.get(Wiadomosci.class, bn);
                tx.commit();
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return k;
    }

    void editM(int idM, String oM, String tM) {
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
        org.hibernate.Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Wiadomosci stat = (Wiadomosci)session.get(Wiadomosci.class,idM);
            stat.setTytul(tM);
            stat.setTresc(oM);
            tx.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    void editBook(int bn, String title, String opis, int wydawca, int kategoria, int ilosc) {
        
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
        org.hibernate.Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Ksiazki stat = (Ksiazki)session.get(Ksiazki.class,bn);
            stat.setTytul(title);
            stat.setOpis(opis);
            stat.setIdWydawcy(wydawca);
            stat.setStan(ilosc);
            stat.setIlosc(ilosc);
            stat.setIdKategori(kategoria);
            tx.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    void deleteTA(int bn) {
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        try {
            Query q = session.createQuery("delete TagiK as t where t.id.isbn="+bn);
            q.executeUpdate();
            tx.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    void deleteTAA(int bn) {
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        try {
            Query q = session.createQuery("delete KsiazkiAutorzy as t where t.id.isbn="+bn);
            q.executeUpdate();
            tx.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    void deleteP(int bn) {
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        try {
            Query q = session.createQuery("delete Pliki as t where t.id.isbn="+bn);
            q.executeUpdate();
            tx.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

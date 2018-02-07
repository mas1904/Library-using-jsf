/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wyp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.servlet.http.Part;

/**
 *
 * @author Łukasz
 */
@Named(value = "bookC")
@SessionScoped
public class bookC implements Serializable{

    /**
     * Creates a new instance of bookC
     */
    String title="";
    int isbn=0;
    int ilosc=0;
    String opis="";
    int[] autorzy;
    int[] tagi;
    int wydawca=0;
    int kategoria=0;
    int filesC=-1;
    String noA;
    String imA;
    String naW;
    String naK;
    int naKP=0;

    public int getNaKP() {
        return naKP;
    }

    public void setNaKP(int naKP) {
        this.naKP = naKP;
    }
    String naT;
    String tM;
    String oM;
    String search;
    String a="";
    String k="";
    String tyt="";
    String t="";
    public String getSearch() {
        return search;
    }
    public void sear() throws IOException{
        search+=',';
        a="";
        k="";
        tyt="";
        t="";
        boolean y=false;
        if(search.indexOf("a=")!=-1){
            this.a = search.substring(search.indexOf("a=")).substring(2,search.indexOf(","));
            y=true;
        }if(search.indexOf("k=")!=-1){
            this.k = search.substring(search.indexOf("k=")).substring(2,search.indexOf(","));
            y=true;
        }if(search.indexOf("tag=")!=-1){
            this.t = search.substring(search.indexOf("tag=")).substring(4,search.indexOf(","));
            y=true;
        }if(search.indexOf("tyt=")!=-1){
            this.tyt = search.substring(search.indexOf("tyt=")).substring(4,search.indexOf(","));
            y=true;
        }
        if(y==false)
            this.tyt = search.substring(0, search.length()-1);
        
        FacesContext context = FacesContext.getCurrentInstance();
        String k = context.getExternalContext().getRequestContextPath()+"/search";
        context.getExternalContext().redirect(k);   
        
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getK() {
        return k;
    }

    public void setK(String k) {
        this.k = k;
    }

    public String getTyt() {
        return tyt;
    }

    public void setTyt(String tyt) {
        this.tyt = tyt;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }
    public void setSearch(String search) {
        this.search = search;
    }
    Part obraz;
    Part[] files = new Part[5];
    String[] filn = new String[5];

    public Part[] getFiles() {
        return files;
    }

    public void setFiles(Part[] files) throws IOException {
        this.files = files;
        
    }

    public Part getObraz() {
        return obraz;
    }

    public void setObraz(Part obraz) throws IOException {
        this.obraz = obraz;
    }

    public int getFilesC() {
        return filesC;
    }

    public void setFilesC(int filesC) {
        this.filesC = filesC;
    }
    int idM=0;
    
    public String uploadA(int ind, String nazwa) throws IOException {
        InputStream inputStream = files[ind].getInputStream();      
        
        FileOutputStream outputStream = new FileOutputStream("C:\\Users\\Łukasz\\Documents\\NetBeansProjects\\Projekt jsf\\web\\file\\"+isbn+"\\"+nazwa+"."+getFilename(files[ind]));
        stuff stuff = new stuff();
        stuff.addF(ind,isbn,nazwa,getFilename(files[ind]));
        byte[] buffer = new byte[4096];        
        int bytesRead = 0;
        while(true) {                        
            bytesRead = inputStream.read(buffer);
            if(bytesRead > 0) {
                outputStream.write(buffer, 0, bytesRead);
            }else {
                break;
            }                       
        }
        
        outputStream.close();
        inputStream.close();
        
        return "success";
        
    }
    public String upload() throws IOException {
        InputStream inputStream = obraz.getInputStream();      
        
        FileOutputStream outputStream = new FileOutputStream("C:\\Users\\Łukasz\\Documents\\NetBeansProjects\\Projekt jsf\\web\\img\\books\\"+isbn+".jpg");
         
        byte[] buffer = new byte[4096];        
        int bytesRead = 0;
        while(true) {                        
            bytesRead = inputStream.read(buffer);
            if(bytesRead > 0) {
                outputStream.write(buffer, 0, bytesRead);
            }else {
                break;
            }                       
        }
        
        outputStream.close();
        inputStream.close();
        
        return "success";
    }
 
    private static String getFilename(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                String a = filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1).substring(filename.lastIndexOf('.') + 1); // MSIE fix.
                
                return a;
            }
        }
        return null;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public int getIlosc() {
        return ilosc;
    }

    public void setIlosc(int ilosc) {
        this.ilosc = ilosc;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public int[] getAutorzy() {
        return autorzy;
    }

    public void setAutorzy(int[] autorzy) {
        this.autorzy = autorzy;
    }

    public int[] getTagi() {
        return tagi;
    }

    public void setTagi(int[] tagi) {
        this.tagi = tagi;
    }

    public int getWydawca() {
        return wydawca;
    }

    public void setWydawca(int wydawca) {
        this.wydawca = wydawca;
    }

    public int getKategoria() {
        return kategoria;
    }

    public void setKategoria(int kategoria) {
        this.kategoria = kategoria;
    }

    public String getNoA() {
        return noA;
    }

    public void setNoA(String noA) {
        this.noA = noA;
    }

    public String getImA() {
        return imA;
    }

    public void setImA(String imA) {
        this.imA = imA;
    }

    public String getNaW() {
        return naW;
    }

    public void setNaW(String naW) {
        this.naW = naW;
    }

    public String getNaK() {
        return naK;
    }

    public void setNaK(String naK) {
        this.naK = naK;
    }

    public String getNaT() {
        return naT;
    }

    public void setNaT(String naT) {
        this.naT = naT;
    }
    public bookC() {
    }
    public void dodaj() throws IOException{
        stuff stuff = new stuff();
        new File("C:\\Users\\Łukasz\\Documents\\NetBeansProjects\\Projekt jsf\\web\\file\\"+isbn).mkdir();
        stuff.addBook(isbn,title,opis,wydawca,kategoria,ilosc);
        stuff.addAutorzyB(isbn,autorzy);
        stuff.addTagiB(isbn,tagi);
        if(obraz!=null)
            upload();
        for(int i=0;i < files.length;i++){
            if(files[i]!=null)
            uploadA(i,filn[i]);
        }
        filesC=-1;
        referer();
    }

    public String[] getFiln() {
        return filn;
    }

    public void setFiln(String[] filn) {
        this.filn = filn;
    }
    public void addA() throws IOException{
        stuff stuff = new stuff();
        
        stuff.addA(imA,noA);
        
        referer();
    }
    public void addT() throws IOException{
        stuff stuff = new stuff();
        
        stuff.addT(naT);
        
        referer();
    }
    public void addK() throws IOException{
        stuff stuff = new stuff();
        if(naKP==0)
            naKP=0;
        stuff.addK(naK,naKP);
        naKP=0;
        referer();
    }
    public void addW() throws IOException{
        stuff stuff = new stuff();
        
        stuff.addW(naW);
        
        referer();
    }
    public void edytujp(int bn) throws IOException{
        stuff stuff = new stuff();
        Ksiazki k = stuff.KsiazkaById(bn);
        isbn = k.getIsbn();
        title=k.getTytul();
        ilosc=k.getIlosc();
        opis=k.getOpis();
        List j = stuff.Autorzy(bn);
        List jj = stuff.TagiList(bn);
        int i = 0;
        this.autorzy = new int[j.size()];
        this.tagi = new int[jj.size()];
        
        for( Object a : j){
            Object[] b = (Object[]) a;
            int bb = Integer.parseInt(b[0].toString());
            autorzy[i]=bb;
            i++;
        }
        i = 0;
        for( Object a : jj){
            Object[] b = (Object[]) a;
            int bb = Integer.parseInt(b[0].toString());
            tagi[i]=bb;
            i++;
        }
        //autorzy=stuff.Autorzy(bn).toArray;
        //tagi=stuff.TagiList(bn);
        wydawca=k.getIdWydawcy();
        kategoria=k.getIdKategori();
        
        referer();
    }
    public void edytuj() throws IOException{
        stuff stuff = new stuff();
        stuff.editBook(isbn,title,opis,wydawca,kategoria,ilosc);
        stuff.deleteTA(isbn);
        stuff.deleteTAA(isbn);
        stuff.deleteP(isbn);
        stuff.addAutorzyB(isbn,autorzy);
        stuff.addTagiB(isbn,tagi);
        referer();
    }
    public void editMp(int bn) throws IOException{
        stuff stuff = new stuff();
        Wiadomosci k = stuff.getWiadomosc(bn);
        tM = k.getTytul();
        oM =k.getTresc();
        idM=bn;
        referer();
    }

    public int getIdM() {
        return idM;
    }

    public void setIdM(int idM) {
        this.idM = idM;
    }
    public void editM() throws IOException{
        stuff stuff = new stuff();
        stuff.editM(idM,oM, tM);
        idM=0;
        referer();
    }
    public void czysc() throws IOException{
        title="";
        isbn=0;
        ilosc=0;
        opis="";
        wydawca=0;
        kategoria=0;
        autorzy=null;
        tagi=null;
        referer();
    }
    
    public void delete() throws IOException{
        Map<String, String> k = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        stuff stuff = new stuff();
        stuff.deleteB(Integer.parseInt(k.get("isbn")));
        stuff.deleteTA(Integer.parseInt(k.get("isbn")));
        stuff.deleteTAA(Integer.parseInt(k.get("isbn")));
        referer();
    }
    
    public void deleteA(int a) throws IOException{
        stuff stuff = new stuff();
        stuff.deleteA(a);
        referer();
    }
    public void deleteW(int a) throws IOException{
        stuff stuff = new stuff();
        stuff.deleteW(a);
        referer();
    }
    public void deleteK(int a) throws IOException{
        stuff stuff = new stuff();
        stuff.deleteK(a);
        referer();
    }
    public void deleteT(int a) throws IOException{
        stuff stuff = new stuff();
        stuff.deleteT(a);
        referer();
    }
    
    public void deleteM(int a) throws IOException{
        stuff stuff = new stuff();
        stuff.deleteM(a);
        referer();
    }
    
    public void addM() throws IOException{
        stuff stuff = new stuff();
        stuff.addM(0,tM,oM);
        referer();
    }

    public String gettM() {
        return tM;
    }

    public void settM(String tM) {
        this.tM = tM;
    }

    public String getoM() {
        return oM;
    }

    public void setoM(String oM) {
        this.oM = oM;
    }
    
    public void addC() throws IOException{
        if(filesC<4)filesC++;
        referer();
    }
    public void referer() throws IOException{
        Map<String, Object> k = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> kk = context.getExternalContext().getRequestHeaderMap();
        String kkk = kk.get("referer");
        context.getExternalContext().redirect(kkk.replaceFirst("http://localhost:8080", ""));
    }
    
}

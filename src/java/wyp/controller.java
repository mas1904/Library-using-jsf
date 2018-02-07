/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wyp;

import cart.ShoppingCart;
import cart.ShoppingCartItem;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import org.primefaces.model.TreeNode;

/**
 *
 * @author Łukasz
 */
@Named(value = "controller")
@SessionScoped
public class controller implements Serializable {

    /**
     * Creates a new instance of controller
     */
    DataModel mesL;
    DataModel NewsL;
    DataModel TagiLista;
    DataModel Files;
    ShoppingCart cart;
    Czytelnicy czytelnik;

    public Czytelnicy getCzytelnik() {
        return czytelnik;
    }

    public void setCzytelnik(Czytelnicy czytelnik) {
        this.czytelnik = czytelnik;
    }
    int StopienCzyt;

    public int getStopienCzyt() {
        return StopienCzyt;
    }

    public void setStopienCzyt(int StopienCzyt) {
        this.StopienCzyt = StopienCzyt;
    }

    public String[][] getStopnie() {
        return Stopnie;
    }

    public void setStopnie(String[][] Stopnie) {
        this.Stopnie = Stopnie;
    }
    String[][] Stopnie = {{"1","Użytkownik"},{"2","Pracownik"},{"3","Admin"}};

    public DataModel getFiles() {
        return Files;
    }
    
    public Czytelnicy getCzytelnikById(int id) {
        stuff stuff = new stuff();
        czytelnik = stuff.CzytelnikById(id); 
        return czytelnik;
    }
    public void setFiles(DataModel Files) {
        this.Files = Files;
    }
    TreeNode root;
    TreeNode singleSelectedTreeNode;
    
    public TreeNode getSingleSelectedTreeNode() {
            return singleSelectedTreeNode;
    }
    public void onNodeSelect(){
        System.out.println("sada");
    }
    public void setSingleSelectedTreeNode(TreeNode singleSelectedTreeNode) {
            this.singleSelectedTreeNode = singleSelectedTreeNode;
    }
        
    public void newRoot() {
        stuff stuff = new stuff();
        this.root = stuff.KategorieLista();
    }
    
    public TreeNode getRoot() {
            newRoot();
        return root;
    }
 
    public void setRoot(TreeNode root) {
        this.root = root;
    }
    
    public DataModel getNewsL(int c) {
        News news = new News();
        
            NewsL = new ListDataModel(news.getNewsLista(c));
        
        
        return NewsL;
    }
    public DataModel getKsiazkiBy(int k, int w, int t, int a){
        stuff stuff = new stuff();
        
        return new ListDataModel(stuff.KsiazkiListaBy(k,w,t,a));
    }
    public DataModel getKsiazkiByy(String k, String w, String t, String a){
        stuff stuff = new stuff();
        
        return new ListDataModel(stuff.KsiazkiListaByy(k,w,t,a));
    }
    public DataModel getCzytelnicy(boolean b){
        stuff stuff = new stuff();
        
        return new ListDataModel(stuff.getCzytelnicy(b));
    }
    public Ksiazki getKsiazkaById(int isbn){
        stuff stuff = new stuff();
        return stuff.KsiazkaById(isbn);
    }
    public DataModel getWypozyczeniaById(int isbn){
        stuff stuff = new stuff();
        return new ListDataModel(stuff.getWypozyczeniaById(isbn));
    }
    public DataModel getTagiLista() {
        stuff stuff = new stuff();
            TagiLista = new ListDataModel(stuff.TagiLista());
        return TagiLista;
    }
    public String getKategorieById(int id){
        if(id == 0) return "";
        stuff stuff = new stuff();
        return stuff.KategorieById(id);
    }
    
    public DataModel getTagiList(int isbn) {
        stuff stuff = new stuff();
        return new ListDataModel(stuff.TagiList(isbn));
    }
    
    public String getTagiById(int id){
        stuff stuff = new stuff();
        return stuff.TagiById(id);
        
    }
    public String getWydawcyById(int id){
        if(id == 0) return "";
        stuff stuff = new stuff();
        return stuff.Wydawcy(id);
        
    }
    public DataModel getAutorzyList(int isbn) {
        stuff stuff = new stuff();
        return new ListDataModel(stuff.Autorzy(isbn));
    }
    
    public DataModel getAutorzyList() {
        stuff stuff = new stuff();
        return new ListDataModel(stuff.AutorzyList());
    }
    public DataModel getWydawcyList() {
        stuff stuff = new stuff();
        return new ListDataModel(stuff.WydawcyList());
    }
    public DataModel getKategorieList() {
        stuff stuff = new stuff();
        return new ListDataModel(stuff.KategorieList());
    }
    public DataModel getKsiazki() {
        stuff stuff = new stuff();
        return new ListDataModel(stuff.KsiazkiList());
    }
    
    public DataModel getWypozyczenia(){
        stuff stuff = new stuff();
        return new ListDataModel(stuff.WypozyczeniaList());
        
    }
    public boolean getFiless(){
        stuff stuff = new stuff();
        Map<String, String> k = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Files =  new ListDataModel(stuff.Files(Integer.parseInt(k.get("id"))));
        
        if(Files == null)
            System.out.println();
        return Files != null;
    }
    
    public DataModel getMesL(int c) {
        Mes mes = new Mes();
            mesL = new ListDataModel(mes.getMesLista(c));
        return mesL;
    }
    
    public void changeStyle(int C) throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().put("styl", C);
        String k = context.getExternalContext().getRequestContextPath();
        context.getExternalContext().redirect(k);   
    }
    
    public void addToCart(int isbn) throws IOException{
        Map<String, Object> k = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        if (k.get("user") != null) {
            if(cart == null){
                cart = new ShoppingCart();
                k.put("cart", cart);
            }
            if(isbn > 0){
                stuff stuff = new stuff();
                Ksiazki ksiazka = stuff.KsiazkaById(isbn);
                if(cart.addItem(ksiazka)!=0)
                    stuff.addq(ksiazka.getIsbn(),-1);

            }
        }
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> kk = context.getExternalContext().getRequestHeaderMap();
        String kkk = kk.get("referer");
        context.getExternalContext().redirect(kkk.replaceFirst("http://localhost:8080", ""));
    }
    
    public void change_status(int isbn,Date data,int status) throws IOException{
        Map<String, Object> k = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        if (k.get("user") != null) {
            if(isbn > 0){
                stuff stuff = new stuff();
                Czytelnicy cz = (Czytelnicy)k.get("user");
                stuff.changeStatus(cz.getIdCzytelnika(),isbn,data,status);
                if(status == 5)
                    stuff.addq(isbn,1);
            }
        }
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> kk = context.getExternalContext().getRequestHeaderMap();
        String kkk = kk.get("referer");
        context.getExternalContext().redirect(kkk.replaceFirst("http://localhost:8080", ""));
    }
    public void change_status(int id, int isbn,Date data,int status) throws IOException{
        Map<String, Object> k = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

                stuff stuff = new stuff();
                stuff.changeStatus(id,isbn,data,status);

        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> kk = context.getExternalContext().getRequestHeaderMap();
        String kkk = kk.get("referer");
        context.getExternalContext().redirect(kkk.replaceFirst("http://localhost:8080", ""));
    }
    public void activate(int b) throws IOException{
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> kk = context.getExternalContext().getRequestHeaderMap();
        Map<String, String> id = context.getExternalContext().getRequestParameterMap();
        stuff stuff = new stuff();
        boolean bb = true;
        if(b==0)bb=false;
        stuff.changeActivate(Integer.parseInt(id.get("id")),bb);
        String kkk = kk.get("referer");
        context.getExternalContext().redirect(kkk.replaceFirst("http://localhost:8080", ""));
    }
    public void changeStopien() throws IOException{
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> kk = context.getExternalContext().getRequestHeaderMap();
        Map<String, String> id = context.getExternalContext().getRequestParameterMap();
        stuff stuff = new stuff();
        stuff.changeStopien(Integer.parseInt(id.get("id")),StopienCzyt);
        String kkk = kk.get("referer");
        context.getExternalContext().redirect(kkk.replaceFirst("http://localhost:8080", ""));
    }
    public void block(int b) throws IOException{
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> kk = context.getExternalContext().getRequestHeaderMap();
        Map<String, String> id = context.getExternalContext().getRequestParameterMap();
        stuff stuff = new stuff();
        boolean bb = true;
        if(b==0)bb=false;
        stuff.changeBlock(Integer.parseInt(id.get("id")),bb);
        String kkk = kk.get("referer");
        context.getExternalContext().redirect(kkk.replaceFirst("http://localhost:8080", ""));
    }
    public void delete() throws IOException{
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> kk = context.getExternalContext().getRequestHeaderMap();
        Map<String, String> id = context.getExternalContext().getRequestParameterMap();
        stuff stuff = new stuff();
        
        stuff.deleteUsr(Integer.parseInt(id.get("id")));
        String kkk = kk.get("referer");
        context.getExternalContext().redirect(kkk.replaceFirst("http://localhost:8080", ""));
    }
    public void delFromCart(int isbn) throws IOException{
        Map<String, Object> k = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        if (k.get("user") != null) {
            if(cart == null){
                cart = new ShoppingCart();
                k.put("cart", cart);
            }
            if(isbn > 0){
                stuff stuff = new stuff();
                Ksiazki ksiazka = stuff.KsiazkaById(isbn);
                if(cart.update(ksiazka,"0")!=0)
                    stuff.addq(ksiazka.getIsbn(),1);

            }
        }
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> kk = context.getExternalContext().getRequestHeaderMap();
        String kkk = kk.get("referer");
        context.getExternalContext().redirect(kkk.replaceFirst("http://localhost:8080", ""));
    }
    
    public void zamow() throws IOException{
        Map<String, Object> k = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        if (k.get("user") != null) {
            if(cart != null){
                List<ShoppingCartItem> items;
                items = cart.getItems();
                Czytelnicy id = (Czytelnicy) k.get("user");
                int aid = 0;
                stuff stuff = new stuff();
                for (ShoppingCartItem scItem : items) {
                        aid = stuff.addBooks(id.getIdCzytelnika(),scItem.getProduct().getIsbn());
                }
                cart.clear();
            }
        }
        //TO DO sada
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> kk = context.getExternalContext().getRequestHeaderMap();
        String kkk = kk.get("referer");
        context.getExternalContext().redirect(kkk.replaceFirst("http://localhost:8080", ""));
    }
    
    public void addToQueue(int isbn){
        
    }
    
    public controller() {
        
    }
    
}

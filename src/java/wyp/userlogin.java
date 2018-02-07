/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wyp;

import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Łukasz
 */
@Named(value = "userlogin")
@SessionScoped
public class userlogin implements Serializable{

    /**
     * Creates a new instance of userlogin
     */
    private String username;
    
    private String imie="";
    private String haslo="";
    private String haslop="";
    private String nazw="";
    private String miasto="";
    private String maill="";

    public String getMaill() {
        return maill;
    }

    public void setMaill(String maill) {
        this.maill = maill;
    }
    
    public void register() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        boolean good = true;
        if(imie.equals("")) {
            good = false;
            context.addMessage("reg:imie", new FacesMessage(FacesMessage.SEVERITY_ERROR,"Imie nie może być puste",null));
        }if(haslo.equals("")) {
            good = false;
            context.addMessage("reg:haslo", new FacesMessage(FacesMessage.SEVERITY_ERROR,"Hasło nie może być puste",null));
        }if(!haslop.equals(haslo)) {
            good = false;
            context.addMessage("reg:haslop", new FacesMessage(FacesMessage.SEVERITY_ERROR,"Hasła się nie zgadzają",null));
        }if(nazw.equals("")) {
            good = false;
            context.addMessage("reg:nazw", new FacesMessage(FacesMessage.SEVERITY_ERROR,"Nazwisko nie może być puste",null));
        }if(miasto.equals("")) {
            good = false;
            context.addMessage("reg:miasto", new FacesMessage(FacesMessage.SEVERITY_ERROR,"Miasto nie może być puste",null));
        }if(ulica.equals("")) {
            good = false;
            context.addMessage("reg:ulica", new FacesMessage(FacesMessage.SEVERITY_ERROR,"Ulica nie może być pusta",null));
        }if(maill.equals("")) {
            good = false;
            context.addMessage("reg:maill", new FacesMessage(FacesMessage.SEVERITY_ERROR,"Mail nie może być pusty",null));
        }
        if(good == true){
            System.out.println();
            UserService userServicee = new UserService();
            Czytelnicy user = userServicee.findU(maill);
            if(user == null){
                userServicee.add(imie,nazw,miasto,ulica,maill,haslo);
                context.addMessage("reg", new FacesMessage(FacesMessage.SEVERITY_INFO,"Konto zostało utworzone, musi ono poczekać na aktywację u admina",null));
            }
            else {
                context.addMessage("reg:maill", new FacesMessage(FacesMessage.SEVERITY_ERROR,"Konto z takim mailem już istnieje",null));
            }
        }
        String k = context.getExternalContext().getRequestContextPath() + "/register";
        context.getExternalContext().getFlash().setKeepMessages(true);
        context.getExternalContext().redirect(k);
    }
    
    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getHaslo() {
        return haslo;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }

    public String getHaslop() {
        return haslop;
    }

    public void setHaslop(String haslop) {
        this.haslop = haslop;
    }

    public String getNazw() {
        return nazw;
    }

    public void setNazw(String nazw) {
        this.nazw = nazw;
    }

    public String getMiasto() {
        return miasto;
    }

    public void setMiasto(String miasto) {
        this.miasto = miasto;
    }

    public String getUlica() {
        return ulica;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
    private String ulica;
    private String mail;
    
    public String getMessages(String msg) {
        Iterator<FacesMessage> message = FacesContext.getCurrentInstance().getMessages(msg);
        if(message.hasNext())
            return message.next().getSummary();
        return "";
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    private String password;
    
    private UserService userService;
    
    public userlogin() {
        if(userService == null)
             userService = new UserService();
    }
    
    public void login() throws IOException{        
        Czytelnicy user = userService.find(username, password);
        FacesContext context = FacesContext.getCurrentInstance();
        
        if(user == null || password == null){
            context.addMessage("loginp:login", new FacesMessage(FacesMessage.SEVERITY_ERROR,"Złe dane, spróbuj ponownie",null));
            
            password=null;
            
        }
        else {
            if(user.isAktywny()==true)
                context.getExternalContext().getSessionMap().put("user", user);
            else {
                context.addMessage("loginp:login", new FacesMessage(FacesMessage.SEVERITY_ERROR,"Konto nie zostało aktywowane",null));
                
                password=null;
            }
        }
        String k = context.getExternalContext().getRequestContextPath();
        context.getExternalContext().getFlash().setKeepMessages(true);
        context.getExternalContext().redirect(k);
                
    }
    
    public void logout() throws IOException{
        FacesContext context = FacesContext.getCurrentInstance();
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        String k = context.getExternalContext().getRequestContextPath();
        context.getExternalContext().redirect(k); 
    }
    
}

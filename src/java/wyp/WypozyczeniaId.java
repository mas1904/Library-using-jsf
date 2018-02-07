package wyp;
// Generated 2016-05-30 11:24:48 by Hibernate Tools 4.3.1


import java.util.Date;

/**
 * WypozyczeniaId generated by hbm2java
 */
public class WypozyczeniaId  implements java.io.Serializable {


     private int idCzytelnika;
     private int isbn;
     private Date dataWypozyczenia;

    public WypozyczeniaId() {
    }

    public WypozyczeniaId(int idCzytelnika, int isbn, Date dataWypozyczenia) {
       this.idCzytelnika = idCzytelnika;
       this.isbn = isbn;
       this.dataWypozyczenia = dataWypozyczenia;
    }
   
    public int getIdCzytelnika() {
        return this.idCzytelnika;
    }
    
    public void setIdCzytelnika(int idCzytelnika) {
        this.idCzytelnika = idCzytelnika;
    }
    public int getIsbn() {
        return this.isbn;
    }
    
    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }
    public Date getDataWypozyczenia() {
        return this.dataWypozyczenia;
    }
    
    public void setDataWypozyczenia(Date dataWypozyczenia) {
        this.dataWypozyczenia = dataWypozyczenia;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof WypozyczeniaId) ) return false;
		 WypozyczeniaId castOther = ( WypozyczeniaId ) other; 
         
		 return (this.getIdCzytelnika()==castOther.getIdCzytelnika())
 && (this.getIsbn()==castOther.getIsbn())
 && ( (this.getDataWypozyczenia()==castOther.getDataWypozyczenia()) || ( this.getDataWypozyczenia()!=null && castOther.getDataWypozyczenia()!=null && this.getDataWypozyczenia().equals(castOther.getDataWypozyczenia()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + this.getIdCzytelnika();
         result = 37 * result + this.getIsbn();
         result = 37 * result + ( getDataWypozyczenia() == null ? 0 : this.getDataWypozyczenia().hashCode() );
         return result;
   }   


}


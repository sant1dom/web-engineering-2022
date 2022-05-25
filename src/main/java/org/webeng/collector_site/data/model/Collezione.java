package org.webeng.collector_site.data.model;

import org.webeng.framework.data.DataItem;

import java.time.LocalDate;
import java.util.List;

public interface Collezione extends DataItem<Integer> {
     String getTitolo();
    
     void setTitolo(String titolo);
    
     String getPrivacy();
    
     void setPrivacy(String privacy);
    
     Utente getUtente();
    
     void setUtente(Utente utente);

     LocalDate getDataCreazione();

     void setDataCreazione(LocalDate dataCreazione);
    
     List<Disco> getDischi();
    
     void setDischi(List<Disco> dischi);
    
     List<Utente> getUtentiCondivisi();

     void setUtentiCondivisi(List<Utente> utentiCondivisi);
    
     void addUtenteCondiviso(Utente utente);

     void  removeUtenteCondiviso(Utente utente);
}

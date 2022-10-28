package controllers;

import Interface.ProduitInterface;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;
import model.ProduitModel;

@Named
@SessionScoped
public class ListProduit implements Serializable{
    @EJB
    private ProduitInterface produit;
    public List<ProduitModel> getAll() {
        return produit.getAll();
    };
}

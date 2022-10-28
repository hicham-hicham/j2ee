package Service;

import Interface.ProduitInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Stateless;
import model.ProduitModel;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.Record;

import static org.neo4j.driver.Values.parameters;

@Local(ProduitInterface.class)
@Stateless
public class Produit implements ProduitInterface, AutoCloseable {
    @Override
    public void close() throws Exception {
    driver.close();
    }
    private final Driver driver;

    public Produit() {
        driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "12345"));
    }
    
    @Override
    public List<ProduitModel> getAll() {
        try (Session session = driver.session()) {
            List<Record> list = session.run(
                    "match (ee:Produit) return ee.title,ee.price,ee.rate,ee.img,ee.oldprice,ee.shipp").list();
                        /*"CREATE (a:Greeting) " + "SET a.message = $message "
                                + "RETURN a.message + ', from node ' + id(a)",
                        parameters("message", "message"));*/
                        List<ProduitModel> produitmodellist = new ArrayList<>();
                        for (Record produit:list){
                            String title = produit.get("ee.title").asString();
                            String rate = produit.get("ee.rate").asString();
                            String price = produit.get("ee.price").asString();
                            String img = produit.get("ee.img").asString();
                            String oldprice = produit.get("ee.oldprice").asString();
                            String shipp = produit.get("ee.ship").asString();
                            if(rate.equals("null")){
                                rate = "";
                                //System.out.println("########################   "+produit.get("ee.rate").asString());
                            }
                            if(oldprice.equals("null")){
                                oldprice = "";
                                //System.out.println("########################   "+produit.get("ee.rate").asString());
                            }
                            if(shipp.equals("null")){
                                shipp = "";
                                //System.out.println("########################   "+produit.get("ee.rate").asString());
                            }
                            /*price = price.replace("Dhs", "");
                            price = price.replace(",", "");
                            price = price.replace(".", ",");
                            double _price = Double.parseDouble("8.5 ");
                            */
                            ProduitModel newproduit = new ProduitModel(title,price,rate,img,oldprice,shipp);
                            
                            produitmodellist.add(newproduit);
                        }
                        /*Collections.sort(produitmodellist,new Comparator<ProduitModel>(){
                                @Override
                                public int compare(ProduitModel p1 , ProduitModel p2){
                                    return Double.compare(p1.getPrice(), p2.getPrice());
                                }
                            });*/
                        return produitmodellist;
        }
    };
    
}

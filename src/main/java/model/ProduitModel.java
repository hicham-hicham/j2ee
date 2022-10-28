package model;

public class ProduitModel {
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    private String price;
    private String rate;
    private String img;
    private String oldprice;
    private String shipp;

    public String getOldprice() {
        return oldprice;
    }

    public void setOldprice(String oldprice) {
        this.oldprice = oldprice;
    }

    public String getShipp() {
        return shipp;
    }

    public void setShipp(String shipp) {
        this.shipp = shipp;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public ProduitModel(String title,String price, String rate, String img,String oldprice, String shipp) {
        this.title= title;
        this.price = price;
        this.rate = rate;
        this.img = img;
        this.oldprice = oldprice;
        this.shipp = shipp;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

}

package pack;

import java.util.ArrayList;

/**
 * Klasa służąca do utworzenia obiektów klasy Product i dodaniu ich do listy artykułów sprzedawanych w sklepie
 */
public class Articles
{
    /**utworzony typ wyliczeniowy dla przyporządkowania artykułów*/
    public enum type
    {
        nabiał, pieczywo, chemia, napoje, owoce, warzywa, słodycze, wędliny, AGD;
    }
    /**utworzenie listy produktów*/
    ArrayList<Product> list = new ArrayList<Product>();

    /**
     * Konstuktor główny klasy Articles
     */
    public Articles()
    {
        //utworzenie obiektów klasy Product
        Product banany = new Product("Banany",10,6.5,type.owoce,5,5.7);
        Product woda = new Product("Woda",10,2.3,type.napoje,1.5,2);
        Product chleb = new Product("Chleb",10,4.1,type.pieczywo,3,3.6);
        Product mleko = new Product("Mleko",10,3.5,type.nabiał,2.5,3);
        Product maslo = new Product("Masło",10,7,type.nabiał,5.5,6.5);
        Product szynka = new Product("Szynka",10,13,type.wędliny,10,12);
        Product ser = new Product("Ser żółty",10,5,type.nabiał,4,4.5);
        Product czekolada = new Product("Czekolada",10,4.5,type.słodycze,3.5,4);

        //dodanie utworzonych obiektów klasy Product do listy
        list.add(banany);
        list.add(woda);
        list.add(chleb);
        list.add(mleko);
        list.add(maslo);
        list.add(szynka);
        list.add(ser);
        list.add(czekolada);
    }

    /**
     * Metoda usuwająca artykuł o danym indeksie z listy "list"
     * @param index - nr indeksu artykułu, który ma zostać usunięty z listy
     */
    public void deleting(int index)
    {
        list.remove(index);
    }
}

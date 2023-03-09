package pack;

import java.util.ArrayList;

/**
 * Ta klasa służy do utworzenia listy produktów możliwych do dodania do asortymentu sklepu
 */
public class Replacements
{
    /**utworzenie listy artykułów możliwych do dodania do asortymentu sklepu*/
    ArrayList<Product> replace = new ArrayList<Product>();

    /**
     * Konstuktor główny klasy Replacements
     */
    public Replacements()
    {
        //utworzenie produktów możliwych do dodania
        Product ziemniaki = new Product("Dynie",0,4, Articles.type.warzywa,3,3.5);
        Product pomidory = new Product("Pomidory",0,12, Articles.type.warzywa,10,11);
        Product szampon = new Product("Szampon",0,13, Articles.type.chemia,10,12);

        //dodanie produktów do listy replace
        replace.add(ziemniaki);
        replace.add(pomidory);
        replace.add(szampon);
    }

    /**
     * Metoda usuwająca artykuł o danym indeksie z listy "replace"
     * @param index - nr indeksu artykułu, który ma zostać usunięty z listy
     */
    public void deleting(int index)
    {
        replace.remove(index);
    }

    /**
     * Metoda przyporządkowująca bonus za osiągnięcie 2 poziomu
     */
    public void first_bonus()
    {
        //AGD
        Product odkurzacz = new Product("Odkurzacz",4,450, Articles.type.AGD,340,400);
        Product suszarka = new Product("Suszarka",4,70, Articles.type.AGD,55,65);
        Product blender = new Product("Blender",4,140, Articles.type.AGD,100,120);
        Product patelnia = new Product("Patelnia",4,80, Articles.type.AGD,60,70);
        Product garnki = new Product("Garnki",4,250, Articles.type.AGD,180,220);

        //umieszczenie sprzętu AGD na liście możliwych do dodania towarów
        replace.add(odkurzacz);
        replace.add(suszarka);
        replace.add(blender);
        replace.add(patelnia);
        replace.add(garnki);
    }
}

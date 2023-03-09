package pack;

/**
 * Klasa służąca do monitorowania obecnego budżetu sklepu oraz zamawiania dostaw artykułów
 */
public class Delivery
{
    /**obecny budget sklepu*/
    public double budget=500;

    /**
     * Metoda umożliwiająca obliczenie kosztu zamówienia
     * @param amount - ilość zamawianych sztuk danego artykułu
     * @param price - cena dostawy danego artykułu
     */
    public void order(int amount, double price)
    {
        /**koszt danej dostawy*/
        double cost = amount * price;
        if(cost<=budget) //dostawa jest mozliwa jezeli budget jest wiekszy lub równy jej kosztowi
        {
            budget=budget-cost;
        }
    }
}

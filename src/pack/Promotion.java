package pack;

/**
 * Ta klasa służy do monitorowania i zwiększania popularności sklepu
 */
public class Promotion
{
    /** aktualny poziom popularności sklepu, która wpływa na ilość sprzedawanych towarów*/
    public int popularity=0;

    /**
     * Metoda inkrementująca poziom popularności sklepu
     */
    public void promote()
    {
        popularity=popularity+2;
    }
}

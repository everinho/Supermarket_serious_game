package pack;

/**
 * To klasa produktów, które po utworzeniu dodawane są do odpowiedniej listy artykułów
 */
public class Product
{
    /**nazwa produktu*/
    String name;
    /**aktualna ilość produktu w asortymencie sklepu*/
    int amount;
    /**aktualna cena produktu*/
    double price;
    /**optymalna rynkowa cena sprzedaży produktu*/
    double optimal_price;
    /**cena za dostawę produktu*/
    double delivery_price;
    /**typ wyliczeniowy (utworzony w klasie Articles) segregujący produkty*/
    Articles.type type;

    /**
     * Konstuktor klasy Product, służący do tworzenia artykułów sklepowych
     * @param name - nazwa produktu
     * @param amount - aktualna ilość produktu w asortymencie sklepu
     * @param price - aktualna cena produktu
     * @param type - rodzaj produktu
     * @param delivery_price - cena za dostawę produktu
     * @param optimal_price - optymalna rynkowa cena sprzedaży produktu
     */
    Product(String name, int amount, double price, Articles.type type, double delivery_price, double optimal_price)
    {
        this.name = name;
        this.amount = amount;
        this.price = price;
        this.type = type;
        this.delivery_price=delivery_price;
        this.optimal_price=optimal_price;
    }
}

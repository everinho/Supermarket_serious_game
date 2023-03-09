package pack;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DecimalFormat;

/**
 * Okno główne gry, główny obszay graficzny gry
 */
public class Game_interface
{
    /**utworzenie obiektu klasy frame*/
    JFrame frame = new JFrame("NowyNaturalny supermarket");
    //utworzene buttonów,labeli i progress baru składających się na okienko interfejsu gry
    JLabel icon_label = new JLabel();
    JLabel art_label = new JLabel("Artykuły sklepowe");
    JLabel budget_label = new JLabel();
    JLabel shop_label = new JLabel();
    JLabel first_poziom = new JLabel();
    JLabel next_poziom = new JLabel();
    JLabel tytul_label = new JLabel();
    JLabel ludzik_label = new JLabel();
    JButton menu_button = new JButton();
    JButton bilans_button = new JButton();
    JButton dodaj_button = new JButton();
    JButton usun_button = new JButton();
    JButton dostawa_button = new JButton();
    JButton reguluj_button = new JButton();
    JButton dzien_button = new JButton();
    JButton prop_button = new JButton();
    JButton promuj_button = new JButton();
    JProgressBar poziom_bar = new JProgressBar();
    /**utworzenie obiektu klasy Articles*/
    Articles articles = new Articles();
    /**utworzenie obiektu klasy Replacements*/
    Replacements replacements = new Replacements();
    /**utworzenie obiektu klasy Delivery*/
    Delivery delivery = new Delivery();
    JLabel budget_ilosc = new JLabel(delivery.budget+" [zł]");
    /**rząd, w którym znajduje się artykuł zaznaczony z tabeli*/
    int row;
    /**formatowanie budżetu do 2 miejsc po przecinku*/
    private static final DecimalFormat df = new DecimalFormat("0.00");
    /**sumuje ilosc inkrementacji poziomu w progress barze*/
    int ile=0;
    /**do przechowywania poprzedniej wartości progress baru przed dodaniem kolejnych punktów doświadczenia*/
    int a=0;
    /**utworzenie obiektu klasy Levelling*/
    Levelling levelling = new Levelling();
    /**utworzenie obiektu klasy Promotion*/
    Promotion promotion = new Promotion();
    /**czy zatrudniono pracownika przy osiagnieciu 4 poziomu; 1 = zatrudniony, 0 = niezatrudniony*/
    int zatrudnienie;
    /**przy zmianie na 1 dodanie komunikatu o promocji do podpowiedzi*/
    int komunikat_promocja=0;
    /**przy zmianie na 1 dodanie komunikatu o dodaniu nowych artykułów do podpowiedzi*/
    int komunikat_dodawanie=0;
    /**przy zmianie na 1 dodanie komunikatu o braku towarów do podpowiedzi*/
    int komunikat_brak_towarow=0;
    /**przy zmianie na 1 dodanie komunikatu o przecenie do podpowiedzi*/
    int komunikat_przecena=0;
    /**koszt zamawianej dostawy*/
    double cost=0;
    /**zliczanie zasymulowanych dni*/
    int dzien=0;

    /***/
    /**
     * Główny konstruktor klasy interfeju gry
     */
    Game_interface()
    {
        UIManager.put("OptionPane.messageFont", new Font("System", Font.BOLD, 16));//zmiana czcionki w JOptionPane

        ImageIcon background_image = new ImageIcon("src\\images\\tlo.png");
        icon_label.setBounds(0,0,1280,1024);
        icon_label.setIcon(background_image);

        ImageIcon icon = new ImageIcon("src\\images\\shop_icon.png");
        frame.setIconImage(icon.getImage());

        ImageIcon shop_image = new ImageIcon("src\\images\\shop.png");
        shop_label.setBounds(340,340,600,600);
        shop_label.setIcon(shop_image);

        ImageIcon tytul = new ImageIcon("src\\images\\headline.png");
        tytul_label.setBounds(340,10,600,100);
        tytul_label.setIcon(tytul);

        ImageIcon ludzik = new ImageIcon("src\\images\\ludzik.png");
        ludzik_label.setBounds(40,520,250,420);
        ludzik_label.setIcon(ludzik);

        ImageIcon menu = new ImageIcon("src\\images\\menu.png");
        menu_button.setBounds(1110,910,130,60);
        menu_button.setFocusable(false);
        menu_button.setIcon(menu);
        menu_button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        /**Dodaj obsługę zdarzeń - wciśnięcie klawisza "Menu"*/
        menu_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                click_sound(); //dźwięk kliknięcia przycisku
                frame.dispose(); //zamknięcie okna interfejsu
                Menu menu = new Menu(); //powrót do Menu
            }
        });

        art_label.setBounds(1075,200,190,110);
        art_label.setFont(new Font("Segoe UI",Font.BOLD,17));

        String header[]= new String[]{"Nazwa","Typ","Cena","Ilość","Cena dostawy"}; //nagłówek tabeli
        DefaultTableModel dtm = new DefaultTableModel(header,0);
        JTable tabela = new JTable(); //tabela wyświetlająca listę artykułów
        tabela.setModel(dtm);
        tabela.setFont(new Font("Segoe UI",Font.BOLD,14));
        tabela.setBackground(Color.pink);
        tabela.setRowHeight(30);
        ImageIcon bilans = new ImageIcon("src\\images\\button_stan.png");
        bilans_button.setIcon(bilans);
        bilans_button.setBounds(1075,300,160,70);
        bilans_button.setFocusable(false);
        bilans_button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        /**Dodaj obsługę zdarzeń - wciśnięcie klawisza "Stan"*/
        bilans_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                click_sound(); //dźwięk kliknięcia przycisku
                dtm.setRowCount(0); //zresetowanie licznika rzędu
                for(int i = 0; i<articles.list.size(); i++)
                {
                    //zapisanie danych o artykułach z listy
                    Object[] obs = {articles.list.get(i).name,articles.list.get(i).type,articles.list.get(i).price,articles.list.get(i).amount,articles.list.get(i).delivery_price};
                    dtm.addRow(obs); //dodanie do modelu tabeli danych
                }
                JOptionPane.showMessageDialog(null, new JScrollPane(tabela),"Bilans produktów",JOptionPane.INFORMATION_MESSAGE);
            }
        });

        ImageIcon add = new ImageIcon("src\\images\\button_dodaj.png");
        DefaultTableModel model = new DefaultTableModel(header,0);
        JTable dodawanie = new JTable(); //tabela wyświetlająca listę artykułów możliwych do dodania
        dodawanie.setModel(model);
        dodawanie.setShowGrid(false);
        dodawanie.setFont(new Font("Segoe UI",Font.BOLD,14));
        dodawanie.setBackground(Color.pink);
        dodawanie.setRowHeight(30);
        dodaj_button.setIcon(add);
        dodaj_button.setBounds(1075,380,160,70);
        dodaj_button.setFocusable(false);
        dodaj_button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        /**Dodaj obsługę zdarzeń - wciśnięcie klawisza "Dodaj"*/
        dodaj_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                click_sound(); //dźwięk kliknięcia przycisku
                model.setRowCount(0); //zresetowanie licznika rzędu
                for(int j = 0; j<replacements.replace.size(); j++)
                {
                    //zapisanie danych o artykułach z listy
                    Object[] obs = {replacements.replace.get(j).name,replacements.replace.get(j).type,replacements.replace.get(j).price,replacements.replace.get(j).amount,replacements.replace.get(j).delivery_price};
                    model.addRow(obs); //dodanie do modelu tabeli danych
                }
                JOptionPane.showMessageDialog(null, new JScrollPane(dodawanie), "Dodawanie artykułu", JOptionPane.INFORMATION_MESSAGE);
                row=dodawanie.getSelectedRow(); //przypisanie do zmiennej row indeksu wybranego rzędu
                if(row>=0)
                {
                    int dialogButton = JOptionPane.YES_NO_OPTION;
                    int dialogResult = JOptionPane.showConfirmDialog(frame,"Dodaj ten artykuł","Dodaj",dialogButton);
                    if(dialogResult==0)
                    {
                        articles.list.add(replacements.replace.get(row));
                        model.removeRow(row);
                        replacements.deleting(row);
                        model.setRowCount(0);
                    }
                }
            }
        });

        ImageIcon usun = new ImageIcon("src\\images\\button_usun.png");
        usun_button.setIcon(usun);
        usun_button.setBounds(1075,460,160,70);
        usun_button.setFocusable(false);
        usun_button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        /**Dodaj obsługę zdarzeń - wciśnięcie klawisza "Usun"*/
        usun_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                click_sound(); //dźwięk kliknięcia przycisku
                dtm.setRowCount(0); //zresetowanie licznika rzędu
                for(int i = 0; i<articles.list.size(); i++)
                {
                    //zapisanie danych o artykułach z listy
                    Object[] obs = {articles.list.get(i).name,articles.list.get(i).type,articles.list.get(i).price,articles.list.get(i).amount,articles.list.get(i).delivery_price};
                    dtm.addRow(obs);
                }
                JOptionPane.showMessageDialog(null, new JScrollPane(tabela), "Usuwanie artykułu", JOptionPane.INFORMATION_MESSAGE);
                row=tabela.getSelectedRow(); //przypisanie do zmiennej row indeksu wybranego rzędu
                if(row>=0)
                {
                    int dialogButton = JOptionPane.YES_NO_OPTION;
                    int dialogResult = JOptionPane.showConfirmDialog(frame,"Usuń ten artykuł","Usuń",dialogButton);
                    if(dialogResult==0)
                    {
                        replacements.replace.add(articles.list.get(row));
                        dtm.removeRow(row);
                        articles.deleting(row);
                        dtm.setRowCount(0);
                        for(int i = 0; i<articles.list.size(); i++)
                        {
                            Object[] obs = {articles.list.get(i).name,articles.list.get(i).type,articles.list.get(i).price,articles.list.get(i).amount};
                            dtm.addRow(obs);
                        }
                    }
                }
            }
        });

        ImageIcon deliver = new ImageIcon("src\\images\\button_dostawa.png"); //nagłówek tabeli
        String delivery_header[]= new String[]{"Nazwa","Ilość","Cena zamówienia"};
        DefaultTableModel dostawa_model = new DefaultTableModel(delivery_header,0);
        JTable dostawa = new JTable();
        dostawa.setModel(dostawa_model);
        dostawa.setShowGrid(false);
        dostawa.setFont(new Font("Segoe UI",Font.BOLD,14));
        dostawa.setBackground(Color.pink);
        dostawa.setRowHeight(30);
        dostawa_button.setIcon(deliver);
        dostawa_button.setBounds(1075,540,160,70);
        dostawa_button.setFocusable(false);
        dostawa_button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        /**Dodaj obsługę zdarzeń - wciśnięcie klawisza "Dostawa"*/
        dostawa_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                click_sound(); //dźwięk kliknięcia przycisku
                dostawa_model.setRowCount(0); //zresetowanie licznika rzędu
                for(int i = 0; i<articles.list.size(); i++)
                {
                    //zapisanie danych o artykułach z listy
                    Object[] obs = {articles.list.get(i).name,articles.list.get(i).amount,articles.list.get(i).delivery_price};
                    dostawa_model.addRow(obs);
                }
                JOptionPane.showMessageDialog(null, new JScrollPane(dostawa), "Zamawianie dostawy", JOptionPane.INFORMATION_MESSAGE);
                row=dostawa.getSelectedRow(); //przypisanie do zmiennej row indeksu wybranego rzędu
                if(row>=0)
                {
                    String amount = JOptionPane.showInputDialog("Ile sztuk chcesz zamówić?");
                    dostawa.getModel().setValueAt(amount, dostawa.getSelectedRow(), 1);
                    int amount_int = Integer.parseInt(amount);
                    cost=amount_int*articles.list.get(row).delivery_price;
                    if(cost<delivery.budget)
                    {
                        delivery.order(amount_int,articles.list.get(row).delivery_price);
                        articles.list.get(row).amount+=amount_int;
                        budget_ilosc.setText(df.format(delivery.budget)+" [zł]");
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null,"Nie posiadasz wystarczających funduszy na przeprowadzenie takiej dostawy!","WSKAZÓWKA",JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });

        ImageIcon reguluj = new ImageIcon("src\\images\\button_reguluj-ceny.png");
        String columns[]= new String[]{"Nazwa","Cena"};
        DefaultTableModel price_model = new DefaultTableModel(columns,0);
        JTable przecena = new JTable();
        przecena.setModel(price_model);
        przecena.setShowGrid(false);
        przecena.setFont(new Font("Segoe UI",Font.BOLD,14));
        przecena.setBackground(Color.pink);
        przecena.setRowHeight(30);
        reguluj_button.setIcon(reguluj);
        reguluj_button.setBounds(1075,620,160,70);
        reguluj_button.setFocusable(false);
        reguluj_button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        /**Dodaj obsługę zdarzeń - wciśnięcie klawisza "Reguluj ceny"*/
        reguluj_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                click_sound(); //dźwięk kliknięcia przycisku
                price_model.setRowCount(0); //zresetowanie licznika rzędu
                for(int i = 0; i<articles.list.size(); i++)
                {
                    //zapisanie danych o artykułach z listy
                    Object[] obs = {articles.list.get(i).name,articles.list.get(i).price};
                    price_model.addRow(obs);
                }
                JOptionPane.showMessageDialog(null, new JScrollPane(przecena), "Regulowanie cen", JOptionPane.INFORMATION_MESSAGE);
                row=przecena.getSelectedRow(); //przypisanie do zmiennej row indeksu wybranego rzędu
                if(row>=0)
                {
                    String price = JOptionPane.showInputDialog("Wprowadź nową cenę");
                    przecena.getModel().setValueAt(price, przecena.getSelectedRow(), 1);
                    Double price_db = Double.parseDouble(price);
                    articles.list.get(row).price=price_db;
                }
            }
        });

        ImageIcon sym = new ImageIcon("src\\images\\button_symuluj-dzien.png");
        dzien_button.setIcon(sym);
        dzien_button.setBounds(540,240,200,80);
        dzien_button.setFocusable(false);
        dzien_button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        /**Dodaj obsługę zdarzeń - wciśnięcie klawisza "Symuluj dzien"*/
        dzien_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                day_simulation_sound(); //dźwięk kliknięcia przycisku
                dzien++;
                double przychody=0;
                double zakupione;
                double ilosc_kupiona;
                double promocja=(double) promotion.popularity/10;
                double bonus = (double) levelling.current_bonus/10;
                if(dzien>2 && promotion.popularity==0)
                {
                    if(komunikat_promocja==0)
                    {
                        komunikat_promocja=1; //dodanie komunikatu o promocji do podpowiedzi
                        prop_button.setBorder(BorderFactory.createLineBorder(Color.red, 3));
                        komunikat_sound(); //dźwięk przychodzącego komunikatu
                    }
                }
                for(int i=0;i<articles.list.size();i++)
                {
                    if(articles.list.get(i).amount>0)
                    {
                        if(articles.list.get(i).price<=articles.list.get(i).optimal_price) //większy sprzedawalność przy optymalnej cenie
                        {
                            ilosc_kupiona=0.2+promocja+bonus;
                            if(ilosc_kupiona>1) ilosc_kupiona=0.7;
                        }
                        else //mniejsza sprzedawalność przy nieoptymalnej cenie
                        {
                            ilosc_kupiona=0.1+promocja+bonus;
                            if(ilosc_kupiona>1) ilosc_kupiona=0.7;
                        }
                        if(articles.list.get(i).amount<5)
                        {
                            zakupione = articles.list.get(i).price;
                            przychody=przychody+zakupione;
                            double cos = 1;
                            articles.list.get(i).amount= articles.list.get(i).amount - (int) cos;
                        }
                        else {
                            zakupione = (articles.list.get(i).amount*ilosc_kupiona)*articles.list.get(i).price;
                            przychody=przychody+zakupione;
                            double cos = articles.list.get(i).amount*ilosc_kupiona;
                            articles.list.get(i).amount= articles.list.get(i).amount - (int) cos;
                        }
                    }
                    else {
                        if(zatrudnienie==1) //jeśli został zatrudniony pracownik - automatyczne uzupełnianie wyprzedanych towarów
                        {
                            delivery.order(10,articles.list.get(i).delivery_price);
                            articles.list.get(i).amount+=10;
                            budget_ilosc.setText(delivery.budget+" [zł]");
                        }
                        else {
                            if(komunikat_brak_towarow==0)
                            {
                                komunikat_brak_towarow=1; //dodanie komunikatu o braku towarów do podpowiedzi
                                prop_button.setBorder(BorderFactory.createLineBorder(Color.red, 3));
                                komunikat_sound(); //dźwięk przychodzącego komunikatu
                            }
                        }
                    }
                }
                if(przychody==0)
                {
                    if(komunikat_brak_towarow==0)
                    {
                        promotion.popularity=0; //wyzerowanie popularności sklepu w przypadku dnia bez przychodów
                        komunikat_brak_towarow=1; //dodanie komunikatu o braku towarów do podpowiedzi
                        prop_button.setBorder(BorderFactory.createLineBorder(Color.red, 3));
                        komunikat_sound(); //dźwięk przychodzącego komunikatu
                    }
                }
                if(przychody>0 && przychody<80) //jeśli niezadowalające przychody
                {
                    if(komunikat_przecena==0)
                    {
                        komunikat_przecena=1; //dodanie komunikatu o przecenie do podpowiedzi
                        prop_button.setBorder(BorderFactory.createLineBorder(Color.red, 3));
                        komunikat_sound(); //dźwięk przychodzącego komunikatu
                    }
                }
                if(przychody>0)
                {
                    fill_bar(); //wypełnianie progress baru, jeżeli zarobiono cokolwiek danego dnia
                }
                delivery.budget = delivery.budget + przychody;
                if(zatrudnienie==1)
                {
                    delivery.budget=delivery.budget-100; //pensja dzienna pracownika
                }
                budget_ilosc.setText(df.format(delivery.budget)+" [zł]");
            }
        });

        ImageIcon propozycje = new ImageIcon("src\\images\\button_podpowiedzi.png");
        prop_button.setIcon(propozycje);
        prop_button.setBounds(45,300,200,70);
        prop_button.setFocusable(false);
        prop_button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        /**Dodaj obsługę zdarzeń - wciśnięcie klawisza "Podpowiedzi"*/
        prop_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                click_sound(); //dźwięk kliknięcia przycisku
                if(komunikat_dodawanie==1) //jeżeli dodano do podpowiedzi komunikat o dodawaniu artykułów
                {
                    JOptionPane.showMessageDialog(null,"W zakładce \"DODAJ\" w sekcji ARTYKUŁÓW znajdują sie produkty, które możesz wprowadzić do asortymentu sklepu! ","WSKAZÓWKA",JOptionPane.INFORMATION_MESSAGE);
                    komunikat_dodawanie=0;
                    prop_button.setBorder(null);
                }
                if(komunikat_promocja==1) //jeżeli dodano do podpowiedzi komunikat o promocji
                {
                    JOptionPane.showMessageDialog(null,"W celu zwiększenia popularności sklepu możesz za 100 zł opłacić jego promocję!","WSKAZÓWKA",JOptionPane.INFORMATION_MESSAGE);
                    komunikat_promocja=0;
                    prop_button.setBorder(null);
                }
                if(komunikat_przecena==1) //jeżeli dodano do podpowiedzi komunikat o przecenie
                {
                    JOptionPane.showMessageDialog(null,"Jeżeli artykuły nie sprzedają się wedle twoich oczekiwań możesz zmienić ich cenę na niższą, to zdecydowanie nie umknie uwadze potencjalnych klientów!","WSKAZÓWKA",JOptionPane.INFORMATION_MESSAGE);
                    komunikat_przecena=0;
                    prop_button.setBorder(null);
                }
                if(komunikat_brak_towarow==1) //jeżeli dodano do podpowiedzi komunikat o braku towarów
                {
                    JOptionPane.showMessageDialog(null,"W sklepie wyczerpały się resztki niektórych towarów, zorganizuj ich dostawę!","WSKAZÓWKA",JOptionPane.INFORMATION_MESSAGE);
                    komunikat_brak_towarow=0;
                    prop_button.setBorder(null);
                }

            }
        });

        ImageIcon promocja = new ImageIcon("src\\images\\button_promuj-sklep.png");
        promuj_button.setIcon(promocja);
        promuj_button.setBounds(45,400,200,70);
        promuj_button.setFocusable(false);
        promuj_button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        /**Dodaj obsługę zdarzeń - wciśnięcie klawisza "Promuj sklep"*/
        promuj_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                click_sound(); //dźwięk kliknięcia przycisku
                if(delivery.budget>=100) //promocja sklepu jeżeli budżet wynosi conajmniej 100zł
                {
                    promotion.promote();
                    delivery.budget-=100;
                    budget_ilosc.setText(df.format(delivery.budget)+" [zł]");
                }
                else
                {
                    //komunikat o braku środków na promocję
                    JOptionPane.showMessageDialog(null,"Nie posiadasz wystarczających funduszy, aby poczynić promocję sklepu","Promocja niemożliwa",JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        ImageIcon budget = new ImageIcon("src\\images\\budget.png");
        budget_label.setBounds(20,10,160,70);
        budget_label.setIcon(budget);

        budget_ilosc.setBounds(40,100,140,30);
        budget_ilosc.setFont(new Font("Segoe",Font.BOLD,20));

        poziom_bar.setValue(0);
        poziom_bar.setBounds(290,140,700,30);
        poziom_bar.setStringPainted(true);
        poziom_bar.setString("Poziom sklepu");
        poziom_bar.setForeground(Color.GREEN);

        ImageIcon first = new ImageIcon("src\\images\\number-1.png");
        first_poziom.setBounds(225,120,64,64);
        first_poziom.setIcon(first);

        ImageIcon next = new ImageIcon("src\\images\\number-2.png");
        next_poziom.setBounds(995,120,64,64);
        next_poziom.setIcon(next);

        //ustawienie właściwości okna interfejsu
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280,1024);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setVisible(true);

        //dodanie wszystkich elementów do okna
        frame.add(menu_button);
        frame.add(art_label);
        frame.add(bilans_button);
        frame.add(dodaj_button);
        frame.add(usun_button);
        frame.add(dostawa_button);
        frame.add(reguluj_button);
        frame.add(dzien_button);
        frame.add(prop_button);
        frame.add(promuj_button);
        frame.add(budget_label);
        frame.add(poziom_bar);
        frame.add(first_poziom);
        frame.add(next_poziom);
        frame.add(shop_label);
        frame.add(tytul_label);
        frame.add(ludzik_label);
        frame.add(budget_ilosc);
        frame.add(icon_label);
    }

    /**
     * Metoda uzupełniająca progress bar punktami doświadczenia i zdobotymi bonusami
     */
    public void fill_bar()
    {
        /**ilość dodawanych punktów doświadczenia + bonus do pd + popularność sklepu*/
        int pd=20+promotion.popularity+levelling.current_bonus;
        if(ile==0)
        {
            poziom_bar.setValue(pd);
            a=pd;
        }
        else
        {
            poziom_bar.setValue(a+pd);
            a=a+pd;
        }
        ile++;
        if(a==100) //po zapełnieniu progress bar awans na kolejny poziom
        {
            levelling.current_level++; //zwiększenie aktualnego poziomu o 1
            levelling.next_level(); //bonus za kolejny poziom

            if(levelling.current_level==2) //jesli osiagnieto 2 poziom
            {
                //wyświetlenie komunikatu o osiągnięciu 2 poziomu
                JOptionPane.showMessageDialog(null,levelling.text, "OSIĄGNIĘTO AWANS NA 2 POZIOM", JOptionPane.INFORMATION_MESSAGE);
                replacements.first_bonus(); //bonus za 2 poziom

                //podmiana ikon obecnego poziomu i następnego poziomu
                ImageIcon first = new ImageIcon("src\\images\\number-2.png");
                first_poziom.setBounds(225,120,64,64);
                first_poziom.setIcon(first);

                ImageIcon next = new ImageIcon("src\\images\\number-3.png");
                next_poziom.setBounds(995,120,64,64);
                next_poziom.setIcon(next);
            }
            if(levelling.current_level==3) //jesli osiagnieto 3 poziom
            {
                promotion.promote(); //darmowa promocja jako bonus
                //wyświetlenie komunikatu o osiągnięciu 3 poziomu
                JOptionPane.showMessageDialog(null,levelling.text, "OSIĄGNIĘTO AWANS NA 3 POZIOM", JOptionPane.INFORMATION_MESSAGE);

                //podmiana ikon obecnego poziomu i następnego poziomu
                ImageIcon first = new ImageIcon("src\\images\\number-3.png");
                first_poziom.setBounds(225,120,64,64);
                first_poziom.setIcon(first);

                ImageIcon next = new ImageIcon("src\\images\\number-4.png");
                next_poziom.setBounds(995,120,64,64);
                next_poziom.setIcon(next);
            }
            if(levelling.current_level==4) //jesli osiagnieto 4 poziom
            {
                //wyświetlenie komunikatu o osiągnięciu 4 poziomu, zapytanie o zatrudnienie pracownika
                zatrudnienie = JOptionPane.showConfirmDialog(null,levelling.text, "OSIĄGNIĘTO AWANS NA 4 POZIOM", JOptionPane.YES_NO_OPTION);
                if(zatrudnienie==JOptionPane.YES_OPTION) //jeśli wybrano YES zatrudnia się pracownika
                {
                    JOptionPane.showMessageDialog(null,"Pomyślnie zatrudniono pracownika!","NOWY PRACOWNIK",JOptionPane.INFORMATION_MESSAGE);
                    zatrudnienie=1;
                }
                //podmiana ikon obecnego poziomu i następnego poziomu
                ImageIcon first = new ImageIcon("src\\images\\number-4.png");
                first_poziom.setBounds(225,120,64,64);
                first_poziom.setIcon(first);

                ImageIcon next = new ImageIcon("src\\images\\number-5.png");
                next_poziom.setBounds(995,120,64,64);
                next_poziom.setIcon(next);
            }
            if(levelling.current_level==5) //jesli osiagnieto 5 poziom
            {
                //wyświetlenie komunikatu o osiągnięciu 5 - ostatniego poziomu
                JOptionPane.showMessageDialog(null,levelling.endgame(), "OSIĄGNIĘTO AWANS NA 5 - OSTATNI POZIOM", JOptionPane.INFORMATION_MESSAGE);

                frame.dispose(); //zamknięcie okna interfejsu
                Menu menu = new Menu(); //powrót do Menu
            }
            poziom_bar.setValue(0);
            ile=0;
        }
        else if(a>100) //po zapełnieniu progress bar awans na kolejny poziom
        {
            levelling.current_level++; //zwiększenie aktualnego poziomu o 1
            levelling.next_level(); //bonus za kolejny poziom

            if(levelling.current_level==2) //jesli osiagnieto 2 poziom
            {
                //wyświetlenie komunikatu o osiągnięciu 2 poziomu
                JOptionPane.showMessageDialog(null,levelling.text, "OSIĄGNIĘTO AWANS NA 2 POZIOM", JOptionPane.INFORMATION_MESSAGE);
                replacements.first_bonus();

                //podmiana ikon obecnego poziomu i następnego poziomu
                ImageIcon first = new ImageIcon("src\\images\\number-2.png");
                first_poziom.setBounds(225,120,64,64);
                first_poziom.setIcon(first);

                ImageIcon next = new ImageIcon("src\\images\\number-3.png");
                next_poziom.setBounds(995,120,64,64);
                next_poziom.setIcon(next);
            }
            if(levelling.current_level==3) //jesli osiagnieto 3 poziom
            {
                promotion.promote(); //darmowa promocja jako bonus
                //wyświetlenie komunikatu o osiągnięciu 3 poziomu
                JOptionPane.showMessageDialog(null,levelling.text, "OSIĄGNIĘTO AWANS NA 3 POZIOM", JOptionPane.INFORMATION_MESSAGE);

                //podmiana ikon obecnego poziomu i następnego poziomu
                ImageIcon first = new ImageIcon("src\\images\\number-3.png");
                first_poziom.setBounds(225,120,64,64);
                first_poziom.setIcon(first);

                ImageIcon next = new ImageIcon("src\\images\\number-4.png");
                next_poziom.setBounds(995,120,64,64);
                next_poziom.setIcon(next);
            }
            if(levelling.current_level==4) //jesli osiagnieto 4 poziom
            {
                //wyświetlenie komunikatu o osiągnięciu 4 poziomu, zapytanie o zatrudnienie pracownika
                zatrudnienie = JOptionPane.showConfirmDialog(null,levelling.text, "OSIĄGNIĘTO AWANS NA 4 POZIOM", JOptionPane.YES_NO_OPTION);

                if(zatrudnienie==JOptionPane.YES_OPTION) //jeśli wybrano YES zatrudnia się pracownika
                {
                    JOptionPane.showMessageDialog(null,"Pomyślnie zatrudniono pracownika!","NOWY PRACOWNIK",JOptionPane.INFORMATION_MESSAGE);
                    zatrudnienie=1;
                }
                //podmiana ikon obecnego poziomu i następnego poziomu
                ImageIcon first = new ImageIcon("src\\images\\number-4.png");
                first_poziom.setBounds(225,120,64,64);
                first_poziom.setIcon(first);

                ImageIcon next = new ImageIcon("src\\images\\number-5.png");
                next_poziom.setBounds(995,120,64,64);
                next_poziom.setIcon(next);
            }
            if(levelling.current_level==5) //jesli osiagnieto 5 poziom
            {
                //wyświetlenie komunikatu o osiągnięciu 5 - ostatniego poziomu
                JOptionPane.showMessageDialog(null,levelling.endgame(), "OSIĄGNIĘTO AWANS NA 5 - OSTATNI POZIOM", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose(); //zamknięcie okna interfejsu
                Menu menu = new Menu(); //powrót do Menu
            }
            a=a-100;
            poziom_bar.setValue(a);
            ile=1;
        }
    }

    /**
     * Metoda odtwarzająca dźwięk klikniętego klawisza
     */
    public void click_sound()
    {
        try
        {
            File click = new File("src\\audio\\click.wav");
            Clip c = AudioSystem.getClip();
            c.open(AudioSystem.getAudioInputStream(click));
            c.start();
        }catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Metoda odtwarzająca dźwięk symulacji dnia
     */
    public void day_simulation_sound()
    {
        try
        {
            File simulation = new File("src\\audio\\symulacja.wav");
            Clip c = AudioSystem.getClip();
            c.open(AudioSystem.getAudioInputStream(simulation));
            c.start();
        }catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Metoda odtwarzająca dźwięk przychodzącego komunikatu
     */
    public void komunikat_sound()
    {
        try
        {
            File komunikat = new File("src\\audio\\komunikat.wav");
            Clip c = AudioSystem.getClip();
            c.open(AudioSystem.getAudioInputStream(komunikat));
            c.start();
        }catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}

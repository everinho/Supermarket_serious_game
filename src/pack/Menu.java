package pack;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Okno Menu gry
 */
public class Menu implements ActionListener {
    /**utworzenie obiektu klasy frame*/
    JFrame frame = new JFrame("NowyNaturalny supermarket");
    //utworzene buttonów i labeli składających się na okienko menu
    JButton graj = new JButton();
    JButton wyjdz = new JButton();
    JButton pomoc = new JButton();
    JLabel menu_label = new JLabel();
    JLabel icon_label = new JLabel();
    JLabel sklepikarz_label_1 = new JLabel();
    JLabel sklepikarz_label_2 = new JLabel();

    /**treść wyświetlanego komunikatu przy kliknięciu "POMOC"*/
    String pomoc_tekst = "Witaj w grze NowyNaturalny supermarket!\n" +
            "Gra polega na osiąganiu kolejnych poziomów swojego sklepu. Jest 5 poziomów, po osiągnięciu każdego czeka na Ciebie pewna premia lub upominek.\n" +
            "Na poziomy składają się punkty doświadczenia, które możesz uzyskać przez symulowanie kolejnych dni pracy sklepu. \n" +
            "W tym czasie musisz na bieżąco uzupełniać zapasy artykułów, dodawać nowe produkty do swojej oferty oraz ustawiać umiarkowane ceny.\n" +
            "Możesz także przeznaczyć część swoich funduszy na promowanie sklepu, dzięki czemu wzrośnie jego popularność, a tym samym przychody.\n" +
            "Powodzenia!";

    /**
     * Konstuktor główny klasy Menu
     */
    Menu() {
        ImageIcon background_image = new ImageIcon("src\\images\\fotoo.png"); //ustawienie tła menu
        icon_label.setBounds(0, 0, 1280, 1024);
        icon_label.setIcon(background_image);

        ImageIcon icon = new ImageIcon("src\\images\\shop_icon.png"); //ustawienie ikonki okna
        frame.setIconImage(icon.getImage());

        ImageIcon tytul = new ImageIcon("src\\images\\tytul.png"); //ustawienie nagłówku tytułowego
        menu_label.setBounds(240, 100, 800, 120);
        menu_label.setIcon(tytul);

        ImageIcon shopkeeper = new ImageIcon("src\\images\\sklepikarz_menu.png"); //ustawienie grafiki sklepikarza nr1
        sklepikarz_label_1.setBounds(40,470,410,400);
        sklepikarz_label_1.setIcon(shopkeeper);

        ImageIcon lada = new ImageIcon("src\\images\\sklepikarz_menu_2.png"); //ustawienie grafiki sklepikarza nr2
        sklepikarz_label_2.setBounds(840,560,360,420);
        sklepikarz_label_2.setIcon(lada);

        //ustawienie właściwości buttona "GRAJ"
        graj.setBounds(510, 460, 260, 110);
        graj.setFocusable(false);
        graj.addActionListener(this);
        graj.setCursor(new Cursor(Cursor.HAND_CURSOR));
        graj.setOpaque(false);
        graj.setContentAreaFilled(false);
        graj.setBorderPainted(false);

        //ustawienie właściwości buttona "POMOC"
        pomoc.setBounds(510, 585, 260, 110);
        pomoc.setFocusable(false);
        pomoc.addActionListener(this);
        pomoc.setCursor(new Cursor(Cursor.HAND_CURSOR));
        pomoc.setOpaque(false);
        pomoc.setContentAreaFilled(false);
        pomoc.setBorderPainted(false);

        //ustawienie właściwości buttona "WYJDZ"
        wyjdz.setBounds(510, 715, 260, 110);
        wyjdz.setFocusable(false);
        wyjdz.addActionListener(this);
        wyjdz.setCursor(new Cursor(Cursor.HAND_CURSOR));
        wyjdz.setOpaque(false);
        wyjdz.setContentAreaFilled(false);
        wyjdz.setBorderPainted(false);

        //ustawienie właściwości okna
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 1024);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setVisible(true);

        //dodanie elementów okna
        frame.add(graj);
        frame.add(wyjdz);
        frame.add(pomoc);
        frame.add(menu_label);
        frame.add(sklepikarz_label_1);
        frame.add(sklepikarz_label_2);
        frame.add(icon_label);
    }
    /**Dodaj obsługę zdarzeń dla klikania klawiszy Menu*/
    @Override
    public void actionPerformed(ActionEvent e)
    {
        /**dźwięk wciśnięcia klawisza*/
        click_sound();

        if (e.getSource() == graj) //obsługa kliknięcia przycisku "GRAJ"
        {
            frame.dispose();
            Game_interface gi = new Game_interface(); //otworzenie nowego okna gry, rozpoczęcie gry od nowa
        }
        if( e.getSource() == pomoc) //wyświetlenie podstawowych informacji pomocniczych
        {
            UIManager.put("OptionPane.messageFont", new Font("System", Font.BOLD, 17)); //zmiana czcionki w JOptionPane
            //wyświetlenie komunikatu zawierającego podstawowe informacje o grze
            JOptionPane.showMessageDialog(null,pomoc_tekst,"Pomoc",JOptionPane.INFORMATION_MESSAGE);
        }
        if (e.getSource() == wyjdz) //zamknięcie gry z pozycji menu
        {
            frame.dispose();
        }
    }

    /**
     * Metoda odtwarzająca dźwięk klikniętego klawisza
     */
    public void click_sound()
    {
        try {
            File click = new File("src\\audio\\click.wav");
            Clip c = AudioSystem.getClip();
            c.open(AudioSystem.getAudioInputStream(click));
            c.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
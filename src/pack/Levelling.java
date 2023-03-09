package pack;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

/**
 * Klasa służąca do monitorowania awansu na kolejne poziomy
 */

public class Levelling
{
    /**aktualny poziom sklepu, gracz rozpoczyna od poziomu 1*/
    public int current_level=1;
    /**treść komunikatu przy osiągnięciu nowego poziomu*/
    public String text;
    /**aktualny bonus do punktów doświadczenia*/
    public int current_bonus=0;

    /**
     *  Metoda odpowiedzialna za nadawanie bonusów za osiągnięcie następnego poziomu
     */
    public void next_level()
    {
        /**wywołanie funkcji odtwarzającej dźwięk awansu na następny poziom*/
        next_level_sound();
        if(current_level==2)
        {
            current_bonus=current_bonus+1; //zwiększenie bonusu do punktów doświadczenia
            text="UZYSKANO PREMIĘ ZA OSIĄGNIĘCIE 2 POZIOMU\n" +
                    "\n" +
                    "Możliwe są do dodania nowe artykuły z zakresu sprzętu AGD!";
        }
        if(current_level==3)
        {
            current_bonus=current_bonus+1; //zwiększenie bonusu do punktów doświadczenia
            text="UZYSKANO PREMIĘ ZA OSIĄGNIĘCIE 3 POZIOMU\n" +
                    "\n" +
                    "Otrzymano darmową promocję sklepu oraz bonus do punktów doświadczenia!";
        }
        if(current_level==4)
        {
            current_bonus=current_bonus+1; //zwiększenie bonusu do punktów doświadczenia
            text="UZYSKANO PREMIĘ ZA OSIĄGNIĘCIE 4 POZIOMU!\n" +
                    "\n" +
                    "Otrzymano bonus do punktów doświadczenia oraz możliwość zatrudnienia pracownika!\n" +
                    "Będzie on pobierał pensję w wysokości 100zł/dzień oraz będzie odpowiedzialny za zamawianie dostaw w przypadku wyczerpania jakiegoś towaru!\n" +
                    "\n" +
                    "Czy chcesz zatrudnić pracownika?";
        }

    }

    /**
     * Metoda sygnalizująca koniec gry przy osiągnięciu 5 - ostatniego poziomu gry,
     * @return - komunikat zakończenia gry
     */
    public String endgame()
    {
        return "GRATULUJĘ UKOŃCZENIA ROZGRYWKI!";
    }

    /**
     * Metoda odtwarzająca dźwięk osiągnięcia następnego poziomu
     */
    public void next_level_sound()
    {
        try
        {
            File level_up = new File("src\\audio\\level-up.wav");
            Clip c = AudioSystem.getClip();
            c.open(AudioSystem.getAudioInputStream(level_up));
            c.start();
        }catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}

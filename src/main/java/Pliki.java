//import sun.util.calendar.LocalGregorianCalendar;

import java.io.*;
import java.lang.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Pliki {
    public static String[] separate_string(String input, String separator) {
        return input.split(separator);
    }

    public static void main(String[] args) throws FileNotFoundException {

        File forces_file = new File("C:/Users/matys/Desktop/PROGRAMY_STUDIA/INNE/PROPAGACJA/forces_input.txt");
        File elements_file = new File("C:/Users/matys/Desktop/PROGRAMY_STUDIA/INNE/PROPAGACJA/elements_input.txt");
        Map<String, Integer> forces_map = new HashMap<String, Integer>();
        Map<String, Double> elements_map = new HashMap<String, Double>();
        String data = "not declared";
        if (forces_file.exists() && elements_file.exists()) {
            //forces
            Scanner forces = new Scanner(forces_file);
            while (forces.hasNextLine()) {
                //mapka string i int
                String linia = forces.nextLine();
                if (linia.charAt(0) != '#' && linia.charAt(0) != '\n') {
                    String[] dane = separate_string(linia, ": ");
                    forces_map.put(dane[0], Integer.parseInt(dane[1]));
                }
            }
            System.out.println("Siły " + forces_map);

            //elements tłumaczę i objaśniam co tu następuje
            Scanner elements = new Scanner(elements_file); //wiadomo
            while (elements.hasNextLine()) {
                String linia1 = elements.nextLine(); //ppobiera linię
                if (linia1.length() != 0 && linia1.charAt(0) != '#') {
                    //pierwsza część warunku sprawdza czy linia jest pusta
                    //druga część warunku sprawdza czy linia nie zaczyna się od '#'

                    //System.out.println(linia1); // sprawdzenie jak wygląda linia w pliku

                    String[] dane1 = separate_string(linia1, ": "); //rozdzielenie linii na tablicę stringów

                    //System.out.println("1: '"+dane1[0]+"' 2: '"+dane1[1]+"'"); //sprawdzenie jak wygląda tablica Stringów

                    if (!dane1[0].equals("date")) {
                        //jeśli wartość pierwszego elementu stringa to nie jest date -> zapisuje do mapy
                        //System.out.println(dane1[0]);
                        elements_map.put(dane1[0], Double.parseDouble(dane1[1]));
                    } else {
                        //jeśli to jest 'date' -> zapisuje do zmiennej date
                        data = dane1[1];
                    }
                }
            }
            //wypisywanie daty i mapy elementy
            System.out.println("Elementy " + elements_map);
            System.out.println("Data: " + data);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault());
            LocalDateTime date = LocalDateTime.parse(data, formatter);

            //Przypisanie wartości elementom
            int year = date.getYear();
            int month = date.getMonthValue();
            int day = date.getDayOfMonth();
            int hour = date.getHour();
            int min = date.getMinute();
            int sec = date.getSecond();
            double miliSec = date.getNano() / 1000000;
            double sekPlusMili = sec + miliSec / 1000.0;


            double a = elements_map.get("a");                 // semi major axis in meters
            double e = elements_map.get("e");               // eccentricity
            double i = Math.toRadians(elements_map.get("i"));        // inclination
            double omega = Math.toRadians(elements_map.get("omega"));  // perigee argument
            double raan = Math.toRadians(elements_map.get("raan"));   // right ascension of ascending node
            double lM = elements_map.get("lM");  // mean anomaly

            double mass = elements_map.get("mass"); //satellite mass
            double area = elements_map.get("area"); // satellite area

            //Przypisanie wartości sił

            int GMdegree = forces_map.get("GMdegree");
            int GMorder = forces_map.get("GMorder");
            int sun = forces_map.get("sun");
            int moon = forces_map.get("moon");
            int atmosphere = forces_map.get("atmosphere");
            int ATMmodel = forces_map.get("ATMmodel");
            int SRP = forces_map.get("SRP");
            int OT = forces_map.get("OT");
            int RAcc = forces_map.get("RAcc");

        }

    }
}

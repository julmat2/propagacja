import java.io.*;
import java.lang.*;
import java.util.*;

public class Pliki {
    public static String[] separate_string(String input,String separator)
    {
        return input.split(separator);
    }
    public static void main(String[] args) throws FileNotFoundException {

        File forces_file = new File("C:/Users/matys/Desktop/PROGRAMY_STUDIA/INNE/PROPAGACJA/forces_input.txt");
        File elements_file = new File("C:/Users/matys/Desktop/PROGRAMY_STUDIA/INNE/PROPAGACJA/elements_input.txt");

        if(forces_file.exists() && elements_file.exists())
        {
            //forces
            Scanner forces = new Scanner(forces_file);
            while(forces.hasNextLine())
            {
                String linia = forces.nextLine();
                if(linia.charAt(0)!='#' && linia.charAt(0)!='\n')
                {
                    String[] dane = separate_string(linia,": ");
                    int x=0;
                    while(x!=dane.length)
                    {
                        System.out.print(dane[x]+" ");
                        x++;
                    }
                    System.out.print("\n");
                }
            }

            //elements
            Scanner elements = new Scanner(elements_file);
            while(elements.hasNextLine())
            {
                String linia1 = elements.nextLine();
                if(linia1.charAt(0)!='#' && linia1.charAt(0)!='\n')
                {
                    String[] dane1 = separate_string(linia1,": ");
                    int x1=0;
                    while(x1!=dane1.length)
                    {
                        System.out.print(dane1[x1]+" ");
                        x1++;
                    }
                    System.out.print("\n");
                }
            }

        }
    }
}

import org.orekit.data.DataProvidersManager;
import org.orekit.data.DirectoryCrawler;
import org.orekit.frames.Frame;
import org.orekit.frames.FramesFactory;
import org.orekit.orbits.KeplerianOrbit;
import org.orekit.orbits.Orbit;
import org.orekit.orbits.PositionAngle;
import org.orekit.propagation.SpacecraftState;
import org.orekit.propagation.analytical.KeplerianPropagator;
import org.orekit.time.AbsoluteDate;
import org.orekit.time.TimeScale;
import org.orekit.time.TimeScalesFactory;

import java.io.File;


public class Propagator {
    public static void main(String[] args) {

//wczytuję dane z orekit-data
        File orData = new File("C:/Users/matys/Desktop/PROGRAMY_STUDIA/INNE/PROPAGACJA");
        DataProvidersManager dman = DataProvidersManager.getInstance();
        dman.addProvider(new DirectoryCrawler(orData));

//definiuję ramkę odniesienia
        Frame inertialFrame = FramesFactory.getEME2000();

//Ustawiam stan początkowy
        TimeScale utc = TimeScalesFactory.getUTC();
        AbsoluteDate initialDate = new AbsoluteDate(2004, 01, 01, 23, 30, 00.000, utc);

        double mu = 3.986004415e+14;

        double a = 24396159;                 // semi major axis in meters
        double e = 0.72831215;               // eccentricity
        double i = Math.toRadians(7);        // inclination
        double omega = Math.toRadians(180);  // perigee argument
        double raan = Math.toRadians(261);   // right ascension of ascending node
        double lM = 0;                       // mean anomaly

//definiuję początkową orbitę - Keplerian Orbit
        Orbit initialOrbit = new KeplerianOrbit(a, e, i, omega, raan, lM, PositionAngle.MEAN,
                inertialFrame, initialDate, mu);

//Definiuję propagator jako Keplerian Propagator
        KeplerianPropagator kepler = new KeplerianPropagator(initialOrbit);

        double duration = 600.; //czas trwania propagacji [s]
        AbsoluteDate finalDate = initialDate.shiftedBy(duration);
        double stepT = 60.; //rozmiar kroku propagacji [s]
        int cpt = 1; //początkowy numer kroku
        for (AbsoluteDate extrapDate = initialDate;
             extrapDate.compareTo(finalDate) <= 0;
             extrapDate = extrapDate.shiftedBy(stepT)) {
            SpacecraftState currentState = kepler.propagate(extrapDate);
            System.out.println("step " + cpt++);
            System.out.println(" time : " + currentState.getDate());
            System.out.println(" " + currentState.getOrbit());
        }

    }
}



import org.hipparchus.ode.nonstiff.AdaptiveStepsizeIntegrator;
import org.hipparchus.ode.nonstiff.DormandPrince853Integrator;
import org.hipparchus.util.FastMath;
import org.orekit.data.DataProvidersManager;
import org.orekit.data.DirectoryCrawler;
import org.orekit.forces.ForceModel;
import org.orekit.forces.gravity.HolmesFeatherstoneAttractionModel;
import org.orekit.forces.gravity.potential.GravityFieldFactory;
import org.orekit.forces.gravity.potential.NormalizedSphericalHarmonicsProvider;
import org.orekit.frames.Frame;
import org.orekit.frames.FramesFactory;
import org.orekit.orbits.KeplerianOrbit;
import org.orekit.orbits.Orbit;
import org.orekit.orbits.OrbitType;
import org.orekit.orbits.PositionAngle;
import org.orekit.propagation.SpacecraftState;
import org.orekit.propagation.numerical.NumericalPropagator;
import org.orekit.propagation.sampling.OrekitFixedStepHandler;
import org.orekit.time.AbsoluteDate;
import org.orekit.time.TimeScale;
import org.orekit.time.TimeScalesFactory;
import org.orekit.utils.IERSConventions;

import java.io.File;
import java.util.Locale;


public class PropagatorNumeryczny {
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

        // Initial state definition
        SpacecraftState initialState = new SpacecraftState(initialOrbit);


//Definiuję propagator jako Numeric Propagator
        // Adaptive step integrator
// with a minimum step of 0.001 and a maximum step of 1000
        double minStep = 0.001;
        double maxstep = 1000.0;
        double positionTolerance = 10.0;
        OrbitType propagationType = OrbitType.KEPLERIAN;
        double[][] tolerances =
                NumericalPropagator.tolerances(positionTolerance, initialOrbit, propagationType);
        AdaptiveStepsizeIntegrator integrator =
                new DormandPrince853Integrator(minStep, maxstep, tolerances[0], tolerances[1]);

        NumericalPropagator propagator = new NumericalPropagator(integrator);
        propagator.setOrbitType(propagationType);


//dodaję modele sił
        NormalizedSphericalHarmonicsProvider provider =
                GravityFieldFactory.getNormalizedProvider(10, 10);
        ForceModel holmesFeatherstone =
                new HolmesFeatherstoneAttractionModel(FramesFactory.getITRF(IERSConventions.IERS_2010, true), provider);

        propagator.addForceModel(holmesFeatherstone);

        System.out.println("          date                a           e" +
                "           i         \u03c9          \u03a9" +
                "          \u03bd");
     class TutorialStepHandler implements OrekitFixedStepHandler {

//            public void init(final SpacecraftState s0, final AbsoluteDate t) {
//                System.out.println("          date                a           e" +
//                        "           i         \u03c9          \u03a9" +
//                        "          \u03bd");
//            }

            public void handleStep(SpacecraftState currentState, boolean isLast) {
                KeplerianOrbit o = (KeplerianOrbit) OrbitType.KEPLERIAN.convertType(currentState.getOrbit());

                System.out.format(Locale.US, "%s %12.3f %10.8f %10.6f %10.6f %10.6f %10.6f%n",
                        currentState.getDate(),
                        o.getA(), o.getE(),
                        FastMath.toDegrees(o.getI()),
                        FastMath.toDegrees(o.getPerigeeArgument()),
                        FastMath.toDegrees(o.getRightAscensionOfAscendingNode()),
                        FastMath.toDegrees(o.getTrueAnomaly()));
                if (isLast) {
                    System.out.println("this was the last step ");
                    System.out.println();
                }
            }

        }

        propagator.setMasterMode(60., new TutorialStepHandler());
        propagator.setInitialState(initialState);
        SpacecraftState finalState = propagator.propagate(new AbsoluteDate(initialDate, 630.));

        KeplerianOrbit Final = (KeplerianOrbit) OrbitType.KEPLERIAN.convertType(finalState.getOrbit());
        System.out.println("Final state:");
        System.out.format(Locale.US ,"%s %12.3f %10.8f %10.6f %10.6f %10.6f %10.6f%n", Final.getDate(), Final.getA(), Final.getE(), FastMath.toDegrees(Final.getI()), FastMath.toDegrees(Final.getPerigeeArgument()), FastMath.toDegrees(Final.getRightAscensionOfAscendingNode()), FastMath.toDegrees(Final.getTrueAnomaly()));
        }

    }



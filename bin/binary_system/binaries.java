import java.io.File;
import java.io.FileWriter;
import java.io.*;

public class binaries {
	/**
	Now uses the same Body class as VerletCollision
	The Body class in this file is commented out
	(will be deleted later)
	The radius is set to 1 (too small for collision to be detected)
	*/

	public static final int dt = 720;
	public static final double au = 1.496e11; //au in m
	public static final double sm = 1.989e30; //solar mass in kg
	public static final double G = 6.67e-11;

    public static void main (String[] args) throws IOException {
        // settings
        int n = 4;      // number of bodies
        double simTime = 1e9;
        int totalSteps = (int) Math.round(simTime / dt);
		double spread = 7e11;		// determines the square where the bodies are generated

        String dataDir, configDir;
        if (System.getProperty("os.name").equals("Mac OS X")) {
            dataDir = "/Users/DY/Term_Project/bin/binary_system/testdata.data";
            configDir = "/Users/DY/Term_Project/bin/binary_system/sim.cfg";
        }
        else {
            dataDir = "c://Users//alexd//Documents//School//Winter2019//CompSci//TermProject//Term_Project//bin//binary_system//testdata.data";
            configDir = "c://Users//alexd//Documents//School//Winter2019//CompSci//TermProject//Term_Project//bin//binary_system//sim.cfg";
        }

        File file = new File(dataDir);
		FileWriter writer = new FileWriter(file);

		File config = new File(configDir); //davids laptop
        FileWriter cfgWriter = new FileWriter(config);

		// writing config to config file
		cfgWriter.write(Integer.toString(n) + '\n');
		cfgWriter.write(Integer.toString(totalSteps) + '\n');
		cfgWriter.write(Double.toString(spread));
		cfgWriter.close();

        NewBody[] bodies = new NewBody[n];

        bodies[0] = new NewBody(-1.75*au, 0, 0, -13782, 5*sm, 1e7, "Centauri A");
        bodies[1] = new NewBody(1.75*au, 0, 0, 17792, 3*sm, 1e7, "Centauri B");
		bodies[3] = new NewBody(-2.0*au, 0, 0, -13782 + -133144, 6e24, 1e4, "planet");
		bodies[2] = new NewBody(5*au, 0, 0, Math.sqrt((G*8.0*sm)/(5*au)), 6e24, 1e4, "planet2");

        for (int i = 0;i < bodies.length;i++) {
            bodies[i].prime(bodies, dt);
        }

		for (int step = 0; step < totalSteps; step++) {
            for (int i = 0; i < n; i++) {
				bodies[i].step(bodies, dt);
				String newLine = Integer.toString(i) + " " + Double.toString(bodies[i].x) + " " + Double.toString(bodies[i].y) + " " + Double.toString(bodies[i].mass);
				writer.write(newLine);
                writer.write("\n");
            }
        }

        writer.close();

    }

    static double randint(double min, double max) {
        /** Return a random double ranged from min to max
        similar function to random.randint() in python
        */
        return Math.random() * (max - min + 1) + min;
    }
}

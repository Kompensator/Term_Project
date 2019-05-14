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

    public static void main (String[] args) throws IOException {
        // settings
        int n = 3;      // number of bodies
        double simTime = 1e9;
        int totalSteps = (int) Math.round(simTime / dt);
		double spread = 5e11;		// determines the square where the bodies are generated

        File file = new File("/Users/DY/Term_Project/bin/binary_system/testdata.data");       // data file
        // File file = new File("c://Users//alexd//Documents//School//Winter2019//CompSci//TermProject//Term_Project//bin//binary_system//testdata.data"); //davids laptop
		FileWriter writer = new FileWriter(file);

		File config = new File("/Users/DY/Term_Project/bin/binary_system/sim.cfg");           // config file for animation
		// File config = new File("c://Users//alexd//Documents//School//Winter2019//CompSci//TermProject//Term_Project//bin//binary_system//sim.cfg"); //davids laptop
        FileWriter cfgWriter = new FileWriter(config);

		// writing config to config file
		cfgWriter.write(Integer.toString(n) + '\n');
		cfgWriter.write(Integer.toString(totalSteps) + '\n');
		cfgWriter.write(Double.toString(spread));
		cfgWriter.close();

        NewBody[] bodies = new NewBody[n];

        // add Bodie to bodies
        // for (int i = 0; i < n; i ++) {
        //     String bodyName = "Body" + i;
        //     bodies[i] = new Body(randint(-1.4e12, 1.4e12), randint(-1.4e12, 1.4e12), randint(-2e5, 2e5), randint(-2e5, 2e5), randint(7.5e22, 3e30), 1, bodyName);
        // }
        bodies[0] = new NewBody(-1.75*(1.496e11), 0, 0, -13782, 5*(1.989e30), 1e7, "Centauri A");
        bodies[1] = new NewBody(1.75*(1.496e11), 0, 0, 17792, 3*(1.989e30), 1e7, "Centauri B");
		bodies[2] = new NewBody(0.75*(1.496e11), 0, 0, 17792 + 51566, 6e24, 1e4, "planet");

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

import java.io.File;
import java.io.FileWriter;
import java.io.*;

public class VerletCollision {

	public static final int dt = 7200;

    public static void main (String[] args) throws IOException {

        int n = 100;      // number of bodies
        double simTime = 1e7;
        int totalSteps = (int) Math.round(simTime / dt);
        double spread = 1.4e12;		// determines the square where the bodies are generated
        
        File file = new File("/Users/DY/Term_Project/bin/testdata.data");               // data file
		// File file = new File("c://Users//alexd//Documents//School//Winter2019//CompSci//TermProject//Term_Project//bin//testdata.data");
        FileWriter writer = new FileWriter(file);

		File config = new File("/Users/DY/Term_Project/bin/sim.cfg");                   // config  for animation
		// File config = new File("your path here");
		FileWriter cfgWriter = new FileWriter(config);

		// writing config to config file
		cfgWriter.write(Integer.toString(n) + '\n');
		cfgWriter.write(Integer.toString(totalSteps) + '\n');
		cfgWriter.write(Double.toString(spread));
		cfgWriter.close();

        Body[] bodies = new Body[n];
        // add Bodie to bodies
        for (int i = 0; i < n; i ++) {
            String bodyName = "Body" + i;
            bodies[i] = new Body(randint(-spread, spread), randint(-spread, spread), randint(-2e5, 2e5), randint(-2e5, 2e5), randint(7.5e22, 3e30), 4e9, bodyName);
        }
        // bodies[0] = new Body(1.5e11, 0, 0, 3e4, 6e24, 1e6, "Earth");
        // bodies[1] = new Body(0, 0, 0, 0, 2e30, 1e6, "Sun");

        for (int step = 0; step < totalSteps; step++) {
            for (int i = 0; i < n; i ++) {
                if (bodies[i].merged == false) {
                    bodies[i].calculatePosition(bodies, dt);
                }
            }

            for (int i = 0; i < n; i ++) {
				if (bodies[i].merged == false) {
	                bodies[i].updatePosition();
				}
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

import java.io.File;
import java.io.FileWriter;
import java.io.*;

public class SunEarthMoon {
	/**
	Now uses the same Body class as VerletCollision
	The Body class in this file is commented out
	(will be deleted later)
	The radius is set to 1 (too small for collision to be detected)
	*/

	public static final int dt = 300;

    public static void main (String[] args) throws IOException {
        // settings
        int n = 3;      // number of bodies
        double simTime = 5e8;
        int totalSteps = (int) Math.round(simTime / dt);
		double spread = 1.5e11;		// determines the square where the bodies are generated
       
        // File file = new File("c://Users//alexd//Documents//School//Winter2019//CompSci//TermProject//Term_Project//bin//Sun_Earth_Moon//testdata.data"); //davids laptop
        File file = new File("/Users/DY/Term_Project/bin/Sun_Earth_Moon/testdata.data");       // data file	
        FileWriter writer = new FileWriter(file);

		// File config = new File("c://Users//alexd//Documents//School//Winter2019//CompSci//TermProject//Term_Project//bin//Sun_Earth_Moon//sim.cfg"); //davids laptop
		File config = new File("/Users/DY/Term_Project/bin/Sun_Earth_Moon/sim.cfg");           // config file for animation
       
	   FileWriter cfgWriter = new FileWriter(config);
        
		// writing config to config file
		cfgWriter.write(Integer.toString(n) + '\n');
		cfgWriter.write(Integer.toString(totalSteps) + '\n');
		cfgWriter.write(Double.toString(spread));
		cfgWriter.close();

        Body[] bodies = new Body[n];

        // add Bodie to bodies
        // for (int i = 0; i < n; i ++) {
        //     String bodyName = "Body" + i;
        //     bodies[i] = new Body(randint(-1.4e12, 1.4e12), randint(-1.4e12, 1.4e12), randint(-2e5, 2e5), randint(-2e5, 2e5), randint(7.5e22, 3e30), 1, bodyName);
        // }
        bodies[0] = new Body(1.496e11, 0, 0, 29741, 5.97e24, 1e4, "Earth");
        bodies[1] = new Body(0, 0, 0, 0, 1.989e30, 1e7, "Sun");
		bodies[2] = new Body(1.49984e11, 0, 0, 30820, 7.348e22, 1e4, "Moon");       // v = 30764

        for (int step = 0; step < totalSteps; step++) {
            for (int i = 0; i < n; i++) {
                bodies[i].EulerNextPos(bodies, dt);
            }

            for (int i = 0; i < n; i++) {
                bodies[i].calculatePosition(bodies, dt);
            }

            for (int i = 0; i < n; i++) {
                bodies[i].updatePosition();
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

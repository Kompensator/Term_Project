import java.io.File;
import java.io.FileWriter;
import java.io.*;
import body.Body;

public class VerletCollision {

	public static final int dt = 7200;

    public static void main (String[] args) throws IOException {
        // File file = new File("/Users/DY/java/Term Project/position2.data");
		File file = new File("c://Users//alexd//Documents//School//Winter2019//CompSci//TermProject//Term_Project//testdata.data");
        // File file = new File("your path here");
		
        FileWriter writer = new FileWriter(file);

        int n = 100;      // number of bodies
        double simTime = 1e7;
        long totalSteps = (long) Math.round(simTime / dt);

        Body[] bodies = new Body[n];
        // add Bodie to bodies
        for (int i = 0; i < n; i ++) {
            String bodyName = "Body" + i;
            bodies[i] = new Body(randint(-1.4e12, 1.4e12), randint(-1.4e12, 1.4e12), randint(-2e5, 2e5), randint(-2e5, 2e5), randint(7.5e22, 3e30), 4e9, bodyName);
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
        /* Return a random integer ranged from min to max
        similar function to random.randint() in python
        */
        return Math.random() * (max - min + 1) + min;
    }
}

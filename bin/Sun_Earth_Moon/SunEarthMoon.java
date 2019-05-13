import java.io.File;
import java.io.FileWriter;
import java.io.*;


public class SunEarthMoon {
    
    public static void main(String[] argv) throws IOException {
        /** Instantialize NewBody class for Verlet integration */
        final int dt = 600;
        final int n = 3;
        final double simTime = 1e8;
        final int totalSteps = (int)(simTime/dt);
        double spread = 1.5e11;
        
        File file = new File("c://Users//alexd//Documents//School//Winter2019//CompSci//TermProject//Term_Project//bin//Sun_Earth_Moon//testdata.data"); //davids laptop
        //File file = new File("/Users/DY/Term_Project/bin/Sun_Earth_Moon/testdata.data");       // data file	
        FileWriter writer = new FileWriter(file);

		File config = new File("c://Users//alexd//Documents//School//Winter2019//CompSci//TermProject//Term_Project//bin//Sun_Earth_Moon//sim.cfg"); //davids laptop
		//File config = new File("/Users/DY/Term_Project/bin/Sun_Earth_Moon/sim.cfg");           // config file for animation
       
	   FileWriter cfgWriter = new FileWriter(config);
        
		// writing config to config file
		cfgWriter.write(Integer.toString(n) + '\n');
		cfgWriter.write(Integer.toString(totalSteps) + '\n');
		cfgWriter.write(Double.toString(spread));
		cfgWriter.close();


        NewBody[] bodies = new NewBody[n];

        bodies[0] = new NewBody(1.496e11, 0, 0, 29741, 5.97e24, 1e4, "Earth");
        bodies[1] = new NewBody(0, 0, 0, 0, 1.989e30, 1e7, "Sun");
        bodies[2] = new NewBody(1.49984e11, 0, 0, 30764, 7.348e22, 1e4, "Moon");       // v = 30764
        

        for (int i = 0;i < bodies.length;i++) {
            bodies[i].prime(bodies, dt);
        }

        for (int step = 0;step < totalSteps;step++) {
            for (int i = 0;i < bodies.length;i++) {
                bodies[i].step(bodies, dt);
                String newLine = Integer.toString(i) + " " + Double.toString(bodies[i].x) + " " + Double.toString(bodies[i].y) + " " + Double.toString(bodies[i].mass);
				writer.write(newLine);
                writer.write("\n");
            }
        }
        
        writer.close();
    }
}
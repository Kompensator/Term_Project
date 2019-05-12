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

	public static final int dt = 720;

    public static void main (String[] args) throws IOException {
        // settings
        int n = 3;      // number of bodies
        double simTime = 1e8;
        int totalSteps = (int) Math.round(simTime / dt);
		double spread = 1.5e11;		// determines the square where the bodies are generated
       
        File file = new File("c://Users//alexd//Documents//School//Winter2019//CompSci//TermProject//Term_Project//bin//Sun_Earth_Moon//testdata.data"); //davids laptop
        //File file = new File("/Users/DY/Term_Project/bin/Sun_Earth_Moon/testdata.data");       // data file	
		// File file = new File("your path here")
        FileWriter writer = new FileWriter(file);

		//File config = new File("/Users/DY/Term_Project/bin/sim.cfg");           // config file for animation
		File config = new File("c://Users//alexd//Documents//School//Winter2019//CompSci//TermProject//Term_Project//bin//Sun_Earth_Moon//sim.cfg"); //davids laptop
		//File config = new File("/Users/DY/Term_Project/bin/Sun_Earth_Moon/sim.cfg");           // config file for animation
       
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
		bodies[2] = new Body(1.49984e11, 0, 0, 30764, 7.348e22, 1e4, "Moon");

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

// class Body {
//     // Attributes: x, y, vx, vy, ax, ay, mass, radius
//     double x, y, mass, vx, vy, ax, ay, axplusone, ayplusone, radius,temp_x,temp_y;
//     String name;
//     boolean merged;      // for two bodies merging into one
//     public static final int dt = 7200;
//     public static final double G = 6.6740831e-11;
//
//     public Body (double x, double y, double vx, double vy, double mass, double radius, String name) {
//         // constructor
//         this.x = x;
//         this.y = y;
//         this.vx = vx;
//         this.vy = vy;
//         this.mass = mass;
//         this.radius = radius;
//         this.name = name;
//         this.ax = 0;
//         this.ay = 0;
// 		this.axplusone = 0;
// 		this.ayplusone = 0;
//         this.temp_x = x;
//         this.temp_y = y;
//         this.merged = false;
//     }
//
//     public void updateAcceleration (Body[] bodies) {
//         // updates ax and ay of the body with respect to other bodies
//         for (int i = 0; i < bodies.length; i ++) {
//             Body otherBody = bodies[i];
//             if (otherBody.name != this.name) {       // makes sure a body doesn't calculate acc on itself
//                 double r = Math.sqrt(Math.pow((this.x - otherBody.x),2) + Math.pow((this.y - otherBody.y),2));
//                 double temp_acc;
//                 try {
//                     temp_acc = (G * otherBody.mass)/Math.pow(r,3);       // temp_acc * deltax = ax
//                 }
//                 catch (ArithmeticException e){
//                     // catch division / 0
//                     temp_acc = 0;
//                 }
//                 this.ax += temp_acc * (otherBody.x - this.x);
//                 this.ay += temp_acc * (otherBody.y - this.y);
//             }
//
// 			if (otherBody.name != this.name){
//                 double thisxplus = this.x + this.vx * dt;
//                 double thisyplus = this.y + this.vy * dt;
//                 double otherxplus = otherBody.x + otherBody.vx * dt;
//                 double otheryplus = otherBody.y + otherBody.vy * dt;
//
// 				double r = Math.sqrt(Math.pow((thisxplus - otherxplus),2) + Math.pow((thisyplus - otheryplus),2));
//                 double temp_acc;
//                 try {
//                     temp_acc = (G * otherBody.mass)/Math.pow(r,3);       // temp_acc * deltax = ax
//                 }
//                 catch (ArithmeticException e){
//                     temp_acc = 0;
//                 }
//                 this.axplusone += temp_acc * (otherxplus - thisxplus);
//                 this.ayplusone += temp_acc * (otheryplus - thisyplus);
// 			}
//         }
//
//     }
//
//     public void updateVelocity (Body[] bodies, int dt) {
//         // computes the updated velocity with ax and ay
//         this.updateAcceleration(bodies);
//         this.vx += 0.5 * (this.ax + this.axplusone) * dt;
//         this.vy += 0.5 * (this.ay + this.ayplusone) * dt;
//     }
//
//     public void calculatePosition(Body[] bodies, int dt) {
//         // calculates the new positions and stores them in temp_x and temp_y
//         this.updateVelocity(bodies, dt);
//         this.temp_x += this.vx * dt + 0.5 * this.ax * dt * dt;
//         this.temp_y += this.vy * dt + 0.5 * this.ay * dt * dt;
//     }
//
//     public void updatePosition() {
//         // this method should be called after calling calculatePosition
//         // replaces position with temporary values
// 		// clear all acceleration
//
//         this.x = this.temp_x;
//         this.y = this.temp_y;
//         this.ax = 0;
//         this.ay = 0;
// 		this.axplusone = 0;
// 		this.ayplusone = 0;
//     }
// }

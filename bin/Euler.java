import java.io.File;
import java.io.FileWriter;
import java.io.*;

public class Euler {
    public static void main (String[] args) throws IOException {
        File file = new File("/Users/DY/java/Term Project/position.data");
        FileWriter writer = new FileWriter(file);

        int n = 5;      // number of bodies
        int dt = 7200;
        double simTime = 1e9;
        long totalSteps = (long) Math.round(simTime / dt);

        Body[] bodies = new Body[n];
        // add Bodie to bodies
        for (int i = 0; i < n; i ++) {
            String bodyName = "Body" + i;
            bodies[i] = new Body(randint(-3e11, 3e11), randint(-3e11, 3e11), randint(-1e5, 1e5), randint(-1e5, 1e5), 1e30, 1e5, bodyName);
        }
        // bodies[0] = new Body(1.5e11, 0, 0, 3e4, 6e24, 1e6, "Earth");
        // bodies[1] = new Body(0, 0, 0, 0, 2e30, 1e6, "Sun");

        for (int step = 0; step < totalSteps; step++) {
            for (int i = 0; i < n; i ++) {
                bodies[i].calculatePosition(bodies, dt);
            }

            for (int i = 0; i < n; i ++) {
                bodies[i].updatePosition();
                writer.write(Double.toString(bodies[i].x));
                writer.write("\n");
                writer.write(Double.toString(bodies[i].y));
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

class Body {
    // Attributes: x, y, vx, vy, ax, ay, mass, radius
    double x, y, mass, vx, vy, ax, ay, radius,temp_x,temp_y;
    String name;
    boolean merged;      // for two bodies merging into one

    public Body (double x, double y, double vx, double vy, double mass, double radius, String name) {
        // constructor
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.mass = mass;
        this.radius = radius;
        this.name = name;
        this.ax = 0;
        this.ay = 0;
        this.temp_x = x;
        this.temp_y = y;
        this.merged = false;
    }

    public void updateAcceleration (Body[] bodies) {
        // updates ax and ay of the body with respect to other bodies
        final double G = 6.6740831e-11;
        for (int i = 0; i < bodies.length; i ++) {
            Body otherBody = bodies[i];
            if (otherBody.name != this.name){       // makes sure a body doesn't calculate acc on itself
                double r = Math.sqrt(Math.pow((this.x - otherBody.x),2) + Math.pow((this.y - otherBody.y),2));
                double temp_acc;
                try {
                    temp_acc = (G * otherBody.mass)/Math.pow(r,3);       // temp_acc * deltax = ax
                }
                catch (ArithmeticException e){
                    temp_acc = 0;
                }
                this.ax += temp_acc * (otherBody.x - this.x);
                this.ay += temp_acc * (otherBody.y - this.y);
            }
        }

    }

    public void updateVelocity (Body[] bodies, int dt) {
        // computes the updated velocity with ax and ay
        this.updateAcceleration(bodies);
        this.vx += this.ax * dt;
        this.vy += this.ay * dt;
    }

    public void calculatePosition(Body[] bodies, int dt) {
        // calculates the new positions and stores them in temp_x and temp_y
        this.updateVelocity(bodies, dt);
        this.temp_x += this.vx * dt;
        this.temp_y += this.vy * dt;
    }

    public void updatePosition() {
        // this method should be called after calling calculatePosition
        // replaces position with temporary values

        this.x = this.temp_x;
        this.y = this.temp_y;
        this.ax = 0;
        this.ay = 0;
    }
}

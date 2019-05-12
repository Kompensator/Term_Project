public class Body {
    /**
    Ressource for Verlet and VerletCollision
    */
    public double x, y, mass, vx, vy, ax, ay, axplusone, ayplusone, radius, temp_x, temp_y, euler_x, euler_y;
    public String name;
    public boolean merged;      // for two bodies merging into one
	public static final int dt = 900;
    public static final double G = 6.6740831e-11;


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
		this.axplusone = 0;
		this.ayplusone = 0;
        this.temp_x = x;
        this.temp_y = y;
        this.merged = false;
        this.euler_x = this.x;
        this.euler_y = this.y;
    }

    public boolean collisionDetection(Body body) {
        /* returns true if the body is collided with another body
        */
        boolean collision = false;      // default: no collision
        if (body.name != this.name) {
            double r = Math.sqrt(Math.pow((this.x - body.x),2) + Math.pow((this.y - body.y),2));
            if (r <= 1*(body.radius + this.radius)) {
                collision = true;    // collision detected
            }

        }
        return collision;
    }

    public void collisionPhsysics (Body otherBody) {
        /* handles collision phsics
        modifies the properties the two colliding bodies
        deprecates the ligher one
        */

        // mass transfer and setting coordinate to 0
        if (this.mass <= otherBody.mass) {
            this.merged = true;
            otherBody.mass += this.mass;
            this.x = 0;
            this.y = 0;
            this.temp_x = 0;
            this.temp_y = 0;
        }
        else {
            otherBody.merged = true;
            this.mass += otherBody.mass;
            otherBody.x = 0;
            otherBody.y = 0;
            otherBody.temp_x = 0;
            otherBody.temp_y = 0;
        }

        // momentum transfer
        if (this.mass <= otherBody.mass) {
            otherBody.vx = (this.mass*this.vx + otherBody.mass*otherBody.vx)/(this.mass + otherBody.mass);
            otherBody.vy = (this.mass*this.vy + otherBody.mass*otherBody.vy)/(this.mass + otherBody.mass);
        }
        else {
            this.vx = (this.mass*this.vx + otherBody.mass*otherBody.vx)/(this.mass + otherBody.mass);
            this.vy = (this.mass*this.vy + otherBody.mass*otherBody.vy)/(this.mass + otherBody.mass);
        }


    }

    public void updateAcceleration (Body[] bodies) {
        /* updates ax and ay of the body with respect to other bodies
        */

        boolean collision;

        for (int i = 0; i < bodies.length; i ++) {

            Body otherBody = bodies[i];
            if (this.merged == false && otherBody.merged == false) {
                collision = this.collisionDetection(otherBody);

                // if there's a collision between this and another body, stop the loop
                if (collision == true) {
                    this.collisionPhsysics(otherBody);
                    System.out.println("Collision occured!");
                    break;
                }
                else {
                    if (otherBody.name != this.name){       // makes sure a body doesn't calculate acc on itself
                        double r = Math.sqrt(Math.pow((this.x - otherBody.x),2) + Math.pow((this.y - otherBody.y),2));
                        double temp_acc;
                        try {
                            temp_acc = (G * otherBody.mass)/Math.pow(r,3);       // temp_acc * deltax = ax
                        }
                        catch (ArithmeticException e){
                            // catch division / 0
                            temp_acc = 0;
                        }
                        this.ax += temp_acc * (otherBody.x - this.x);
                        this.ay += temp_acc * (otherBody.y - this.y);
                    }

                    if (otherBody.name != this.name){
                        // computes and updates axplusone and ayplusone

                        double r = Math.sqrt(Math.pow((this.euler_x - otherBody.euler_x),2) + Math.pow((this.euler_y - otherBody.euler_y),2));
                        double temp_acc;
                        try {
                            temp_acc = (G * otherBody.mass)/Math.pow(r,3);       // temp_acc * deltax = ax
                        }
                        catch (ArithmeticException e){
                            temp_acc = 0;
                        }
                        this.axplusone += temp_acc * (otherBody.euler_x - this.euler_x);
                        this.ayplusone += temp_acc * (otherBody.euler_y - this.euler_y);
                    }
                }
            }
        }

    }

    public void updateVelocity (Body[] bodies, int dt) {
        /* computes the updated velocity with ax and ay
        */
        this.updateAcceleration(bodies);
        this.vx += 0.5 * (this.ax + this.axplusone) * dt;
        this.vy += 0.5 * (this.ay + this.ayplusone) * dt;
    }

    public void calculatePosition(Body[] bodies, int dt) {
        /* calculates the new positions and stores them in temp_x and temp_y
        /** calculates the new positions and stores them in temp_x and temp_y
         * does NOT directly update x and y
        */
        this.updateVelocity(bodies, dt);
        this.temp_x += this.vx * dt + 0.5 * this.ax * dt * dt;
        this.temp_y += this.vy * dt + 0.5 * this.ay * dt * dt;
    }

    public void updatePosition() {
        // this method should be called after calling calculatePosition
        // replaces position with temporary values
		// clear all acceleration

        this.x = this.temp_x;
        this.y = this.temp_y;
        this.ax = 0;
        this.ay = 0;
		this.axplusone = 0;
        this.ayplusone = 0;
        
    }

    public void EulerNextPos(Body[] bodies, int dt) {
        /** updates the current body's position with respect to other bodies
         * This "guessed" position is used for Verlet's method 
         * to calculate  Ax n+1
         * Changes ONLY fields euler_x and euler_y
         * all other variables are limited to the method
         */
        double ax = 0;
        double ay = 0;
        double temp_vx = this.vx;
        double temp_vy = this.vy;
        for (int i = 0; i < bodies.length; i++) {
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
                ax += temp_acc * (otherBody.x - this.x);
                ay += temp_acc * (otherBody.y - this.y);
            }
        }
        // change temp_vx and temp_vy based on acceleration
        temp_vx += ax * dt;
        temp_vy += ay * dt;
        
        this.euler_x = this.x + temp_vx * dt;
        this.euler_y = this.y + temp_vy * dt;
    }
}

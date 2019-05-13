public class NewBody {
    /**
    Ressource for Verlet and VerletCollision
    Fixed Verlet integration
    */
    public double x, y, mass, vx, vy, ax, ay, axlast, aylast, radius;
    public String name;
    public static final double G = 6.6740831e-11;


    public NewBody (double x, double y, double vx, double vy, double mass, double radius, String name) {
        /** constructing NewBody takes the same parameters as Body */
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.mass = mass;
        this.radius = radius;
        this.name = name;
        this.ax = 0;
        this.ay = 0;
		this.axlast = 0;
		this.aylast = 0;
    }
   
    public void calculateAcceleration(NewBody[] bodies) {
        /** calculates current accel (an), doesn't replace anything */
        for (int i = 0;i < bodies.length;i++) {
            if (bodies[i].name != this.name) {
                double r = Math.sqrt(Math.pow((this.x - bodies[i].x),2) + Math.pow((this.y - bodies[i].y),2));
                double temp_acc;
                try {
                    temp_acc = (G * bodies[i].mass)/Math.pow(r,3);       // temp_acc * deltax = ax
                }
                catch (ArithmeticException e){
                    // catch division / 0
                    temp_acc = 0;
                }
                this.ax += temp_acc * (bodies[i].x - this.x);
                this.ay += temp_acc * (bodies[i].y - this.y);
            }
        }
    }


    public void calculateNextPosition(int dt) {
        this.x += this.vx * dt + 0.5 * this.ax * dt * dt;
        this.y += this.vy * dt + 0.5 * this.ay * dt * dt;
    }


    public void calculateVelocity(int dt) {
        // this.vx += 0.5 * (this.ax + this.axlast) * dt;
        // this.vy += 0.5 * (this.ay + this.aylast) * dt;
        this.vx += (0.4 * this.ax + 0.6 * this.axlast) * dt;
        this.vy += (0.4 * this.ay + 0.6 * this.aylast) * dt;
    }


    public void step(NewBody[] bodies, int dt) {
        /** normal Verlet integration step
         * calculates an+1
         * calculates vn+1
         * calculates xn+1
         */
        this.calculateAcceleration(bodies);
        this.calculateVelocity(dt);
        this.calculateNextPosition(dt);
        this.axlast = this.ax;
        this.aylast = this.ay;
        this.ax = 0;
        this.ay = 0;
    }


    public void prime(NewBody[] bodies, int dt) {
        /** primes Verlet integration 
         * Should be ran only once
         * Calculates an (becomes anlast)
         * Calculates xn+1
        */
        this.calculateAcceleration(bodies);
        this.calculateNextPosition(dt);
        this.axlast = this.ax;
        this.aylast = this.ay;
        this.ax = 0;
        this.ay = 0;
    }
}

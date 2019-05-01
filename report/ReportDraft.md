David Alexander and Ding-Yi Zhang
Introduction to Programming in Science and Engineering
Profs Bhatnagar, Briere and Sumner
Presentation Date

#"Term Project Report"

**Intoduction**

Binary star systems consist of two stars that orbit each other. Here on Earth, we only have one Sun, but in a binary system, there would be two nearby stars, like on Tatooine in Star Wars. These star systems are surprisingly common, and the nearest solar system to ours, the Centauri system, is made up of a binary star system and another orbiting star. Stars are quite easy to detect using modern astronomical methods. Planets,however, being much smaller and much less bright, are more difficult to spot. Therefore, the question that our term project aims to answer is: what would the orbit of a planet orbiting a binary system look like? This is an applied version of the three-body problem, but it is also a relevant scientific question. Given how common these systems are, it would be interesting to see how a possibly habitable planet would orbit around a system like Centauri. And more importantly; would Luke really be able to see two sunsets at once?

**Scientific Basis**

Astronomy is a very ancient field, and many of the equations that we will use were discovered as far back as the 17th Century, by a man named Johannes Kepler. He theorized three laws, now known as Kepler’s laws. The first law states that any body orbiting another body will follow a path in the shape of an ellipse. For example, Earth’s orbit around the Sun, which appears circular, is in fact a near perfect ellipse. Ellipses have a major and a minor axis, the major axis being longer than the minor, and contain two focus points. Kepler’s first law also states that the orbited body will be at one of the two focus points. Kepler’s second law describes the velocities of the two bodies. It states that areal velocity, or m2/s, stays constant as long as there is no tangential acceleration. This means that the angular and tangential velocities of an orbiting body will depend on how far it is from the focus: the further it is, the slower it will go, and the closer it is, the faster it will go. From this law we can derive equations to find the velocity of an object at any given point on its orbit. The third law states that there is a relation between the mass of the two bodies, the semi-major axis of the orbit and the period of the orbit, and that for the same conditions, the exact same orbit will be obtained. All these laws can also be related back to and derived from a single equation; Newton’s law of universal gravity.  Binary star systems also obey these laws, and using data that has gathered experimentally, we have all we need to be able to simulate the orbits of the two stars.

**Mathematical Methods**

All three of Kepler’s laws can be related back to and derived from a single equation; Newton’s universal law of gravity. This law is what makes the others true. Newton’s law simply gives the gravitational force, and therefore acceleration, felt by one object due to another. This means that with the right initial conditions, we can simulate the entire system using only universal gravity and kinematics equations; Kepler’s laws should hold, since they are based on universal gravity. The problem then becomes one of numerical integration, and calculating the acceleration,velocity and position of each object involved after a certain small timestep. For this we will use a modified, self-starting version of Verlet’s method:

        *put equations here when I figure out how*

This simplifies our code, as we don’t have to worry about calculating an initial step or two with a different method. This method calculates the position at the next timestep using the first derivative, velocity, and the second derivative, acceleration, while also updating those two values. The precision of our simulation depends on the size of our timestep, and is accurate to the fourth derivative of position.

**Verification and Results**

To verify the accuracy of our model, we simulate the Earth-Moon-Sun system and measure the period of Earth’s orbit around the Sun. As this is a large-scale system, we expect an error of a few percent compared to the real value. Once we verify this, we can proceed to a simulation of the Centauri stars, and obtain real data for the orbit of a planet.

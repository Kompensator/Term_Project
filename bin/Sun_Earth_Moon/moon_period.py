from numpy import (arccos, dot)
import math


class body():
    def __init__(self):
        self.x = []
        self.y = []
    

def unit_vector(vector):
    norm = (vector[0]**2 + vector[1]**2)**0.5
    return [dim/norm for dim in vector]


def angle_between(v1, v2):
    u1 = unit_vector(v1)
    u2 = unit_vector(v2)
    return arccos(dot(u1, u2))

def main():
    sim_time = 1e8
    dt = 600
    n = 3
    total_steps = int(sim_time//dt)

    file = open("testdata.data",'r')
    bodies = []

    for i in range(n):
        bodies.append(body())

    for i in range(total_steps):
        for j in range(n):
            try:
                data = [float (n) for n in file.readline().split()];
                bodies[j].x.append(data[1])
                bodies[j].y.append(data[2])
            except:
                break

    orbit_count = 0
    last_earth_angle = 0
    last_moon_angle = 0
    periods = []
    last_orbit = 0
    for i in range(total_steps):
        """ processing loop
        """
        v1 = [bodies[0].x[i] - bodies[1].x[i], bodies[0].y[i] - bodies[1].y[i]]         # earth vector
        v2 = [bodies[2].x[i] - bodies[1].x[i], bodies[2].y[i] - bodies[1].y[i]]         # moon vector

        earth_angle = angle_between(v1, [1, 0])
        moon_angle = angle_between(v2, [1, 0])
        

        if (earth_angle < moon_angle and last_earth_angle > last_moon_angle):
            orbit_count += 1
            periods.append(i - last_orbit)
            last_orbit = i

        last_earth_angle = earth_angle
        last_moon_angle = moon_angle

    print("total orbits: %r" %orbit_count)
    periods = [round(period * dt/(60*60*24),2) for period in periods]
    print(periods)


if __name__ == "__main__":
    main()
import numpy as np
from sys import exit

sim_time = 3.154e7
dt = 600
n = 3
total_steps = int(sim_time//dt)

file = open("testdata.data",'r')


class body():
    def __init__(self):
        self.x = []
        self.y = []
    

def unit_vector(vector):
    return vector/np.linalg.norm(vector)


def angle_between(v1, v2):
    u1 = unit_vector(v1)
    u2 = unit_vector(v2)
    return np.arccos(np.clip(np.dot(u1, u2), -1.0, 1.0))


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
for i in range(total_steps):
    """ processing loop
    """
    v1 = [bodies[0].x[i] - bodies[1].x[i], bodies[0].y[i] - bodies[1].y[i]]         # earth vector
    v2 = [bodies[2].x[i] - bodies[1].x[i], bodies[2].y[i] - bodies[1].y[i]]         # sun vector

    earth_angle = angle_between(v1, [1, 0])
    moon_angle = angle_between(v2, [1, 0])

    if earth_angle < moon_angle and last_earth_angle > last_moon_angle:
        # orbit_count += 1
        print(i)
        exit(0)

    last_earth_angle = earth_angle
    last_moon_angle = moon_angle

print(orbit_count)
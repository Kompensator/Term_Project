import matplotlib.pyplot as plt
from matplotlib.animation import FuncAnimation
import numpy as np
from sys import argv
from sys import exit

try:
    if argv[1].lower() == "focused":
        focused = True
        print("Center of mass animation")
    else:
        focused = False
        print("Normal animation")
except IndexError:
    focused = False
    print("Normal animation")


# settings
global n, number_of_dots

data_file = "testdata.data"
cfg_file = "sim.cfg"
cfg = open(cfg_file, "r")

# read settings fro sim.cfg
n = 4
total_steps = int(1e9//720) #sim time over dt
number_of_dots = 150
# spread determines the bound of the animation
spread = 15*1.496e11
skip_steps = 150
mass_ratio = 0.625

file = open(data_file, "r")

# setting up fig and axis
fig, ax = plt.subplots()
ax.set_facecolor('xkcd:black')
ax.set_xlim(-spread, spread)
ax.set_ylim(-spread, spread)


class body():
    def __init__(self):
        self.x = []
        self.y = []
        self.mass = 0.0


def shift_coordinate(shift_x, shift_y, trace_x, trace_y):
    """shifts all the elements in a 2d list by x and y amount
    centers call the trace points with respect to Earth
    returns the shifted lists
    """
    arr_x = np.array(trace_x)
    arr_y = np.array(trace_y)
    arr_x = arr_x - shift_x
    arr_y = arr_y - shift_y
    return arr_x.tolist(), arr_y.tolist()


def update_focused(frame):
    trail_x, trail_y = [], []
    frame *= skip_steps

    try:
        star1x, star1y = bodies[0].x[frame], bodies[0].y[frame]
        star2x, star2y = bodies[1].x[frame], bodies[1].y[frame]
        mass_center_x = star1x + (1 - mass_ratio) * (star2x - star1x)
        mass_center_y = star1y + (1 - mass_ratio) * (star2y - star1y)


        for i in range(n):
            animated_bodies[i].set_data(bodies[i].x[frame] - mass_center_x, bodies[i].y[frame] - mass_center_y)

        for i in range(n):
            body = bodies[i]
            if frame < number_of_dots:
                trail_x.append(body.x[0:frame])
                trail_y.append(body.y[0:frame])

            else:
                trail_x.append(body.x[(frame-number_of_dots):frame])
                trail_y.append(body.y[(frame-number_of_dots):frame])

        trail_x, trail_y = shift_coordinate(mass_center_x, mass_center_y, trail_x, trail_y)

        animated_bodies[n].set_data(trail_x,trail_y)

        return animated_bodies
    except IndexError:
        print("Animation finished")
        exit(0)

def update_normal(frame):
    trail_x, trail_y = [], []
    frame *= skip_steps

    try:
        for i in range(n):
            x = bodies[i].x[frame]
            y = bodies[i].y[frame]

            # merge handling
            if not (x == 0 and y == 0):
                animated_bodies[i].set_data(x,y)
            else:
                animated_bodies[i].set_data([],[])

        # writing in the trace
        for i in range(n):
            body = bodies[i]
            if frame < number_of_dots:
                trail_x.append(body.x[0:frame])
                trail_y.append(body.y[0:frame])

            else:
                trail_x.append(body.x[(frame-number_of_dots):frame])
                trail_y.append(body.y[(frame-number_of_dots):frame])

        animated_bodies[n].set_data(trail_x,trail_y)

        return animated_bodies

    except IndexError:
        print("Animation finished")
        exit(0)


# creating objects to that contains the position data
bodies = []
for i in range(n):
    bodies.append(body())

for i in range(total_steps):
    for j in range(n):
        try:
            data = [float (n) for n in file.readline().split()];
            bodies[j].x.append(data[1])
            bodies[j].y.append(data[2])
            bodies[j].mass = data[3]
        except:
            break

# creating aniated objects
if not focused:
    star1, = plt.plot([],[],color='red',marker='o',markersize=9,animated=True)
    star2, = plt.plot([],[],color='yellow',marker='o',markersize=7,animated=True)
    planet1, = plt.plot([],[],color='blue',marker='o',markersize=5.5,animated=True)
    planet2, = plt.plot([],[],color='blue',marker='o',markersize=6,animated=True)

    animated_bodies = [star1, star2, planet1, planet2]

    update = update_normal

else:
    star1, = plt.plot([],[],color='red',marker='o',markersize=9,animated=True)
    star2, = plt.plot([],[],color='yellow',marker='o',markersize=7,animated=True)
    planet1, = plt.plot([],[],color='blue',marker='o',markersize=5.5,animated=True)
    planet2, = plt.plot([],[],color='blue',marker='o',markersize=6,animated=True)

    animated_bodies = [star1, star2, planet1, planet2]

    update = update_focused


# creating trail of bodies
trail, = plt.plot([], [], 'bo', markersize=0.2, animated=True)
animated_bodies.append(trail)


ani = FuncAnimation(fig, update, interval=1, blit=True)
plt.show()

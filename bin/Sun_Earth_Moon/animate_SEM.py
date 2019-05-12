import matplotlib.pyplot as plt
from matplotlib.animation import FuncAnimation
import matplotlib.animation as animation
from sys import argv
import numpy as np

# determine which animation to run
try:
    if argv[1].lower() == "focused":
        focused = True
        print("Earth Focused Animation")
    else:
        focused = False
        print("Sun Focused Animation")
except IndexError:
    focused = False
    print("Sun Focused Animation")


# settings
global n, number_of_dots

data_file = "testdata.data"
cfg_file = "sim.cfg"
cfg = open(cfg_file, "r")

# read settings fro sim.cfg
n = int(cfg.readline())
total_steps = int(cfg.readline())
number_of_dots = 150
# spread determines the bound of the animation
if not focused:
    spread = 2.4e11
else:
    spread = 2e9
file = open(data_file, "r")

# trying to write the animation to a file
# Writer = animation.writers['html']
# writer = Writer(fps=15, metadata=dict(artist='Me'), bitrate=1800)

# setting up fig and axis
fig, ax = plt.subplots(figsize=(9,12))
plt.subplots_adjust(left=0.1, right=0.9, top=0.9, bottom=0.1)
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



def update_normal(frame):
    trail_x, trail_y = [], []
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

def update_focused(frame):
    trail_x, trail_y = [], []

    earth_x, earth_y = bodies[0].x[frame], bodies[0].y[frame]
    # adjust Moon's position so that Earth is at the center of graph
    moon_x, moon_y = bodies[2].x[frame] - earth_x, bodies[2].y[frame] - earth_y 
    animated_bodies[0].set_data(0, 0)
    animated_bodies[2].set_data(moon_x, moon_y)

    # writing in the trace
    for i in range(n):
        body = bodies[i]
        if frame < number_of_dots:
            trail_x.append(body.x[0:frame])
            trail_y.append(body.y[0:frame])

        else:
            trail_x.append(body.x[(frame-number_of_dots):frame])
            trail_y.append(body.y[(frame-number_of_dots):frame])

    trail_x, trail_y = shift_coordinate(earth_x, earth_y, trail_x, trail_y)
    animated_bodies[n].set_data(trail_x,trail_y)

    return animated_bodies

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
    earth_plot, = plt.plot([],[],color='#0077be',marker='o',markersize=5,animated=True)
    sun_plot, = plt.plot([],[],color='#ffff00',marker='o',markersize=12,animated=True)
    moon_plot, = plt.plot([],[],color='#f5f3ce',marker='o',markersize=3,animated=True)

    animated_bodies = [earth_plot, sun_plot, moon_plot]          # the packing order is important
    # creating trail of bodies
    trail, = plt.plot([], [], 'bo', markersize=0.2, animated=True)
    animated_bodies.append(trail)
    update = update_normal

else:
    earth_plot, = plt.plot([],[],color='#0077be',marker='o',markersize=8,animated=True)
    sun_plot, = plt.plot([],[],color='#ffff00',marker='o',markersize=12,animated=True)
    moon_plot, = plt.plot([],[],color='#f5f3ce',marker='o',markersize=6,animated=True)

    animated_bodies = [earth_plot, sun_plot, moon_plot]          # the packing order is importantnt
    trail, = plt.plot([], [], 'bo', markersize=1, animated=True)
    animated_bodies.append(trail)
    update = update_focused

ani = FuncAnimation(fig, update, interval=1, blit=True)
plt.show()


# fig1 = plt.figure()
# im_ani = animation.ArtistAnimation(fig1, ims, interval=50, repeat_delay=3000,
#                                    blit=True)
# im_ani.save('im.mp4', writer=writer)

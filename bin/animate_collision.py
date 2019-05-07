import matplotlib.pyplot as plt
from matplotlib.animation import FuncAnimation
import matplotlib.animation as animation
import time

# settings
global n, number_of_dots
n = 3 #number of bodies
data_file = "testdata.data"
total_steps = int(1e8/900)
number_of_dots = 100
file = open(data_file, "r")

# trying to write the animation to a file
# Writer = animation.writers['html']
# writer = Writer(fps=15, metadata=dict(artist='Me'), bitrate=1800)

# setting up fig and axis
fig, ax = plt.subplots()
ax.set_facecolor('xkcd:black')
ax.set_xlim(-2e11, 2e11)
ax.set_ylim(-2e11, 2e11)


class body():
    def __init__(self):
        self.x = []
        self.y = []
        self.mass = 0.0

def update(frame):
    trail_x, trail_y = [], []
    # n = 5
    # number_of_dots = 500
    for i in range(n):
        x = bodies[i].x[frame]
        y = bodies[i].y[frame]
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


# creating objects to that contains the position data
bodies = []
animated_bodies = []

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
for i in range(n):
    new, = plt.plot([],[],color='red',marker='o',markersize=9,animated=True)
    animated_bodies.append(new)

# creating trail of bodies
trail, = plt.plot([], [], 'bo', markersize=0.2, animated=True)
animated_bodies.append(trail)
ani = FuncAnimation(fig, update, interval=1, blit=True)
plt.show()


# fig1 = plt.figure()
# im_ani = animation.ArtistAnimation(fig1, ims, interval=50, repeat_delay=3000,
#                                    blit=True)
# im_ani.save('im.mp4', writer=writer)

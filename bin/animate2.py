import matplotlib.pyplot as plt
from matplotlib.animation import FuncAnimation
import matplotlib.animation as animation
import time

# settings
global n, number_of_dots
n = 20
data_file = "position.data"
total_steps = int(1e8//7200)
number_of_dots = 150
file = open(data_file, "r")


class body():
    def __init__(self):
        self.x = []
        self.y = []

def update(frame):
    trail_x, trail_y = [], []
    # n = 5
    # number_of_dots = 500
    for i in range(n):
        animated_bodies[i].set_data(bodies[i].x[frame], bodies[i].y[frame])
    
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

bodies = []
animated_bodies = []

for i in range(n):
    bodies.append(body())

for i in range(total_steps):
    for j in range(n):
        try:
            x = float(file.readline().strip("\n"))
            y = float(file.readline().strip("\n"))
            bodies[j].x.append(x)
            bodies[j].y.append(y)
        except:
            break


# trying to write the animation to a file
# Writer = animation.writers['html']
# writer = Writer(fps=15, metadata=dict(artist='Me'), bitrate=1800)

# setting up fig and axis
fig, ax = plt.subplots()
ax.set_facecolor('xkcd:black')
ax.set_xlim(-7e11, 7e11)
ax.set_ylim(-7e11, 7e11)

# creating aniated objects
for i in range(n):
    new, = plt.plot([],[],color='red',marker='o',markersize=7,animated=True)
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




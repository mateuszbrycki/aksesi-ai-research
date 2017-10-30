from random import randint
import matplotlib.pyplot as plt

from read_gesture import read_gesture

number_of_gestures_to_plot = 10
number_of_gestures = 50000


def get_x_values(points):
    return [x[0] for x in points]


def get_y_values(points):
    return [x[1] for x in points]


def plot_gesture(type, points):
    plt.title(type)
    plt.plot(get_x_values(points), get_y_values(points), 'o', label='Points')
    plt.axis('scaled')
    plt.show()


for i in range(1, number_of_gestures_to_plot):

    gesture_number = randint(0, number_of_gestures)
    gesture_type, gesture_points = read_gesture(gesture_number)
    plot_gesture(gesture_type, gesture_points)
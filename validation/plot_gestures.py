from random import randint
import matplotlib.pyplot as plt

from read_gesture import read_gesture

number_of_gestures_to_plot = 10
number_of_gestures = 50000

for i in range(1, number_of_gestures_to_plot):

    gesture_number = randint(0, number_of_gestures)
    gesture_type, gesture_points = read_gesture(gesture_number)
    plt.title(gesture_type)
    plt.plot([x[0] for x in gesture_points], [x[1] for x in gesture_points], 'o', label='Points')
    plt.show()
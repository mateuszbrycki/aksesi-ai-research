import numpy as np
import gesture_type_converter as converter

final_gesture_matrix_size = 30


def load_gesture(gesture_number):
    gesture_file = open("../data/" + str(gesture_number) + ".txt", "r")
    gesture_type = gesture_file.readline().rstrip()

    gesture_points = gesture_file.readlines()
    gesture_points = gesture_points[1:len(gesture_points)]

    for i in range(len(gesture_points)):
        gesture_points[i] = gesture_points[i].split(" ")
        for x in range(0, 1):
            gesture_points[i][x] = gesture_points[i][x].rstrip("\r\n")

    return gesture_points, gesture_type


def scale_points(new_min, new_max, gesture_points):
    max = np.amax(gesture_points)
    min = np.amin(gesture_points)
    delta = max - min
    new_delta = new_max - new_min

    for i in range(len(gesture_points)):
        for x in range(len(gesture_points[i])):
            gesture_points[i][x] = (new_delta * (gesture_points[i][x] - min) / delta) + new_min

    return gesture_points


def convert_to_array(gesture_points):
    result = np.zeros((final_gesture_matrix_size, final_gesture_matrix_size))

    for points in gesture_points:
        x = int(round(points[0]))
        y = int(round(points[1]))
        result[x][y] = 1

    return result


def prepare_gesture_points(gesture_points):
    gesture_points = np.array(gesture_points).astype(np.float)
    result = convert_to_array(scale_points(0, final_gesture_matrix_size - 1, gesture_points))
    return result


def prepare_gesture_type(gesture_type):
    return converter.to_matrix(gesture_type)


def load_batch(batch_number, batch_size):
    starting_gesture = batch_size * batch_number

    x = np.zeros((batch_size, final_gesture_matrix_size, final_gesture_matrix_size))
    y = np.zeros((batch_size, 5))

    for i in range(starting_gesture, starting_gesture + batch_size):
        current_gesture = i - starting_gesture
        gesture_points, gesture_type = load_gesture(i)

        x[current_gesture] = prepare_gesture_points(gesture_points)
        y[current_gesture] = prepare_gesture_type(gesture_type)

    return x, y

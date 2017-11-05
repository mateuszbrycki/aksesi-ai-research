import numpy as np

predefined_gestures = ['LINE_VERTICAL', 'LINE_HORIZONTAL', 'LINE_DIAGONAL_RIGHT', 'LINE_DIAGONAL_LEFT', 'CIRCLE']


def to_matrix(gesture_type):

    result = np.zeros(len(predefined_gestures))
    index = predefined_gestures.index(gesture_type)
    result[index] = 1

    return result

def get_number(gesture_type):
    return predefined_gestures.index(gesture_type)
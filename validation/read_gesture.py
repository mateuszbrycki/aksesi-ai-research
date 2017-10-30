
def read_gesture(gesture_number):
    gesture_file = open("../data/" + str(gesture_number) + ".txt", "r")
    gesture_type = gesture_file.readline().rstrip()

    gesture_points = gesture_file.readlines()
    gesture_points = gesture_points[1:len(gesture_points)]

    for i in range(len(gesture_points)):
        gesture_points[i] = gesture_points[i].split(" ")
        for x in range(0, 1):
            gesture_points[i][x] = gesture_points[i][x].rstrip("\r\n")

    return gesture_type, gesture_points

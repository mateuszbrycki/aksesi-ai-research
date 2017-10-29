declare -a GESTURES_TYPES=("LINE_VERTICAL" "LINE_HORIZONTAL" "LINE_DIAGONAL_RIGHT" "LINE_DIAGONAL_LEFT" "CIRCLE");

for GESTURE in "${GESTURES_TYPES[@]}"
do
    value=$(find data/ -type f -exec head -n 1 {} \; | grep $GESTURE | wc -l);
    echo -e "$GESTURE\t-> $value";
done
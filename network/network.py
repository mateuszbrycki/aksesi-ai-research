import tensorflow as tf
import input_data
import numpy as np

def conv2d(img, w, b):
    return tf.nn.relu(tf.nn.bias_add(tf.nn.conv2d(img, w, strides=[1,1,1,1], padding='SAME'), b))

def max_pool(img, k):
        return tf.nn.max_pool(img, ksize=[1,k,k,1], strides=[1,k,k,1], padding='SAME')

batch_size = 128

learning_rate = 0.001
training_iters = 100000
display_step = 10

n_input = 900
n_classes = 5

dropout = 0.75

x = tf.placeholder(tf.float32, [None, n_input])
_X = tf.reshape(x, shape=[-1, 30, 30, 1])
y = tf.placeholder(tf.float32, [None, n_classes])

wc1 = tf.Variable(tf.random_normal([5,5,1,32]))
bc1 = tf.Variable(tf.random_normal([32]))
conv1 = conv2d(_X, wc1, bc1)
conv1 = max_pool(conv1, k=2)
keep_prob = tf.placeholder(tf.float32)
conv1 = tf.nn.dropout(conv1, keep_prob)


wc2 = tf.Variable(tf.random_normal([5,5,32,64]))
bc2 = tf.Variable(tf.random_normal([64]))
conv2 = conv2d(conv1, wc2, bc2)
conv2 = max_pool(conv2, k=2)
conv2 = tf.nn.dropout(conv2, keep_prob)

wd1 = tf.Variable(tf.random_normal([8*8*64, 1024]))
bd1 = tf.Variable(tf.random_normal([1024]))
dense1 = tf.reshape(conv2, [-1, wd1.get_shape().as_list()[0]])
dense1 = tf.nn.relu(tf.add(tf.matmul(dense1, wd1), bd1))
dense1 = tf.nn.dropout(dense1, keep_prob)

wout = tf.Variable(tf.random_normal([1024, n_classes]))
bout = tf.Variable(tf.random_normal([n_classes]))

pred = tf.add(tf.matmul(dense1, wout), bout)
cost = tf.reduce_mean(tf.nn.softmax_cross_entropy_with_logits(logits=pred, labels=y))
optimizer = tf.train.AdamOptimizer(learning_rate=learning_rate).minimize(cost)

correct_pred = tf.equal(tf.argmax(pred, 1), tf.argmax(y, 1))
accuracy = tf.reduce_mean(tf.cast(correct_pred, tf.float32))

init = tf.initialize_all_variables()

with tf.Session() as sess:
    sess.run(init)
    step = 0
    while step * batch_size < training_iters:
        batch_xs, batch_ys = input_data.load_batch(step, batch_size)

        batch_xs = np.resize(batch_xs, (batch_size, 900))
        # batch_ys = np.resize(batch_ys, (1, 5))

        sess.run(optimizer, feed_dict={x: batch_xs, y: batch_ys, keep_prob: dropout})
        if step % display_step == 0:
            acc = sess.run(accuracy, feed_dict={x: batch_xs, y: batch_ys, keep_prob: 1.})
            loss = sess.run(cost, feed_dict={x: batch_xs, y: batch_ys, keep_prob: 1.})

            print "Iter " + str(step*batch_size) + ", minbatch Loss={:.6f}".format(loss) + ", Training accuracy={:.5f}".format(acc)

        step += 1
    print "Optimization finished!"

    batch_xs, batch_ys = input_data.load_batch(49999, 1)

    batch_xs = np.resize(batch_xs, (1, 900))
    batch_ys = np.resize(batch_ys, (1, 5))

    print "Testing accuracy: ", \
         sess.run(accuracy,\
                  feed_dict={x: batch_xs,\
                             y: batch_ys,\
                            keep_prob: 1.})


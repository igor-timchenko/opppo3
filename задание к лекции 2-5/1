import numpy as np
import time

# Размер массива
N = 10**7

# Создание массива
array = np.random.rand(N)

def sum_sequential(arr):
    total = 0
    for i in range(len(arr)):
        total += arr[i]
    return total

def sum_random(arr):
    total = 0
    indices = np.random.permutation(len(arr))
    for i in indices:
        total += arr[i]
    return total

# Замер времени для последовательного доступа
start_time = time.time()
sequential_sum = sum_sequential(array)
sequential_time = time.time() - start_time
print(f"Sequential Sum: {sequential_sum}, Time: {sequential_time:.6f} seconds")

# Замер времени для случайного доступа
start_time = time.time()
random_sum = sum_random(array)
random_time = time.time() - start_time
print(f"Random Sum: {random_sum}, Time: {random_time:.6f} seconds")

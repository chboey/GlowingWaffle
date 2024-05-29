# Exploring the Best Algorithmic Approaches for the Travelling Salesman Problem

## Introduction
The Travelling Salesman Problem (TSP) is a classic optimization problem where the goal is to find the shortest possible route that visits each city exactly once and returns to the origin city. This project compares heuristic and metaheuristic algorithms in terms of solution quality and computation time.

## Literature Review
### Approximation Algorithms
Approximation algorithms provide near-optimal solutions to NP-hard problems within a reasonable amount of time.

### Heuristic Algorithms
Heuristic algorithms, like Tabu Search and Nearest Neighbour with 3-OPT, use educated assumptions to explore potential solutions quickly but may require parameter tuning.

### Metaheuristic Algorithms
Metaheuristic algorithms, such as Simulated Annealing, are more flexible and can solve various problems without extensive customization.

## Methodology
### Algorithms and Parameters
Three algorithms were chosen for benchmarking:

| Algorithm                  | Parameters                                    |
|----------------------------|-----------------------------------------------|
| Nearest Neighbour with 3-OPT | N/A                                         |
| Tabu Search                | Maximum Iterations: 1000, Tabu Size: 14       |
| Simulated Annealing        | Cooling Rate: <1.00, Initial Temperature: Calculated, Iteration Count: 100000 |

### Pseudocode
**Nearest Neighbour with 3-OPT**
```plaintext
Initialize tour with nearest neighbour heuristic
repeat until no improvement
  for each edge (i, j) in the tour
    for each edge (k, l) in the tour
      if 3-OPT condition is satisfied
        swap edges (i, j) and (k, l)
return tour
```

**Tabu Search**
```plaintext
Initialize current solution
repeat until stopping criterion met
  generate candidate solutions
  select best candidate not in tabu list
  update tabu list
  if candidate is better than current
    update current solution
return best solution
```

**Simulated Annealing**
```plaintext
Initialize current solution and temperature
repeat until stopping criterion met
  generate new solution by modifying current solution
  if new solution is better, accept it
  else accept it with probability based on temperature
  reduce temperature
return best solution
```

##Performance Assessment

The algorithms were assessed based on the time taken (in nanoseconds) and the total distance of the best solution generated.

Comparison of Solutions
Algorithm	Total Distance (km)	Time Taken (ns)
Nearest Neighbour with 3-OPT	44665	320,969,400
Simulated Annealing	59063	1,657,400
Tabu Search	117538	2,985,697,400

### Comparison of Solutions
| Algorithm                     | Total Distance (km) | Time Taken (ns)   |
|-------------------------------|----------------------|---------------------|
| Nearest Neighbour with 3-OPT | 44665                | 320,969,400         |
| Simulated Annealing           | 59063                | 1,657,400           |
| Tabu Search                   | 117538               | 2,985,697,400       |

### Iteration Analysis
| Algorithm                     | Iterations Used | Time per Iteration (ns) |
|-------------------------------|-----------------|-------------------------|
| Nearest Neighbour with 3-OPT | 1               | 320,969,400             |
| Simulated Annealing           | 100000          | 16.574                  |
| Tabu Search                   | 1000            | 2,985,697.4             |

---

## Sensitivity Analysis

Simulated Annealing Cooling Rate: Adjusting the cooling rate had little effect on the total distance for large iterations.

Tabu Size in Tabu Search: Larger tabu sizes generally resulted in worse solutions due to the algorithm rejecting better solutions that were too similar to previous ones.

## Conclusion
Simulated Annealing offers a good balance between computational time and solution quality, making it the most efficient algorithm for this TSP instance among those tested.



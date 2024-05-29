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



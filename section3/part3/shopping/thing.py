"""
ID: kevinsh4
TASK: shopping
LANG: PYTHON3
"""
from typing import NamedTuple, Counter, Tuple, Set, Dict, List
from dataclasses import dataclass
import heapq, argparse


ProductCode = int
Price       = int


MAX_OFFER_QUANTITY  = 5
MAX_REQUIRED        = 5
MAX_CODE            = 999
MAX_PRICE           = 9999


@dataclass
class Offer:
    quantity:   Counter[ProductCode]
    price:      Price


@dataclass
class Problem:
    price:      Dict[ProductCode, Price]
    offers:     List[Offer]
    required:   Counter[ProductCode]


State = Tuple[int, ...]


def main():
    problem = parse_file('shopping.in')
    with open('shopping.out', 'w') as written:
        written.write(f'{solve(problem)}\n')


def solve(problem: Problem) -> int:
    products    = tuple(problem.required.keys())
    start_state = tuple(problem.required.values())
    goal_state  = (0,) * len(problem.required)

    def successors(state):
        has_offers = False
        for offer in applicable_offers(state):
            has_offers = True
            yield offer.price, apply(state, offer)
        if not has_offers:
            total = sum(
                problem.price[code] * quantity
                for code, quantity in zip(products, state)
            )
            yield total, goal_state

    def applicable_offers(state):
        return (
            offer
            for offer in problem.offers
            if all(
                quantity >= offer.quantity[code]
                for code, quantity in zip(products, state)
            )
        )

    def apply(state, offer):
        return tuple(
            quantity - offer.quantity[code]
            for code, quantity in zip(products, state)
        )

    def goal(state):
        return state == goal_state

    min_price_bound = problem.price.copy()
    for offer in problem.offers:
        total_undiscounted_price = sum(
            problem.price[code] * offer.quantity[code]
            for code in offer.quantity
        )
        for code in offer.quantity:
            discount_price = problem.price[code] * offer.price / total_undiscounted_price
            min_price_bound[code] = min(
                min_price_bound[code],
                discount_price
            )

    def heuristic(state):
        return sum(
            quantity * min_price_bound[code]
            for code, quantity in zip(products, state)
        )

    def prune(state):
        if state in prune.visited:
            return True
        elif (s := sum(state)) < prune.min_sum:
            prune.min_sum = s
            prune.visited.add(state)
            return False
        else:
            dominated = (not sum(state) < prune.min_sum) and any(
                all(
                    a >= b
                    for a, b in zip(state, other)
                )
                for other in prune.visited
            )
            prune.visited.add(state)
            return dominated

    prune.visited   = set()
    prune.min_sum   = float('inf')

    return heuristic_min_cost_search(
        start           = start_state,
        successor_fn    = successors,
        goal_fn         = goal,
        heuristic_fn    = heuristic,
        prune_fn        = prune,
    )


def parse_file(filename):
    problem = Problem(price=dict(), offers=list(), required=Counter())

    with open(filename, 'r') as file:
        num_offers = int(file.readline())
        for i in range(num_offers):
            num_pairs, *pairs, price = map(int, file.readline().split())

            assert 0 <= price <= MAX_PRICE
            assert len(pairs) == num_pairs * 2

            offer = Offer(quantity=Counter(), price=price)

            for code, quantity in zip(pairs[0::2], pairs[1::2]):
                assert 0 <= code        <= MAX_CODE
                assert 0 <= quantity    <= MAX_OFFER_QUANTITY
                offer.quantity[code] = quantity

            problem.offers.append(offer)

        num_unique_items = int(file.readline())
        for i in range(num_unique_items):
            code, quantity, price   = map(int, file.readline().split())

            assert 0 <= code        <= MAX_CODE
            assert 0 <= quantity    <= MAX_REQUIRED
            assert 0 <= price       <= MAX_PRICE

            problem.price[code]     = price
            problem.required[code]  = quantity

    return problem


def heuristic_min_cost_search(start, successor_fn, goal_fn, heuristic_fn, prune_fn):
    heap    = [(0, 0, start)]
    while heap:
        heuristic_cost, cost, state = heapq.heappop(heap)
        if prune_fn(state):
            continue
        if goal_fn(state):
            return cost
        for edge_cost, new_state in successor_fn(state):
            new_cost = cost + edge_cost
            new_heuristic_cost = new_cost + heuristic_fn(new_state)
            heapq.heappush(heap, (new_heuristic_cost, new_cost, new_state))


main()
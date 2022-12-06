#!/bin/bash

for f in ./src/day*.clj; do
    file="$(basename "$f")"
    clj -X "${file%%.*}/run"
done

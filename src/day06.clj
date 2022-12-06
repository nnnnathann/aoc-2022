(ns day06
  (:require aoc)
  (:require clojure.set)
  (:require clojure.string))

; description file (includes some various context and test cases)
(def data-file (aoc/read-inputs "day06"))

; input 1 file
(defn real-input1 [] (aoc/read-file "day06_input1"))

(def puzzle
  {:input1 (apply str (get data-file :input1))
   :expect1 (read-string (get data-file :expected1))
   :real1 (real-input1)
   :input2 (get data-file :input2)
   :expect2 (read-string (get data-file :expected2))
   :real2 (real-input1)})

(defn all-different [coll]
  (= (count (set coll)) (count coll)))

; find the index of the first set of
; n distinct chars
(defn first-disjoint [n coll i]
  (if (all-different (take n (drop i coll)))
    (+ n i)
    (recur n coll (+ i 1))))

(defn solve1
  "solve the first puzzle stage"
  [input]
  (first-disjoint 4 input 0))

(defn solve2
  "solve the second puzzle stage"
  [input]
  (first-disjoint 14 input 0))


#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defn run [_opts]
  (aoc/run-puzzle solve1 solve2 puzzle))

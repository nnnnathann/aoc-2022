(ns day04
  (:require aoc)
  (:require clojure.set)
  (:require clojure.string))

; description file (includes some various context and test cases)
(def data-file (aoc/read-inputs "day04"))

; input 1 file
(defn real-input1 [] (aoc/read-file-lines "day04_input1"))

(def puzzle
  {:input1 (get data-file :input1)
   :expect1 (read-string (get data-file :expected1))
   :real1 (real-input1)
   :input2 (get data-file :input2)
   :expect2 (read-string (get data-file :expected2))
   :real2 (real-input1)})

(defn parse-pair [line]
  (aoc/map-in read-string (map #(clojure.string/split % #"-") (clojure.string/split line #","))))

(defn contains [[[a b] [c d]]]
  (or (and (>= a c) (<= b d)) (and (>= c a) (<= d b))))

(defn overlap [[a b]]
  (not (or (< (apply max a) (apply min b)) (> (apply min a) (apply max b)))))

(defn solve1
  "solve the first puzzle stage"
  [input]
  (aoc/sum (map #(if (contains %) 1 0) (map parse-pair input))))

(defn solve2
  "solve the second puzzle stage"
  [input]
  (aoc/sum (map #(if (overlap %) 1 0) (map parse-pair input))))


#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defn run [_opts]
  (aoc/run-puzzle solve1 solve2 puzzle))

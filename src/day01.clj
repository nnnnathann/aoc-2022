(ns day01
  (:require aoc)
  (:require clojure.string))

; description file (includes some various context and test cases)
(def data-file (aoc/read-inputs "day01"))

; input 1 file
(defn real-input1 [] (aoc/read-file-lines "day01_input1"))

; input 2 is the same as 1 the first day
(defn real-input2 [] (aoc/read-file-lines "day01_input1"))

(def day1puzzle
  {:input1 (get data-file :input1)
   :expect1 (read-string (get data-file :expected1))
   :real1 (real-input1)
   :input2 (get data-file :input2)
   :expect2 (read-string (get data-file :expected2))
   :real2 (real-input2)})

(defn sum-line-groups
  "reads lines separated by blanks and sums each group"
  [input]
  (->>
   (aoc/sep-by-blank-lines input)
   (#(aoc/map-in read-string %))
   (#(map aoc/sum %))
   (aoc/sort-rev)))

(defn solve1
  "solve the first puzzle stage"
  [input]
  (first (sum-line-groups input)))

(defn solve2
  "solve the second puzzle stage"
  [input]
  (aoc/sum (take 3 (sum-line-groups input))))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defn run [_opts]
  (aoc/run-puzzle solve1 solve2 day1puzzle))

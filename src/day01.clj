(ns day01
  (:require aoc)
  (:require clojure.string))

(def data-file (aoc/read-inputs "day01"))

(defn real-input1 [] (aoc/read-file-lines "day01_input1"))

(defn real-input2 [] (aoc/read-file-lines "day01_input1"))

(defn sum-line-groups
  "reads lines separated by blanks and sums the groups"
  [input]
  (->>
   (aoc/sep-by-blank-lines input)
   (#(aoc/map-in read-string %))
   (#(map aoc/sum %))
   (aoc/sort-rev)))

(defn solve1
  [input]
  (first (sum-line-groups input)))



(defn solve2
  [input]
  (aoc/sum (take 3 (sum-line-groups input))))

(def puzzle
  {:input1 (get data-file :input1)
   :expect1 (read-string (get data-file :expected1))
   :real1 (real-input1)
   :input2 (get data-file :input2)
   :expect2 (read-string (get data-file :expected2))
   :real2 (real-input2)})

(defn run-puzzle [p]
  (println "test match 1: " (solve1 (get p :input1)) (get p :expect1))
  (println "real answer 1: " (solve1 (get p :real1)))
  (println "test match 2: " (solve2 (get p :input2)) (get p :expect2))
  (println "real answer 2: " (solve2 (get p :real2))))


(defn run [opts]
  (run-puzzle puzzle))


(defn run-bkp [opts]
  (let [input1 (get data-file :input1)
        expect1 (read-string (get data-file :expected1))
        real1 (real-input1)
        input2 (get data-file :input2)
        expect2 (read-string (get data-file :expected2))
        real2 (real-input2)]
    (println "test match 1: " (solve1 input1) expect1)
    (println "real answer 1: " (solve1 real1))
    (println "test match 2: " (solve2 input2) expect2)
    (println "real answer 2: " (solve2 real2))))
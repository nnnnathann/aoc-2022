(ns day03
  (:require aoc)
  (:require clojure.set))

; description file (includes some various context and test cases)
(def data-file (aoc/read-inputs "day03"))

; input 1 file
(defn real-input1 [] (aoc/read-file-lines "day03_input1"))

(def puzzle
  {:input1 (get data-file :input1)
   :expect1 (read-string (get data-file :expected1))
   :real1 (real-input1)
   :input2 (get data-file :input2)
   :expect2 (read-string (get data-file :expected2))
   :real2 (real-input1)})

(defn char-priority [c]
  (let [ci (int c)]
    (if (< ci 97) (+ 27 (- ci 65)) (+ 1 (- ci 97)))))

(defn halve [xs] (split-at (/ (count xs) 2) xs))

(defn find-errors [sackline]
  (apply clojure.set/intersection
         (map set (halve (map char-priority sackline)))))

(defn find-badges [sackgroup]
  (apply clojure.set/intersection (map set (aoc/map-in char-priority sackgroup))))

(defn solve1
  "solve the first puzzle stage"
  [input]
  (aoc/sum (map aoc/sum (map #(into [] %) (map find-errors input)))))

(defn solve2
  "solve the second puzzle stage"
  [input]
  (aoc/sum (map (comp aoc/sum find-badges) (partition 3 input))))


#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defn run [_opts]
  (aoc/run-puzzle solve1 solve2 puzzle))

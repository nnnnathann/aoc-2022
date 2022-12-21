(ns day07
  (:require aoc
            [clojure.string :as string])
  (:require clojure.set)
  (:require clojure.string))

; description file (includes some various context and test cases)
(def data-file (aoc/read-inputs "day07"))

; input 1 file
(defn real-input1 [] (aoc/read-file "day07_input1"))

(def puzzle
  {:input1 (get data-file :input1)
   :expect1 (read-string (get data-file :expected1))
   :real1 (real-input1)
   :input2 (get data-file :input2)
   :expect2 (read-string (get data-file :expected2))
   :real2 (real-input1)})

(defn split-path [p] (string/split p #"/"))

(defn update-cwd [cwd dir]
  (if (= dir "..") (drop-last (split-path cwd)) cwd))

(defn dir-size-reduce [[acc cwd] line]
  (if (string/starts-with? "$ cd " line) [acc (update-cwd cwd (string/split line #"$ cd "))] [acc cwd]))

(defn get-dir-sizes
  [lines]
  (get 0 (reduce dir-size-reduce [0 "/"] lines)))

(defn solve1
  "solve the first puzzle stage"
  [input]
  (aoc/sum (get-dir-sizes input)))

(defn solve2
  "solve the second puzzle stage"
  [input]
  (println "todo"))


#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defn run [_opts]
  (aoc/run-puzzle solve1 solve2 puzzle))

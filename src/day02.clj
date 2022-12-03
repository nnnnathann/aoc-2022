(ns day02
  (:require aoc)
  (:require [clojure.string :as string]))

; description file (includes some various context and test cases)
(def data-file (aoc/read-inputs "day02"))

; input 1 file
(defn real-input1 [] (aoc/read-file-lines "day02_input1"))

; input 2 is the same as 1 the first day
(defn real-input2 [] (aoc/read-file-lines "day02_input1"))

(def puzzle
  {:input1 (get data-file :input1)
   :expect1 (read-string (get data-file :expected1))
   :real1 (real-input1)
   :input2 (get data-file :input2)
   :expect2 (read-string (get data-file :expected2))
   :real2 (real-input2)})

(require '[clojure.core.match :refer [match]])


(def shape-value {:rock 1, :paper 2, :scissors 3})


(def decoder1 {"A" :rock, "B" :paper, "C" :scissors
               , "X" :rock, "Y" :paper, "Z" :scissors})

(def decoder2 {"A" :rock, "B" :paper, "C" :scissors
               , "X" :left, "Y" :tie, "Z" :right})


(defn winner [[a b]]
  (match [a b]
    [:rock :paper] :right
    [:rock :scissors] :left
    [:paper :rock] :left
    [:paper :scissors] :right
    [:scissors :rock] :right
    [:scissors :paper] :left
    :else :tie))

(defn score-game [[a b]]
  (let [av (get shape-value a)
        , bv (get shape-value b)]
    (match (winner [a b])
      :left [(+ av 6) bv]
      :right [av (+ bv 6)]
      :tie [(+ av 3) (+ bv 3)])))

(def beats {:rock :paper, :paper :scissors, :scissors :rock})
(def loses {:rock :scissors, :paper :rock, :scissors :paper})

(defn find-shape [left outcome]
  (match outcome
    :tie left
    :left (get loses left)
    :right (get beats left)))

(defn decode1
  [code]
  (get decoder1 code))

(defn decode2
  [code]
  (get decoder2 code))

(defn read-games [decoder input]
  (map #(map decoder (string/split % #" ")) input))

(defn total-score [games]
  (->> (map score-game games) (map second) (aoc/sum)))

(defn solve1
  "solve the first puzzle stage"
  [input]
  (total-score (read-games decode1 input)))


(defn replace-shape [[left right]]
  [left (find-shape left right)])

(defn solve2
  "solve the second puzzle stage"
  [input]
  (total-score (map replace-shape (read-games decode2 input))))

(defn run1 [_opts]
  (println (solve1 (get puzzle :input1))))

(defn run2 [_opts]
  (println (solve2 (get puzzle :input2))))


#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defn run [_opts]
  (aoc/run-puzzle solve1 solve2 puzzle))

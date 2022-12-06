(ns day05
  (:require aoc
            [clojure.string :as string])
  (:require clojure.set)
  (:require clojure.string))

; description file (includes some various context and test cases)
(def data-file (aoc/read-inputs "day05"))

; input 1 file
(defn real-input1 [] (aoc/read-file-lines "day05_input1"))

(def puzzle
  {:input1 (get data-file :input1)
   :expect1 (get data-file :expected1)
   :real1 (real-input1)
   :input2 (get data-file :input2)
   :expect2 (get data-file :expected2)
   :real2 (real-input1)})

(defn read-crates [input]
  (->>
   (map #(str % " ") input)
   (map #(partition 4 %))
   (aoc/map-in #(apply str %))
   (aoc/map-in #(re-find #"\w" %))
   drop-last))

(defn read-instruction [line]
  (let [nums (map read-string (re-seq #"\d+" line))]
    {:move (nth nums 0), :from (nth nums 1), :to (nth nums 2)}))

(defn parse-input [input]
  (let [[crates-str, instructions-str] (aoc/remove-blank-line-groups (partition-by #(not (string/includes? "move" %)) input))]
    {:instructions (map read-instruction instructions-str)
     ,:crates (read-crates crates-str)}))

(defn get-top [crates]
  (->>
   (aoc/transpose crates)
   (aoc/filter-in aoc/not-nil)
   (map first)
   (apply str)))

(defn apply-instruction
  "apply a series of move / from / to instructions to a parsed
   set of crate stacks. incremental flag true means the simulation
   will move one crate at a time, false means multi-crate pickup"
  [crates, {m :move, f :from, t :to}, incremental]
  (let [; transpose the crate stacks for easier list operations
        ct (aoc/transpose crates)
        ; instructions are not zero indexed
        , f' (- f 1)
        , t' (- t 1)
        ; from and to columns
        , from (nth ct f')
        , to (nth ct t')
        ; get the moved crates from the from
        , moved (aoc/take-n-non-nil m from)
        ; new from column with removed crates
        , newfrom (aoc/drop-n-non-nil m from)
        ; new "to" column with added crates
        , newto (aoc/unshift-non-nil (if incremental (reverse moved) moved) to)
        ; find the max height of the new stack to normalize height
        , height (max (count newto) (apply max (map count ct)))
        ; updated stacks (before padding the height to match)
        , newstack (assoc (assoc ct f' newfrom) t' newto)]
    (aoc/transpose (map #(aoc/pad-before height nil %) newstack))))

(defn solve1
  "solve the first puzzle stage"
  [input]
  (let [{instructions :instructions, crates :crates} (parse-input input)]
    (get-top (reduce #(apply-instruction %1 %2 true) crates instructions))))

(defn solve2
  "solve the second puzzle stage"
  [input]
  (let [{instructions :instructions, crates :crates} (parse-input input)]
    (get-top (reduce #(apply-instruction %1 %2 false) crates instructions))))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defn run [_opts]
  (aoc/run-puzzle solve1 solve2 puzzle))

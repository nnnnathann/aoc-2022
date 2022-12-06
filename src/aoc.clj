(ns aoc
  (:require [clojure.java.io :as io])
  (:require [clojure.string :as string]))

(defn map-in
  "map an iterable of iterables"
  [f xs]
  (map #(map f %) xs))

(defn filter-in
  "filter an iterable of iterables"
  [f xs]
  (map #(filter f %) xs))

(defn sum
  "sum it up!"
  [xs]
  (reduce + xs))

(defn sort-rev
  "sort then reverse"
  [xs]
  (reverse (sort xs)))

(defn read-file
  "reads a resource file based on the name, assumes text ext"
  [day]
  (slurp (io/reader (str "resources/" day ".txt"))))

(defn read-file-lines [name]
  (string/split-lines (read-file name)))

(defn is-line-delimiter [input]
  (not (= nil (re-find #"^```" input))))


(defn remove-blank-line-groups [input]
  (filter not-empty (aoc/filter-in (comp not string/blank?) input)))

(defn parse-inputs
  "returns a puzzle day file from normalized input format (description, inputs, expectations for testing)"
  [content]
  (let [parts (partition-by is-line-delimiter content)]
    {:description (nth parts 0)
     :input1 (nth parts 2)
     :expected1 (string/join (nth parts 6))
     :input2 (nth parts 10)
     :expected2 (string/join (nth parts 14))}))

(defn sep-by-blank-lines
  [input]
  (filter not-empty (map #(filter not-empty %) (partition-by clojure.string/blank? input))))

(defn read-inputs
  "parses an input description file into sections based on my weird little delimiter format ``` "
  [day]
  (let [content (string/split-lines (read-file day))]
    (if (< (count content) 14) (throw "invalid input") (parse-inputs content))))


(defn run-puzzle
  "accepts two solvers and a puzzle definition with :input1 :expect1 :real1 (and the 2 counterparts)"
  [solver1 solver2 p]
  (time (do (println "test match 1: " (solver1 (get p :input1)) (get p :expect1))
            (println "real answer 1: " (solver1 (get p :real1)))
            (println "test match 2: " (solver2 (get p :input2)) (get p :expect2))
            (println "real answer 2: " (solver2 (get p :real2))))))


(defn transpose [m]
  (apply mapv vector m))

(defn pad-before [len val colls]
  (let [diff (- len (count colls))]
    (if (> diff 0) (concat (repeat diff val) colls) colls)))

(defn not-nil [a]
  (not= nil a))

(defn take-n-non-nil [n colls]
  (take n (filter aoc/not-nil colls)))

(defn drop-n-non-nil [n colls]
  (let [len (count colls)
        , remain (drop n (filter aoc/not-nil colls))]
    (pad-before len nil remain)))

(defn unshift-non-nil [els colls]
  (let [len (count colls), non-null (filter aoc/not-nil colls)]
    (pad-before len nil (concat els non-null))))

(defn zip [a b] (map vector a b))

(defn index [a] (zip (range 0 (count a)) a))

(defn debug-pipe [msg a]
  (let [_ (println (if (not-empty msg) msg "DEBUG: ") a)]
    a))
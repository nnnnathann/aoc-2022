(ns aoc
  (:require [clojure.java.io :as io])
  (:require [clojure.string :as string]))

(defn map-in [f xs]
  (map #(map f %) xs))

(defn sum [xs]
  (reduce + xs))

(defn sort-rev
  [xs]
  (reverse (sort xs)))

(defn read-file [day]
  (slurp (io/reader (str "resources/" day ".txt"))))

(defn read-file-lines [name]
  (string/split-lines (read-file name)))

(defn is-line-delimiter [input]
  (not (= nil (re-find #"^```" input))))


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
  [day]
  (let [content (string/split-lines (read-file day))]
    (parse-inputs content)))

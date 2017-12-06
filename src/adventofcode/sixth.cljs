(ns adventofcode.sixth
  (:require [cljs.nodejs :as nodejs]
            ))

(nodejs/enable-util-print!)

(def input [0 2 7 0])

(defn get-max [row]
  (apply max row))

(def size (count input))
(println "input size" size)

(def ones (repeat 1))
(def zeros (repeat 0))

(defn partition-memory [memory size index]
  (if (< memory size)
    (concat 
      (take (- (+ memory index) size) ones)
      (take index zeros)
      (take (+ index (- size memory)) ones))

    (let [div (dec size)
          remaining (mod memory div)
          parts (quot memory div)]
      (assoc 
        (apply vector (take size (repeat parts)))
        index
        remaining))))


(defn solution []
  (let [target (get-max input)
        index (.indexOf input target)]
    (println 
      target
      index)
    (partition-memory target size index)))

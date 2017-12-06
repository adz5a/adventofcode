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

(defn shift [offset vect]
  (let [length (count vect)
        head (drop (- length offset) vect)
        tail (take offset vect)]
    (apply vector (concat head tail))))

(defn partition-memory [memory size index]
  (if (< memory size)
    (let [vect (concat
                 '(0)
                 (take memory ones)
                 (take (- size memory) zeros))
          shifted-vect (shift index vect)]
      shifted-vect)

    (let [div (dec size)
          remaining (mod memory div)
          parts (quot memory div)]
      (assoc 
        (apply vector (take size (repeat parts)))
        index
        remaining))))


(defn update-memory [current-state update-vect index]
  (apply vector (map + (assoc current-state index 0) update-vect)))

(defn solution []
  (loop [history #{}
         state input]

    (if (contains? history state)
      (count history)

      (let [target (get-max state)
            index (.indexOf state target)
            update-vect (partition-memory target size index)
            new-state (update-memory state update-vect index)]

        (println 
          target
          index
          update-vect
          new-state)

        (recur
          (conj history state)
          new-state)))))

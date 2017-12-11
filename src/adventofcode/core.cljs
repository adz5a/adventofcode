(ns adventofcode.core
  (:require [cljs.nodejs :as nodejs]))

(nodejs/enable-util-print!)

(def move-north [1
                 1
                 0
                 -1
                 0
                 0])

(def x [1 0 0 0 0 0])

(defn rotate [vect n]
  (let [head (take n vect)
        tail (drop n vect)]
    (apply vector (concat tail head))))

(def zeros (repeat 0))

(defn vect-k [dim k]
  (apply vector (concat (take k zeros)
                          '(1)
                          (take (- dim k) zeros))))

; (def rotation-matrix [(rotate 1 x)
;                       x
;                       (rotate 2 x)])

(def identity-matrix (apply vector (map (partial vect-k 5) (range 6))))

(defn mat-product [mat vect]
  (apply vector (map
                  (fn [line]
                    (reduce
                      (partial +)
                      (map vector line vect)))
                  mat)))


(println identity-matrix)
(println (rotate identity-matrix 1))

(ns adventofcode.third
  (:require [cljs.nodejs :as nodejs]))

(nodejs/enable-util-print!)

(defn move-right [pos]
  (let [[x y] pos]
    [(inc x) y]))

(defn move-left [pos]
  (let [[x y] pos]
    [(dec x) y]))

(defn move-up [pos]
  (let [[x y] pos]
    [x (inc y)]))

(defn move-down [pos]
  (let [[x y] pos]
    [x (dec y)]))


(defn abs [n]
  (max (- n) n))


(defn steps [pos]
  (apply + (map abs pos)))

(defn dist [pos]
  (apply max (map abs pos)))


(def move-fns [move-left move-up move-down move-right])

(defn choose-move [moves]
  (let [dist-moves (apply hash-map 
                          (apply concat (map #(list (dist %) %)
                                             moves)))
        min-dist (apply min (keys dist-moves))]
    (dist-moves min-dist)))

(defn get-moves [pos]
  (map #(% pos) move-fns))

(defn move
  ([pos]
   (move pos #{}))
  ([pos previous-pos]
   (let [moves (filter
                 #(nil? (previous-pos %))
                 (get-moves pos))]
     (choose-move moves))))

(defn solution []
  (loop [pos [0 0]
         prevs #{[0 0]}]
    (if (= (count prevs) 265149)
      (do
        (steps pos))
      (let [next-move (move pos prevs)]
        (recur next-move
               (conj prevs next-move))))))

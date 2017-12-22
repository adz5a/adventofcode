(ns adventofcode.core
  (:require [cljs.nodejs :as nodejs]
            [cljs.pprint :refer [pprint]]
            [cljs.test :refer-macros [deftest
                                      is
                                      testing
                                      run-tests]]))

(nodejs/enable-util-print!)

(def input 265149)

(defn sqrt [v]
  (.sqrt js/Math v))

(defn floor [v]
  (.floor js/Math v))

(def abs (comp
           (partial apply max)
           (juxt + -)))

(defn m-norm [vect]
 (reduce + (map 
             abs
             vect)))

(deftest test:m-norm
  (is (= 2
         (m-norm [1 1])))
  (is (= 2
         (m-norm [-1 1]))))


(defn norm [v]
  (->> v
      (map abs)
      (apply max)))

(deftest test:norm
  (is (= 1
         (norm [1 1])))
  (is (= 1
         (norm [-1 1]))))

(defn compare-norms [v1 v2]
  (< (norm v1)
     (norm v2)))

(deftest test:compare-norm
  (is (= true
         (compare-norms [1 1]
                        [2 1])))

  (is (= false
         (compare-norms [1 1]
                        [0 1])))

  (is (= [[1 1]
          [1 0]
          [2 1]]
         (sort compare-norms [
                              [1 1]
                              [1 0]
                              [2 1]
                              ]))))


(defn get-moves [[x y]]
  (rest (sort [[(dec x) y]
               [(inc x) y]
               [x (inc y)]
               [x (dec y)]])))

(defn get-upper [n]
  (let [l (floor (sqrt n))
        ])
  (inc l))

(defn get-possible-moves [coord]
  (let [n (norm coord)
        moves (filter #(= n (norm %))
                     (get-moves coord))]
    (apply vector moves)))

(deftest test:get-possible-moves
  (is (= [
          [2 0]
          [2 2]
          ]
         (get-possible-moves [2 1]))))


(pprint sol1)
; (run-tests)

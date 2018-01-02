(ns adventofcode.core
  (:require [cljs.nodejs :as nodejs]
            [lumo.io :refer [slurp]]
            [clojure.string :refer [split]]
            [cljs.test :refer-macros [deftest
                                      is
                                      testing
                                      run-tests]]))

(nodejs/enable-util-print!)

(defn toVector [line]
  (let [[model transform] (split line " => ")]
    [(apply vector (map #(apply vector (split % ""))
                        (split model "/")))
     (apply vector (map #(apply vector (split % ""))
                        (split transform "/")))]))

(def rules (as-> (slurp "src/adventofcode/rules.txt") v
                (split v "\n")
                (map toVector v)
                (reduce #(conj %1  [(first %2)
                                    (second %2)]) {} v)))

(println rules)

(defn flip [pattern]
  ;; pattern [l1 l2]
 (let [lines (apply map vector pattern)]
   (apply vector (map #(->> %
                           (reverse)
                           (apply vector)) lines))))


(deftest test:flip
  (is (= [
          [1 1 1]
          [2 2 2]
          [3 3 3]
          ]
         (flip [
                [1 2 3]
                [1 2 3]
                [1 2 3]
                ])))


  (is (=
       [
          [3 2 1]
          [3 2 1]
          [3 2 1]
        ]
       (flip (flip [
                    [1 2 3]
                    [1 2 3]
                    [1 2 3]
                    ]))))

  (is (=
       [[4 3]
        [2 1]]
       (flip [
              [3 1]
              [4 2]
              ])))
  )

(run-tests)

(ns adventofcode.core
  (:require [cljs.nodejs :as nodejs]
            [cljs.test :refer-macros [deftest
                                      is
                                      testing
                                      run-tests]]))

(nodejs/enable-util-print!)
(defn flip [pattern]
  ;; pattern [l1 l2]
 (let [lines (apply map vector pattern)]
   (apply vector (map #(->> %
                           (reverse)
                           (apply vector)) lines))))

(nodejs/enable-util-print!)

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
        [3 4]]
       (flip [
              [3 1]
              [4 2]
              ])))
  )

(run-tests)

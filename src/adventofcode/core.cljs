(ns adventofcode.core
  (:require [cljs.nodejs :as nodejs]
            [cljs.test :refer-macros [deftest
                                      is
                                      testing
                                      run-tests]]))

(def particules [
                 {:p [  3,0,0] :v [ 2,0,0] :a [ -1,0,0]}
                 {:p [  4,0,0] :v [ 0,0,0] :a [ -2,0,0]}
                 ])

(def p1 (first particules))
(def p2 (second particules))

(nodejs/enable-util-print!)


(defn dot-product [v1 v2]
  (reduce + (map * v1 v2)))

(defn abs[n]
  (max n (- n)))

(defn m-norm [vect]
  (apply + (map abs vect)))

(defn vect-sum [& vectors]
  (apply vector (apply map + vectors)))

(defn scal-prod [scal vect]
  (apply vector (map (partial * scal) vect)))

(defn get-pivoting-position [p]
  (let [a (:a p)
        v (:v p)
        position (:p p)
        dot-prod-value (dot-product a v)]

    (if (pos? dot-prod-value)
      position

      (let [norm-a (m-norm a)
            norm-v (m-norm v)
            k (quot norm-v norm-a)
            a (scal-prod (inc k)
                         a)
            move (vect-sum v a)]
        (println move)
        (vect-sum position
                  v
                  a)))))

(deftest test:vect-sum
  (is (= [3 4 3]
         (vect-sum
           [1 1 1]
           [1 2 1]
           [1 1 1]))))

(deftest test:scal-prod
  (is (= [3 3 3]
         (scal-prod 3 [1 1 1]))))

(deftest test:m-norm
  (is (= 0
         (m-norm [0 0 0])))
  (is (= 3
         (m-norm [1 1 1])))
  (is (= 3
         (m-norm [1 -1 1]))))


(deftest test:dot-product
  (is (= 0
         (dot-product
           [1 1 1]
           [0 0 0])))
  (is (= 0
         (dot-product
           [1 0 1]
           [-1 0 1]))))


(run-tests)

(println (get-pivoting-position p1))

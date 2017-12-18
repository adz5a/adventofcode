(ns adventofcode.core
    (:require [cljs.nodejs :as nodejs]
              [cljs.test :refer-macros [deftest
                                        is
                                        testing
                                        run-tests]]))

(nodejs/enable-util-print!)

; (def commands [
;             [:set :a 1]
;             [:add :a 2]
;             [:mul :a :a]
;             [:mod :a 5]
;             [:snd :a]
;             [:set :a 0]
;             [:rcv :a]
;             [:jgz :a -1]
;             [:set :a 1]
;             [:jgz :a -2]
;             ])

(def commands [
               [:set :i 31]
               [:set :a 1]
               [:mul :p 17]
               [:jgz :p :p]
               [:mul :a 2]
               [:add :i -1]
               [:jgz :i -2]
               [:add :a -1]
               [:set :i 127]
               [:set :p 826]
               [:mul :p 8505]
               [:mod :p :a]
               [:mul :p 129749]
               [:add :p 12345]
               [:mod :p :a]
               [:set :b :p]
               [:mod :b 10000]
               [:snd :b]
               [:add :i -1]
               [:jgz :i -9]
               [:jgz :a 3]
               [:rcv :b]
               [:jgz :b -1]
               [:set :f 0]
               [:set :i 126]
               [:rcv :a]
               [:rcv :b]
               [:set :p :a]
               [:mul :p -1]
               [:add :p :b]
               [:jgz :p 4]
               [:snd :a]
               [:set :a :b]
               [:jgz 1 3]
               [:snd :b]
               [:set :f 1]
               [:add :i -1]
               [:jgz :i -11]
               [:snd :a]
               [:jgz :f -16]
               [:jgz :a -19]
               ])

(defn operate-on-registers [operation registers register operande]
  (let [target (register registers)
        value (if (keyword? operande)
                (operande registers)
                operande)]
    (assoc registers register (operation target value))))

(def r-set (partial operate-on-registers (fn [_ v]
                                         v)))

(deftest test:r-set
  (is (= {:a 1} (r-set {} :a 1)))
  (is (= {:a 2 :b 2}
         (r-set
           (r-set {} :a 2)
           :b
           :a))))


(def r-mul (partial operate-on-registers *))

(deftest test:r-mul
  (is (= {:a 6} (r-mul {:a 3} :a 2)))
  (is (= {:a 9} (r-mul {:a 3} :a :a))))


(def r-add (partial operate-on-registers +))

(def r-mod (partial operate-on-registers mod))



(run-tests)

(def operations {:set r-set
                 :mul r-mul
                 :add r-add
                 :mod r-mod})

(def res (loop [idx 0
                snd nil
                registers {}]
           (let [command (nth commands idx)
                 [cmd-name target-register arg] command]

             (case cmd-name
               :snd (recur (inc idx)
                           (target-register registers)
                           registers)

               :jgz (let [offset (if (keyword? arg)
                                   (arg registers)
                                   arg)
                          reg-value (target-register registers)]
                      (if (> reg-value 0)
                        (recur (+ idx offset)
                               snd
                               registers)
                        (recur (inc idx)
                               snd
                               registers)))

               :rcv (if (not= snd 0)
                      snd
                      (recur (inc idx)
                             snd
                             registers))

               (recur (inc idx)
                      snd
                      ((cmd-name operations)
                       registers
                       target-register
                       arg))))))

(println res)

(ns adventofcode.core
  (:require 
            [cljs.nodejs :as nodejs]
            [lumo.io :refer [slurp]]
            [clojure.pprint :refer [pprint]]
            [cljs.test :refer-macros [deftest
                                      is
                                      testing
                                      run-tests]]
            [clojure.string :refer [split]]))

(def input (split 
             (slurp "src/adventofcode/input.txt")
             "\n"))

; (println (first input))

(def cmds {"inc" +
           "dec" -})

(def preds {"==" =
            "<" <
            ">" >
            "!=" not=
            ">=" >=
            "<=" <=})

(defn process-tokens [registers
                      [target cmd value _ reg pred operande]]


  (let [offset (int value)
        predicate (get preds pred)
        update-reg (get cmds cmd)
        pred-val (predicate 
                   (int (get registers reg))
                   (int operande))]
    (if pred-val
      (update registers target #(update-reg % offset))
      registers)))

(nodejs/enable-util-print!)

(def final-state (reduce process-tokens {}
                         (map #(split % " ") input)))

; (def reversed-map (apply hash-map 
;                          (apply concat (map #(list (get final-state %) %)
;                                                      (keys final-state)))))
(pprint final-state)
(def sol1 (apply max (map second final-state)))
(println sol1)

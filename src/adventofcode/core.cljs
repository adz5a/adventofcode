(ns adventofcode.core
  (:require 
            [cljs.nodejs :as nodejs]
            [lumo.io :refer [slurp]]
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
                      [target cmd valueS _ reg pred operande]]

  ; (println target cmd valueS reg pred operande)
  (let [value (int valueS)
        curr-reg-val (get registers reg)
        predicate (get preds pred)
        pred-val (predicate curr-reg-val (int operande))]
    (if pred-val
      (update registers target (get cmds cmd) value)
      registers)))

(nodejs/enable-util-print!)

(def final-state (reduce process-tokens {}
                         (map #(split % " ") input)))

(def reversed-map (apply hash-map 
                         (apply concat (map #(list (get final-state %) %)
                                                     (keys final-state)))))

(def m (apply min (keys reversed-map)))
(println (get reversed-map m))

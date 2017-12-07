(ns adventofcode.core
  (:require [cljs.nodejs :as nodejs]))

(nodejs/enable-util-print!)

(def input [
            ['pbga]
            ['xhth]
            ['ebii]
            ['havc]
            ['ktlj]
            ['fwft  ['ktlj, 'cntj, 'xhth]]
            ['qoyq]
            ['padx  ['pbga, 'havc, 'qoyq]]
            ['tknk  ['ugml, 'padx, 'fwft]]
            ['jptl]
            ['ugml  ['gyxo, 'ebii, 'jptl]]
            ['gyxo]
            ['cntj]
            ]
)


(defn build-map [input]
  (reduce (fn [hmap
               [prog-name children] program]
            (assoc hmap prog-name children))
          {}
          input
          ))

(def has-child nil?)

(defn build-sets [input]
  (reduce (fn [state
               [prog-name children] program]
            (let [has-children (vector? children)

                  with-parent (apply conj (:with-parent state) children)

                  with-no-parent (if (contains? 
                                       (:with-parent state)
                                       prog-name)

                                   (:with-no-parent state)
                                   (conj (:with-no-parent state) prog-name))]



              {:with-parent with-parent
               :with-no-parent with-no-parent}))

          {:with-parent #{}
           :with-no-parent #{}}
          input))

(defn solution [input]
  (let [sets (build-sets input)]
   (filter #(not ((:with-parent sets) %)) 
           (:with-no-parent sets))))
; (println (build-map input))

(println (solution input))

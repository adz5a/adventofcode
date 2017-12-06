(ns adventofcode.core
  (:require [cljs.nodejs :as nodejs]
            [adventofcode.sixth]))

(nodejs/enable-util-print!)

(println (adventofcode.sixth/solution))

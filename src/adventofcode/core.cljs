(ns adventofcode.core
  (:require [cljs.nodejs :as nodejs]
            [cljs.test :refer-macros [deftest
                                      is
                                      testing
                                      run-tests]]))

(nodejs/enable-util-print!)

(deftest test:example
  (is (= 1 1)))

(run-tests)

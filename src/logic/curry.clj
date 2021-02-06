(ns logic.curry
  (:require [clojure.core :as core]))

(comment
  (c/defn my-fn
          [arg1 arg2]
          (str arg1 arg2))

  (defn simple-map
    [c-fn #_ "recebe 3 args"
     list]
    (map (c-fn arg1 arg2) list))

  (my-sum
    ([x] (partial my-sum x))
    ([x y] (partial my-sum x y))
    ([x y z] (+ x y z))))

(defn my-sum
  [x y z]
  (+ x y z))

(defmacro defn
  [our-fn-name args body]
  `(core/defn ~our-fn-name
     (~(drop-last args) (partial ~our-fn-name ~@(drop-last args)))
     (~args ~body))
    #_(recur fn-name (drop-last args) body))

(macroexpand (defn my-sum
                   [x y]
                   (+ x y)))
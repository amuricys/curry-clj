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
  [fn-name args body]
  `(core/defn ~fn-name
     ~@(->> (range 1 (count args))
            (map (fn [arg-count]
                   `(~(vec (take arg-count args)) (partial ~fn-name ~@(take arg-count args))))))
     (~args ~body)))

(macroexpand (defn my-sum
               [x y]
               (+ x y)))

(defn my-sum
  [x y z a]
  (+ x y z a))

(as-> (my-sum 1) %
      (map % [2 3 4])
      (apply juxt %)
      (% 10)
      (apply juxt %)
      (% 100))

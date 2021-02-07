(ns logic.curry
  (:require [clojure.core :as core]))

(defmacro defn
  [fn-name args body]
  `(core/defn ~fn-name
     ~@(->> (range 1 (count args))
            (map (fn [arg-count]
                   `(~(vec (take arg-count args)) (partial ~fn-name ~@(take arg-count args))))))
     (~args ~body)))

(ns name.atw.garden-of-edn
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io])
  (:import java.lang.AutoCloseable
           java.io.PushbackReader))

(defn opening [f]
  (fn [^AutoCloseable closeable]
    (with-open [x closeable]
      (f x))))

(defn resource [n]
  (-> n
      io/resource
      io/reader
      PushbackReader.
      ((opening edn/read))))

(defn delay-resource [n] (delay (resource n)))

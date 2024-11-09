(ns name.atw.garden-of-edn
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io])
  (:import java.lang.AutoCloseable
           java.io.PushbackReader))

(defn opening [f]
  (fn [^AutoCloseable closeable]
    (with-open [x closeable]
      (f x))))

(defn resource
  ([n] (resource n nil))
  ([n opts]
   (-> n
       io/resource
       io/reader
       PushbackReader.
       ((opening (partial edn/read opts))))))

(defn delay-resource
  ([n] (delay-resource n nil))
  ([n opts] (delay (resource n opts))))

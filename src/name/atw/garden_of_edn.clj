(ns name.atw.garden-of-edn
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io])
  (:import java.lang.AutoCloseable
           java.io.PushbackReader))

(defn opening [f]
  (fn [^AutoCloseable closeable]
    (with-open [x closeable]
      (f x))))

(defn file
  ([n] (file {} n))
  ([opts n]
   (-> n
       io/file
       io/reader
       PushbackReader.
       ((opening (partial edn/read opts))))))

(defn delay-file
  ([n] (delay-file {} n))
  ([opts n] (delay (file opts n))))

(defn resource
  ([n] (resource {} n))
  ([opts n]
   (-> n
       io/resource
       io/reader
       PushbackReader.
       ((opening (partial edn/read opts))))))

(defn delay-resource
  ([n] (delay-resource {} n))
  ([opts n] (delay (resource opts n))))

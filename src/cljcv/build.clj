(ns cljcv.build
  (:gen-class)
  (:require [hawk.core :as hawk])
  (:require [clojure.java.io :as io])
  (:require [cljcv.pdf :as pdf]))

(defn find-resumes
  "Returns a list of input files"
  []
  (filter #(re-matches #".*\.clj$" %) (seq (.list (clojure.java.io/file "data")))))

(defn read-resume
  "Slurp an input file and merge it with defaults"
  [file]
  (merge (read-string (slurp file)) (read-string (slurp "data/default.clj"))))

(defn remove-extension
  "Remove extension from a file path."
  [file-path]
  (if-let [[_ pure-file-path] (re-matches #"(.*)\..*" file-path)]
    pure-file-path
    file-path))

(defn basename
  "Return the basename of a file"
  [file]
   (.getName (io/file file)))

(defn single
  "Write a single resume to PDF file"
  [file]
  (do (println "Building " file)
   (pdf/build (read-resume file) (remove-extension (basename file)))))

(defn all
  "Write all resumes to PDF files"
  [options]
  (doseq [file (find-resumes)]
    (println "Building " file)
    (pdf/build (read-resume (str "data/" file)) (remove-extension file))))

(defn watch
  "Watch data/ and rebuild when files are changed"
  [options]
  (hawk/watch! [{:paths (mapv (fn [file] (str "data/" file)) (find-resumes))
                :handler (fn [ctx e]
                            (single (str (get e :file)))
                            ctx)}]))

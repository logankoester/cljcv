(ns cljcv.build
  (:gen-class)
  (:require [cljcv.utils :as utils])
  (:require [hawk.core :as hawk])
  (:require [clojure.java.io :as io])
  (:require [cljcv.pdf :as pdf]))

(defn find-resumes
  "Returns a list of input files"
  [dir]
  (filter #(re-matches #".*\.clj$" %) (seq (.list (clojure.java.io/file dir "data")))))

(defn remap-skill-paths
  [resume]
  (update-in
    resume [:skills]
    (fn
      [skills]
      (mapv
        (fn
          [skill]
          (update-in skill [:icon]
                     (fn [icon]
                       (utils/project-path icon))))
        skills))))

(defn remap-experience-paths
  [resume]
  (update-in
    resume [:experience]
    (fn
      [experiences]
      (mapv
        (fn
          [experience]
          (update-in experience [:logo]
                     (fn [logo]
                       (utils/project-path logo))))
        experiences))))

(defn remap-resume-paths
  "Remap local paths in a resume to relative project paths"
  [resume]
  (remap-skill-paths
    (remap-experience-paths
      resume)))

(defn read-resume
  "Slurp an input file and merge it with defaults"
  [options file]
  (let
    [resume-path (str (get options :dir) "data/" file)
     default-path (str (get options :dir) "data/" "default.clj")]
    (remap-resume-paths
      (merge (read-string (slurp resume-path)) (read-string (slurp default-path))))))

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
  (doseq [file (find-resumes (get options :dir))]
    (println "Building " file)
    (let
      [output-path (str (get options :dir) "public/" (remove-extension file) ".pdf")]
      (pdf/build (read-resume options file) output-path))))

(defn watch
  "Watch data/ and rebuild when files are changed"
  [options]
  (hawk/watch! [{:paths (mapv (fn [file] (str "data/" file)) (find-resumes))
                :handler (fn [ctx e]
                            (single (str (get e :file)))
                            ctx)}]))

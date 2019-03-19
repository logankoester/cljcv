(ns cljcv.utils
  (:gen-class)
  (:require [pathetic.core :as pathetic]))

(defn partition-columns
  "Partitions a list into n columns"
  [n data]
  (partition-all
    (int (Math/ceil
           (/ (count data) (float n)))) data))

(defn project-path
  "Compute the relative path from the cwd to a file in the project directory"
  [options filename]
  (let
    [project-root (pathetic.core/resolve (System/getProperty "user.dir") (get options :dir))
     absolute-path (str project-root "/" filename)]
    (pathetic.core/relativize (System/getProperty "user.dir") absolute-path)))

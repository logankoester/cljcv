(ns cljcv.utils
  (:gen-class))

(defn partition-columns
  "Partitions a list into n columns"
  [n data]
  (partition-all
    (int (Math/ceil
           (/ (count data) (float n)))) data))

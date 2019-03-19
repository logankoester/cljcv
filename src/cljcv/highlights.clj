(ns cljcv.highlights
  (:gen-class)
  (:require [cljcv.utils :as utils])
  (:require [clojure.java.io :as io])
  (:use clj-pdf.core))

(defn render
  "Render the highlights section"
  [highlights]
  (vector
    [:heading 
    {:encoding :unicode
      :ttf-name (utils/project-path "resources/fonts/Padauk-Bold.ttf")}
    "Highlights"]
    [:line {:dotted true}]
    [:spacer 1]
    [:pdf-table
      {:border false
      :width-percent 100}
      [50 50]
      [
        (apply vector :list (nth (utils/partition-columns 2 highlights) 0))
        (apply vector :list (nth (utils/partition-columns 2 highlights) 1))
      ]
    ]))

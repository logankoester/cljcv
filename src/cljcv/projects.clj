(ns cljcv.projects
  (:gen-class)
  (:require [cljcv.utils :as utils])
  (:require [clojure.java.io :as io])
  (:use clj-pdf.core))

(defn project-template
  [projects]
  (apply vector :list {:symbol " - "} (mapv (fn [project]
          [:paragraph
            {:size 10}
            [:phrase (get project :description)]
            [:spacer 1]
          ]) projects)))

(def projects-template
  (template
    [:paragraph
     [:heading 
      {:encoding :unicode
       :style { :size 14 }
       :ttf-name (utils/project-path "resources/fonts/Padauk-Bold.ttf")}
      (str $title "\n")]

     (project-template $projects)]))

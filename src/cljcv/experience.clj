(ns cljcv.experience
  (:gen-class)
  (:require [clojure.java.io :as io])
  (:require [cljcv.utils :as utils])
  (:use clj-pdf.core))

(def experience-template
  (template
    [:paragraph
      [:spacer 1]
      [:pdf-table
        {:border false
        :width-percent 100}
        [20 55 25]
        [
        [:image 
          {
          :width 64
          :height 64
          }
          $logo]
        [:paragraph
          [:heading.job-title
            {:encoding :unicode 
             :ttf-name (utils/project-path "resources/fonts/Alice-Regular.ttf")}
           $title]
          [:phrase { :style :bold }  $company "\n"]]
        [:paragraph
          { :align :right }
          $date-range
          "\n"
          [:phrase {
                    :style :italic
                    :size 7
                    :color [100 100 100]}
            (clojure.string/join "  " $tags)]]
        ]]
      [:pdf-table
        {:border false
        :width-percent 100}
        [20 80]
        [
        ""
        [:paragraph
          [:paragraph $role "\n"]
          [:paragraph.description $description]
          ]
      ]]]
    ))

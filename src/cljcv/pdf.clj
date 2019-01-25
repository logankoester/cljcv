(ns cljcv.pdf
  (:gen-class)
  (:require [clojure.java.io :as io])
  (:require [cljcv.skills :as skills])
  (:require [cljcv.experience :as experience])
  (:require [cljcv.highlights :as highlights])
  (:use clj-pdf.core))

(def stylesheet
  {:name {:color [51 141 201]}
   :description { :color [100 100 100] }
   :job-title {
               :style {
                :size 14
               }
               :color [51 141 201]
               }})

(defn cover
  "Render the cover page"
  [cover]
  (vector
    [:spacer 8]
    [:heading 
    {:encoding :unicode
      :ttf-name "resources/fonts/Padauk-Bold.ttf"}
    (get cover :heading)]
    [:spacer 1]
    [:paragraph
    {:size 14}
    (clojure.string/join "\n\n" (get cover :body))
    [:spacer 1]
      [:image
        {
        :width 200
        :height 83
        }
        (get cover :signature)]
    ]

    [:pagebreak]))

(defn build
  "Write the resume to a PDF file"
  [input output]
  (pdf
    [{:stylesheet stylesheet
      :register-system-fonts? true
      :font {:encoding :unicode
            :ttf-name "resources/fonts/Apercu_Regular.ttf"}
      :title (get input :name)
      :author (get input :name)
      :creator (get input :name)
      :footer {:page-numbers true}
      :pages true
      :header {
              :table
              [:pdf-table
                {:border false
                :width-percent 100}
                [15 35 50]
                [
                [:image 
                  {
                  :width 64
                  :height 64
                  }
                  (get input :avatar)]
                [:heading.name
                  {:encoding :unicode
                  :style { :size 28 }
                  :ttf-name "resources/fonts/nobel-regular.ttf"}
                  (get input :name)]
                [:paragraph
                  { :align :right }
                  (get input :address)
                  "\n"
                  [:anchor {:target (str "mailto:" (get input :email))} (get input :email)]
                  "\n"
                  [:anchor {:target (get input :url)} (get input :url)]]
                ]]}}
      (if (get input :cover) (cover (get input :cover)))
      [:spacer 6]
      [:paragraph 
      {:size 14}
      (get input :intro)]
      [:spacer 2]
      [:line]
      
      [:spacer 2]
      (highlights/render (get input :highlights))

      [:spacer 3]
      [:heading 
      {:encoding :unicode
        :ttf-name "resources/fonts/Padauk-Bold.ttf"}
      "Technical Skills"]
      [:line {:dotted true}]
      (skills/skill-bars-svg [40 485] (get input :skills))
      (skills/render-skills-columns 2 (get input :skills))

      [:pagebreak]
      [:spacer 6]
      [:heading 
      {:encoding :unicode
        :ttf-name "resources/fonts/Padauk-Bold.ttf"}
      "Experience"]
      [:line {:dotted true}]
      (experience/experience-template (nth (partition-all 3 (get input :experience)) 0))
      [:pagebreak]
      [:spacer 6]
      [:heading 
      {:encoding :unicode
        :ttf-name "resources/fonts/Padauk-Bold.ttf"}
      "Experience"]
      [:line {:dotted true}]
      (experience/experience-template (nth (partition-all 3 (get input :experience)) 1))
    ] (str "public/" output ".pdf")))

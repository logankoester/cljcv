(ns cljcv.skills
  (:gen-class)
  (:require [cljcv.utils :as utils])
  (:use clj-pdf.core))

(require '[dali.io :as io])
(require 'clojure.string)

(def experience
  {:entry-level 62
   :intermediate 124
   :experienced 186
   :expert 248})

(defn humanizeSymbol
  "Convert a symbol like :entry-level to a string like 'Entry level'"
  [sym]
  (
   clojure.string/replace (clojure.string/capitalize (name sym)) #"-" " "))

(defn render-skills-table
  "Return template output for each skill in the vector"
  [skills]
  (
   let [cell-meta { :padding-top 17 }]
    (apply vector :pdf-table {} [7 43 20 30] (mapv (fn [skill]
                      [
                        [:pdf-cell (merge cell-meta { :align :left :padding-top 23 })
                          [:image { :width 12 :height 12 } (get skill :icon)]]
                        [:pdf-cell (merge cell-meta { :align :left })
                          [:phrase (get skill :name)]]
                        [:pdf-cell (merge cell-meta { :align :left }) (str (get skill :years) "+ years")]
                        [:pdf-cell (merge cell-meta { :align :right :padding-right 8 }) (humanizeSymbol (get skill :experience))]
                      ] ) skills))))

(defn render-skills-columns
  "Returns a table with n columns of subtables for each skill"
  [n skills]
  (
   let [columns (utils/partition-columns n skills)]
    (vector :pdf-table
            {:border false
             :width-percent 100}
            nil
            (mapv render-skills-table columns))))

(defn skill-bar-line
  "Returns an SVG line"
  [position size value]
  [
   [:line
    {:stroke "#e8e8e8" :stroke-width 4}
    position [(+ (get position 0) size) (get position 1)]]
   [:line
    {:stroke "#338dc9" :stroke-width 4}
    position [(+ (get position 0) value) (get position 1)]]
   ])

(defn skill-bars
  "Render SVG progress bars"
  [offset skills]
  (apply concat (map-indexed (fn [column skills]
    (map-indexed (fn [i skill]
        (let [x (+ (get offset 0) (* column 262))]
          (let [y (+ (get offset 1) (* i 37))]
            (skill-bar-line [x y] 248 (get experience (get skill :experience)))))
      ) skills)
    ) (utils/partition-columns 2 skills))))

(defn skill-bars-svg
  "Render SVG progress bars"
  [offset skills]
   (let [page [:dali/page
               {}
               (apply concat (skill-bars offset skills))]]
      [:svg
        {:translate [0 0 ]}
        (io/render-svg-string page)])
      )

(ns cljcv.core
  (:gen-class)
  (:require [clojure.tools.cli :refer [parse-opts]])
  (:require [cljcv.init :as init])
  (:require [cljcv.build :as build])
  (:require [cljcv.pdf :as pdf]))

(def cli-options
  [
   ["-h" "--help" "Display help and exit"]])

(defn usage [options-summary]
  (->> ["Produce a nicely formatted curriculum vitae or résumé in various formats."
        ""
        "Usage: cljcv [options] action"
        ""
        "Options:"
        options-summary
        ""
        "Actions:"
        "  init     Generate a project skeleton in the current working directory"
        "  build    Write all variants of the current project into public/"
        "  watch    Watch data/ and rebuild when files are changed"
        ""
        "Please refer to the manual page for more information."]
       (clojure.string/join \newline)))

(defn error-msg [errors]
  (str "The following errors occurred while parsing your command:\n\n"
       (clojure.string/join \newline errors)))

(defn validate-args
  "Validate command line arguments. Either return a map indicating the program
  should exit (with a error message, and optional ok status), or a map
  indicating the action the program should take and the options provided."
  [args]
  (let [{:keys [options arguments errors summary]} (parse-opts args cli-options)]
    (cond
      (:help options) ; help => exit OK with usage summary
      {:exit-message (usage summary) :ok? true}
      errors ; errors => exit with description of errors
      {:exit-message (error-msg errors)}
      ;; custom validation on arguments
      (and (= 1 (count arguments))
           (#{"init" "build" "watch"} (first arguments)))
      {:action (first arguments) :options options}
      :else ; failed custom validation => exit with usage summary
      {:exit-message (usage summary)})))

(defn exit [status msg]
  (println msg)
  (System/exit status))

(defn -main
  "Produce a nicely formatted curriculum vitae or résumé in various formats."
  [& args]

  (let [{:keys [action options exit-message ok?]} (validate-args args)]
    (if exit-message
      (exit (if ok? 0 1) exit-message)
      (case action
        "init" (init/run options)
        "build" (build/all options)
        "watch" (build/watch options)))))

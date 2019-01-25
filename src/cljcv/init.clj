(ns cljcv.init
  (:require [clojure.java.io :as io])
  (:require [me.raynes.fs :as fs])
  (:gen-class))

(defn copy-resource! [resource-filename]
  (let [resource-file (io/resource (str "boilerplate/resources/" resource-filename))
        tmp-file (io/file "resources/" resource-filename)]
    (with-open [in (io/input-stream resource-file)] (io/copy in tmp-file))))

(def default-clj (io/resource
                   "boilerplate/data/default.clj" ))

(def variant-clj (io/resource
                   "boilerplate/data/variant.clj" ))

(def gitignore (io/resource
                   "boilerplate/gitignore" ))

(def readme (io/resource
                   "boilerplate/README.md" ))

(defn run
  "Generate a project skeleton in the current working directory"
  [options]
    (fs/mkdir "public")
    (fs/mkdir "data")
    (fs/mkdir "resources")
    (fs/mkdir "resources/experience")
    (fs/mkdir "resources/skills")
    (fs/mkdir "resources/fonts")

    (copy-resource! "avatar.png")
    (copy-resource! "signature.png")
    (copy-resource! "experience/logo.png")
    (copy-resource! "skills/icon.png")

    (copy-resource! "fonts/Alice-Regular.ttf")
    (copy-resource! "fonts/Apercu-Medium.ttf")
    (copy-resource! "fonts/Apercu_Regular.ttf")
    (copy-resource! "fonts/nobel-regular.ttf")
    (copy-resource! "fonts/Padauk-Bold.ttf")

    (spit "data/default.clj" (slurp default-clj))
    (spit "data/variant.clj" (slurp variant-clj))
    (spit ".gitignore" (slurp gitignore))
    (spit "README.md" (slurp readme)))

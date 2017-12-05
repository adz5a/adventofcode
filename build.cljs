(require '[lumo.build.api :as b])

(b/build "src"
         {:main 'adventofcode.core
          :output-to "dist/main.js"
          :target :nodejs})

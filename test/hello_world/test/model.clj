(ns hello-world.test.model
  (:use clojure.test
        ring.mock.request  
        hello-world.model))

(deftest test-app
  (testing "testing for agent"
    (let [fresh-agent (agent {})]
      (is (= (class data-store)
             (class fresh-agent)))
      (is (= @data-store
             @fresh-agent)))))

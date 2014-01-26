(ns hello-world.test.handler
  (:use clojure.test
        ring.mock.request  
        hello-world.handler))

(deftest test-app
  (testing "main route"
    (let [response (app (request :get "/"))]
      (is (= (:status response) 200))
      (is (= (:body response) "Hello World"))))

  (testing "user exists"
    (let [response (app (request :get "/user/1"))]
      (is (= (:status response) 200))
      (is (= (:body response) "1"))))

  (testing "nonexistent user does not exist"
    (let [response (app (request :get "/user/このユーザがあるわけがねえ"))]
      (is (= (:status response) 404))))

  (testing "not-found route"
    (let [response (app (request :get "/invalid"))]
      (is (= (:status response) 404)))))

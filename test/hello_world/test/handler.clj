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

  (testing "testing posting operation"
    (let [response-1 (app (request :post "/user/2"))
          response-2 (app (request :get "/user/2"))
          response-3 (app (request :post "/user/1"))]
      (is (= (:status response-1) 200))
      (is (= (:status response-2) 200))
      (is (= (:body response-2) "2"))
      (is (= (:status response-3) 400))))

  (testing "testing putting operation"
    (let [response-1 (app (request :put "/user/1/2"))
          response-2 (app (request :get "/user/1"))
          response-3 (app (request :put "/user/3/3"))]
          ; these subtests are not isolated: the state of 
          ; the system is persisted throughout the test
      (is (= (:status response-1) 200))
      (is (= (:status response-2) 200))
      (is (= (:body response-2) "2"))
      (is (= (:status response-3) 400))))

  (testing "testing deletion operation"
    (let [response-1 (app (request :delete "/user/1"))
          response-2 (app (request :get "/user/1"))]
      (is (= (:status response-1) 200))
      (is (= (:status response-2) 404))))

  (testing "not-found route"
    (let [response (app (request :get "/invalid"))]
      (is (= (:status response) 404)))))

(deftest newtest
  ; this test is isolated from the previous test
  (let [response (app (request :get "/user/1"))]
      (is (= (:status response) 200))
      (is (= (:body response) "1"))))

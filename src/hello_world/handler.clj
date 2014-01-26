(ns hello-world.handler
  (:use [compojure.core]
  			[hello-world.model])
  (:require [compojure.handler :as handler]
            [compojure.route :as route]))

(defroutes app-routes
  (GET "/" [] "Hello World")
  (GET "/user/:id"
  	[id]
    (let [id-key (keyword id)]
      (if (contains? @data-store id-key)
        (@data-store 
          (keyword id))
        (route/not-found
          (str "User " id " was not found")))))
  (POST "/user/:id"
  	[id]   
    (let [id-key (keyword id)]
      (if (contains? @data-store id-key)
        {:status 400
         :body "User " id "already exists -- consider PUT"}
        (do
          (swap! data-store 
            assoc (keyword id) id)
          (str "User " id " was added")))))
  (PUT "/user/:id/:newvalue"
  	[id newvalue]
  	(let [id-key (keyword id)]
      (if (contains? @data-store id-key)
        (do
          (swap! data-store 
            assoc (keyword id) newvalue)
          (str "User " id " was updated"))
        {:status 400
         :body "User " id "does not exist -- consider POST"})))
  (DELETE "/user/:id"
  	[id]
    (let [id-key (keyword id)]
      (if (contains? @data-store id-key)
        (do
          (swap! data-store 
            dissoc (keyword id))
          (str "User" id " was deleted"))
        (route/not-found
          (str "User " id " was not found")))))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))

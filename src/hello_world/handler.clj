(ns hello-world.handler
  (:use [compojure.core]
  			[hello-world.model])
  (:require [compojure.handler :as handler]
            [compojure.route :as route]))

(defroutes app-routes
  (GET "/" [] "Hello World")
  (GET "/user/:id"
  	[id]
  	(@data-store 
  		(keyword id)))
  (POST "/user/:id"
  	[id]
  	(str "we post"))
  (PUT "/user/:id"
  	[id]
  	(str "we put"))
  (DELETE "/user/:id"
  	[id]
  	(str "we delete"))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))

#!/usr/bin/env bb
(ns inspect-jwt
  "Decodes base64 header and payload of a jwt, does not have any interest in verification"
  (:require [clojure.string :as str]))

(def decoder (java.util.Base64/getDecoder))

(defn base64->string
  [in]
  (String. (.decode decoder in)))

(defn -main
  [& args]
  (let [jwt (first args)]
    (assert (and (= (count args) 1)
                 (string? jwt)
                 (re-matches #".+\..+\..+" jwt))
            "Must have only 1 argument of a syntactically correct JWT token")
    (let [[header payload]
          (map base64->string (take 2 (str/split jwt #"\.")))]
      (printf "\nHeader: %s\nPayload: %s\n" header payload))))

(try
  (apply -main *command-line-args*)
  (catch Exception e
    (println e)))

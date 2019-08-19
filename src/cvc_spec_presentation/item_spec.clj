(ns cvc-spec-presentation.item-spec
  (:require [clojure.spec.alpha :as s]))

;;Campos do item ->sku description quantity
;;(partial re-matches #"^[a-zA-Z0-9]{4}-[a-zA-Z0-9]{4}-[a-zA-Z0-9]{6}$")


;;No caso da especificação de uma entidade, cada chave vira uma spec
(s/def ::sku (s/and string? not-empty ))
(s/def ::description (s/and string? not-empty))
(s/def ::quantity (s/or :zero zero? :positive pos-int?))

;;A spec da entidade é a soma das specs de suas chaves
(s/def ::item (s/keys :req-un  [::sku ::description ::quantity]))


(s/exercise ::item)





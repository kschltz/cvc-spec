(ns cvc-spec-presentation.item-spec
  (:require [clojure.spec.alpha :as s]
            [clojure.test.check.generators :as g]))

;;Campos do item ->sku description quantity



(defn sku-gen []
  (g/fmap #(str (subs % 0 4) "-"(subs % 0 4) "-"(subs % 0 6))
          (g/fmap #(apply str %)(g/vector g/char-alpha 10))))

;;No caso da especificação de uma entidade, cada chave vira uma spec
(s/def ::sku (s/with-gen (s/and string? not-empty (partial re-matches #"^[a-zA-Z0-9]{4}-[a-zA-Z0-9]{4}-[a-zA-Z0-9]{6}$"))
                         sku-gen))
(s/def ::description (s/and string? not-empty))
(s/def ::quantity (s/or :zero zero? :positive int?))

;;A spec da entidade é a soma das specs de suas chaves/propriedades/atributos
(s/def ::item (s/keys :req-un  [::sku ::description ::quantity]))


(s/exercise ::item)





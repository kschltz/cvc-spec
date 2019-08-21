(ns cvc-spec-presentation.cart-spec
  (:require [clojure.spec.alpha :as s]
            [cvc-spec-presentation.item-spec :as is]
            [clojure.test.check.properties :as prop]
            [clojure.test.check :as tc]
            [clojure.test :as t]
            [clojure.test.check.generators :as g]))


;;Quaisquer funções podem ser utilizadas em specs
;;levando em consideração que todos os valores em clojure tem peso lógico
;;sendo: false e nil os únicos valores considerados como falso lógico
;; todos os demais valores são considerados equivalentes ao verdadeiro lógico
;;Ex.: (if nil) -> false



;Quando a estratégia de geração aleatória não encontra um valor
;de acordo com a spec, é impossivel gerar valores automaticamente
(defn mail-gen [] (g/fmap #(str % "@email.com") g/string))



;Qualquer função serve como definição pra uma spec
(defn email? [str]
  (re-matches  #".+\@.+\..+" str))

(s/def ::uuid  uuid?)
;Spec com gerador custom
(s/def ::email (s/with-gen  email? mail-gen))
(s/def ::items (s/coll-of ::is/item))

;Multiplas specs podem compor um conjuntos de specificações pra
;uma estrutura de dados
;onde o valor de cada tupla deve estar em conformidade com a spec da chave da tupla
;  spec-name    | spec-function
;  ::some-spec  | any-value
(s/def ::cart (s/keys :req-un [::uuid  ::items]
                      :opt-un [::email]))


;É possivél fazer o caminho inverso e transformar spec em funções geradoras
(def cart-gen (s/gen ::cart))

(defn negative-amounts? [cart]
  (->> (:items cart)
       (map :quantity)
       (filter neg?)
       (empty?)
       (not)))

(def no-negative-item-amount
  (prop/for-all [cart cart-gen]
                (not(negative-amounts? cart))))

(tc/quick-check 10000 no-negative-item-amount)




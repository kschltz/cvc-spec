(ns cvc-spec-presentation.memory-db
  (:require [datomic.api :as d]
            [clojure.spec.alpha :as s]))


(def uri "datomic:mem://local")

(d/create-database uri)

(def conn (d/connect uri))

(def item-schema [{:db/ident     :item/sku
                   :db/valueType :db.type/string
                   :db/cardinality :db.cardinality/one
                   :db/unique :db.unique/identity
                   }
                  {:db/ident     :item/description
                   :db/valueType :db.type/string
                   :db/cardinality :db.cardinality/one
                   }
                  {:db/ident     :item/inventory_quantity
                   :db/valueType :db.type/long
                   :db/cardinality :db.cardinality/one
                   }])

(def cart-schema [{:db/ident     :cart/uuid
                   :db/valueType :db.type/string
                   :db/cardinality :db.cardinality/one
                   :db/unique :db.unique/identity
                   }
                  {:db/ident     :cart/email
                   :db/valueType :db.type/string
                   :db/cardinality :db.cardinality/one
                   :db/unique :db.unique/identity
                   }
                  {:db/ident     :cart/items
                   :db/valueType :db.type/ref
                   :db/cardinality :db.cardinality/many
                   }])

(d/transact conn item-schema)
(d/transact conn cart-schema)


(defn save-item [item]
  (let [{:keys [sku description quantity]} item
        item-transaction [{:item/sku sku
                           :item/description description
                           :item/inventory_quantity quantity}]]
    (d/transact conn item-transaction)))





(save-item {:sku "kaue"
            :description "some desc"
            :quantity 100})
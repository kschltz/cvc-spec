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


(defn- item->trx-sku [item]
  {:item/sku (:sku item)})




(defn find-item [sku]
  (d/q '[:find (pull ?s [*])
        :in $ ?sku
        :where [?s :item/sku ?sku]] (d/db conn) sku conn))

(defn find-cart [uuid]
  (d/q '[:find (pull ?u [*])
        :in $ ?uuid
        :where [?u :cart/uuid ?uuid]]
       (d/db conn) uuid conn))

(defn save-cart [cart]
           (let [{:keys [uuid email items]} cart
                 items-trx (map item->trx-sku items)
                 cart-transaction [{:cart/uuid uuid
                                    :cart/email email
                                    :cart/items items-trx}]]
             (d/transact conn cart-transaction)))

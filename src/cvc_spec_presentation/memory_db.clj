(ns cvc-spec-presentation.memory-db
    (:require [datomic.api :as d]
              [clojure.spec.alpha :as s]
              [cvc-spec-presentation.item-spec :as is]
              [cvc-spec-presentation.schemas :as ss]))


(def uri "datomic:mem://local")
(d/create-database uri)
(def conn (d/connect uri))
(d/transact conn ss/item-schema)
(d/transact conn ss/cart-schema)
(d/transact conn ss/purchase-schema)


(defn save-item [item] {:pre [(s/valid? ::is/item item)]}
      (let [{:keys [sku description quantity]} item
            item-transaction [{:item/sku                sku
                               :item/description        description
                               :item/inventory_quantity quantity}]]
           (d/transact conn item-transaction)))
(s/fdef save-item
        :args (s/cat :item ::is/item)
        :ret some?)


(defn- item->trx-sku [item]{:pre [(s/valid? ::is/item item)]}
       {:item/sku (:sku item)})


(defn find-item [sku]
      (d/q '[:find (pull ?s [*])
             :in $ ?sku
             :where [?s :item/sku ?sku]] (d/db conn) sku conn))


(defn- subtract-inv [item amount]
  (let [db-item (get-in (find-item (:sku item)) [0 0])
        inv-quantity (:item/inventory_quantity db-item)
        remainder (- inv-quantity amount)]
    (when (>= remainder 0)
      (save-item (assoc item :quantity remainder)))))

(defn find-cart [uuid]
      (d/q '[:find (pull ?u [*])
             :in $ ?uuid
             :where [?u :cart/uuid ?uuid]]
           (d/db conn) uuid conn))

(defn save-cart [cart]
      (let [{:keys [uuid email items]} cart
            items-trx (map item->trx-sku items)
            cart-transaction [{:cart/uuid  uuid
                               :cart/email email
                               :cart/items items-trx}]]
           (d/transact conn cart-transaction)))

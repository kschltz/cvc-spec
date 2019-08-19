(ns cvc-spec-presentation.schemas)

(def item-schema [{:db/ident       :item/sku
                   :db/valueType   :db.type/string
                   :db/cardinality :db.cardinality/one
                   :db/unique      :db.unique/identity
                   }
                  {:db/ident       :item/description
                   :db/valueType   :db.type/string
                   :db/cardinality :db.cardinality/one
                   }
                  {:db/ident       :item/inventory_quantity
                   :db/valueType   :db.type/long
                   :db/cardinality :db.cardinality/one
                   }])

(def purchase-schema [{:db/ident       :purchase/cart_uuid
                       :db/valueType   :db.type/ref
                       :db/cardinality :db.cardinality/one
                       }
                      {:db/ident       :purchase/item_sku
                       :db/valueType   :db.type/ref
                       :db/cardinality :db.cardinality/one
                       }
                      {:db/ident       :purchase/item_amount
                       :db/valueType   :db.type/long
                       :db/cardinality :db.cardinality/one
                       }])

(def cart-schema [{:db/ident       :cart/uuid
                   :db/valueType   :db.type/string
                   :db/cardinality :db.cardinality/one
                   :db/unique      :db.unique/identity
                   }
                  {:db/ident       :cart/email
                   :db/valueType   :db.type/string
                   :db/cardinality :db.cardinality/one
                   :db/unique      :db.unique/identity
                   }
                  {:db/ident       :cart/items
                   :db/valueType   :db.type/ref
                   :db/cardinality :db.cardinality/many
                   }])


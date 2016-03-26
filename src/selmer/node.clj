(ns selmer.node
  " Node protocol for the objects that get accum'd in the post-parse vector.
  Same vector that will be processed by the runtime context-aware renderer.
  Currently only TextNodes and FunctionNodes. Anything that requires action
  upon context map data at runtime is handled by a generated anonymous function. "
  (:gen-class))

;; Generic INode protocol

(defprotocol INode
  (render-node [this context-map] "Renders the context"))

;; Implements fn handler for the context map. fn handlers can
;; access any data in the context map.

(deftype FunctionNode [handler]
  INode
  (render-node [this context-map]
    (handler context-map)))

;; Implements dumb text content injection at runtime.

(deftype TextNode [text]
  INode
  (render-node [this context-map]
    text)
  (toString [_]
    text))

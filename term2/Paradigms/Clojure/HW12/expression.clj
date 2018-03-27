(defn constant [value] (fn [args] value))
(defn variable [value] (fn [args] (args value)))

(defn allOps [oper] (fn [& a]
                      (fn [args] (apply oper (map (fn [f] (f args)) a)))
                      ))


(def add (allOps +))
(def subtract (allOps -))
(def multiply (allOps *))
(def divide (allOps (fn [a, b] (/ (double a) b)) ))
(def negate (allOps -))
(def sin (allOps (fn [a] (Math/sin a))))
(def cos (allOps (fn [a] (Math/cos a))))
(def sinh (allOps (fn [a] (Math/sinh a))))
(def cosh (allOps (fn [a] (Math/cosh a))))

(def op {'+ add '- subtract '* multiply '/ divide 'negate negate 'cosh cosh 'sinh sinh})


(defn parseFunction [expr] (
                             (fn parse [val] (cond
                                               (symbol? val) (variable (str val))
                                               (number? val) (constant val)
                                               (list? val) (apply (op (first val)) (map parse (rest val)))))
                             (read-string expr)))



;(print (parseFunction "(cos (+ x y))") ({"x" 3 "y" 4}))
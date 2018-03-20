//"use strict";

var variables = ["x", "y", "z"];

/*function BinaryOperator(first, second) {
    this.first = first;
    this.second = second;

    this.evaluate = function (x, y, z) {
        //return this.apply(this.first.evaluate(x, y, z), this.second.evaluate(x, y, z));
        var op = arguments;
        var ans = [];
        for (var i = 0; i < op.length; i++) ans.push (op[i].evaluate (x, y, z));
        return this.apply(null, ans);
    };

    this.toString = function() {
	    return this.first.toString() + ' ' + this.second.toString() + ' ' + this.operator;
    };
};*/

function Operator(f, s) {
    return function () {
            this.args = arguments;

            this.evaluate = function () {
                var ans = [];
                for (var i = 0; i < this.args.length; i++) ans.push (this.args[i].evaluate.apply (this.args[i], arguments));
                return f.apply(null, ans);
            };

            this.toString = function () {
                var str = "";
                for (var i = 0; i < this.args.length; i++) {
                    str += this.args[i].toString() + (i !== this.args.length-1 ? " " : "");
                }
                return str + " " + s;
            };

            this.prefix = function () {
                var pstr = "";
                for (var i = 0; i < this.args.length; i++) {
                     pstr += this.args[i].prefix() + (i !== this.args.length-1 ? " " : "");
                }
                return '(' + s + " " + pstr + ')';
            }
    }
}

function Const(x) {
	this.value = x;

    this.evaluate = function() {
        return this.value;
    };

    this.toString = function() {
        return '' + this.value;
    };

    this.prefix = function() {
        return this.toString();
    };
}

function Variable(name) {
	this.name = name;
	this.evaluate = function(x, y, z) {
    	    if (this.name === 'x') {
                return x;
            } else if (this.name === 'y') {
                return y;
            } else {
                return z;
            }
    };

    this.toString = function() {
    	return this.name;
    };

    this.prefix = function() {
        return this.toString();
    };
};

var Add = Operator(function (x, y) { return x + y; }, "+");

var Subtract = Operator(function (x, y) { return x - y; }, "-");

var Multiply = Operator(function (x, y) { return x * y; }, "*");

var Divide = Operator(function (x, y) { return x / y; }, "/");

var Negate = Operator(function (x) { return -x; }, "negate");

var Sinh = Operator(function (x) { return (Math.exp(x) - Math.exp(-x))/2; }, "sinh");

var Cosh = Operator(function (x) { return (Math.exp(x) + Math.exp(-x))/2; }, "cosh");

var Power = Operator(function (x, y) { return Math.pow(x, y) }, "pow");

var Log = Operator(function (x, y) { return Math.log(Math.abs(y))/Math.log(Math.abs(x)) }, "log");

var Square = Operator(function (x) { return x*x; }, "square");

var Sqrt = Operator(function (x) { return Math.sqrt(Math.abs(x)); }, "sqrt");

function parsePrefix(expr) {
	var bal = 0;
    for (var i = 0; i < expr.length() ; i++) {
        if (expr[i] === '(') {
            bal++;
        } else if(expr[i] === ')') {
            bal--;
        }
        if (bal < 0) {
            throw new Error('expected (');
        }
    }
    if (bal !== 0) {
        throw new Error('expected )');
    }
	var binaryOperators = { '+': Add,
                         '-': Subtract,
                         '*': Multiply,
                         '/': Divide,
                         'pow': Power,
                         'log': Log
    };
    var unaryOperators = { 'negate': Negate,
                        'sinh': Sinh,
                        'cosh': Cosh,
                        'square': Square,
                        'sqrt': Sqrt
    };
	var index = -1;

	function parseRecur() {
	    index++;
	    while (index !== expr.length() && " ()".indexOf(expr.charAt(index)) !== -1) index++;
		if(index === expr.length()) {
			throw new Error('not enough operands');
		}

		var end = index;
		while (end !== expr.length() && " ()".indexOf(expr.charAt(end)) === -1) end++;
		var token = expr.substring (index, end);
		if (token in binaryOperators) {
			return new binaryOperators[token](parseRecur(), parseRecur());
		} else if (token in unaryOperators) {
			return new unaryOperators[token](parseRecur());
		} else if(/^-?[0-9]+$/.test(token)) {
			return new Const(parseInt(token));
		} else if(variables.indexOf(token) !== -1) {
			return new Variable(token);
		} else {
			throw new Error('unrecognizable token: ' + token);
		}
	}

	var result = parseRecur();
	index++;
	while (index !== expr.length() && " ()".indexOf(expr.charAt(index)) !== -1) index++;
	if (index !== expr.length()) {
		throw new Error('operator missing');
	}
	return result;
}
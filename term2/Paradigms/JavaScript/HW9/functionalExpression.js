"use strict";

var variables = ["x", "y", "z", "u", "v", "w"];

function Operator(f) {
    return function() {
        var op = arguments;
        return function () {
            var ans = [];
            for (var i = 0; i < op.length; i++) ans.push (op[i].apply (null, arguments));
            return f.apply(null, ans);
    }
    }
}

/*function binaryOperator(first, second, f) {
    return function() {
        return f(first.apply(null, arguments), second.apply(null, arguments));
    }
}*/

function cnst(value) {
	return function() {
		return value;
	}
}

function variable(name) {
    return function() {
        return arguments[variables.indexOf(name)];
    }
}

function pi() {
	return Math.PI;
}

function e() {
	return Math.E;
}

function x() {
	    return arguments[0];
}

function y() {
        return arguments[1];
}

function z() {
        return arguments[2];
}

var min3 = Operator (function (a, b, c) {
    return Math.min.apply (null, arguments); }
    );

var max5 = Operator (function (a, b, c, d, e) {
	return Math.max.apply (null, arguments); }
);

var negate = Operator (function(a) { return -a;});

var add = Operator (function(a, b) { return a + b;});

var subtract = Operator (function(a, b) { return a - b;});

var multiply = Operator (function(a, b) { return a * b;});

var divide = Operator (function(a, b) { return a / b;});
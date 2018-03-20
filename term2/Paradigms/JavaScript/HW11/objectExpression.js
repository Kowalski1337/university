var variables = {x: 0, y: 1, z: 2};

function Variable(name) {
    this.name = name;
}

Variable.prototype.evaluate = function () {
    if (this.name === 'x') {
        return arguments[0];
    } else if (this.name === 'y') {
        return arguments[1];
    } else {
        return arguments[2];
    }
};

Variable.prototype.toString = function () {
    return this.name;
};

Variable.prototype.prefix = function () {
    return this.toString();
};

function BinaryOperator(left, right) {
    this.left = left;
    this.right = right;
}

BinaryOperator.prototype.evaluate = function () {
    return this.solve(this.left.evaluate.apply(this.left, arguments), this.right.evaluate.apply(this.right, arguments));
};

BinaryOperator.prototype.toString = function () {
    return this.left.toString() + ' ' + this.right.toString() + ' ' + this.operator;
};

BinaryOperator.prototype.prefix = function () {
    return '(' + this.operator + ' ' + this.left.prefix() + ' ' + this.right.prefix() + ')';
};

function UnaryOperator(left) {
    this.left = left;
}

UnaryOperator.prototype.evaluate = function () {
    return this.solve(this.left.evaluate.apply(this.left, arguments));
};

UnaryOperator.prototype.toString = function () {
    return this.left.toString() + ' ' + this.operator;
};

UnaryOperator.prototype.prefix = function () {
    return '(' + this.operator + ' ' + this.left.prefix() + ')';
};

function Negate(left) {
    UnaryOperator.call(this, left);
    this.operator = 'negate';
}

Negate.prototype = Object.create(UnaryOperator.prototype);

Negate.prototype.solve = function (a) {
    return -a;
};

function Sinh(left) {
    UnaryOperator.call(this, left);
    this.operator = 'sinh';
}

Sinh.prototype = Object.create(UnaryOperator.prototype);

Sinh.prototype.solve = function (a) {
    return (Math.pow(Math.E, a) - Math.pow(Math.E, -a)) / 2;
};

function Cosh(left) {
    UnaryOperator.call(this, left);
    this.operator = 'cosh';
}

Cosh.prototype = Object.create(UnaryOperator.prototype);

Cosh.prototype.solve = function (a) {
    return (Math.pow(Math.E, a) + Math.pow(Math.E, -a)) / 2;
};

function Cos(left) {
    UnaryOperator.call(this, left);
    this.operator = 'cos';
}

Cos.prototype = Object.create(UnaryOperator.prototype);

Cos.prototype.solve = function (a) {
    return Math.cos(a);
};

function Sin(left) {
    UnaryOperator.call(this, left);
    this.operator = 'sin';
}

Sin.prototype = Object.create(UnaryOperator.prototype);

Sin.prototype.solve = function (a) {
    return Math.sin(a);
};

function Add(left, right) {
    BinaryOperator.call(this, left, right);
    this.operator = '+';
}

Add.prototype = Object.create(BinaryOperator.prototype);

Add.prototype.solve = function (a, b) {
    return a + b;
};

function Subtract(left, right) {
    BinaryOperator.call(this, left, right);
    this.operator = '-';
}

Subtract.prototype = Object.create(BinaryOperator.prototype);

Subtract.prototype.solve = function (a, b) {
    return a - b;
};

function Multiply(left, right) {
    BinaryOperator.call(this, left, right);
    this.operator = '*';
}

Multiply.prototype = Object.create(BinaryOperator.prototype);

Multiply.prototype.solve = function (a, b) {
    return a * b;
};

function Divide(left, right) {
    BinaryOperator.call(this, left, right);
    this.operator = '/';
}

Divide.prototype = Object.create(BinaryOperator.prototype);

Divide.prototype.solve = function (a, b) {
    return a / b;
};

function Const(a) {
    this.value = a;
}

Const.prototype.evaluate = function () {
    return this.value;
};

Const.prototype.toString = function () {
    return '' + this.value;
};

Const.prototype.prefix = function () {
    return this.toString();
};

function parsePrefix(expr) {
    var balance = 0;
    for (var i = 0; i < expr.length; i++) {
        if (expr[i] === '(') {
            balance++;
        } else if (expr[i] === ')') {
            balance--;
        }
        if (balance < 0) {
            throw new Error('opa, unexpected ")" at index:' + i);
        }
    }
    if (balance !== 0) {
        throw new Error('expected some ")" at the end of expression');
    }
    if (expr[0] === '(' && (expr[1] === 'x' || expr[1] === '0') && expr[2] === ')') {
        throw new Error("can't find an operator");
    }
    expr = expr.replace(/[()]/g, ' ');
    expr = expr.trim();
    var binary = {
        '+': Add,
        '-': Subtract,
        '*': Multiply,
        '/': Divide
    };

    var unary = {
        'negate': Negate,
        'sinh': Sinh,
        'cosh': Cosh,
        'sin': Sin,
        'cos': Cos
    };
    var elements = expr.split(/\s+/);

//var elements = expr.split(/\(|\)/); 

//console.log(elements); 
    function parseMe() {
        if (elements.length === 0) {
            throw new Error('i want an operand');
        }
        var element = elements.shift();
        if (element.length === 0) {
            throw new Error("it's so pity");
        }
        if (element in binary) {
            return new binary[element](parseMe(), parseMe());
        } else if (element in unary) {
            return new unary[element](parseMe());
        } else if (/^-?[0-9]+$/.test(element)) {
            return new Const(parseInt(element));
        } else if (/^x$|^y$|^z$/.test(element)) {
            return new Variable(element);
        } else {
            throw new Error('unknown element : ' + element);
        }
    }

    var res = parseMe();
    if (elements.length > 0) {
        throw new Error("can't find an operator");
    }
    return res;
}

/* 
var expr = new Negate(new Variable('x')); 
console.log(expr.evaluate(1, 0, 0)); 
*/
/*
TestRational.run
UnitTest.gui
*/

TestRational : UnitTest {

	var minIntVal= -1000, maxIntVal=1000;
	var minFloatVal= -1000.1, maxFloatVal=1000.1;
	var numTests = 1000;

	test_reciprocal_NonZeroIntInput {
		numTests.do {
			var x = rrand(minIntVal,maxIntVal);
			var y = 1 + maxIntVal.rand * [-1,1].choose;
			var z = Rational(x,y);
			this.assertEquals(
				z,
				z.reciprocal.reciprocal,
				format( "Reciprocal test with % passed.", z)
			);
			}
	}

	test_newFromString_NonZeroIntInput {
		numTests.do {
			var x = rrand(minIntVal,maxIntVal);
			var y = 1 + maxIntVal.rand * [-1,1].choose;
			var xStr = x.asString;
			var yStr = y.asString;
			var rat1 = Rational(x,y);
			var rat2 = Rational.newFrom(xStr ++ "/"++ yStr);
			this.assertEquals(
				rat1,
				rat2,
				format( "String entry test with % passed.", rat1)
			);
		}
	}

	test_commutativeAdd_NonZeroIntInput {
		numTests.do {
			var x1 = rrand(minIntVal,maxIntVal);
			var y1 = 1 + maxIntVal.rand * [-1,1].choose;
			var x2 = rrand(minIntVal,maxIntVal);
			var y2 = 1 + maxIntVal.rand;
			var z1 = Rational(x1,y1);
			var z2 = Rational(x2,y2);
			this.assertEquals(
				z1 + z2,
				z2 + z1,
				format( "Commutative Add test with % and % passed.", z1, z2));
			}
	}

	test_commutativeMul_NonZeroIntInput {
		numTests.do {
			var x1 = rrand(minIntVal,maxIntVal);
			var y1 = 1 + maxIntVal.rand * [-1,1].choose;
			var x2 = rrand(minIntVal,maxIntVal);
			var y2 = 1 + maxIntVal.rand * [-1,1].choose;
			var z1 = Rational(x1,y1);
			var z2 = Rational(x2,y2);
			this.assertEquals(
				z1 * z2,
				z2 * z1,
				format( "Commutative Add test with % and % passed.", z1, z2));
			}
	}

	test_Additive_Inverse_NonZeroIntInput {
		numTests.do {
			var x = rrand(minIntVal,maxIntVal);
			var y = 1 + maxIntVal.rand * [1,1.neg].choose;
			var rat=Rational(x,y);

			this.assertEquals(
				Rational(x * (-1), y),
				Rational(x, y * (-1)),
				format( "Additive Inverse test 1 with % passed.", rat)
			);
			this.assertEquals(
				(-1) * rat,
				Rational(x * (-1), y),
				format( "Additive Inverse test 2 with % passed.", rat)
			);
			this.assertEquals(
				(-1) * rat,
				Rational(x, y * (-1)),
				format( "Additive Inverse test 3 with % passed.", rat)
			)
		}
	}

	test_Multiplicative_Inverse_NonZeroIntInput {
		numTests.do {
			var x    = 1 + maxIntVal.rand * [-1,1].choose;
			var y    = 1 + maxIntVal.rand * [-1,1].choose;
			var rat1 = Rational(x,y);
			var rat2 = Rational(y,x);

			this.assertEquals(
				rat1.pow(-1),
				rat2,
				format( "Multiplicative Inverse test with % passed.", rat1)
			);
		}
	}

	test_Div_Eq_ReciprocalMul_NonZeroIntInput  {
		numTests.do {
			var x1 = rrand(minIntVal,maxIntVal);
			var y1 = 1 + maxIntVal.rand * [-1,1].choose;
			var x2 = 1 + maxIntVal.rand * [-1,1].choose;
			var y2 = 1 + maxIntVal.rand * [-1,1].choose;
			var z1 = Rational(x1,y1);
			var z2 = Rational(x2,y2);
			this.assertEquals(
				z1 / z2,
				z1 * z2.reciprocal,
				format( "Division is equivalent to multiplying by the reciprocal test with % and %", z1, z2));
			}
	}

	test_Neg_Subtraction_NonZeroIntInput {
		numTests.do {
			var x1 = rrand(minIntVal,maxIntVal);
			var y1 = 1 + maxIntVal.rand * [-1,1].choose;
			var x2 = rrand(minIntVal,maxIntVal);
			var y2 = 1 + maxIntVal.rand * [-1,1].choose;
			var z1 = Rational(x1,y1);
			var z2 = Rational(x2,y2);
			this.assertEquals(
				z1 - z2,
				z2.neg - z1.neg,
				format( "Subtraction and Inverse test with % and % passed.", z1, z2));
			}
	}

	test_reciprocal_Div_NonZeroIntInput {
		numTests.do {
			var x1 = rrand(minIntVal,maxIntVal);
			var y1 = 1 + maxIntVal.rand * [-1,1].choose;
			var x2 = rrand(minIntVal,maxIntVal);
			var y2 = 1 + maxIntVal.rand * [-1,1].choose;
			var z1 = Rational(x1,y1);
			var z2 = Rational(x2,y2);
			this.assertEquals(
				z1 / z2,
				(z2 / z1).reciprocal,
				format( "Div + reciprocal test with % and % passed.", z1, z2));
			}
	}

	test_Sort_and_Scramble_NonZeroIntInput  {
		var listSize = 1000;

		//numTests.do {
		10.do {
			var ratList;
			ratList = Array.fill(
				listSize,
				{
					Rational(
						rrand(minIntVal, maxIntVal),
						 1 + maxIntVal.rand * [-1,1].choose;)
				}
				);
			this.assertEquals(
				ratList.sort,
				ratList.scramble.sort,
				format( "Sort and Scramble test passed.")
			);
		}
	}

	test_Sort_and_asFloat_NonZeroIntInput  {
		var listSize = 1000;

		//numTests.do {
		10.do {
			var ratList;

			ratList = Array.fill(
				listSize,
				{
					Rational(
						rrand(minIntVal, maxIntVal),
						1 + maxIntVal.rand * [-1,1].choose;)
				}
				);

			this.assertEquals(
				ratList.scramble.sort.asFloat,
				ratList.scramble.asFloat.sort,
				format( "Sort and asFloat test passed.")
			);
		}
	}

	test_Mul_Inverse_DifferentExponents_NonZeroIntInput {
		numTests.do {
			var maxVal = 100;
			var x1 = 1 + maxVal.rand * [-1,1].choose;
			var y1 = 1 + maxVal.rand * [-1,1].choose;
			var rat = Rational(x1,y1);
			var maxExponent = 6;

			maxExponent.do({arg i;
				this.assertEquals(
					rat.pow(i * (-1)),
					rat.pow(i).reciprocal,
					format( "Multiplicative inverse with different exponents test with exp: % and rat: % passed.", i, rat));

			})

		}
	}

	test_Float_Rat_Float {
		numTests.do {
			var maxVal = 1000.01;
			var x = 1 + maxVal.rand * [1,1.neg].choose;
			var y = 1 + maxVal.rand * [1,1.neg].choose;
			var rat = Rational(x,y);
			var float = x/y;

			this.assertFloatEquals(
				a: rat.asFloat,
				b: float.asRational.asFloat,
				message: format(
					"Float/Rational conversion with  rat: % and float: % passed.", rat, float),
				within: 0.000001
			);
		}
	}

	test_Rat_Float_Rat {
		numTests.do {
			var maxVal = 100;
			var x = 1 + maxVal.rand * [1,1.neg].choose;
			var y = 1 + maxVal.rand * [1,1.neg].choose;
			var rat = Rational(x,y);
			var float = x/y;

			this.assertEquals(
				a: rat,
				b: float.asRational.asFloat.asRational,
				message: format(
					"Float/Rational conversion with rat: % and float: % passed.", rat, float)
			);
		}
	}


	test_Exponentiation {
	   numTests.do {
			var maxVal = 9;
			var x = 1 + maxVal.rand;
			var y = 1 + maxVal.rand;
			var r = Rational(x,y);
			var e = rrand(1, 5).round;
			this.assertEquals(
				r,
				r.pow(e).pow(e.reciprocal),
				format(
					"Exponentiation rational: % and exponent: % passed.", r, e));
		}
	}

	// associative property
	test_AssociativeAdd_NonZeroIntInput {
		numTests.do {
			var x1 = rrand(minIntVal,maxIntVal);
			var y1 = 1 + maxIntVal.rand * [-1,1].choose;
			var x2 = rrand(minIntVal,maxIntVal);
			var y2 = 1 + maxIntVal.rand * [-1,1].choose;
			var x3 = rrand(minIntVal,maxIntVal);
			var y3 = 1 + maxIntVal.rand * [-1,1].choose;
			var z1 = Rational(x1,y1);
			var z2 = Rational(x2,y2);
			var z3 = Rational(x3,y3);

			this.assertEquals(
				(z1 + z2) + z3,
				z1 + (z2 + z3),
				format( "Associative Sum test with %, % and %passed.", z1, z2, z3));
		}
	}

	test_AssociativeMul_NonZeroIntInput {
		numTests.do {
			var x1 = rrand(minIntVal,maxIntVal);
			var y1 = 1 + maxIntVal.rand * [-1,1].choose;
			var x2 = rrand(minIntVal,maxIntVal);
			var y2 = 1 + maxIntVal.rand * [-1,1].choose;
			var x3 = rrand(minIntVal,maxIntVal);
			var y3 = 1 + maxIntVal.rand * [-1,1].choose;
			var z1 = Rational(x1,y1);
			var z2 = Rational(x2,y2);
			var z3 = Rational(x3,y3);

			this.assertEquals(
				(z1 * z2) * z3,
				z1 * (z2 * z3),
				format( "Associative Mul test with %, % and %passed.", z1, z2, z3));
		}
	}

	// distributive property
	// (a + b) * c = (a * c) + (b * c)
	test_Distributive_1_PositiveIntInput {
		numTests.do {
			var minVal = 1;
			var maxVal = 20;
			var x1 = rrand(minVal,maxVal);
			var y1 = 1 + maxVal.rand ;
			var x2 = rrand(minVal,maxVal);
			var y2 = 1 + maxVal.rand ;
			var x3 = rrand(minVal,maxVal);
			var y3 = 1 + maxVal.rand ;
			var a = Rational(x1,y1);
			var b = Rational(x2,y2);
			var c = Rational(x3,y3);

			this.assertEquals(
				(a + b) * c,
				(a * c) + (b * c),
				format( "Associative property 1 test with %, % and % passed.", a, b, c)
			);
		}
	}

	test_commutativeAdd_Array_NonZeroIntInput {
		numTests.do {
			var n = 20;
			var minVal = -10;
			var maxVal = 10;
			var xs = Array.fill(n, {rrand(minVal,maxVal)});
			var ys = Array.fill(n, {1 + maxVal.rand * [-1,1].choose});
			var rats;

			rats = n.collect({arg i; Rational(xs[i], ys[i])});
			this.assertEquals(
				rats.scramble.sum,
				rats.scramble.sum,
				format( "Commutative with Array summation test with %", rats));
		}
	}
}


/*
TestRational.run
UnitTest.gui
*/

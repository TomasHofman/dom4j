/*

This is an antlr (www.antlr.org) grammar specifying
a recognizer/parser/lexer group compliant with the
W3C Recommendation known as:

	XML Path Language (XPath)
	Version 1.0
	W3C Recommendation 16 November 1999

	http://www.w3c.org/TR/1999/REC-xpath-19991116

It is annotated with both implementation comments,
and references to the XPath standard's production
rules for recognizing parts of an xpath.

   author: bob mcwhirter (bob@werken.com)
  license: Apache Software Foundation
copyright: bob mcwhirter AND Werken & Sons Company

*/

header
{
	package org.dom4j.xpath.parser;

	import org.dom4j.xpath.DefaultXPath;
	import org.dom4j.xpath.impl.*;

	import java.util.List;
	import java.util.ArrayList;
	import java.util.Collections;
	
	/** Generated by <a href="http://www.antlr.org/">antlr parser-generator</a> */
}

class XPathRecognizer extends Parser;
	options
	{
		k = 2;
		exportVocab=DefaultXPath;
	}

	// ----------------------------------------
	// Helpful methods

	{
		private Expr makeBinaryExpr(Op op, Expr lhs, Expr rhs)
		{
			if ( op == null ) {
				return lhs;
			}

			return new BinaryExpr(op, lhs, rhs);
		}
		private Expr makeUnionExpr(Expr lhs, Expr rhs)
		{
                        if ( rhs == null ) {
                            return lhs;
                        }
                        if ( lhs == null ) {
                            return rhs;
                        }
			return new UnionExpr(lhs, rhs);
		}

                RecognitionException recognitionException;

                public void reportError(RecognitionException ex)
                {
                        this.recognitionException = ex;
                }

                public RecognitionException getRecognitionException()
                {
                        return recognitionException;
                }
	}

xpath returns [Expr expr]
	{
		expr = null;
	}
	:
		expr=union_expr
	;

location_path returns [LocationPath path]
	{
		path = null;
	}
	:
			path=absolute_location_path
		|	path=relative_location_path
	;

absolute_location_path returns [LocationPath path]
	{
		path = new LocationPath();
	}
	:
		(	SLASH^
		|	DOUBLE_SLASH^
			{
				path.addStep( new NodeTypeStep("descendant-or-self", "node") );
			}
		)
		(	(AT|STAR|DOT|IDENTIFIER)=>
			path=i_relative_location_path[path]
			|
		)
		{
			path.setIsAbsolute(true);
		}
	;

relative_location_path returns [LocationPath path]
	{
		path = null;
	}
	:
		path=i_relative_location_path[path]
	;

i_relative_location_path[LocationPath in_path] returns [LocationPath path]
	{
		if (in_path == null) {
			path = new LocationPath();
		} else {
			path = in_path;
		}

		Step step = null;
	}
	:
		step=step
		{
			path.addStep(step);
		}
		(	(	SLASH^
			|	DOUBLE_SLASH^
				{
					path.addStep( new NodeTypeStep("descendant-or-self", "node") );
				}
			) 	step=step
				{
					path.addStep(step);
				}
		)*
	;

step returns [Step step]
	{
	    step             = null;
		String axis      = "child";
		String localName = null;
		String prefix    = null;
		String nodeType  = null;
		Predicate pred   = null;
	}
	:
		(
			// If it has an axis
			(	(IDENTIFIER DOUBLE_COLON | AT)=> axis=axis
			|
			)

			(
				(	
					(
						(	ns:IDENTIFIER COLON 
							{
								prefix = ns.getText();
							}
						)? 
							(	id:IDENTIFIER
								{
									localName = id.getText();
								}
							|		STAR
								{
									localName = "*";
								}
							)
					)
					{ 
						step = new NameTestStep(axis, prefix, localName);
					}
				)

				|	

				step=special_step[axis]
			)
			(
				pred=predicate
				{
					((UnAbbrStep)step).addPredicate(pred);
				}
			)*
		)
		|	step=abbr_step
			(
				pred=predicate
				{
					((UnAbbrStep)step).addPredicate(pred);
				}
			)*
	;

special_step[String axis] returns [Step step]
	{
		step = null;
		String piTarget = null;
	}
	:
	{
		LT(1).getText().equals("processing-instruction")
	}?
		IDENTIFIER LEFT_PAREN 
			(	target:IDENTIFIER 
				{
					piTarget = target.getText();
				}
			)?
		RIGHT_PAREN
		{
			step = new PIStep(axis, piTarget);
		}
	|
	{
		LT(1).getText().equals("comment")
			||
		LT(1).getText().equals("text")
			||
		LT(1).getText().equals("node")
	}?
		nodeType:IDENTIFIER LEFT_PAREN RIGHT_PAREN
		{
			step = new NodeTypeStep(axis, nodeType.getText());
		}

	;

axis returns [String axisName]
	{
		axisName = null;
	}
	:
		(	id:IDENTIFIER DOUBLE_COLON^
			{
				axisName = id.getText();
			}
		|	AT
			{
				axisName = "attribute";
			}
		)
	;

// ----------------------------------------
//		Section 2.4
//			Predicates
// ----------------------------------------

// .... production [8] ....
//
predicate returns [Predicate pred]
	{
		pred = null;
	}
	:
		LEFT_BRACKET^ pred=predicate_expr RIGHT_BRACKET!
	;

// .... production [9] ....
//
predicate_expr returns [Predicate pred]
	{
		pred = null;
		Expr expr = null;
	}
	:
		expr=expr
		{
			pred = new Predicate(expr);
		}
	;

// .... production [12] ....
//
abbr_step returns [Step step]
	{
		step = null;
	}
	:
			DOT
			{
				step = new NodeTypeStep("self", "node");
			}
		|	DOT_DOT
			{
				step = new ParentStep();
			}
	;

// .... production [13] ....
//
abbr_axis_specifier
	:
		( AT )?
	;


// ----------------------------------------
//		Section 3
//			Expressions
// ----------------------------------------

// ----------------------------------------
//		Section 3.1
//			Basics
// ----------------------------------------

// .... production [14] ....
//
expr returns [Expr expr]
	{
		expr = null;
	}
	:
		expr=or_expr
	;

// .... production [15] ....
//
primary_expr returns [Expr expr]
	{
		expr = null;
	}
	:
			expr=variable_reference
		|	LEFT_PAREN! expr=expr RIGHT_PAREN!
		|	expr=literal
		|	expr=number
		|	expr=function_call
	;

literal returns [Expr expr]
	{
		expr = null;
	}
	:
		lit:LITERAL^
		{
			expr = new StringExpr(lit.getText());
		}
	;

number returns [Expr expr]
	{
		expr = null;
	}
	:
		num:NUMBER^
		{
			expr = new NumberExpr(num.getText());
		}
	;

variable_reference returns [VariableExpr expr]
	{
		expr = null;
	}
	:
		DOLLAR_SIGN^ id:IDENTIFIER
		{
			expr = new VariableExpr(id.getText());
		}
	;

// ----------------------------------------
//		Section 3.2
//			Function Calls
// ----------------------------------------

// .... production [16] ....
//
function_call returns [FunctionExpr expr]
	{
		expr      = null;
		List args = null;
	}
	:
		id:IDENTIFIER LEFT_PAREN^ ( args=arg_list )? RIGHT_PAREN!

		{
			expr = new FunctionExpr(id.getText(), args);
		}
	;

// .... production [16.1] ....
//
arg_list returns [List args]
	{
		args = new ArrayList();
		Expr expr = null;
	}
	:
		expr=argument 
		{
			args.add(expr);
		}	

		( COMMA expr=argument 
			{
				args.add(expr);
			}
		)*
	;

// .... production [17] ....
//
argument returns [Expr expr]
	{
		expr = null;
	}
	:
		expr=expr
	;

// ----------------------------------------
//		Section 3.3
//			Node-sets
// ----------------------------------------

// .... production [18] ....
//
union_expr returns [Expr expr]
	{
		expr = null;

		Expr rhs = null;
	}
	:
		expr=path_expr
		( 	PIPE! 			
			rhs=path_expr
                        {
                            expr = makeUnionExpr(expr, rhs);
                        }
		)*

	;

// .... production [19] ....
//

path_expr returns [PathExpr expr]
	{
		expr = null;
		LocationPath path = null;
	}
	:
		// This is here to differentiate between the
		// special case of the first step being a NodeTypeTest
		// or just a normal filter-expr function call.

		// Is it a special nodeType 'function name'

		(IDENTIFIER LEFT_PAREN)=>{ 
			LT(1).getText().equals("processing-instruction")
				||
			LT(1).getText().equals("comment")
				||
			LT(1).getText().equals("text")
				||
			LT(1).getText().equals("node")
		}?

		expr=location_path
		|	
		(IDENTIFIER LEFT_PAREN)=>
		expr=filter_expr 
			(	path=absolute_location_path 
				{ 
					((FilterExpr)expr).setLocationPath(path);
				}
			)?
		|
		(DOT|DOT_DOT|SLASH|DOUBLE_SLASH|IDENTIFIER|AT)=>
		expr=location_path		
		|	
		expr=filter_expr 
			(	path=absolute_location_path 
				{ 
					((FilterExpr)expr).setLocationPath(path);
				}
			)?
	;

// .... production [20] ....
//
filter_expr returns [ FilterExpr expr ]
	{
		expr = null;
		Predicate pred = null;
		Expr filterExpr = null;
	}
	:
		filterExpr=primary_expr 
		{
			expr = new FilterExpr(filterExpr);
		}
		(	pred=predicate 
			{
				expr.addPredicate(pred);
			}
		)*
	;


// ----------------------------------------
//		Section 3.4
//			Booleans
// ----------------------------------------

// .... production [21] ....
//
or_expr returns [Expr expr]
	{
		expr = null;
		Expr lhs = null;
		Expr rhs = null;
		Op op = null;
	}
	:
		expr=and_expr (	KW_OR^				{ op = Op.OR; }
						rhs=and_expr 
		{
			expr = makeBinaryExpr(op, expr, rhs);
		}
		)*
	;

// .... production [22] ....
//
and_expr returns [Expr expr]
	{
		expr = null;
		Expr lhs = null;
		Expr rhs = null;
		Op op = null;
	}
	:
		lhs=equality_expr (	KW_AND^ 			{ op = Op.AND; }
							rhs=equality_expr )?

		{
			expr = makeBinaryExpr(op, lhs, rhs);
		}
	;

// .... production [23] ....
//
equality_expr returns [Expr expr]
	{
		expr = null;
		Expr lhs = null;
		Expr rhs = null;
		Op op = null;
	}
	:
		lhs=relational_expr (	(	EQUALS^		{ op = Op.EQUAL; }
								|	NOT_EQUALS^	{ op = Op.NOT_EQUAL; }
								)
								rhs=relational_expr
							)?
		{
			expr = makeBinaryExpr(op, lhs, rhs);
		}
	;

// .... production [24] ....
//
relational_expr returns [Expr expr]
	{
		expr = null;
		Expr lhs = null;
		Expr rhs = null;
		Op op = null;

	}
	:
		lhs=additive_expr	(	(	LT^		{ op = Op.LT; }
								|	GT^		{ op = Op.GT; }
								|	LTE^	{ op = Op.LT_EQUAL; }
								|	GTE^	{ op = Op.GT_EQUAL; }
								)
								rhs=additive_expr
							)?
		{
			expr = makeBinaryExpr(op, lhs, rhs);
		}
	;

// ----------------------------------------
//		Section 3.5
//			Numbers
// ----------------------------------------

// .... production [25] ....
//
additive_expr returns [Expr expr]
	{
		expr = null;
		Expr lhs = null;
		Expr rhs = null;
		Op op = null;
	}
	:
		lhs=mult_expr	(	(	PLUS^	{ op = Op.PLUS; }
							|	MINUS^	{ op = Op.MINUS; }
							)
							rhs=mult_expr
						)?

		{
			expr = makeBinaryExpr(op, lhs, rhs);
		}
	;

// .... production [26] ....
//
mult_expr returns [Expr expr]
	{
		expr = null;
		Expr lhs = null;
		Expr rhs = null;
		Op op = null;
	}
	:
		lhs=unary_expr	(	(	STAR^	{ op = Op.MULTIPLY; }
							|	DIV^	{ op = Op.DIV; }
							|	MOD^	{ op = Op.MOD; }
							)
							rhs=unary_expr
						)?

		{
			expr = makeBinaryExpr(op, lhs, rhs);
		}
	;

// .... production [27] ....
//
unary_expr returns [Expr expr]
	{
		expr = null;
	}
	:
			expr=union_expr
		|
			MINUS expr=unary_expr
			{
				expr = new NegativeExpr(expr);
			}
	;

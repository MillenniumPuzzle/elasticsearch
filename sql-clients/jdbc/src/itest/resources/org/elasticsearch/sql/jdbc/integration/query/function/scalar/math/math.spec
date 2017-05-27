//
// Math
//

mathAbs
SELECT ABS(emp_no) m, first_name FROM "emp.emp" WHERE emp_no < 10010 ORDER BY emp_no;
mathACos
SELECT ACOS(emp_no) m, first_name FROM "emp.emp" WHERE emp_no < 10010 ORDER BY emp_no;
mathASin
SELECT ASIN(emp_no) m, first_name FROM "emp.emp" WHERE emp_no < 10010 ORDER BY emp_no;
mathATan
SELECT ATAN(emp_no) m, first_name FROM "emp.emp" WHERE emp_no < 10010 ORDER BY emp_no;
//mathCbrt
//SELECT CBRT(emp_no) m, first_name FROM "emp.emp" WHERE emp_no < 10010 ORDER BY emp_no;
mathCeil
SELECT CEIL(emp_no) m, first_name FROM "emp.emp" WHERE emp_no < 10010 ORDER BY emp_no;
mathCos
SELECT COS(emp_no) m, first_name FROM "emp.emp" WHERE emp_no < 10010 ORDER BY emp_no;
mathCosh
SELECT COSH(emp_no) m, first_name FROM "emp.emp" WHERE emp_no < 10010 ORDER BY emp_no;
mathDegrees
SELECT DEGREES(emp_no) m, first_name FROM "emp.emp" WHERE emp_no < 10010 ORDER BY emp_no;
mathFloor
SELECT CAST(FLOOR(emp_no) AS INT) m, first_name FROM "emp.emp" WHERE emp_no < 10010 ORDER BY emp_no;
mathLog
SELECT LOG(emp_no) m, first_name FROM "emp.emp" WHERE emp_no < 10010 ORDER BY emp_no;
mathLog10
SELECT LOG10(emp_no) m, first_name FROM "emp.emp" WHERE emp_no < 10010 ORDER BY emp_no;
mathRadians
SELECT RADIANS(emp_no) m, first_name FROM "emp.emp" WHERE emp_no < 10010 ORDER BY emp_no;
mathRound
SELECT CAST(ROUND(emp_no) AS INT) m, first_name FROM "emp.emp" WHERE emp_no < 10010 ORDER BY emp_no;
mathRadians
SELECT RADIANS(emp_no) m, first_name FROM "emp.emp" WHERE emp_no < 10010 ORDER BY emp_no;
mathSin
SELECT SIN(emp_no) m, first_name FROM "emp.emp" WHERE emp_no < 10010 ORDER BY emp_no;
mathSinH
SELECT SINH(emp_no) m, first_name FROM "emp.emp" WHERE emp_no < 10010 ORDER BY emp_no;
mathSqrt
SELECT SQRT(emp_no) m, first_name FROM "emp.emp" WHERE emp_no < 10010 ORDER BY emp_no;
mathTan
SELECT TAN(emp_no) m, first_name FROM "emp.emp" WHERE emp_no < 10010 ORDER BY emp_no;

//
// Combined methods
//

mathAbsOfSin
SELECT ABS(SIN(emp_no)) m, first_name FROM "emp.emp" WHERE emp_no < 10010 ORDER BY emp_no;
mathAbsOfCeilOfSin
SELECT EXP(ABS(CEIL(SIN(DEGREES(emp_no))))) m, first_name FROM "emp.emp" WHERE emp_no < 10010 ORDER BY emp_no;
mathAbsOfCeilOfSinWithFilter
SELECT EXP(ABS(CEIL(SIN(DEGREES(emp_no))))) m, first_name FROM "emp.emp" WHERE EXP(ABS(CEIL(SIN(DEGREES(emp_no))))) < 10 ORDER BY emp_no;

//
// Filter by Scalar
// 
mathAbsFilterAndOrder
SELECT emp_no, ABS(emp_no) m, first_name FROM "emp.emp" WHERE ABS(emp_no) < 10010 ORDER BY ABS(emp_no);
mathACosFilterAndOrder
SELECT emp_no, ACOS(emp_no) m, first_name FROM "emp.emp" WHERE ACOS(emp_no) < 10010 ORDER BY ACOS(emp_no);
mathASinFilterAndOrder
SELECT emp_no, ASIN(emp_no) m, first_name FROM "emp.emp" WHERE ASIN(emp_no) < 10010 ORDER BY ASIN(emp_no);
//mathATanFilterAndOrder
//SELECT emp_no, ATAN(emp_no) m, first_name FROM "emp.emp" WHERE ATAN(emp_no) < 10010 ORDER BY ATAN(emp_no);
mathCeilFilterAndOrder
SELECT emp_no, CEIL(emp_no) m, first_name FROM "emp.emp" WHERE CEIL(emp_no) < 10010 ORDER BY CEIL(emp_no);
//mathCosFilterAndOrder
//SELECT emp_no, COS(emp_no) m, first_name FROM "emp.emp" WHERE COS(emp_no) < 10010 ORDER BY COS(emp_no);
//mathCoshFilterAndOrder
//SELECT emp_no, COSH(emp_no) m, first_name FROM "emp.emp" WHERE COSH(emp_no) < 10010 ORDER BY COSH(emp_no);

//
// constants - added when folding will be supported
// 
//mathConstantPI
//SELECT ABS(emp_no) m, PI(), first_name FROM "emp.emp" WHERE emp_no < 10010 ORDER BY emp_no;

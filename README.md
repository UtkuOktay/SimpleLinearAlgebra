# Simple Linear Algebra Library

This is a simple library for linear algebra written in Java. It consists of two classes: Vector and Matrix. They contain some essential methods for the operations.

## Vector Class
This class is used to create vector objects and perform basic operations on them. Moreover, the Matrix class uses the Vector class in order to perform the operations in a more understandable way. Thus, the code becomes clearer.

## Matrix Class
This class is used to create matrix objects and work with them. Some problems such as matrix multiplication, determinant and finding the inverse may be tiring to solve by hand due to repetitive time-consuming processes although they are not difficult. The class has methods to perform some of them.

## Code Examples
### Vector Class:
```Java
Vector vector1 = new Vector(new double[]{2, 5, 12});
double lengthOfVector1 = vector1.getLength();

Vector vector2 = new Vector(new double[]{3, 4, 7});
double dotProduct = Vector.dotProduct(vector1, vector2);
```
### Matrix Class:
```Java
Matrix matrix1 = new Matrix(new double[][]{{1, 5, 3}, {7, 2, 4}, {3, 0, 4}});
double determinant = matrix1.determinant();

Matrix matrix2 = new Matrix(new double[][]{{2, 6, 7, 1}, {3, 15, 4, 2}, {1, 9, 7, 4}});
Matrix multiplicationOfMatrices = Matrix.multiplication(matrix1, matrix2);
```
## Please Note
- Indexes are used in the same way in the Java. E.g., For the vector [2, 4, 6], 4 is at the 1st index for the library.

- I develop it for my self-improvement. Hence, I do not mainly focus on the performance and the library does not have many functions. There are some methods which may run slowly:
    - **Matrix Multiplication:** It uses dot product and has a complexity of O(n^3).
    - **Determinant:** It uses Cofactor Expansion (Laplace Expansion). It is a recursive algorithm and therefore the determinant method calls itself. It has a complexity of O(n!) and may run very slowly when working with big matrices.
    - **Inverse:** It calls determinant method several times and therefore the problems stated for the determinant method apply to this function as well.
- The library may produce incorrect results and/or may not work properly. In any case of direct or indirect damage, loss etc., the developer will not be liable.

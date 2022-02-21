public class Matrix {

    private double[][] elements;

    //A constructor method with two arguments of its dimension. Creates a new array with the specified dimension. (First row, second column)
    public Matrix(int rows, int columns) {
        if (rows < 1 || columns < 1)
            throw new IllegalArgumentException("Dimension must be at least 1x1");
        elements = new double[rows][columns];
    }

    //A constructor method with an argument of a 2D double array. Assigns a copy of the given array to the elements array.
    public Matrix(double[][] elements) {
        int column = elements[0].length; //Checking if all rows have the same number of elements. If not, it throws an exception.

        for (int i = 1; i < elements.length; i++) {
            if (column != elements[i].length)
                throw new IllegalArgumentException("All rows must have the same number of elements.");
            column = elements[i].length;
        }

        this.elements = new double[elements.length][elements[0].length];

        for (int i = 0; i < elements.length; i++) {
            for (int j = 0; j < elements[0].length; j++) {
                this.elements[i][j] = elements[i][j];
            }
        }
    }

    //A constructor method which copies the elements of the given matrix.
    public Matrix(Matrix matrix) {
        elements = new double[matrix.getRowCount()][matrix.getColumnCount()];
        for (int i = 0; i < elements.length; i++) {
            for (int j = 0; j < elements[0].length; j++) {
                elements[i][j] = matrix.getElement(i, j);
            }
        }
    }

    //Returns a copy of the matrix.
    public Matrix clone() {
        return new Matrix(this);
    }

    //Returns the count of rows of the matrix.
    public int getRowCount() { //Returns the row count of the matrix.
        return elements.length;
    }

    //Returns the count of columns of the matrix.
    public int getColumnCount() { //Returns the column count of the matrix.
        return elements[0].length;
    }

    //Returns the element at given coordinates.
    public double getElement(int row, int column) {
        if (row < 0 || column < 0 || row > elements.length || column > elements[0].length)
            throw new IndexOutOfBoundsException("Location " + row + ", " + column + " does not exist in the matrix.");
        return elements[row][column];
    }

    //Sets the element at given coordinates.
    public void setElement(int row, int column, double value) {
        if (row < 0 || column < 0 || row > elements.length || column > elements[0].length)
            throw new IndexOutOfBoundsException("Location" + row + ", " + column + " does not exist in the matrix.");
        elements[row][column] = value;
    }

    //Checks whether given matrices are of the same dimension.
    public static boolean checkDimensions(Matrix m1, Matrix m2) {
        return m1.getRowCount() == m2.getRowCount() && m1.getColumnCount() == m2.getColumnCount();
    }

    //Takes a matrix as the argument and adds its elements to the elements of this matrix.
    //Throws an IllegalArgumentException when the two matrices' dimensions are not equal.
    public void add(Matrix m1) {
        if (!Matrix.checkDimensions(this, m1))
            throw new IllegalArgumentException("Dimensions of two matrices are not equal.");

        for (int i = 0; i < getRowCount(); i++) {
            for (int j = 0; j < getColumnCount(); j++) {
                setElement(i, j, getElement(i, j) + m1.getElement(i, j));
            }
        }
    }

    //Takes a matrix as the argument and adds its elements to the elements of this matrix.
    //Throws an IllegalArgumentException when the two matrices' dimensions are not equal.
    public void subtract(Matrix m1) {
        if (!Matrix.checkDimensions(this, m1))
            throw new IllegalArgumentException("Dimensions of two matrices are not equal.");

        for (int i = 0; i < getRowCount(); i++) {
            for (int j = 0; j < getColumnCount(); j++) {
                setElement(i, j, getElement(i, j) - m1.getElement(i, j));
            }
        }
    }

    //Performs the multiplication by a scalar. Multiplies each element by the scalar number.
    public void multiplyByAScalar(double scalar) {
        for (int i = 0; i < getRowCount(); i++) {
            for (int j = 0; j < getColumnCount(); j++) {
                setElement(i, j, getElement(i, j) * scalar);
            }
        }
    }

    //Returns the specified row of the matrix as a vector.
    public Vector getRowVector(int row) {
        if (row >= getRowCount())
            throw new IndexOutOfBoundsException("Row " + row + " does not exist in this vector.");

        return new Vector(elements[row]);
    }

    //Returns the specified column of the matrix as a vector.
    public Vector getColumnVector(int column) {
        if (column >= getColumnCount())
            throw new IndexOutOfBoundsException("Column" + column + " does not exist in this vector.");

        double[] components = new double[elements.length];

        for (int i = 0; i < components.length; i++) {
            components[i] = getElement(i, column);
        }

        return new Vector(components);
    }

    //Performs the multiplication of given matrices and returns the resultant matrix. The resultant matrix is new and the others are preserved.
    //For the multiplication, one's column count must match with the other's column count. Checks for it, if needed, changes their order for the operations.
    //If the dimensions are not equal, throws an IllegalArgumentException.
    public static Matrix multiplication(Matrix m1, Matrix m2) {
        int m1RowCount = m1.getRowCount();
        int m1ColumnCount = m1.getColumnCount();

        int m2RowCount = m2.getRowCount();
        int m2ColumnCount = m2.getColumnCount();

        if (m1ColumnCount != m2RowCount) {
            if (m1RowCount == m2ColumnCount) { //If needed, swaps them for the next operations
                Matrix temp = m1;
                m1 = m2;
                m2 = temp;

                m1RowCount = m1.getRowCount(); //Assigns new values after swapping the matrices.
                m1ColumnCount = m1.getColumnCount();

                m2RowCount = m2.getRowCount();
                m2ColumnCount = m2.getColumnCount();
            }
            else {
                throw new IllegalArgumentException("Dimensions of matrices are not appropriate.");
            }
        }

        Matrix result = new Matrix(m1RowCount, m2ColumnCount); //Resultant matrix has a dimension of first one's column count times the other's row count.

        //Calculates the dot product of row vectors of the first matrix and column vectors of the second matrix and place them accordingly.
        //Not an optimized way, has a complexity of O(n^3).
        for (int i = 0; i < m1RowCount; i++) {
            Vector rowVector = m1.getRowVector(i);
            for (int j = 0; j < m2ColumnCount; j++) {
                Vector columnVector = m2.getColumnVector(j);
                result.setElement(i, j, Vector.dotProduct(rowVector, columnVector));
            }
        }

        return result;
    }

    //Creates and returns the transpose of the matrix.
    public Matrix getTranspose() {
        Matrix transposedMatrix = new Matrix(getColumnCount(), getRowCount());
        for (int i = 0; i < getRowCount(); i++) {
            for (int j = 0; j < getColumnCount(); j++) {
                transposedMatrix.setElement(j, i, getElement(i, j)); //Row and column indexes are interchanged to make it transpose.
            }
        }
        return transposedMatrix;
    }

    //Creates and returns an identity matrix for the given dimension.
    public static Matrix createIdentityMatrix(int dimension) {
        Matrix matrix = new Matrix(dimension, dimension);
        for (int i = 0; i < matrix.getRowCount(); i++) {
            matrix.setElement(i, i, 1);
        }
        return matrix;
    }

    //Checks whether the matrix is a square matrix.
    //A square matrix is a matrix whose row count is equal to its column count. E.g.: 1x1, 2x2, 3x3.
    public boolean isSquare() {
        return getRowCount() == getColumnCount();
    }

    //Removes the specified row from the matrix.
    public void removeRow(int rowToRemove) {
        if (rowToRemove < 0 || rowToRemove >= elements.length)
            throw new IndexOutOfBoundsException("Row " + rowToRemove +  " does not exist in the matrix.");

        double[][] temp = elements;
        elements = new double[elements.length - 1][elements[0].length];

        for (int i = 0; i < elements.length; i++) {
            for (int j = 0; j < elements[0].length; j++) {
                if (i < rowToRemove)
                    elements[i][j] = temp[i][j];
                else
                    elements[i][j] = temp[i + 1][j];
            }
        }
    }

    //Removes the specified column from the matrix.
    public void removeColumn(int columnToRemove) {
        if (columnToRemove < 0 || columnToRemove >= elements[0].length)
            throw new IndexOutOfBoundsException("Column " + columnToRemove + " does not exist in the matrix.");

        double[][] temp = elements;
        elements = new double[elements.length][elements[0].length - 1];

        for (int i = 0; i < elements.length; i++) {
            for (int j = 0; j < elements[0].length; j++) {
                if (j < columnToRemove)
                    elements[i][j] = temp[i][j];
                else
                    elements[i][j] = temp[i][j + 1];
            }
        }
    }

    //Finds and returns the minor matrix of the matrix.
    public Matrix getMinorMatrix(int row, int column) {
        Matrix minorMatrix = clone();
        minorMatrix.removeRow(row);
        minorMatrix.removeColumn(column);
        return minorMatrix;
    }

    //Computes the determinant of the matrix using the Cofactor Expansion method.
    //Since the Cofactor Expansion method is recursive, this method does recursive calls and not an efficient way (O(n!)). Main focus is on simplicity.
    //However, if the matrix contains a row or column with all zeroes, it directly returns 0.
    public double determinant() {
        if (!isSquare())
            throw new RuntimeException("Determinant of non-square matrices cannot be computed.");

        if (getRowCount() == 1) //Base condition (To end recursive calls at some point)
            return elements[0][0]; //Determinant of a 1x1 matrix is equal to the only element in it.

        for (int i = 0; i < getRowCount(); i++) {
            if (getRowVector(i).isZeroVector()) //Checks if there is a row with all zeroes.
                return 0;
        }
        for (int i = 0; i < getColumnCount(); i++) {
            if (getColumnVector(i).isZeroVector()) //Checks if there is a column with all zeroes.
                return 0;
        }

        double determinant = 0;
        for (int i = 0; i < getRowCount(); i++) {
            for (int j = 0; j < 1; j++) {
                double result = getElement(i, j) * getMinorMatrix(i, j).determinant();
                if ((i + j) % 2 == 1)
                    result *= -1;
                determinant += result;
            }
        }

        return determinant;
    }

    //Interchanges given two rows.
    public void interchangeRows(int row1, int row2) {
        if (row1 < 0 || row1 >= getRowCount())
            throw new IndexOutOfBoundsException("Row " + row1 + " does not exist in the matrix.");
        if (row2 < 0 || row2 >= getRowCount())
            throw new IndexOutOfBoundsException("Row " + row2 + " does not exist in the matrix.");

        Vector rowVector = getRowVector(row2);
        for (int i = 0; i < getColumnCount(); i++) {
            setElement(row2, i, getElement(row1, i));
            setElement(row1, i, rowVector.getComponent(i));
        }

    }

    //Multiplies all elements in the given rows with the given non-zero number.
    public void multiplyARowBy(int row, double number) {
        if (row < 0 || row >= getRowCount())
            throw new IndexOutOfBoundsException("Row " + row + " does not exist in the matrix.");
        if (number == 0)
            throw new IllegalArgumentException("Number cannot be 0.");

        for (int i = 0; i < getColumnCount(); i++) {
            setElement(row, i, getElement(row, i) * number);
        }
    }

    //Adds a multiple of a row with a number to another row. (Number * Row1 + Row2 -> Row2)
    public void addAScalarTimesARowToTheRow(int row1, int row2, double number) {
        if (row1 < 0 || row1 >= getRowCount())
            throw new IndexOutOfBoundsException("Row " + row1 + " does not exist in the matrix.");
        if (row2 < 0 || row2 >= getRowCount())
            throw new IndexOutOfBoundsException("Row " + row2 + " does not exist in the matrix.");

        for (int i = 0; i < getColumnCount(); i++) {
            setElement(row2, i, getElement(row2, i) + getElement(row1, i) * number);
        }
    }

    //Finds and returns the inverse of the matrix using determinants.
    //Not an optimized way. The determinant method is innefficient (O(n!) and makes recursive calls) and this method uses it several times.
    public Matrix getInverse() {
        if (!isSquare())
            throw new RuntimeException("Non square matrices do not have inverses.");

        double determinant = determinant();

        if (determinant == 0)
            throw new RuntimeException("Matrices whose determinants are equal to 0 do not have inverses.");

        Matrix matrix = new Matrix(getRowCount(), getColumnCount());

        for (int i = 0; i < getRowCount(); i++) {
            for (int j = 0; j < getColumnCount(); j++) {
                double value = 1 / determinant * getMinorMatrix(j, i).determinant();
                if ((i + j) % 2 == 1)
                    value *= -1;
                matrix.setElement(i, j, value);
            }
        }
        return matrix;
    }

    //Overrides the toString function.
    //Returns a string containing a text-based representation of the matrix.
    public String toString() {
        String text = "";
        for (int i = 0; i < getRowCount(); i++) {
            text += "|";
            for (int j = 0; j < getColumnCount() - 1; j++) {
                text += getElement(i, j) + ", ";
            }
            text += getElement(i, getColumnCount() - 1) + "|\n";
        }
        return text;
    }
}
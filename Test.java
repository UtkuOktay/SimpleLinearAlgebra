//A test file to see if Vector and Matrix classes work.

public class Test {

    public static void main(String[] args) {
        Vector v1 = new Vector(new double[]{1, 3, 5});
        Vector v2 = new Vector(new double[]{2 ,4, 15});

        System.out.println("Dot product of v1 and v2: " + Vector.dotProduct(v1, v2));
        System.out.println("Cross product of v1 and v2: " + Vector.crossProduct(v1, v2));
        System.out.println("Angle between v1 and v2: " + v1.getAngleBetween(v2));
        System.out.println("The length of v1: " + v1.getLength());
        System.out.println("Unit vector of v2: " + v2.getUnitVector());
        v1.add(v2);
        System.out.println("v2 is added to v1. Final v1: " + v1);
        v2.multiplyByAScalar(2);
        System.out.println("v2 is multiplied by 2. Final v2: " + v2);
        v1.subtract(v2);
        System.out.println("v2 is subtracted from v1. Final v1: " + v1);

        System.out.println("Standard unit vectors for a 3D subspace:");
        Vector[] standardUnitVectors = Vector.getStandardUnitVectors(3);
        for (int i = 0; i < standardUnitVectors.length; i++) {
            System.out.println(standardUnitVectors[i]);
        }
        System.out.println();

        Matrix m1 = new Matrix(new double[][]{{1, 2, 3}, {4, 6, 5}, {14, 16, 20}, {8, 10, 11}});
        Matrix m2 = new Matrix(new double[][]{{5, 2, 7, 11, 1}, {3, 5, 12, 4, 8}, {5, 10, 16, 2, 9}});

        System.out.println("m1:\n" + m1);
        System.out.println("m2:\n" + m2);

        System.out.println();
        System.out.println("Multiplication of m1 and m2:\n" + Matrix.multiplication(m1, m2));

        System.out.println("Transpose of m1:\n" + m1.getTranspose());

        Matrix m3 = new Matrix(new double[][]{{12, 4, 4}, {7, 0, 1}, {4, 0, 9}});
        System.out.println("m3:\n" + m3);
        System.out.println("Determinant of m3: " + m3.determinant());

        Matrix m5 = Matrix.createIdentityMatrix(3);
        System.out.println("3x3 Identity Matrix:\n" + m5);
        m5.multiplyByAScalar(5);
        System.out.println("m5 is multiplied by 5:\n" + m5);

        System.out.println("m3:\n" + m3);
        m3.add(m5);
        System.out.println("m5 is added to m3. m3:\n" + m3);
        m3.subtract(m5);
        System.out.println("m5 is subtracted from m3. m3:\n" + m3);

        m3.interchangeRows(0, 1);
        System.out.println("m3's first two row is interchanged:\n" + m3);

        m3.multiplyARowBy(1, 3);
        System.out.println("Row 1 of m3 is multiplied by 3:\n" + m3);

        m3.addAScalarTimesARowToTheRow(0, 1, 3);
        System.out.println("3 times row 0 is added to row 1:\n" + m3);

        System.out.println("The inverse of m3:\n" + m3.getInverse());
    }
}
public class Vector {

    private double[] components; //A 1D double array to store the components of the vector.

    //A constructor method with a parameter of dimension. Creates a new array with the specified dimension.
    public Vector(int dimension) {
        if (dimension < 0)
            throw new IllegalArgumentException("Dimension cannot be less than 0.");
        components = new double[dimension];
    }

    //A constructor method with an argument of a 1D double array. Assigns a copy of the given array to the components array.
    public Vector(double[] components) {
        this.components = components.clone();
    }

    //A constructor method which copies the components of the given vector.
    public Vector(Vector vector) {
        components = new double[vector.getDimension()];
        for (int i = 0; i < components.length; i++) {
            setComponent(i, vector.getComponent(i));
        }
    }

    public Vector clone() {
        return new Vector(this);
    }

    //Returns the dimension of the vector.
    public int getDimension() { //Returns the dimension of the vector. Since its components are kept in the array, its dimension is equal to the length of the array.
        return components.length;
    }

    //Returns the nth component of the vector.
    public double getComponent(int index) {
        if (index < 0 || index > components.length - 1)
            throw new IndexOutOfBoundsException("Index " + index + " does not exist in this vector.");
        return components[index];
    }

    //Sets the nth component of the vector.
    public void setComponent(int index, double value) {
        if (index < 0 || index > components.length - 1)
            throw new IndexOutOfBoundsException("Index " + index + " does not exist in this vector.");
        components[index] = value;
    }

    //Calculates and returns the length of the vector.
    public double getLength() {
        double length = 0;

        for (int i = 0; i < getDimension(); i++) {
            length += Math.pow(getComponent(i), 2); //Summing the square of each element.
        }

        length = Math.sqrt(length); //Calculating the square root of the sum.

        return length;
    }

    //Multiplies the vector by a scalar number.
    public void multiplyByAScalar(double scalar) {
        for (int i = 0; i < getDimension(); i++) {
            setComponent(i, getComponent(i) * scalar); //Each component is multiplied by the scalar number.
        }
    }

    //Adds each component of the other vector to its corresponding components.
    public void add(Vector v) {
        if (getDimension() != v.getDimension())
            throw new IllegalArgumentException("Vectors of different dimensions cannot be added.");

        for(int i = 0; i < getDimension(); i++) {
            setComponent(i, getComponent(i) + v.getComponent(i));
        }
    }

    //Subtracts each component of the other vector from its corresponding components.
    public void subtract(Vector v) {
        if (getDimension() != v.getDimension())
            throw new IllegalArgumentException("Vectors of different dimensions cannot be subtracted.");

        for(int i = 0; i < getDimension(); i++) {
            setComponent(i, getComponent(i) - v.getComponent(i));
        }
    }

    //Calculates and returns the result of the dot product of itself and the vector in the arguments.
    public static double dotProduct(Vector v1, Vector v2) {
        if (v1.getDimension() != v2.getDimension())
            throw new IllegalArgumentException("Dot product of vectors of different dimensions cannot be calculated.");

        double result = 0;
        for (int i = 0; i < v1.getDimension(); i++) {
            result += v1.getComponent(i) * v2.getComponent(i); //Corresponding components are individually multiplied and added to the result variable.
        }
        return result; //The result of dot product is a scalar.
    }

    public static Vector crossProduct(Vector v1, Vector v2) {
        if (v1.getDimension() != 3 || v2.getDimension() != 3)
            throw new IllegalArgumentException("Vectors' dimensions must be 3 for the cross product.");

        Vector result = new Vector(v1.getDimension());
        result.setComponent(0, v1.getComponent(1) * v2.getComponent(2) - v1.getComponent(2) * v2.getComponent(1));
        result.setComponent(1, v1.getComponent(2) * v2.getComponent(0) - v1.getComponent(0) * v2.getComponent(2));
        result.setComponent(2, v1.getComponent(0) * v2.getComponent(1) - v1.getComponent(1) * v2.getComponent(0));

        return result;
    }

    //Calculates and returns the unit vector of the vector. The unit vector is a vector whose length (magnitude) is 1.
    //The unit vector is found by dividing each component by the length of the vector.
    public Vector getUnitVector() {
        double length = getLength();
        Vector vector = new Vector(getDimension());

        for (int i = 0; i < getDimension(); i++) {
            vector.setComponent(i, getComponent(i) / length);
        }

        return vector; //The result is a vector.
    }

    //Takes a vector as the argument and returns the angle between them in degrees.
    //Calculates the dot product of two vectors and using their length, calculates cosine of the angle between them.
    //Then, using arccos function calculates the angle.
    public double getAngleBetween(Vector v) {
        double dotProductResult = Vector.dotProduct(this, v);
        double cosx = dotProductResult / (getLength() * v.getLength());
        return Math.toDegrees(Math.acos(cosx));
    }

    //Takes an integer value of the dimension as the argument and returns standard unit vectors of the subspace of given dimension.
    public static Vector[] getStandardUnitVectors(int dimension) {
        if (dimension < 1)
            throw new IllegalArgumentException("Dimension must at least be 1.");

        Vector[] vectors = new Vector[dimension];

        for (int i = 0; i < vectors.length; i++) {
            double[] componentsOfVector = new double[dimension];

            componentsOfVector[i] = 1.0;
            vectors[i] = new Vector(componentsOfVector);
        }
        return vectors;
    }

    //Checks if the vector is a zero vector. A zero vector is a vector whose components are all zero.
    public boolean isZeroVector() {
        boolean isZero = true;

        for (int i = 0; i < getDimension(); i++) {
            if (getComponent(i) != 0) {
                isZero = false;
                break;
            }
        }
        return isZero;
    }
    //Overrides the toString method and returns the components in the form "[a, b, c...]".
    public String toString() {
        String text = "[";

        for(int i = 0; i < getDimension() - 1; i++) {
            text += getComponent(i) + ", ";
        }

        text += getComponent(getDimension() - 1) + "]";
        return text;
    }
}
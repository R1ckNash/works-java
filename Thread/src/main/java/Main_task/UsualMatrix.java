package Main_task;

public class UsualMatrix {
    private int[][] matrix;

    UsualMatrix(int row, int column) {
        matrix = new int[row][column];
    }

    UsualMatrix(UsualMatrix obj) {
        matrix = new int[obj.getSizeRow()][obj.getSizeColumn()];
        for (int i = 0; i < obj.getSizeRow(); i++) {
            System.arraycopy(obj.matrix[i], 0, matrix[i], 0, obj.getSizeColumn());
        }
    }

    public void sum(UsualMatrix obj) {
        if (getSizeColumn() != obj.getSizeColumn() && getSizeRow() != obj.getSizeRow())
            throw new RuntimeException("Error! The matrix have different sizes!");

        for (int i = 0; i < getSizeRow(); i++) {
            for (int j = 0; j < getSizeColumn(); j++) {
                matrix[i][j] += obj.matrix[i][j];
            }
        }
    }

    public void product(UsualMatrix obj) {
        if (getSizeColumn() != obj.getSizeRow())
            throw new RuntimeException("Error! The matrix have different sizes!");

        int[][] result = new int[getSizeRow()][obj.getSizeColumn()];

        for (int i = 0; i < getSizeRow(); i++) {
            for (int j = 0; j < obj.getSizeColumn(); j++) {
                for (int k = 0; k < obj.getSizeRow(); k++) {
                    result[i][j] += matrix[i][k] * obj.matrix[k][j];
                }
            }
        }

        matrix = result;
    }

    public void setElement(int row, int column, int value) {
        if (row < 0 || column < 0 || row >= getSizeRow() || column >= getSizeColumn())
            throw new RuntimeException("Error! Outside the matrix!");

        matrix[row][column] = value;
    }

    public int getElement(int row, int column) {
        if (row < 0 || column < 0 || row >= getSizeRow() || column >= getSizeColumn())
            throw new RuntimeException("Error! Outside the matrix!");

        return matrix[row][column];
    }

    public int getSizeColumn() {
        return matrix[0].length;
    }

    public int getSizeRow() {
        return matrix.length;
    }

    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (this == obj)
            return true;
        if (!(obj instanceof UsualMatrix))
            return false;

        UsualMatrix mtrx = (UsualMatrix) obj;
        for (int i = 0; i < getSizeRow(); i++)
            for (int j = 0; j < getSizeColumn(); j++)
                if (matrix[i][j] != mtrx.matrix[i][j])
                    return false;

        return true;
    }

    public String toString() {
        StringBuilder matrixToString = new StringBuilder();
        for (int i = 0; i < getSizeRow(); i++) {
            matrixToString.append("[");
            for (int j = 0; j < getSizeColumn(); j++) {
                matrixToString.append(matrix[i][j]);
                if (j != getSizeColumn() - 1) matrixToString.append(" ");
            }
            matrixToString.append("]\n");
        }
        return matrixToString.toString();
    }
}
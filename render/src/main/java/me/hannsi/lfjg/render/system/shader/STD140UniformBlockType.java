package me.hannsi.lfjg.render.system.shader;

import me.hannsi.lfjg.core.utils.math.RowColumn;
import me.hannsi.lfjg.core.utils.type.system.IEnumTypeBase;

public enum STD140UniformBlockType implements IEnumTypeBase {
    BOOL(0, "Bool", 4, 4, -1, -1, RowColumn.NONE),
    FLOAT(1, "Float", 4, 4, -1, -1, RowColumn.NONE),
    INT(2, "Int", 4, 4, -1, -1, RowColumn.NONE),
    UINT(3, "UInt", 4, 4, -1, -1, RowColumn.NONE),
    DOUBLE(4, "Double", 4, 4, -1, -1, RowColumn.NONE),
    BVEC2(5, "BoolVector2", 8, 8, -1, -1, RowColumn.NONE),
    BVEC3(6, "BoolVector3", 16, 12, -1, -1, RowColumn.NONE),
    BVEC4(7, "BoolVector4", 16, 16, -1, -1, RowColumn.NONE),
    VEC2(8, "Vector2", 8, 8, -1, -1, RowColumn.NONE),
    VEC3(9, "Vector3", 16, 12, -1, -1, RowColumn.NONE),
    VEC4(10, "Vector4", 16, 16, -1, -1, RowColumn.NONE),
    IVEC2(11, "IntVector2", 8, 8, -1, -1, RowColumn.NONE),
    IVEC3(12, "IntVector3", 16, 12, -1, -1, RowColumn.NONE),
    IVEC4(13, "IntVector4", 16, 16, -1, -1, RowColumn.NONE),
    UVEC2(14, "uIntVector2", 8, 8, -1, -1, RowColumn.NONE),
    UVEC3(15, "uIntVector3", 16, 12, -1, -1, RowColumn.NONE),
    UVEC4(16, "uIntVector4", 16, 16, -1, -1, RowColumn.NONE),
    DVEC2(17, "DoubleVector2", 16, 16, -1, -1, RowColumn.NONE),
    DVEC3(18, "DoubleVector3", 32, 24, -1, -1, RowColumn.NONE),
    DVEC4(19, "DoubleVector4", 32, 32, -1, -1, RowColumn.NONE),
    FLOAT_4(20, "Float[4]", 16, 64, 16, -1, RowColumn.NONE),
    VEC2_4(21, "Vector2[4]", 16, 64, 16, -1, RowColumn.NONE),
    VEC3_4(22, "Vector3[4]", 16, 64, 16, -1, RowColumn.NONE),
    VEC4_4(23, "Vector4[4]", 16, 64, 16, -1, RowColumn.NONE),
    DVEC2_4(24, "DoubleVector2[4]", 16, 64, 16, -1, RowColumn.NONE),
    DVEC3_4(25, "DoubleVector3[4]", 32, 128, 32, -1, RowColumn.NONE),
    DVEC4_4(26, "DoubleVector4[4]", 32, 128, 32, -1, RowColumn.NONE),
    MAT2(27, "Matrix2x2", 16, 32, -1, 16, RowColumn.ROW),
    MAT2x3(28, "Matrix2x3", 16, 48, -1, 16, RowColumn.ROW),
    MAT2x4(29, "Matrix2x4", 16, 64, -1, 16, RowColumn.ROW),
    MAT3(30, "Matrix3x3", 16, 48, -1, 16, RowColumn.ROW),
    MAT3x2(31, "Matrix3x2", 16, 32, -1, 16, RowColumn.ROW),
    MAT3x4(32, "Matrix3x4", 16, 64, -1, 16, RowColumn.ROW),
    MAT4(33, "Matrix4x4", 16, 64, -1, 16, RowColumn.ROW),
    MAT4x2(34, "Matrix4x2", 16, 32, -1, 16, RowColumn.ROW),
    MAT4x3(35, "Matrix4x3", 16, 48, -1, 16, RowColumn.ROW),
    DMAT2(36, "DoubleMatrix2x2", 16, 32, -1, 16, RowColumn.ROW),
    DMAT2x3(37, "DoubleMatrix2x3", 16, 48, -1, 16, RowColumn.ROW),
    DMAT2x4(38, "DoubleMatrix2x4", 16, 64, -1, 16, RowColumn.ROW),
    DMAT3(39, "DoubleMatrix3x3", 32, 96, -1, 32, RowColumn.ROW),
    DMAT3x2(40, "DoubleMatrix3x2", 32, 64, -1, 32, RowColumn.ROW),
    DMAT3x4(41, "DoubleMatrix3x4", 16, 128, -1, 32, RowColumn.ROW),
    DMAT4(42, "DoubleMatrix4x4", 32, 128, -1, 32, RowColumn.ROW),
    DMAT4x2(43, "DoubleMatrix4x2", 32, 64, -1, 32, RowColumn.ROW),
    DMAT4x3(44, "DoubleMatrix4x3", 32, 96, -1, 32, RowColumn.ROW),
    DMAT2_4(45, "DoubleMatrix2x2[4]", 16, 128, 32, 16, RowColumn.ROW),
    DMAT2x3_4(46, "DoubleMatrix2x3[4]", 16, 192, 48, 16, RowColumn.ROW),
    DMAT2x4_4(47, "DoubleMatrix2x4[4]", 16, 256, 64, 16, RowColumn.ROW),
    DMAT3_4(48, "DoubleMatrix3x3[4]", 32, 384, 96, 32, RowColumn.ROW),
    DMAT3x2_4(49, "DoubleMatrix3x2[4]", 32, 256, 64, 32, RowColumn.ROW),
    DMAT3x4_4(50, "DoubleMatrix3x4[4]", 32, 512, 128, 32, RowColumn.ROW),
    DMAT4_4(51, "DoubleMatrix4x4[4]", 32, 512, 128, 32, RowColumn.ROW),
    DMAT4x2_4(52, "DoubleMatrix4x2[4]", 32, 256, 64, 32, RowColumn.ROW),
    DMAT4x3_4(53, "DoubleMatrix4x3[4]", 32, 384, 96, 32, RowColumn.ROW),
    MAT2_4_COLUMN(54, "Matrix2x2[4]_column", 16, 128, 32, 16, RowColumn.COLUMN),
    MAT2x3_4_COLUMN(55, "Matrix2x3[4]_column", 16, 128, 32, 16, RowColumn.COLUMN),
    MAT2x4_4_COLUMN(56, "Matrix2x4[4]_column", 16, 128, 32, 16, RowColumn.COLUMN),
    MAT3_4_COLUMN(57, "Matrix3x3[4]_column", 16, 192, 48, 16, RowColumn.COLUMN),
    MAT3x2_4_COLUMN(58, "Matrix3x2[4]_column", 16, 192, 48, 16, RowColumn.COLUMN),
    MAT3x4_4_COLUMN(59, "Matrix3x4[4]_column", 16, 192, 48, 16, RowColumn.COLUMN),
    MAT4_4_COLUMN(60, "Matrix4x4[4]_column", 16, 256, 64, 16, RowColumn.COLUMN),
    MAT4x2_4_COLUMN(61, "Matrix4x2[4]_column", 16, 256, 64, 16, RowColumn.COLUMN),
    MAT4x3_4_COLUMN(62, "Matrix4x3[4]_column", 16, 256, 64, 16, RowColumn.COLUMN),
    DMAT2_4_COLUMN(63, "DoubleMatrix2x2[4]_column", 16, 128, 32, 16, RowColumn.COLUMN),
    DMAT2x3_4_COLUMN(64, "DoubleMatrix2x3[4]_column", 32, 256, 64, 32, RowColumn.COLUMN),
    DMAT2x4_4_COLUMN(65, "DoubleMatrix2x4[4]_column", 32, 256, 64, 32, RowColumn.COLUMN),
    DMAT3_4_COLUMN(66, "DoubleMatrix3x3[4]_column", 32, 384, 96, 32, RowColumn.COLUMN),
    DMAT3x2_4_COLUMN(67, "DoubleMatrix3x2[4]_column", 16, 192, 48, 16, RowColumn.COLUMN),
    DMAT3x4_4_COLUMN(68, "DoubleMatrix3x4[4]_column", 32, 384, 96, 32, RowColumn.COLUMN),
    DMAT4_4_COLUMN(69, "DoubleMatrix4x4[4]_column", 32, 512, 128, 32, RowColumn.COLUMN),
    DMAT4x2_4_COLUMN(70, "DoubleMatrix4x2[4]_column", 16, 256, 64, 16, RowColumn.COLUMN),
    DMAT4x3_4_COLUMN(71, "DoubleMatrix4x3[4]_column", 32, 512, 128, 32, RowColumn.COLUMN);

    final int id;
    final String name;
    final int alignment;
    final int byteSize;
    final int arrayStride;
    final int matrixStride;
    final RowColumn matrixOrder;

    STD140UniformBlockType(int id, String name, int alignment, int byteSize, int arrayStride, int matrixStride, RowColumn matrixOrder) {
        this.id = id;
        this.name = name;
        this.alignment = alignment;
        this.byteSize = byteSize;
        this.arrayStride = arrayStride;
        this.matrixStride = matrixStride;
        this.matrixOrder = matrixOrder;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    public int getAlignment() {
        return alignment;
    }

    public int getByteSize() {
        return byteSize;
    }

    public int getArrayStride() {
        return arrayStride;
    }

    public int getMatrixStride() {
        return matrixStride;
    }

    public RowColumn getMatrixOrder() {
        return matrixOrder;
    }
}

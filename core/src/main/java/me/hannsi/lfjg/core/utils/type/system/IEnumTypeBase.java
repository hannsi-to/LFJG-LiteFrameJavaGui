package me.hannsi.lfjg.core.utils.type.system;

/**
 * Interface representing a base type for enumerations.
 */
public interface IEnumTypeBase {

    /**
     * Gets the unique identifier of the enumeration type.
     *
     * @return the unique identifier of the enumeration type
     */
    int getId();

    /**
     * Gets the name of the enumeration type.
     *
     * @return the name of the enumeration type
     */
    String getName();
}
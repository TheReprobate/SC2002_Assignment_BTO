package btosystem.classes.enums;

/**
 * Represents the different types of neighborhood in Singapore.
 */
public enum Neighborhood {
    ANG_MO_KIO,
    BEDOK,
    BISHAN,
    BUKIT_BATOK,
    BUKIT_MERAH,
    BUKIT_PANJANG,
    BUKIT_TIMAH,
    CENTRAL_AREA,
    CHOA_CHU_KANG,
    CLEMENTI,
    GEYLANG,
    HOUGANG,
    HOLLAND_VILLAGE,
    JURONG,
    KALLANG,
    KATONG,
    MARINE_PARADE,
    PASIR_RIS,
    PUNGGOL,
    QUEENSTOWN,
    SEMBAWANG,
    SENGKANG,
    SERANGOON,
    SIMEI,
    TAMPINES,
    TANJONG_PAGAR,
    TOA_PAYOH,
    WOODLANDS;

    /**
     * Returns a human-readable formatted string representation of the Neighbourhood.
     *
     * @return the formatted string representation
     */
    @Override
    public String toString() {
        return this.name().replace("_", " ");
    }
}

package security.domain;

/**
 * This is an enum of different local weaver result types, used to determine the type of data obtained from local
 * weavers.
 */
public enum LocalWeaverResultType {
    DATA_MODEL("dataModel"), SECURITY("security"), FLOW_STRUCTURE("flowStructure"), BYTE_CODE_FLOW_STRUCTURE("byteCodeFlowStructure");

    /**
     * This is the type of result, stored as a string.
     */
    private String resultType;

    LocalWeaverResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getResultType(){
        return this.resultType;
    }
}

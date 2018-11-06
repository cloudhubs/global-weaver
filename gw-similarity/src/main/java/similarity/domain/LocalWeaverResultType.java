package similarity.domain;

public enum LocalWeaverResultType {
    DATA_MODEL("dataModel"), SECURITY("security"), FLOW_STRUCTURE("flowStructure"), BYTE_CODE_FLOW_STRUCTURE("byteCodeFlowStructure");

    private String resultType;

    LocalWeaverResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getResultType(){
        return this.resultType;
    }
}

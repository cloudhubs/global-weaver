package harvester.domain;

public class LocalWeaverResult {
    private int id;
    private LocalWeaverResultType type;
    private String data;
    private int moduleId;
    private String moduleName;

    public LocalWeaverResult() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalWeaverResultType getType() {
        return type;
    }

    public void setType(LocalWeaverResultType type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
}

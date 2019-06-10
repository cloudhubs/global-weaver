# Harvester


## Init command

URL:
```aidl
http://localhost:7002/analyze
```

Method:
```aidl
POST
```

REQUEST:
```aidl
{
    "request":{
        "pathToCompiledMicroservices":"/Users/svacina/git/advseproject/user-management",
        "organizationName":"advseproject",
        "organizationPath":"/edu/baylor/ecs/seer/",
        "localWeaverHttp":"http://localhost:8080",
        "gwEntityHttp":"http://localhost:7000/data/entityModel",
        "gwGeneratorHttp":"http://localhost:7001/generator/uml",
        "productsDirectory":"/Users/svacina/dev/cerny/gw-outputs/"
    }
}
```
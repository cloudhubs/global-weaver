
## 2019-06-10

### Git Repositories

* common
* local-weaver
* global-weaver

Pull all three repositories

Check out the "domain-model" branch in local-weaver

```
> cd <root-of-local-weaver>
> git checkout "domain-model"
```

Check out the "entity-data" branch in global-weaver

```
> cd <root-of-global-weaver>
> git checkout "entity-data"
```

### Maven Configuration

#### Install common

```
> cd <root-of-common>
> mvn clean install
```

#### Install local-weaver

```
> cd <root-of-local-weaver>
> mvn clean install
```

### Building global-weaver

#### Build gw-data
```
> cd <root-of-global-weaver>
> cd gw-data
> mvn clean install
```

#### Build gw-generator
```
> cd <root-of-global-weaver>
> cd gw-generator
> mvn clean install
```

#### Build gw-harvester
```
> cd <root-of-global-weaver>
> cd gw-harvester
> mvn clean install
```

#### Build gw-security
```
> cd <root-of-global-weaver>
> cd gw-security
> mvn clean install
```

#### Build gw-similarity
```
> cd <root-of-global-weaver>
> cd gw-similarity
> mvn clean install
```
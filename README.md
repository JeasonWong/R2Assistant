### R2Assistant


generate R2.java's fields rapidly.

#### USAGE

##### add the plugin

```groovy
apply plugin: 'me.wangyuwei.r2assistant'
```

##### run command

if you want to scan all of sub modules,
run `./gradlew sweepR2`

if you want to scan specified module,
run `./gradlew sweepR2 -PmoduleName=${subModuleName}`

#### TODO

- ~~support add parameter to scan specified module.~~
- support @BindViews.
- support @OnClick.
- ...



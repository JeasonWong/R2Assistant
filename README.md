### R2Assistant

when you use butterknife in sub module, once add a new resId in xml, you must rebuild your project for generating R2.java, it costs too much time.

R2Assistant is a tool to generate R2.java's fields rapidly.


#### SHOW

![r2assistant.gif](http://ojafgwnc4.bkt.clouddn.com/r2assistant.gif)

#### USAGE

##### add the plugin in app's build.gradle

```groovy
apply plugin: 'me.wangyuwei.r2assistant'

buildscript {
    repositories {
        maven {
            url 'https://dl.bintray.com/wangyuwei/maven'
        }
    }
    dependencies {
        classpath 'me.wangyuwei:r2assistant-plugin:1.0.0'
    }
}
```

##### run command

if you want to scan all of sub modules,
run `./gradlew sweepR2`

if you want to scan specified module,
run `./gradlew sweepR2 -PmoduleName=${subModuleName}`

#### TODO

- ~~support add parameter to scan specified module.~~
- support R2.string.
- support R2.color.
- ...



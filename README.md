### R2Assistant

when you use butterknife in sub module, once add a new resId in xml, you must rebuild your project for generating R2.java, it costs too much time.

R2Assistant is a tool to generate R2.java's fields rapidly.

[中文介绍](http://www.wangyuwei.me/2017/12/02/%E4%B8%80%E4%B8%AA%E5%BF%AB%E9%80%9F%E7%94%9F%E6%88%90R2-java%E4%B8%ADfields%E7%9A%84%E6%8F%92%E4%BB%B6/)

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


#### QQ Group

479729938

#### **License**

```license
Copyright [2017] [JeasonWong of copyright owner]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```


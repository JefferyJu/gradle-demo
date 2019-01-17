# Gradle学习笔记（二）

### 一、打包

#### 1、打jar包

> **build.gradle** 

```
plugins {
    id 'java'
}

group 'com.imooc.gradle'
version '1.0-SNAPSHOT'

sourceCompatibility = 1.8

repositories {
    mavenCentral()
}

dependencies {
    testCompile group: 'junit', name: 'junit', version: '4.12'
}
```

![](https://img-blog.csdnimg.cn/20190117104634617.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0plZmZlcnlfSnU=,size_16,color_FFFFFF,t_70)

#### 2、打war包

> 在低版本的Gradle中写法：

```
apple plugin: 'war'
```

> 在高版本的Gradle中写法：

```Grovvy
plugins {
    id 'java'
    id 'war'
}
```

![](https://img-blog.csdnimg.cn/20190117104719923.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0plZmZlcnlfSnU=,size_16,color_FFFFFF,t_70)



### 二、构建脚本概要

#### 1、构建块

> Gradle构建中的两个基本概念是**项目（Project）**和**任务（Task）**,每个构建至少包含一个项目，项目中包含一个或多个任务。在多项目构建中，一个项目可以依赖于其他项目；类似的，任务可以形成一个依赖关系图来确保他们的执行顺序。

#### 2、项目（project）

> 一个项目代表一个正在构建的组件（比如一个jar文件），当构建启动后，Gradle会基于build.gradle实例化一个**org.gradle.api.Project**类，并且能够通过project变量使其隐式可用。

```
group 、name、version
```

```
apply、dependencies、repositories、task
```

```
属性的其他配置方式：ext、gradle.properties
```

> 例如： 隐式可用 

```
project.group='com.imooc.gradle'   
```

```
group 'com.imooc.gradle'  //隐式可用
```



#### 3、任务（task）

> 任务对应org.gradle.api.Task。主要包括任务动作和任务依赖。任务动作定义了一个最小的工作单元。可用定义依赖于其他任务、动作序列和执行条件。

```
dependsOn
```

```
doFirst、doLast<<
```

#### 4、自定义任务

```
def createDir = {
    path ->
        File dir = new File(path)
        if (!dir.exists()) {
            dir.mkdirs()
        }
}

task makeJavaDir(){
    def paths = ['src/main/java', 'src/main/resource','src/test/java','src/test/resource']
    doFirst {
        paths.forEach(createDir)
    }
}

task makeWebDir(){
    dependsOn 'makeJavaDir'
    def paths = ['src/main/webapp', 'src/test/webapp']
    doLast {
        paths.forEach(createDir)
    }
}
```

![](https://img-blog.csdnimg.cn/20190117104758515.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0plZmZlcnlfSnU=,size_16,color_FFFFFF,t_70)

#### 5、构建生命周期

![](https://img-blog.csdnimg.cn/20190117104825192.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0plZmZlcnlfSnU=,size_16,color_FFFFFF,t_70)

![](https://img-blog.csdnimg.cn/20190117104853622.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0plZmZlcnlfSnU=,size_16,color_FFFFFF,t_70)

### 三、依赖管理

> 几乎所有基于JVM的软件项目都需要依赖外部类库来重现现有的功能。自动化的依赖管理可以明确依赖的版本，可以解决因传递性依赖带来的版本冲突。

#### 1、工件坐标

```
group 、name、version    //GroupId、ArtifactId、Version
```

#### 2、常用仓库

- mavenLocal //本机通过maven使用的仓库
- mavenCentral //公共仓库
- jcenter //公共仓库
- 自定义maven仓库
- 文件仓库（不建议使用）

#### 3、依赖的传递性

> B依赖Ａ，如果Ｃ依赖Ｂ，那么Ｃ依赖A

#### 4、自动化依赖管理

![](https://img-blog.csdnimg.cn/2019011710492817.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0plZmZlcnlfSnU=,size_16,color_FFFFFF,t_70)

#### 5、依赖阶段配置

- compile
- runtime
- testCompile
- testRuntime  



![](https://img-blog.csdnimg.cn/20190117104959122.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0plZmZlcnlfSnU=,size_16,color_FFFFFF,t_70)

```
repositories { 
	// 按顺序查找
	maven{
        url 'maven私服地址'
    }
    mavenLocal() //本地的maven仓库
    mavenCentral()
}

dependencies {
    compile 'ch.qos.logback:logback-classic:1.2.1'
    testCompile group: 'junit', name: 'junit', version: '4.12'
}
```

#### 6、依赖管理

##### 6.1 解决冲突

1. 查看依赖报告

2. 排除传递性依赖

    > **默认处理策略： Gradle会自动帮我们选择依赖中最高版本的jar包**

    **修改默认的解决策略：冲突时构建失败**

    ```
    configurations.all{
        resolutionStrategy{
            // 修改gradle不处理版本冲突
            failOnVersionConflict()
        }
    }
    ```

    **排除传递性依赖**

    ```
    compile('org.hibernate:hibernate-core:3.6.3.Final'){
         exclude group: "org.slf4j", module: "slf4j-api"
        //transitive = false  排除所有传递性依赖，非常少见
    }
    ```

    3.强制指定一个版本


```
configurations.all {
    resolutionStrategy{
        force 'org.slf4j:slf4j-api:1.7.24'
    }
}
```

##### 6.2  版本冲突举例

![/](https://img-blog.csdnimg.cn/20190117105024596.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0plZmZlcnlfSnU=,size_16,color_FFFFFF,t_70)

###### 6.2.1  依赖hibernate

```
dependencies {
    compile 'org.hibernate:hibernate-core:3.6.3.Final'
//    compile 'ch.qos.logback:logback-classic:1.2.1'
    testCompile group: 'junit', name: 'junit', version: '4.12'
}
```

![](https://img-blog.csdnimg.cn/20190117105050491.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0plZmZlcnlfSnU=,size_16,color_FFFFFF,t_70)



###### 6.2.2  依赖logback

```
dependencies {
//    compile 'org.hibernate:hibernate-core:3.6.3.Final'
    compile 'ch.qos.logback:logback-classic:1.2.1'
    testCompile group: 'junit', name: 'junit', version: '4.12'
}
```

![](https://img-blog.csdnimg.cn/20190117105407517.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0plZmZlcnlfSnU=,size_16,color_FFFFFF,t_70)

###### 6.2.3 依赖hibernate与logback

```
dependencies {
    compile 'org.hibernate:hibernate-core:3.6.3.Final'
    compile 'ch.qos.logback:logback-classic:1.2.1'
    testCompile group: 'junit', name: 'junit', version: '4.12'
}
```

![](https://img-blog.csdnimg.cn/20190117105653312.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0plZmZlcnlfSnU=,size_16,color_FFFFFF,t_70)

###### 6.2.4  查看依赖

![](https://img-blog.csdnimg.cn/2019011711001139.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0plZmZlcnlfSnU=,size_16,color_FFFFFF,t_70)

###### 6.2.5 修改策略

-   **默认策略： Gradle会自动帮我们选择依赖中最高版本的jar包**

-    **版本冲突时构建失败**

```
configurations.all{
    resolutionStrategy{
        // 修改gradle不处理版本冲突
        failOnVersionConflict()
    }
}
```

![](https://img-blog.csdnimg.cn/20190117110943587.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0plZmZlcnlfSnU=,size_16,color_FFFFFF,t_70)

-   **排除依赖**

    >   关闭默认策略，添加排除依赖。 
    >   如下，我们直接排除hibernate的依赖slf4j-api，slf4j-api的jar仅由logback提供。

```
dependencies {
    compile('org.hibernate:hibernate-core:3.6.3.Final') {
    	// module是坐标里面的name
        exclude group: "org.slf4j", module: "slf4j-api"
    }
    compile 'ch.qos.logback:logback-classic:1.2.1'
    testCompile group: 'junit', name: 'junit', version: '4.12'
}
```

![](https://img-blog.csdnimg.cn/20190117111859443.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0plZmZlcnlfSnU=,size_16,color_FFFFFF,t_70)

-   **强制指定一个版本**

    ```
    configurations.all {
        resolutionStrategy {
            // 修改gradle不处理版本冲突
            failOnVersionConflict()
            // 强制指定一个版本
            force 'org.slf4j:slf4j-api:1.7.24'
        }
    }
    ```

    ![](https://img-blog.csdnimg.cn/20190117112254199.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0plZmZlcnlfSnU=,size_16,color_FFFFFF,t_70)

![](https://img-blog.csdnimg.cn/20190117112406304.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0plZmZlcnlfSnU=,size_16,color_FFFFFF,t_70)

### 四、多项目构建

#### 1、项目模块化

>   在企业项目中，包层次和类关系比较复杂，把代码拆分成模块通常是最佳实践，这需要你清晰的划分功能的边界，比如把业务逻辑和数据持久化拆分开来。项目符合**高内聚低耦合**时，模块化就变得很容易，这是一条非常好的软件开发实践。
>
>   [^内聚]: ：每个模块尽可能独立完成自己的功能，不依赖于模块外部的代码。  
>   [^耦合]: ：模块与模块之间接口的复杂程度，模块之间联系越复杂耦合度越高，牵一发而动全身。

#### 2、Todo模块依赖关系

![](https://img-blog.csdnimg.cn/20190117113726230.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0plZmZlcnlfSnU=,size_16,color_FFFFFF,t_70)

3、配置子项目

-   所有项目应用Java插件

-   web子项目打包成war

-   所有项目添加logback日志功能

-   统一配置公共属性

    

4、项目范围

![](https://img-blog.csdnimg.cn/20190117114026292.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0plZmZlcnlfSnU=,size_16,color_FFFFFF,t_70)

>   **settings.gradle**

```
rootProject.name = 'gradledemo'
include 'model'
include 'repository'
include 'web'
```

>   **gradle.properties**

```
group=com.imooc.gradle   
version=1.0-SNAPSHOT
```

>   **build.gradle**

```
allprojects {
    apply plugin: 'java'
    sourceCompatibility = 1.8
}


subprojects {
    repositories {
        mavenCentral()
    }
    dependencies {
        compile 'org.hibernate:hibernate-core:3.6.3.Final'
        compile 'ch.qos.logback:logback-classic:1.2.1'
        testCompile group: 'junit', name: 'junit', version: '4.12'
    }
}

repositories {
    mavenCentral()
}
```

>   **web.gradle**

```
apply plugin: 'war'

dependencies {
    compile project(":repository")
}
```

>   **repository.gradle**

```
dependencies {
    compile project(":model")
}
```

>   **model.gradle**

```
dependencies {
}
```

### 五、Gradle测试

#### 1、流程

![](https://img-blog.csdnimg.cn/20190117221110680.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0plZmZlcnlfSnU=,size_16,color_FFFFFF,t_70)

#### 2、测试发现

-   任何继承自 junit.framework.TestCase 或者 groovy.util.GroovyTestCase的类
-   任何被@RunWith注解的类
-   任何至少包含一个被@Test的类

#### 3、测试案例

![](https://img-blog.csdnimg.cn/20190117222247843.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0plZmZlcnlfSnU=,size_16,color_FFFFFF,t_70)

![](https://img-blog.csdnimg.cn/20190117222339515.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0plZmZlcnlfSnU=,size_16,color_FFFFFF,t_70)

### 六、发布到本地和远程仓库

#### 1、发布流程

![](https://img-blog.csdnimg.cn/20190117222736251.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0plZmZlcnlfSnU=,size_16,color_FFFFFF,t_70)

#### 2、实践

>   **build.gradle**

```
allprojects {
    apply plugin: 'java'
    sourceCompatibility = 1.8

    apply plugin: 'maven-publish'

    publishing {
        publications {
            myLibrary(MavenPublication) {
                from components.java
            }
        }

        repositories {
            maven {
                name = 'myRepo'
                url = "Maven私服地址"
            }
        }
    }
}


subprojects {
    repositories {
        mavenCentral()
    }
    dependencies {
        compile 'org.hibernate:hibernate-core:3.6.3.Final'
        compile 'ch.qos.logback:logback-classic:1.2.1'
        testCompile group: 'junit', name: 'junit', version: '4.12'
    }
}

repositories {
    mavenCentral()
}
```

![](https://img-blog.csdnimg.cn/20190117230105101.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0plZmZlcnlfSnU=,size_16,color_FFFFFF,t_70)

**发布到本地maven仓库时报错：**

>   **nvalid publication 'myLibrary': groupId is not a valid Maven identifier ([A-Za-z0-9_\-.]+).**

**解决办法：**

>   gradle.properties中 group = 'com.imooc.gradle' 的问题 
>
>   group是配置文件,属性值不需要单引号的.改为 group= com.imooc.gradle
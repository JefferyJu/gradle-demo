# Gradle学习笔记（一）

### 一、主流构建工具

- ant
- maven
- gradle

### 二、Gradle是什么

> 一个开源的项目自动化构建工具,建立在Apache Ant 和Apache Maven概念的基础上,并引入了基于Groovy的特定领域语言(DSL),而不再使用XML形式管理构建脚本

### 三、Gradle的安装

1. 确认安装了JDK (cmd中执行,执行完关闭,并在第四步重新打开CMD)

   ```
   java -version
   ```

2. 官网下载Gradle 

   [Gradle]: https://gradle.org/install	"Gradle官网"

3. 配置环境变量

   ```
   GRADLE_HOME = D:\InstalledSoftware\Java\gradle-4.10.3
   Path = %GRADLE_HOME%\bin
   ```

4. 验证

   ```
   gradle -v
   ```

### 四、Groovy是什么

> ​	Groovy是用于Java虚拟机的一种敏捷的动态语言,他是一种成熟的面向对象编程语言,既可以用于面向对象编程,又可用作纯粹的脚本语言 . 使用该种语言不必编写过多的代码, 同时又具有闭包和动态语言的其他特性 .

与Jva相比较有一下特点:

1. Groovy完全兼容Java语法
2. 分号是可选的
3. 类和方法默认是public的
4. 编辑器自动给属性添加getter/setter方法
5. 属性可以直接用点号获取
6. 最后一个表达式的值会被作为返回值
7. == 等于equals(), 不会有NPE

```Groovy
class ProjectVersion {
    private int major
    private int minor

    ProjectVersion(int major, int minor) {
        this.major = major
        this.minor = minor
    }

    int getMajor() {
        return major
    }

    void setMajor(int major) {
        this.major = major
    }

    int getMinor() {
        return minor
    }

    void setMinor(int minor) {
        this.minor = minor
    }
}

ProjectVersion v1 = new ProjectVersion(1, 1)
println v1.minor

ProjectVersion v2 = null

println v2 == v1
```

### 五、Groovy高效特性

#### 1、可选的类型定义(自动识别)

```
def version = 1
```

#### 2、assert断言

```
assert version == 1
```

#### 3、括号是可选的

```
println(version)
//等同于
println version
```

#### 4、字符串

```
def s1 = 'gradle'

def s2 = "gradle version is ${version}"

def s3 = '''gradle
version'''

println(s1)
println(s2)
println(s3)
```

#### 5、集合api

```
// list
def buildTools = ['ant', 'maven']
// 追加
buildTools << 'gradle'

assert buildTools.getClass() == ArrayList

assert buildTools.size() == 3

//map

def buildYears = ['ant': 2000, 'maven': 2004]
buildYears.gradle = 2009

println buildYears.ant
println(buildYears['gradle'])
println(buildYears.getClass())

```

#### 6、闭包

```
def c1 = {
    v ->
        println(v)
}

def c2 = {
    println 'hello'
}

def method1(Closure closure) {
    closure 'param'
}

def method2(Closure closure) {
    closure()
}


method1(c1)
method2(c2)
```

### 六、Groovy基础知识

```
//构建脚本中默认都是有个Project实例
apply plugin: 'java'

version = '0.1'

repositories{
    mavenCentral()
}

dependencies{
    compile 'commons-codec:commons-codec:1.6'
}
```






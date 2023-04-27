# assertThrows
## 本章要点
1. dsfa


当测试用例对应是抛出异常的逻辑时，就不可以用assertEquals()来进行相关的断言。这时候需要有专门处理代码抛出异常的断言，JUnit5框架也专门提供了异常相关的断言。


## assertThrows
`assertThrows()`去判断业务代码抛出的自定义异常。

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230223182234.png)


### 参数

常用的参数传入有三个，`Class<T>`、`Executable`、`String`。

#### `Class<T>`
期望值。希望当前方法中业务逻辑运行的结果。

#### actual
实际值。当前方法在执行完业务逻辑后的实际结果。

#### message
如果期望值和实际值不相同，即断言失败对应显示的提示内容。

#### messageSupplier
生产者接口。

对应的期望值变成了异常类型「`Class<T>`」的期望值，实际的值也是抛出异常的实际值「`Executable`」，同样如果想要断言失败的情况下显示自定义的说明，则加上第3个参数，即断言失败说明「`message`」。

- `assertThrows(Class<T> expectedType, Executable executable, String message)`  
- 断言提供的业务逻辑的执行是否会引发预期类型的​​异常并返回异常对象。
    - `expectedType`
        - 报错的类型的类
    - `executable`
        - 执行的业务流 
    - `message`
        - 断言失败的时候显示说明




